package com.robson.efmextended.mixins;

import com.robson.efmextended.events.HurtEvents;
import com.robson.efmextended.gameasset.EFMExtendedDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @ModifyVariable(
            method = "hurt",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private DamageSource modifySource(DamageSource source) {

        Entity attacker = source.getEntity();

        if (attacker instanceof Player player && HurtEvents.criticalPlayers.contains(player)) {


                return EFMExtendedDamageTypes.criticalAttack(player.level(), player);

        }

        return source;
    }
}