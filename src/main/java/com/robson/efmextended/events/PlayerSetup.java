package com.robson.efmextended.events;

import com.robson.efmextended.utils.ClientDataHandler;
import com.robson.efmextended.utils.CustomKey;
import com.robson.efmextended.utils.DodgeHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerSetup {

    @SubscribeEvent
    public static void onPlayerSetup(PlayerEvent.PlayerLoggedInEvent event){
        if (event.getEntity() != null){
            ClientDataHandler.MANAGER.put(event.getEntity(), new ClientDataHandler(new CustomKey((byte) 10), new DodgeHandler()));
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
        if (event.getEntity() != null){
            ClientDataHandler.MANAGER.remove(event.getEntity());
        }
    }
}
