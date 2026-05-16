package com.robson.efmextended.mixins;

import com.robson.efmextended.EFMExtendedMod;
import com.robson.efmextended.events.HurtEvents;
import com.robson.efmextended.utils.ItemStackUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.concurrent.ThreadLocalRandom;

import static com.robson.efmextended.utils.CustomMotionsHandler.ACTIVE_HEAVY;
import static com.robson.efmextended.utils.CustomMotionsHandler.pushingEntities;

@Mixin(AttackAnimation.class)
public abstract class AttackAnimationMixin extends ActionAnimation {


    @Shadow
    public abstract AttackAnimation.Phase getPhaseByTime(float elapsedTime);


    public AttackAnimationMixin(float transitionTime, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, accessor, armature);
    }

    @Inject(method = "getSwingSound", at = @At("RETURN"), cancellable = true, remap = false)
    private void modifySwingSound(LivingEntityPatch<?> entitypatch, AttackAnimation.Phase phase,CallbackInfoReturnable<SoundEvent> cir) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch && HurtEvents.criticalPlayers.contains(playerPatch.getOriginal())){
            cir.setReturnValue(SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(EFMExtendedMod.MOD_ID, "critical_swing")));
        }
    }

    @Inject(method = "getHitSound", at = @At("RETURN"), cancellable = true, remap = false)
    private void modifyHitSound(LivingEntityPatch<?> entitypatch, AttackAnimation.Phase phase,CallbackInfoReturnable<SoundEvent> cir) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch && HurtEvents.criticalPlayers.contains(playerPatch.getOriginal())){
            cir.setReturnValue(SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(EFMExtendedMod.MOD_ID, "critical_impact")));

        }

    }


    @Inject(method = "getPlaySpeed", at = @At("RETURN"), cancellable = true, remap = false)
    private void modifySpeed(
            LivingEntityPatch<?> entitypatch,
            DynamicAnimation animation,
            CallbackInfoReturnable<Float> cir
    ) {

        if ( entitypatch instanceof PlayerPatch<?> ppatch) {
            Player player = ppatch.getOriginal();


            if (ACTIVE_HEAVY.get(player.getUUID()) != null){
                byte currentpresstick = ACTIVE_HEAVY.get(player.getUUID());
               player.getPersistentData().putByte("efm_heavy_counter", currentpresstick);
                float elapsedtime = entitypatch.getAnimator().getPlayerFor(this.getAccessor()).getElapsedTime();
                AttackAnimation.Phase phase = this.getPhaseByTime(elapsedtime);
                float predelay = Math.max(0.05f, phase.preDelay);
                if (currentpresstick >= 60){
                    ACTIVE_HEAVY.remove(player.getUUID());
                    return;
                }
                if (elapsedtime < predelay) {
                    cir.setReturnValue(getTotalTime()/10f);
                }
            }
        }
    }



    @Inject(method = "begin", at = @At("HEAD"), remap = false)
    private void begin(LivingEntityPatch<?> entitypatch, CallbackInfo ci){
            if (entitypatch instanceof PlayerPatch<?> playerPatch && !playerPatch.getOriginal().level().isClientSide){
                entitypatch.getOriginal().getPersistentData().remove("efm_heavy_counter");
                if (HurtEvents.criticalPlayers.contains(entitypatch.getOriginal())) {
                    entitypatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("performing_efm_critical", false);
                    HurtEvents.criticalPlayers.remove(entitypatch.getOriginal());
                }
                if (pushingEntities.contains(playerPatch.getOriginal())){
                    return;
                }
                float chance = ItemStackUtils.getCriticalChance(playerPatch.getOriginal().getMainHandItem());
                 if (ThreadLocalRandom.current().nextFloat() * 100 < chance) {
                    playerPatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("performing_efm_critical", true);
                    HurtEvents.criticalPlayers.add(playerPatch.getOriginal());
                }
            }
    }


    @Inject(method = "end", at = @At("TAIL"), remap = false)
    private void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd, CallbackInfo ci){
        if (entitypatch instanceof PlayerPatch<?> && !entitypatch.getOriginal().level().isClientSide ){
            if (ACTIVE_HEAVY.get(entitypatch.getOriginal().getUUID()) != null){
                ACTIVE_HEAVY.remove(entitypatch.getOriginal().getUUID());
            }
            if (entitypatch.getOriginal().getPersistentData().contains("efm_heavy_counter")){
                entitypatch.getOriginal().getPersistentData().remove("efm_heavy_counter");
            }

            if (HurtEvents.criticalPlayers.contains(entitypatch.getOriginal())) {
                entitypatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("performing_efm_critical", false);
                HurtEvents.criticalPlayers.remove(entitypatch.getOriginal());
            }

        }
    }
}
