package com.robson.efmextended.utils;

import net.minecraft.world.entity.player.Player;

public class DodgeHandler {

    private byte current_max_dodges;

    private byte cooldowncounter;

    private byte current_dodges;

    private byte counterforstart;

    public DodgeHandler(){
        this.current_dodges = 0;
        this.cooldowncounter = 0;
        this.current_max_dodges = 0;
        this.counterforstart = 0;
    }

    public void tick(Player player){
        this.current_max_dodges = (byte) ItemStackUtils.getMaxDodges(player.getMainHandItem());
        if (this.counterforstart < 60){
            this.counterforstart++;
            return;
        }
        this.cooldowncounter++;
        if (this.current_max_dodges >= 1 && this.current_dodges >= this.current_max_dodges){
            this.cooldowncounter = 0;
            this.current_dodges = this.current_max_dodges;
            this.counterforstart = 0;
            return;
        }
        if (this.current_max_dodges >= 1 && this.cooldowncounter >= 100){
            this.cooldowncounter = 0;
            this.current_dodges++;
        }
    }

    public boolean canDodge(){
        return this.current_dodges >= 1;
    }

    public void consume(){
        this.current_dodges -= 1;
        this.counterforstart = 0;
    }

    public int getDodges(){
        return this.current_dodges;
    }


    public int getMaxDodges(){
        return this.current_max_dodges;
    }
}
