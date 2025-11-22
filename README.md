# WizBiz Card Game (Prototype)

## Overview
Small Java/Maven prototype of a turn-based wizard card game. Player and Enemy draw and play `SpellCard`s from a shared `Deck`. Core responsibilities are split across a simple state machine (`BattleState`) and a `GameController` that coordinates model and UI.

## Prerequisites
1. JDK 17+ (or the project's configured Java version).
2. Maven.
3. JavaFX on the classpath (IDE or `pom.xml` must provide JavaFX).

Run in IntelliJ on Windows by running the `main` in `src/main/java/com/wizbiz/wizard_card_game/GameUI.java` or via Maven if configured for JavaFX.

## How to run (quick)
1. Open project in IntelliJ IDEA (2024.3.2.2).
2. Run `src/main/java/com/wizbiz/wizard_card_game/GameUI.java` main.
3. Or run with Maven if `pom.xml` configured for JavaFX.

## Project structure (key files)
- `src/main/java/com/wizbiz/wizard_card_game/GameUI.java` — JavaFX UI and entry point.
- `src/main/java/com/wizbiz/wizard_card_game/GameController.java` — Singleton controller; game lifecycle, actions, state transitions, logging, and UI updates.
- `src/main/java/com/wizbiz/wizard_card_game/BattleState.java` — State interface for turn states.
- `src/main/java/com/wizbiz/wizard_card_game/PlayerTurnState.java` — Player turn behavior (casts and transitions).
- `src/main/java/com/wizbiz/wizard_card_game/EnemyTurnState.java` — Enemy turn behavior (AI chooses spell and plays).
- `src/main/java/com/wizbiz/wizard_card_game/Actor.java` — Base actor (HP, MP, hand, status effects).
- `src/main/java/com/wizbiz/wizard_card_game/Player.java` and `src/main/java/com/wizbiz/wizard_card_game/Enemy.java` — concrete actors.
- `src/main/java/com/wizbiz/wizard_card_game/Deck.java` and `src/main/java/com/wizbiz/wizard_card_game/DeckIterator.java` — shared deck and simple iterator.
- `src/main/java/com/wizbiz/wizard_card_game/SpellCard.java` — wrapper around `Spell`.
- `src/main/java/com/wizbiz/wizard_card_game/SpellCardFactory.java` — factory to create cards.
- `src/main/java/com/wizbiz/wizard_card_game/spells/Spell.java` — base spell class (mana check + `applyEffect`).
- `src/main/java/com/wizbiz/wizard_card_game/spells/Fireball.java` — example spell (damage + `BurnEffect`).
- `src/main/java/com/wizbiz/wizard_card_game/statuseffects/BurnEffect.java` — example status effect.
- `src/main/java/com/wizbiz/wizard_card_game/EnemyAI.java` — simple random spell choice.

## Gameplay / Flow (brief)
1. `GameController.startGame()` initializes `Player`, `Enemy`, `Deck` and performs initial draws.
2. Controller sets initial state to `PlayerTurnState`.
3. Turn states:
    - `PlayerTurnState.enter()` applies start-of-turn effects and draws. Player can click UI buttons to cast; `castSpell` calls `gc.playCard(...)` then transitions.
    - `EnemyTurnState.enter()` applies start-of-turn effects, enemy draws, AI chooses a card name and `playCard` executes it.
4. `playCard` finds a matching `SpellCard` in actor's hand, removes it, logs, and calls `Spell.cast(...)`.
5. `Spell.cast` checks mana (`hasMp`) and calls `applyEffect` to apply concrete effects (damage, status).

## Extending the project
- Add a new spell:
    1. Create new class under `src/main/java/com/wizbiz/wizard_card_game/spells/` extending `Spell`.
    2. Implement `applyEffect(Actor caster, Actor target)`.
    3. Register the spell in `src/main/java/com/wizbiz/wizard_card_game/SpellCardFactory.java` by adding a `case` with the new name.
    4. Optionally add initial copies in `Deck()` by updating `SpellCardFactory.createMultiple(...)` usage.

- Add a new status effect:
    1. Create a class in `src/main/java/com/wizbiz/wizard_card_game/statuseffects/` implementing `StatusEffect` (must implement `onTurnStart`, `isExpired`, `refresh`).
    2. Use `Actor.addEffect(...)` to apply. The `addEffect` logic refreshes existing effects of the same class.

- Change deck composition:
    - Edit `src/main/java/com/wizbiz/wizard_card_game/Deck.java` constructor to add other spell names via `SpellCardFactory.createMultiple(name, copies)`.

## Important design notes
- Single shared `DeckIterator` is used by both actors so drawing reduces the same pool.
- `BattleState` encapsulates turn logic and transitions; `GameController.changeState(...)` triggers entry actions.
- `Actor.addEffect` refreshes same-class effects instead of stacking.
- `Spell.cast` will silently do nothing if caster lacks mana (no error thrown).

## UI notes
- `GameUI` maps each `SpellCard` in the player's hand to a button; clicking calls `GameController.castSpell(...)`.
- `GameController` updates `actionLog` and calls `GameUI.refreshUI()` when `ui` is set.

## TODO / Known limitations
1. No persistence or saving.
2. No proper mana regeneration beyond `Actor.startTurnEffects` increment (currently \+1 MP each turn).
3. Enemy AI is trivial and assumes Enemy has matching cards in hand.
4. No validation or error messages shown to player when a cast fails due to missing mana or missing card (only logged).

## Where to look first when editing
- Gameplay rules: `GameController.java`, `Spell.java`, `Actor.java`.
- Add spells: `spells` package + `SpellCardFactory.java`.
- UI changes: `GameUI.java`.