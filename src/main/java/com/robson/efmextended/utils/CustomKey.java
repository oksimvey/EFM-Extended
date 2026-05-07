package com.robson.efmextended.utils;

import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.input.EpicFightKeyMappings;

import java.util.ArrayList;
import java.util.List;

import static yesman.epicfight.client.events.engine.ControlEngine.isKeyDown;

public class CustomKey {

    public static List<Player> players = new ArrayList<>();

    private boolean isPressed = false;

    private byte presscounter = 0;

    private final byte counterforstart;

    protected boolean longPressTriggered = false;

    public CustomKey(byte counterforstart) {
        this.counterforstart = counterforstart;
    }

    public byte getPressCounter() {
        return this.presscounter;
    }

    public void onPressTick(Player player) {
        if (!this.isPressed) {
            this.presscounter = 0;
            this.isPressed = true;
            if (isKeyDown(EpicFightKeyMappings.GUARD)){
                CustomMotionsHandler.performPushAttack(player);
                return;
            }
        }
        if (this.isPressed() && !longPressTriggered) {

            this.presscounter++;

            if (this.presscounter >= this.counterforstart) {
                this.presscounter = 0;
                this.isPressed = false;
                CustomMotionsHandler.performHeavyAttack(player);
                this.longPressTriggered = true;
            }
        }
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