package com.wizbiz.wizard_card_game;

import com.wizbiz.wizard_card_game.commands.Command;

import java.util.ArrayList;
import java.util.List;

// SINGLETON - Central game manager using Observer, Command, and State patterns
public class GameController {

    // SINGLETON - only one game instance
    private static GameController instance;

    // STATE PATTERN - current turn
    private BattleState currentState;

    private Player player;
    private Enemy enemy;
    private Deck deck;
    private DeckIterator deckIterator;
    private GameUI ui;

    // OBSERVER PATTERN - auto-notify observers of changes
    private List<GameObserver> observers = new ArrayList<>();

    private StringBuilder actionLog = new StringBuilder();

    // SINGLETON - private constructor
    private GameController() {}

    // SINGLETON - get instance (creates if needed)
    public static GameController getInstance() {
        if (instance == null) {
            synchronized (GameController.class) {
                if (instance == null) {
                    instance = new GameController();
                }
            }
        }
        return instance;
    }

    public void setUI(GameUI ui) {
        this.ui = ui;
    }

    // OBSERVER PATTERN - register/notify observers

    public void addObserver(GameObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }

    // COMMAND PATTERN - execute action commands

    public void executeCommand(Command command) {
        logAction("→ " + command.getDescription());
        command.execute();
    }

    public void startGameWithCustomizations(Player customPlayer, Enemy customEnemy) {
        player = customPlayer;
        enemy = customEnemy;

        deck = new Deck();
        deckIterator = deck.iterator();

        player.drawCards(deckIterator, 5);
        enemy.drawCards(deckIterator, 5);

        logAction("Game started.");
        logAction(player.getName() + " vs " + enemy.getName() + "!");
        logAction("Both wizards drew initial hands.");

        changeState(new PlayerTurnState());
    }

    //============================================
    // Methods used by UI
    //============================================

    /**
     * Called when player clicks a spell card.
     * Delegates to current state to handle the action.
     */
    public void castSpell(String spellName) {
        currentState.castSpell(spellName);
    }

    // Getters for UI to display current game state
    public Player getPlayer() { return player; }
    public Enemy getEnemy() { return enemy; }

    /**
     * Logs an action to the battle log and updates the UI.
     */
    public void logAction(String text) {
        actionLog.append(text).append("\n");
        // Update UI immediately if present
        if (ui != null) ui.updateLog(actionLog.toString());
    }

    //============================================
    // Methods used by Actor classes
    //============================================

    /**
     * Draws cards for an actor (player or enemy) from the deck.
     */
    public void drawForActor(Actor actor, int count) {
        if (deckIterator == null) return;
        actor.drawCards(deckIterator, count);
        logAction((actor instanceof Player ? "Player" : "Enemy") + " drew " + count + " card(s).");
        notifyObservers(); // Observer Pattern - notify all observers
    }

    /**
     * Attempts to play a card from an actor's hand against a target.
     * Searches hand for matching card, removes it, and casts the spell.
     * Returns true if a matching card was found and played successfully.
     */
    public boolean playCard(Actor actor, String cardName, Actor target) {
        SpellCard playedCard = null;
        for (SpellCard c : actor.getHand()) {
            if (c.getName().equals(cardName)) {
                playedCard = c;
                break;
            }
        }
        if (playedCard == null) {
            logAction((actor instanceof Player ? "Player" : "Enemy") + " attempted to play: " + cardName + " (no matching card in hand)");
            return false;
        }

        actor.getHand().remove(playedCard);
        logAction((actor instanceof Player ? "Player" : "Enemy") + " played: " + playedCard.getName());
        playedCard.getSpell().cast(actor, target);
        notifyObservers();
        return true;
    }

    // STATE PATTERN - manage turn transitions

    public void changeState(BattleState state) {
        currentState = state;
        currentState.enter();
    }

    public BattleState getCurrentState() {
        return currentState;
    }
}
