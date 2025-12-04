package com.wizbiz.wizard_card_game.statuseffects;
import com.wizbiz.wizard_card_game.Actor;

public class ShieldEffect implements StatusEffect {
    private int shieldPoints;
    private int maxShieldPoints;

    public ShieldEffect(int shieldPoints) {
        this.shieldPoints = shieldPoints;
        this.maxShieldPoints = shieldPoints;
    }

    @Override
    public void onTurnStart(Actor target) {
    }

    @Override
    public boolean isExpired() {
        return shieldPoints <= 0;
    }

    @Override
    public void refresh(StatusEffect other) {
        if (other instanceof ShieldEffect) {
            this.shieldPoints = this.maxShieldPoints;
        }
    }
}