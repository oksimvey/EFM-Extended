package com.robson.efmextended.events;

import com.robson.efmextended.utils.CustomKey;
import com.robson.efmextended.utils.ItemStackUtils;
import com.robson.efmextended.utils.KeyHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.client.input.EpicFightKeyMappings;

@Mod.EventBusSubscriber
public class ClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        if (Minecraft.getInstance().player != null){
            KeyHandler.handleKeyInput(Minecraft.getInstance().player, EpicFightKeyMappings.ATTACK, CustomKey.HEAVY_ATTACK_KEY);
        }
    }

    @SubscribeEvent
    public static void renderToolTips(ItemTooltipEvent event){
        if (event != null){
            ItemStack stack = event.getItemStack();
            float stamina = ItemStackUtils.getItemStaminaOnBlock(stack);
            if (stamina > 0){
                event.getToolTip().set(event.getToolTip().size() - 1, Component.literal(" " + stamina + " Block Resistance").withStyle(ChatFormatting.DARK_GREEN));
            }
        }
    }
}
