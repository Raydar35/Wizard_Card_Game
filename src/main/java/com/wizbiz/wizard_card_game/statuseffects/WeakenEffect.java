package com.wizbiz.wizard_card_game.statuseffects;
import com.wizbiz.wizard_card_game.Actor;

/**
 * WeakenEffect - Reduces the effectiveness of the target's attacks
 * Applied by: Curse spell
 * Note: Visual indicator only - actual damage reduction would require
 * modifying spell damage calculations
 */
public class WeakenEffect implements StatusEffect {
    private int turnsLeft;

    public WeakenEffect(int turns) {
        this.turnsLeft = turns;
    }

    @Override
    public void onTurnStart(Actor target) {
        // Weaken just persists - actual damage reduction would be in combat
        turnsLeft--;
    }

    @Override
    public boolean isExpired() {
        return turnsLeft <= 0;
    }

    @Override
    public void refresh(StatusEffect other) {
        if (other instanceof WeakenEffect) {
            this.turnsLeft = ((WeakenEffect) other).turnsLeft;
        }
    }
}