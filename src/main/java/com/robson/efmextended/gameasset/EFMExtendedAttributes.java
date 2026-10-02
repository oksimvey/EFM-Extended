package com.robson.efmextended.gameasset;

import com.robson.efmextended.EFMExtendedMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class EFMExtendedAttributes {
    private static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, EFMExtendedMod.MOD_ID);

    public static final RegistryObject<Attribute> CRITICAL_CHANCE = ATTRIBUTES.register(
            "critical_chance",
            () -> new RangedAttribute("attribute.name.efm_extended.critical_chance", 0.0D, 0.0D, 100.0D)
                    .setSyncable(true)
    );

    public static final RegistryObject<Attribute> CRITICAL_MULTIPLIER = ATTRIBUTES.register(
            "critical_multiplier",
            () -> new RangedAttribute("attribute.name.efm_extended.critical_multiplier", 1.0D, 0.0D, 1024.0D)
                    .setSyncable(true)
    );

    private EFMExtendedAttributes() {
    }

    public static void register(IEventBus bus) {
        ATTRIBUTES.register(bus);
        bus.addListener(EFMExtendedAttributes::addPlayerAttributes);
    }

    private static void addPlayerAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, CRITICAL_CHANCE.get());
        event.add(EntityType.PLAYER, CRITICAL_MULTIPLIER.get());
    }
}
