package com.robson.efmextended.events;

import com.robson.efmextended.utils.ClientDataHandler;
import com.robson.efmextended.utils.ItemStackUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ClientEvents {

    @SubscribeEvent
    public static void renderOverlay(RenderGuiOverlayEvent event){
        if (Minecraft.getInstance().player != null && ClientDataHandler.MANAGER.get(Minecraft.getInstance().player) != null){
            ClientDataHandler handler = ClientDataHandler.MANAGER.get(Minecraft.getInstance().player);
            event.getGuiGraphics().drawString(Minecraft.getInstance().font, handler.getDodges() + "/" + handler.getMaxDodges(), 55, 20, 0xFFFFFF);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        if (Minecraft.getInstance().player != null && ClientDataHandler.MANAGER.get(Minecraft.getInstance().player) != null){
            ClientDataHandler.MANAGER.get(Minecraft.getInstance().player).tick(Minecraft.getInstance().player);
        }
    }

    @SubscribeEvent
    public static void renderToolTips(ItemTooltipEvent event){
        if (event != null){
            ItemStack stack = event.getItemStack();
            float stamina = ItemStackUtils.getItemStaminaOnBlock(stack);
            if (stamina > 0){
                event.getToolTip().add( Component.literal(" " + stamina + " Block Resistance"));
            }

            float push_impact = ItemStackUtils.getPushImpact(stack);
            if (push_impact > 0){
                event.getToolTip().add( Component.literal(" " + push_impact + " Push Impact"));
            }
            float pushstamina = ItemStackUtils.getPushConsumption(stack);
            if (pushstamina > 0){
                event.getToolTip().add(Component.literal(" " + pushstamina + "% Stamina Consumption On Push"));
            }
            int dodges = ItemStackUtils.getMaxDodges(stack);
            if (dodges > 0){
                event.getToolTip().add( Component.literal(" " + dodges + " Max Dodges"));
            }
        }
    }
}
