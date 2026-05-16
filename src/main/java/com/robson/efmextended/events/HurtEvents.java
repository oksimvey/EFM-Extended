package com.robson.efmextended.events;

import com.robson.efmextended.utils.ItemStackUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class HurtEvents {

    public static List<Player> criticalPlayers = new ArrayList<>();

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onHurt(LivingHurtEvent event){
        if (event.getSource().getDirectEntity() instanceof Player player && event.getEntity() != null){
            ItemStack stack = player.getMainHandItem();
            float amount = event.getAmount();

                if (player.getPersistentData().contains("efm_heavy_counter")){
                    byte counter = player.getPersistentData().getByte("efm_heavy_counter");
                    if (counter > 0){
                        float multiplier = ItemStackUtils.getHeavyMultiplier(stack);
                        if (multiplier > 0) {
                            amount *= 1 + (multiplier-1) * counter/60;
                        }
                    }
                }

            if (criticalPlayers.contains(player)) {
                float multiplier = ItemStackUtils.getCriticalMultiplier(stack);
                if (multiplier > 0) {
                    amount *= multiplier;
                }
            }
            event.setAmount(amount);
        }
    }
}
