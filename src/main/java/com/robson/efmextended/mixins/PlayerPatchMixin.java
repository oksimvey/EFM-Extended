package com.robson.efmextended.mixins;

import com.robson.efmextended.utils.CustomMotionsHandler;
import com.robson.efmextended.utils.ItemStackUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(PlayerPatch.class)
public abstract class PlayerPatchMixin extends LivingEntityPatch {


    @Redirect(method = "getDamageSource", at = @At(value = "INVOKE", target = "Lyesman/epicfight/world/capabilities/entitypatch/player/PlayerPatch;getImpact(Lnet/minecraft/world/InteractionHand;)F"), remap = false)
    public float getImpact(PlayerPatch<Player> instance, InteractionHand interactionHand) {
        float original = instance.getImpact(interactionHand);
        if (instance.getOriginal() != null && CustomMotionsHandler.pushingEntities.contains(instance.getOriginal())) {
            return original * ItemStackUtils.getPushImpact(instance.getOriginal().getItemInHand(InteractionHand.MAIN_HAND));
        }
        return original;
    }
}
