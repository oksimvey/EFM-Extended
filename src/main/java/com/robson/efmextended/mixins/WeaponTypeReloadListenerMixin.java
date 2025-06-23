package com.robson.efmextended.mixins;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.world.capabilities.item.WeaponTypeReloadListener;

import java.util.Map;

@Mixin(WeaponTypeReloadListener.class)
public interface WeaponTypeReloadListenerMixin {


    @Accessor("CAPABILITY_COMPOUNDS")
    static Map<ResourceLocation, CompoundTag> getCustomWeaponTypeTags(){
        throw new RuntimeException();
    }

}
