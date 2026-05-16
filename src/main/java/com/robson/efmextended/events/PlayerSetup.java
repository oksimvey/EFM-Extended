package com.robson.efmextended.events;

import com.robson.efmextended.utils.ClientDataHandler;
import com.robson.efmextended.utils.CustomKey;
import com.robson.efmextended.utils.DodgeHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerSetup {

    @SubscribeEvent
    public static void onPlayerSetup(PlayerEvent.PlayerLoggedInEvent event){
        if (event.getEntity() != null){
            ClientDataHandler.CLIENT_DATA_MANAGER.put(event.getEntity(), new ClientDataHandler(new CustomKey((byte) 12), new DodgeHandler()));
            event.getEntity().getPersistentData().remove("efm_heavy_counter");
            event.getEntity().getMainHandItem().getOrCreateTag().remove("performing_efm_critical");
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
        if (event.getEntity() != null){
            ClientDataHandler.CLIENT_DATA_MANAGER.remove(event.getEntity());
            event.getEntity().getPersistentData().remove("efm_heavy_counter");

            event.getEntity().getMainHandItem().getOrCreateTag().remove("performing_efm_critical");
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event){
        if (event.getEntity() instanceof Player player && event.getSlot() == EquipmentSlot.MAINHAND){
            event.getFrom().getOrCreateTag().remove("performing_efm_critical");
            player.getPersistentData().remove("efm_heavy_counter");
        }
    }
}
