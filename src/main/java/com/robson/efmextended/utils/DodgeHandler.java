package com.robson.efmextended.utils;

import net.minecraft.world.entity.player.Player;

public class DodgeHandler {

    private int current_max_dodges;

    private int cooldowncounter;

    private int current_dodges;

    public DodgeHandler(){
        this.current_dodges = 0;
        this.cooldowncounter = 0;
        this.current_max_dodges = 0;
    }

    public void tick(Player player){
        this.cooldowncounter++;
        this.current_max_dodges = ItemStackUtils.getMaxDodges(player.getMainHandItem());
        if (this.current_dodges >= this.current_max_dodges){
            this.cooldowncounter = 0;
            this.current_dodges = this.current_max_dodges;
            return;
        }
        if (this.cooldowncounter >= 100){
            this.cooldowncounter = 0;
            this.current_dodges++;
        }
    }

    public boolean canDodge(){
        return this.current_dodges >= 1;
    }

    public void consume(){
        this.current_dodges -= 1;
    }

    public int getDodges(){
        return this.current_dodges;
    }


    public int getMaxDodges(){
        return this.current_max_dodges;
    }
}
