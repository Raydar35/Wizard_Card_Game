# WizBiz Card Game 

## Quick status
This is a small Java/Maven prototype that uses JavaFX for the UI. The project now uses OS-activated Maven profiles to select platform-specific JavaFX native artifacts so Windows and macOS collaborators can build and run without pulling each other's native jars.

## Build & run (recommended)
Use Maven — the project contains OS-activated profiles that automatically select the correct JavaFX platform classifier for your machine.

Prerequisites
- JDK 17 (match the `maven.compiler.release` property). If your team uses a newer JDK, update `pom.xml`'s properties together.
- Maven 3.8+ (3.9 recommended)

Windows (quick)
1. Open a PowerShell terminal and (optional) purge old OpenJFX natives if you previously had mismatched platform jars:

```powershell
Remove-Item -Recurse -Force "$env:USERPROFILE\.m2\repository\org\openjfx"
```

2. Build and run with Maven (the `windows` profile is auto-activated):

```powershell
mvn -U clean package -DskipTests
mvn javafx:run
```

macOS (Intel) (quick)
```bash
rm -rf ~/.m2/repository/org/openjfx
mvn -U clean package -DskipTests
mvn javafx:run
```

macOS (Apple Silicon / aarch64)
If auto-detection on macOS fails for Apple Silicon, run explicitly with the `mac-aarch64` profile:

```bash
mvn -Pmac-aarch64 -U clean package -DskipTests
mvn -Pmac-aarch64 javafx:run
```

What the pom changes do
- The `pom.xml` now defines a property `javafx.platform.classifier` and several OS-activated profiles (`windows`, `linux`, `mac`, `mac-aarch64`). Each JavaFX dependency uses `${javafx.platform.classifier}` as its classifier so Maven downloads the correct native binaries for the current OS.
- This prevents `no suitable pipeline found` and similar native loading errors that occur when platform-specific native jars for another OS (e.g., mac jars) exist on the module path while running on your machine.

IntelliJ tips
- Prefer running `mvn javafx:run` from IntelliJ (Add a Maven run configuration or use the Maven tool window). The javafx-maven-plugin will set up the module path and native loading correctly.
- If you run via an Application run configuration, add these VM options (Windows example):

--add-modules=javafx.controls,javafx.fxml -Dprism.verbose=true

If graphics initialization fails and you need a temporary workaround, add the software fallback:

-Dprism.order=sw

Note: `-Dprism.order=sw` forces software rendering — it avoids a crash but may degrade graphics performance. Use only to test while diagnosing native mismatch issues.

Troubleshooting
- If you see "Error initializing QuantumRenderer: no suitable pipeline found" or native load errors, ensure:
  1. You have deleted/repo-purged any `org.openjfx` jars that were downloaded for the wrong OS (see commands above).
  2. Reimport the Maven project in IntelliJ and run `mvn -U clean package` again.
  3. Run `mvn javafx:run -Dprism.verbose=true` to get detailed native loading logs.
- If you see compilation errors about an unsupported source/target version, make sure your local JDK matches the `maven.compiler.release` property in `pom.xml` or update that property to your team's standard.

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
1. No win/loss conditions implemented yet.
2. No animations or visual effects; purely text-based logging in UI.
3. Need discard pile and reshuffling when deck is exhausted.
4. Enemy AI is trivial and assumes Enemy has matching cards in hand.
5. No validation or error messages shown to player when a cast fails due to missing mana or missing card (only logged).


## Where to look first when editing
- Gameplay rules: `GameController.java`, `Spell.java`, `Actor.java`.
- Add spells: `spells` package + `SpellCardFactory.java`.
- UI changes: `GameUI.java`.
