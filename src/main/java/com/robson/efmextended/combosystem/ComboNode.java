package com.robson.efmextended.combosystem;

import org.jetbrains.annotations.NotNull;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;


public class ComboNode {


    private final ComboNode nextLightAttack;

    private final ComboNode nextHeavyAttackComboNode;

    private final AnimationManager.AnimationAccessor<? extends StaticAnimation> motion;

    public ComboNode(ComboNode nextLightAttack, ComboNode nextHeavyAttackComboNode,@NotNull AnimationManager.AnimationAccessor<? extends StaticAnimation> motion) {
        this.nextLightAttack = nextLightAttack;
        this.nextHeavyAttackComboNode = nextHeavyAttackComboNode;
        this.motion = motion;
    }


    public ComboNode(@NotNull AnimationManager.AnimationAccessor<? extends StaticAnimation> motion) {
        this.motion = motion;
        this.nextLightAttack = null;
        this.nextHeavyAttackComboNode = null;
    }

    public ComboNode(ComboNode nextLightAttack, @NotNull AnimationManager.AnimationAccessor<? extends StaticAnimation> motion) {
        this.nextLightAttack = nextLightAttack;
        this.nextHeavyAttackComboNode = null;
        this.motion = motion;
    }

    public ComboNode(@NotNull AnimationManager.AnimationAccessor<? extends StaticAnimation> motion, ComboNode nextHeavyAttackComboNode){
        this.nextHeavyAttackComboNode = nextHeavyAttackComboNode;
        this.nextLightAttack = null;
        this.motion = motion;
    }

    public ComboNode getLightNode(){
        return this.nextLightAttack;
    }

    public ComboNode getHeavyNode(){
        return this.nextHeavyAttackComboNode;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getMotion(){
        return this.motion;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getNextLightAttack(){
        return this.nextLightAttack.motion;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getNextHeavyAttackComboNode(){
        return this.nextHeavyAttackComboNode.motion;
    }

    
}
