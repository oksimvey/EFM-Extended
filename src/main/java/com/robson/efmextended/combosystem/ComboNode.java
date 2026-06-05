package com.robson.efmextended.combosystem;

import org.jetbrains.annotations.NotNull;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;


public class ComboNode {


    private final ComboNode lightAttack;

    private final ComboNode heavyAttack;

    private final AnimationManager.AnimationAccessor<? extends StaticAnimation> motion;

    public ComboNode(ComboNode lightAttack, ComboNode heavyAttack,@NotNull AnimationManager.AnimationAccessor<? extends StaticAnimation> motion) {
        this.lightAttack = lightAttack;
        this.heavyAttack = heavyAttack;
        this.motion = motion;
    }


    public ComboNode(@NotNull AnimationManager.AnimationAccessor<? extends StaticAnimation> motion) {
        this.motion = motion;
        this.lightAttack = null;
        this.heavyAttack = null;
    }

    public ComboNode(ComboNode lightattack, @NotNull AnimationManager.AnimationAccessor<? extends StaticAnimation> motion) {
        this.lightAttack = lightattack;
        this.heavyAttack = null;
        this.motion = motion;
    }

    public ComboNode(@NotNull AnimationManager.AnimationAccessor<? extends StaticAnimation> motion, ComboNode heavyAttack){
        this.heavyAttack = heavyAttack;
        this.lightAttack = null;
        this.motion = motion;
    }

    public ComboNode getLightNode(){
        return this.lightAttack;
    }

    public ComboNode getHeavyNode(){
        return this.heavyAttack;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getMotion(){
        return this.motion;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getLightAttack(){
        return this.lightAttack.motion;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getHeavyAttack(){
        return this.heavyAttack.motion;
    }

    
}
