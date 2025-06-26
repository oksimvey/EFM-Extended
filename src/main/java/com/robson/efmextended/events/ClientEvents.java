package com.robson.efmextended.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robson.efmextended.utils.ClientDataHandler;
import com.robson.efmextended.utils.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mod.EventBusSubscriber
public class ClientEvents {

    @SubscribeEvent
    public static void renderOverlay(RenderGuiOverlayEvent event) {
        if (event.getOverlay().id().equals(VanillaGuiOverlay.HOTBAR.id())) {
            if (Minecraft.getInstance().player != null && ClientDataHandler.MANAGER.get(Minecraft.getInstance().player) != null) {
                PlayerPatch<Player> ppatch = EpicFightCapabilities.getEntityPatch(Minecraft.getInstance().player, PlayerPatch.class);
                if (ppatch != null && ppatch.getSkill(SkillSlots.DODGE).getSkill() != null) {
                    ResourceLocation locatiion = ppatch.getSkill(SkillSlots.DODGE).getSkill().getSkillTexture();
                    ClientDataHandler handler = ClientDataHandler.MANAGER.get(Minecraft.getInstance().player);
                    if (handler.getMaxDodges() != 0) {
                        for (int i = 0; i < handler.getMaxDodges(); i++) {
                            float color = handler.getDodges() >= i + 1 ? 1 : 0.5f;
                            GuiGraphics guiGraphics = event.getGuiGraphics();
                            Minecraft mc = Minecraft.getInstance();
                            int screenWidth = mc.getWindow().getGuiScaledWidth() / 2;
                            int screenHeight = mc.getWindow().getGuiScaledHeight();
                            int iconSize = 12;
                            int height = ppatch.getOriginal().isCreative() ? 25 : 40;
                            int x = screenWidth + 8 + (i * iconSize);
                            int y = screenHeight - iconSize - height;
                            RenderSystem.enableBlend();
                            RenderSystem.setShader(GameRenderer::getPositionTexShader);
                            RenderSystem.setShaderTexture(0, locatiion);
                            RenderSystem.setShaderColor(color, color, color, 1.0F);
                            guiGraphics.blit(locatiion, x, y, 0, 0, iconSize, iconSize, iconSize, iconSize);
                            RenderSystem.disableBlend();
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        if (Minecraft.getInstance().player != null && ClientDataHandler.MANAGER.get(Minecraft.getInstance().player) != null && !Minecraft.getInstance().isPaused()){
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
