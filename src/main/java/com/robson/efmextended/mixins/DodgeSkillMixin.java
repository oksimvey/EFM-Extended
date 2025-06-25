package com.robson.efmextended.mixins;

import com.robson.efmextended.utils.ClientDataHandler;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(DodgeSkill.class)
public abstract class DodgeSkillMixin extends Skill {

    public DodgeSkillMixin(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public boolean isExecutableState(PlayerPatch<?> executer) {
        EntityState playerState = executer.getEntityState();
        ClientDataHandler handler = ClientDataHandler.MANAGER.get(executer.getOriginal());
        boolean canexecute =  !executer.isInAir() && playerState.canUseSkill() && !((Player)executer.getOriginal()).isInWater() &&
                !((Player)executer.getOriginal()).onClimbable() && ((Player)executer.getOriginal()).getVehicle() == null && handler != null && handler.canDodge();
                if (canexecute && !executer.getOriginal().level().isClientSide) {
                    handler.consume();
                }
        return canexecute;
    }
}
