package com.robson.efmextended.utils;

import net.minecraft.world.entity.player.Player;

public class DodgeHandler {

    private int current_max_dodges;

    private int cooldowncounter;

    private int current_dodges;

    public void tick(Player player){
        this.cooldowncounter++;
        this.current_max_dodges = getMaxDodges(player);
        if (this.current_dodges <= this.current_max_dodges){
            return;
        }
        if (this.cooldowncounter >= 10){
            this.cooldowncounter = 0;
            this.current_dodges++;
        }
    }

    public boolean canDodge(){
        return this.current_dodges >= 1;
    }

    public void consume(){
        this.current_dodges--;
    }

    public static int getMaxDodges(Player player){
        return 0;
    }


}
