package com.robson.efmextended.utils;

import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.events.engine.ControllEngine;

public interface KeyHandler {

    static void handleKeyInput(Player player, KeyMapping key, CustomKey keyAction){
        if (ControllEngine.isKeyDown(key)){
            keyAction.onPressTick(player);
        }
        else if (keyAction.isPressed()) {
            keyAction.onRelease(player);
        }
    }
}
