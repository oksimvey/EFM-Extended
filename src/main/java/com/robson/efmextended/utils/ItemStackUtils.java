package com.robson.efmextended.utils;

import com.robson.efmextended.mixins.ItemCapabilityReloadListenerMixin;
import com.robson.efmextended.mixins.WeaponTypeReloadListenerMixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.ArrayList;
import java.util.List;

public interface ItemStackUtils {

    static String getItemType(ItemStack itemStack){
        if (itemStack != null){
            CompoundTag tag = ItemCapabilityReloadListenerMixin.getCompounds().get(itemStack.getItem());
            if (tag != null){
                return tag.getString("type");
            }
        }
        return "";
    }

    static float getItemStaminaOnBlock(ItemStack itemStack){
        if (itemStack != null){
            CompoundTag tag = ItemCapabilityReloadListenerMixin.getCompounds().get(itemStack.getItem());
            if (tag != null){
                return tag.getFloat("block_resistance");
            }
        }
        return 0;
    }

    static List<String> getHeavyMotion(LivingEntity ent, ItemStack itemStack){
        List<String> heavyMotions = new ArrayList<>();
        if (ent != null && itemStack != null){
            String type = getItemType(itemStack);
            if (!type.isEmpty()){
                CompoundTag tags = WeaponTypeReloadListenerMixin.getCustomWeaponTypeTags().get(new ResourceLocation(type));
                if (tags != null){
                    CompoundTag heavymotions = tags.getCompound("heavy_combos");
                    if (heavymotions != null){
                        String style = getStyle(ent);
                        if (!style.isEmpty()) {
                            ListTag list = heavymotions.getList(style, 8);
                            for (int i = 0; i < list.size(); ++i) {
                                    heavyMotions.add(list.getString(i));
                            }
                        }
                    }
                }
            }
        }
        return heavyMotions;
    }

    static String getPushMotion(LivingEntity ent, ItemStack itemStack){
        if (ent != null && itemStack != null) {
            String type = getItemType(itemStack);
            if (!type.isEmpty()) {
                CompoundTag tags = WeaponTypeReloadListenerMixin.getCustomWeaponTypeTags().get(new ResourceLocation(type));
                if (tags != null) {
                    CompoundTag pushMotions = tags.getCompound("push_motions");
                    if (pushMotions != null) {
                        String style = getStyle(ent);
                        if (!style.isEmpty()) {
                            return pushMotions.getString(style);
                        }
                    }
                }
            }
        }
        return "";
    }

    static String getStyle(LivingEntity ent){
        if (ent != null){
            LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (patch != null){
                return patch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(patch).toString().toLowerCase().replace(" ", "_");
            }
        }
        return "";
    }
}
