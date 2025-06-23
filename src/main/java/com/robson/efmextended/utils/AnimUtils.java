package com.robson.efmextended.utils;

import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public interface AnimUtils {

    static void playAnimation(LivingEntity ent, AnimationManager.AnimationAccessor<? extends StaticAnimation> animation){
        if (ent != null && animation != null){
            LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (patch != null){
                patch.playAnimationSynchronized(animation, animation.get().getTransitionTime());
            }
        }
    }

    static void playAnimation(LivingEntity ent, String animation){
        playAnimation(ent, AnimationManager.byKey(animation));
    }
}
