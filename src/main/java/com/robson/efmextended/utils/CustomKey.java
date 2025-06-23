package com.robson.efmextended.utils;

import net.minecraft.world.entity.player.Player;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

public abstract class CustomKey {

    private boolean isPressed = false;

    private byte presscounter = 0;

    private final byte counterforstart;

    protected boolean longPressTriggered = false;

    public CustomKey(byte counterforstart) {
        this.counterforstart = counterforstart;
    }


    public void onPressTick(Player player) {
        if (!this.isPressed) {
            this.isPressed = true;
        }
        if (this.isPressed() && !longPressTriggered) {
            this.presscounter++;
            if (this.presscounter >= this.counterforstart) {
                this.onLongPress(player);
                this.longPressTriggered = true;
            }
        }
    }

    public abstract void onLongPress(Player player);

    public boolean isPressed() {
        return this.isPressed;
    }

    public void onRelease(Player player) {
        this.isPressed = false;
        this.presscounter = 0;
        this.longPressTriggered = false;
    }
    public static CustomKey HEAVY_ATTACK_KEY = new CustomKey((byte) 10) {

        @Override
        public void onLongPress(Player player) {
            HeavyCombo.performHeavyAttack(player);
        }
    };

}
