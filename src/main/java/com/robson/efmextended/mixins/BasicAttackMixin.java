package com.robson.efmextended.mixins;

import com.robson.efmextended.utils.ClientDataHandler;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(BasicAttack.class)
public class BasicAttackMixin extends Skill {

    public BasicAttackMixin(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public boolean isExecutableState(PlayerPatch<?> executor){
        EntityState playerState = executor.getEntityState();
        Player player = (Player)executor.getOriginal();
        byte input = ClientDataHandler.MANAGER.get(executor.getOriginal()).getKey().getPresscounter();
        return !player.isSpectator() && !executor.isInAir() && playerState.canBasicAttack() && input == 0;
    }
}
