package com.robson.efmextended.utils;

import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;

public class CustomKey {

    private boolean isPressed = false;

    private byte presscounter = 0;

    private final byte counterforstart;

    protected boolean longPressTriggered = false;

    public CustomKey(byte counterforstart) {
        this.counterforstart = counterforstart;
    }

    public void onPressTick(Player player) {
        if (!this.isPressed) {
            this.presscounter = 0;
            this.isPressed = true;
            if (ControllEngine.isKeyDown(EpicFightKeyMappings.GUARD)){
                CustomMotionsHandler.performPushAttack(player);
                return;
            }
        }
        if (this.isPressed() && !longPressTriggered) {
            this.presscounter++;
            if (this.presscounter >= this.counterforstart) {
                this.onLongPress(player);
                this.longPressTriggered = true;
            }
        }
    }

    public void onLongPress(Player player){
        CustomMotionsHandler.performHeavyAttack(player);
    }

    public boolean isPressed() {
        return this.isPressed;
    }

    public void onRelease(Player player) {
        this.isPressed = false;
        this.presscounter = 0;
        this.longPressTriggered = false;
    }
}
