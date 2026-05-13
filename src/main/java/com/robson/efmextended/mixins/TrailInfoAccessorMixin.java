package com.robson.efmextended.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.particle.AbstractTrailParticle;

@Mixin(AbstractTrailParticle.class)
public interface TrailInfoAccessorMixin {

    @Accessor("trailInfo")
    TrailInfo getTrailInfo();

    @Accessor("trailInfo")
    @Mutable
    void setTrailInfo(TrailInfo info);
}