package com.robson.efmextended.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public interface CustomMotionsHandler {

    List<LivingEntity> pushingEntities = new ArrayList<>();

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

    static void performPushAttack(Player player) {
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (playerPatch != null && !playerPatch.getEntityState().attacking() && playerPatch.getEntityState().canBasicAttack()) {
            float staminatoconsume = playerPatch.getMaxStamina() * (ItemStackUtils.getPushConsumption(player.getMainHandItem()) / 100f);
            float currentstamina = playerPatch.getStamina();
            if (currentstamina >= staminatoconsume) {
                playerPatch.setStamina(currentstamina - staminatoconsume);
                String pushmotion = ItemStackUtils.getPushMotion(player, player.getMainHandItem());
                if (!pushmotion.isEmpty()) {
                    AnimationManager.AnimationAccessor<? extends StaticAnimation> animation = AnimationManager.byKey(pushmotion);
                    if (animation != null) {
                        pushingEntities.add(player);
                        AnimUtils.playAnimation(player, animation);
                        removeEntityFromPushingList(player,(int) (600 * (animation.get().getTotalTime() / animation.get().getPlaySpeed(playerPatch, animation.get()))));
                    }
                }
            }
        }
    }

    static void removeEntityFromPushingList(LivingEntity ent, int animduration) {
        Executors.newScheduledThreadPool(1).schedule(() -> {
            if (ent != null) {
                pushingEntities.remove(ent);
            }
        }, animduration, TimeUnit.MILLISECONDS);
    }
}
