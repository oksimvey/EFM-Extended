package com.robson.efmextended.mixins;

import com.robson.efmextended.events.HurtEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.particle.AbstractTrailParticle;
import yesman.epicfight.client.particle.AnimationTrailParticle;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = AnimationTrailParticle.class, remap = false)
public abstract class AnimationTrailMixin extends AbstractTrailParticle<LivingEntityPatch<?>> {

    protected AnimationTrailMixin(
            ClientLevel level,
            LivingEntityPatch<?> owner,
            TrailInfo trailInfo
    ) {
        super(level, owner, trailInfo);
    }



    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void injected(
            ClientLevel level,
            LivingEntityPatch<?> owner,
            Joint joint,
            AssetAccessor<?> animation,
            TrailInfo trailInfo,
            CallbackInfo ci
    ) {
        if (owner instanceof PlayerPatch<?> playerPatch && HurtEvents.criticalPlayers.contains(playerPatch.getOriginal())) {
            trailInfo = new TrailInfo(
                    trailInfo.start(),
                    trailInfo.end(),
                    trailInfo.joint(),
                    trailInfo.particle(),
                    trailInfo.startTime(),
                    trailInfo.endTime(),
                    trailInfo.fadeTime(),
                    trailInfo.rCol(),
                    trailInfo.gCol(),
                    trailInfo.bCol(),
                    trailInfo.interpolateCount(),
                    15,
                    trailInfo.updateInterval(),
                    trailInfo.blockLight(),
                    trailInfo.skyLight(),
                    trailInfo.texturePath(),
                    trailInfo.hand()

            );
            ((TrailInfoAccessorMixin) this).setTrailInfo(trailInfo);
            this.rCol = 1.0F;
            this.gCol = 0.0F;
            this.bCol = 0.0F;
        }
    }
}