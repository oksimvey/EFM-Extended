package com.robson.efmextended.utils;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;

@Mod.EventBusSubscriber
public class KeyHandler {

    @SubscribeEvent
    public static void handleKeys(TickEvent.ClientTickEvent event){
        if (Minecraft.getInstance().player != null){
            handleKeyInput(Minecraft.getInstance().player, EpicFightKeyMappings.ATTACK, CustomKey.HEAVY_ATTACK_KEY);
        }
    }

    static void handleKeyInput(Player player, KeyMapping key, CustomKey keyAction){
        if (ControllEngine.isKeyDown(key)){
            keyAction.onPressTick(player);
            return;
        }
        if (keyAction.isPressed()) {
            keyAction.onRelease(player);
        }
    }
}
