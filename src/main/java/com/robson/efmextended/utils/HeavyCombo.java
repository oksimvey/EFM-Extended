package com.robson.efmextended.utils;

import net.minecraft.world.entity.player.Player;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Arrays;
import java.util.List;

public interface HeavyCombo {

    static void performHeavyAttack(Player player) {
        if (player != null) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch != null && !playerPatch.getEntityState().attacking()) {
                List<String> heavyMotions = ItemStackUtils.getHeavyMotion(player, player.getMainHandItem());
                if (player.isSprinting()) {
                    AnimUtils.playAnimation(player, heavyMotions.get(heavyMotions.size() - 2));
                    return;
                }
                if (player.getDeltaMovement().y() > 0.0D) {
                    AnimUtils.playAnimation(player, heavyMotions.get(heavyMotions.size() - 1));
                    return;
                }

                SkillDataManager dataManager = playerPatch.getSkill(EpicFightSkills.BASIC_ATTACK).getDataManager();
                int comboCounter = (Integer) dataManager.getDataValue((SkillDataKey) SkillDataKeys.COMBO_COUNTER.get());
                ++comboCounter;
                if (comboCounter >= heavyMotions.size() - 2) {
                    comboCounter = 0;
                }
                dataManager.setData(SkillDataKeys.COMBO_COUNTER.get(), comboCounter);
                AnimUtils.playAnimation(player, heavyMotions.get(comboCounter));
            }
        }
    }
}
