package com.robson.efmextended.mixins;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(DodgeSkill.class)
public class DodgeSkillMixin extends Skill {

    public DodgeSkillMixin(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    @Inject(method = "isExecutableState", at = @At("HEAD"), cancellable = true, remap = false)
    public void isExecutableState(PlayerPatch<?> executer, CallbackInfoReturnable<Boolean> cir) {

    }
}
