package com.robson.efmextended.events;

import com.robson.efmextended.utils.ItemStackUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Mod.EventBusSubscriber
public class HurtEvents {

    public static List<Player> criticalPlayers = new ArrayList<>();

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onHurt(LivingHurtEvent event){
        if (event.getSource().getDirectEntity() instanceof Player player){
            ItemStack stack = player.getMainHandItem();
            float amount = event.getAmount();

                if (criticalPlayers.contains(player)){
                    float multiplier = ItemStackUtils.getCriticalMultiplier(stack);
                    player.sendSystemMessage(Component.literal("Critical Hit!"));
                    if (multiplier > 0) {
                        amount *= multiplier;
                    }
            }
                if (player.getPersistentData().contains("efm_heavy_counter")){
                    byte counter = player.getPersistentData().getByte("efm_heavy_counter");
                    if (counter > 0){
                        float multiplier = ItemStackUtils.getHeavyMultiplier(stack);
                        if (multiplier > 0) {
                            amount *= 1 + (multiplier-1) * counter/60;
                        }
                    }
                }
            event.setAmount(amount);
            player.sendSystemMessage(Component.literal("Damage done: " + amount));
        }
    }
}
