package com.robson.efmextended.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientDataHandler {

    public static ConcurrentHashMap<Player, ClientDataHandler> CLIENT_DATA_MANAGER = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<UUID, Byte> AUTO_COOLDOWN = new ConcurrentHashMap<>();

    private final CustomKey key;

    private final DodgeHandler handler;

    public ClientDataHandler(CustomKey key, DodgeHandler handler){
        this.key = key;
        this.handler = handler;
    }

    public byte getAutoCooldown(Player player){
        return AUTO_COOLDOWN.getOrDefault(player.getUUID(), (byte) 0);
    }

    public void incrementCooldown(Player player){
        AUTO_COOLDOWN.put(player.getUUID(), (byte) (AUTO_COOLDOWN.getOrDefault(player.getUUID(), (byte) 0) + 1));
    }

    public void resetCooldown(Player player){
        AUTO_COOLDOWN.put(player.getUUID(), (byte) 0);
    }

    public CustomKey getKey(){
        return this.key;
    }

    public void tick(Player player){
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null || localPlayer != player || Minecraft.getInstance().screen != null){
            return;
        }
        this.handler.tick(player);
        if (ControlEngine.isKeyDown(EpicFightKeyMappings.ATTACK)){
            resetCooldown(player);
            this.key.onPressTick(player);
            return;
        }
        this.key.onRelease(player);
        incrementCooldown(player);
         if (getAutoCooldown(player) >= 40){
            CustomMotionsHandler.MOTIONS_HANDLER.put(player.getUUID(), new ArrayList<>());
        }
    }


    public void consume(){
        this.handler.consume();
    }

    public int getDodges(){
        return this.handler.getDodges();
    }

    public int getMaxDodges(){
        return this.handler.getMaxDodges();
    }

    public boolean canDodge(){
        return this.handler.canDodge();
    }
}
