package com.robson.efmextended.gameasset;

import com.robson.efmextended.EFMExtendedMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EFMExtendedDamageTypes {


    public static final ResourceKey<DamageType> CRITICAL_ATTACK =
            ResourceKey.create(
                    Registries.DAMAGE_TYPE,
                    ResourceLocation.fromNamespaceAndPath(EFMExtendedMod.MOD_ID, "critical_attack")
            );


    public static DamageSource criticalAttack(Level level, Entity attacker) {

        Registry<DamageType> registry =
                level.registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE);

        Holder<DamageType> damageType =
                registry.getHolderOrThrow(CRITICAL_ATTACK);

        return new DamageSource(damageType, attacker);
    }

}
