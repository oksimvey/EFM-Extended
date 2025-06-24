package com.robson.efmextended.mixins;

import com.robson.efmextended.utils.ItemStackUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Mixin(GuardSkill.class)
public class GuardSkillMixin extends Skill {


    public GuardSkillMixin(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }


    @Redirect(method = "guard", at = @At(value = "INVOKE", target = "Lyesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch;consumeForSkill(Lyesman/epicfight/skill/Skill;Lyesman/epicfight/skill/Skill$Resource;F)Z"), remap = false)
    public boolean guard(ServerPlayerPatch instance, Skill skill, Resource resource, float v) {
        float block_resistance = ItemStackUtils.getItemStaminaOnBlock(instance.getOriginal().getMainHandItem());
        float amount = v;
        if (block_resistance > 0){
            amount /= block_resistance / 2;
        }
        return instance.consumeForSkill(skill, resource, amount);
    }
}
