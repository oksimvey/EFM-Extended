package com.robson.efmextended;


import com.robson.efmextended.gameasset.EFMExtendedAnimations;
import com.robson.efmextended.gameasset.EFMExtendedAttributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("efm_extended")
public class EFMExtendedMod {
    public static final Logger LOGGER = LogManager.getLogger(EFMExtendedMod.class);
    private static final String PROTOCOL_VERSION = "1";
    public static final String MOD_ID = "efm_extended";

    public EFMExtendedMod(FMLJavaModLoadingContext context) {
        final IEventBus bus = context.getModEventBus();
        EFMExtendedAttributes.register(bus);
        bus.addListener(EFMExtendedAnimations::registerAnimations);
    }

}
