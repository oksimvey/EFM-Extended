package com.robson.efmextended.mixins;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.data.reloader.ItemCapabilityReloadListener;

import java.util.Map;

@Mixin(ItemCapabilityReloadListener.class)
public interface ItemCapabilityReloadListenerMixin {

    @Accessor("WEAPON_COMPOUNDS")
    static Map<Item, CompoundTag> getCompounds(){
        throw new RuntimeException();
    }

}
