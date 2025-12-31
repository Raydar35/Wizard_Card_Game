# Wizard Card Game (Object-Oriented Design Project)

A turn-based wizard card game implemented in Java using JavaFX as a final project for an object-oriented design course. The game features player-versus-AI gameplay and allows both the player and enemy AI to be customized. This project emphasizes clean object-oriented architecture, encapsulation, extensibility, and game-state management through the use of established design patterns and SOLID principles.

## Technologies

- Java
- JavaFX
- Maven
- IntelliJ IDEA
- GitHub

## Features

#### Gameplay Mechanics

- Turn-based wizard duels using cards with player-versus-AI enemy.
- Shared deck drawing; each actor draws from the same deck and plays a `SpellCard`.
- Wide variety of spells (Fireball, Meteor, Ice Blast, Lightning, Heal, Shield, Posion Cloud, Drain, Thunderbolt, Curse, Regeneration).
- Status effects system (Burn, Freeze, Poison, Regen, Shield, Stun, Weaken) that persist across turns and can be refreshed.
- Mana economy with per turn mana gain and different spell cost.

#### UI & Experience

- Character customization of player (face, hat, robe color, staff) with automatic enemy generation.
- Visual feedback for both hp (health) and mp (mana) as well as spell casting effects.
- Logging of game state and actions with an accessible battle log.
- Spell cards, when hovered, show detailed effects of the spell.
- Victory and defeat screens with the option to continue or retry.

#### AI and Progression

- Simple `EnemyAI` that attempts to select a random playable card and falls back to the cheapest card in hand if it fails.
- Difficulty scaling (enemy hp/mp bonuses) based on player win streak.

## Key Design Patterns

**Singleton:** Ensures only one instance of `GameController` is running.

**Factory:** Allows for easy and central creation of one or multiple spell cards (`SpellCardFactory`).

**Iterator:** Handles deck traversal (`DeckIterator` / `DeckIteratorImpl`).

**Observer:** Updates the UI automatically (`GameObserver`).

**State:** Manages turn flow (`PlayerTurnState` / `EnemyTurnState`).

**Command:** Used for actions (draw, cast, end turn) for undo/redo and logging purposes.

## Collaboration & Contributions

This project was developed in collaboration with two other students as part of an object-oriented design course. The responsibilities below show my individual contributions.

#### My Contributions

- **Project Foundation:** Created the initial project skeleton and architectural framework.
- **Game Flow:** Developed the core game loop and turn-based logic via the `GameController` and state pattern (`PlayerTurnState`, `EnemyTurnState`, `BattleState` interface).
- **Actor System:** Designed the `Actor` abstraction and implemented concrete subclasses (`Player` & `Enemy`).
- **Deck System:** Built the deck system with an iterator pattern (`Deck` & `DeckIterator`) to manage cards efficiently.
- **Spells & Status Effects:** Developed the `Spell` abstract class, `StatusEffect` interface, and `SpellCard` class, allowing both player and AI to use spells while supporting easy extensibility.
- **SpellCard Factory:** Created `SpellCardFactory` to simplify the creation of individual or multiple spell cards.
- **UI Improvements:** Performed minimal UI cleanup of assets and positioning.


#### Team Contributions

- **UI Elements:** Created all the UI elements for the game interface.
- **Observer Pattern:** Added to automatically update UI elements.
- **Customization:** Implemented player and enemy customization options.
- **AI Behavior:** Built the AI behaviors for `EnemyAI`.
- **Spells & Status Effects:** Extended the variety of spells and status effects.
- **Command Pattern:** Implemented (`CastSpellCommand`, `DrawCardCommand`, `EndTurnCommand`, `Command` interface) to support action logging and extensibility.
- **Template Pattern:** Added to spell casting.
- **Singleton Pattern:** Implemented for `GameController`.

## Running the Game

#### Prerequisites

- Java JDK 17+
- JavaFX

#### Steps

1. Clone the repository
2. Import as a Maven project (the IDE should do this automatically)
3. Ensure JDK and JavaFX is configured
4. Run the project (from GameUI)

## Video Showcase
