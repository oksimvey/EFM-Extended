package com.robson.efmextended.utils;

import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;

import java.util.concurrent.ConcurrentHashMap;

public class ClientDataHandler {

    public static ConcurrentHashMap<Player, ClientDataHandler> MANAGER = new ConcurrentHashMap<>();

    private final CustomKey key;

    private final DodgeHandler handler;

    public ClientDataHandler(CustomKey key, DodgeHandler handler){
        this.key = key;
        this.handler = handler;
    }

    public CustomKey getKey(){
        return this.key;
    }

    public void tick(Player player){
        this.handler.tick(player);
        if (ControllEngine.isKeyDown(EpicFightKeyMappings.ATTACK)){
            this.key.onPressTick(player);
            return;
        }
        this.key.onRelease(player);
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
