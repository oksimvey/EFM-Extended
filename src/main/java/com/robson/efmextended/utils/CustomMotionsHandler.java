package com.robson.efmextended.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.robson.efmextended.mixins.ItemCapabilityReloadListenerMixin;

public interface CustomMotionsHandler {

    enum AttackType {
        LIGHT,
        HEAVY
    }

    ConcurrentHashMap<UUID, List<AttackType>> MOTIONS_HANDLER = new ConcurrentHashMap<>();

    static void resetMotions(Player player) {
        MOTIONS_HANDLER.put(player.getUUID(), new ArrayList<>());
    }

    List<LivingEntity> pushingEntities = new ArrayList<>();

    ConcurrentHashMap<UUID, Byte> ACTIVE_HEAVY = new ConcurrentHashMap<>();

    static void performCustomMotionLightAttack(Player player) {
        if (player != null) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);

            if (playerPatch != null && !playerPatch.getEntityState().attacking()) {
                CompoundTag customMotions = ItemStackUtils.getCustomMotion(player, player.getMainHandItem());
                if (customMotions != null) {

                    if (player.isSprinting()) {
                        AnimUtils.playAnimation(player, customMotions.getString("light_dash"));
                        resetMotions(player);
                        return;
                    }
                    if (EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class).isInAir()) {
                        AnimUtils.playAnimation(player, "light_airslash");
                         resetMotions(player);
                        return;
                    }

                    List<AttackType> currentMotions = MOTIONS_HANDLER.getOrDefault(player.getUUID(), new ArrayList<>());

                    CompoundTag currentNode = customMotions.copy();

                    for (AttackType attack : currentMotions) {
                        if (attack == AttackType.LIGHT) {
                            if (currentNode.contains("next_light")) {
                                currentNode = currentNode.getCompound("next_light");
                            } 
                            else {  

                                    List<AttackType>arr = new ArrayList<>();

                                arr.add(AttackType.LIGHT);
                                
                               MOTIONS_HANDLER.put(player.getUUID(), arr);


                                 currentNode = customMotions.getCompound("next_light");

                                 break;    
                            }
                        } 
                        else if (attack == AttackType.HEAVY) {
                            if (currentNode.contains("next_heavy")) {
                                currentNode = currentNode.getCompound("next_heavy");
                            } 
                            else {

                              List<AttackType>arr = new ArrayList<>();

                                arr.add(AttackType.HEAVY);
                                
                               MOTIONS_HANDLER.put(player.getUUID(), arr);


                                 currentNode = customMotions.getCompound("next_heavy");

                                 break;
                                
                            }
                        }
                    }
                    if (currentNode.contains("motion")) {
                        AnimUtils.playAnimation(player, currentNode.getString("motion"));
                    
                    } 
                    else  resetMotions(player);
                }
            }
        }
    }

    static void performCustomMotionHeavyAttack(Player player) {
        if (player != null) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);

            if (playerPatch != null && !playerPatch.getEntityState().attacking()) {
                CompoundTag customMotions = ItemStackUtils.getCustomMotion(player, player.getMainHandItem());
                if (customMotions != null) {

                    if (player.isSprinting()) {
                        AnimUtils.playAnimation(player, customMotions.getString("heavy_dash"));
                         resetMotions(player);
                     return;
                    }
                    if (EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class).isInAir()) {
                        AnimUtils.playAnimation(player, "heavy_airslash");
                         resetMotions(player);
                       return;
                    }

                    List<AttackType> currentMotions = MOTIONS_HANDLER.getOrDefault(player.getUUID(), new ArrayList<>());

                    CompoundTag currentNode = customMotions.copy();

                    for (AttackType attack : currentMotions) {
                        if (attack == AttackType.LIGHT) {

                            if (currentNode.contains("next_light")) {
                                currentNode = currentNode.getCompound("next_light");
                            } 

                            else { 

                                List<AttackType>arr = new ArrayList<>();

                                arr.add(AttackType.LIGHT);

                               MOTIONS_HANDLER.put(player.getUUID(), arr);

                                currentNode = customMotions.getCompound("next_light");

                                break;
                            }
                        } 

                        else if (attack == AttackType.HEAVY) {
                            if (currentNode.contains("next_heavy")) {
                                currentNode = currentNode.getCompound("next_heavy");
                            } 
                            else {
                               
                                 List<AttackType>arr = new ArrayList<>();

                                arr.add(AttackType.HEAVY);
                                
                               MOTIONS_HANDLER.put(player.getUUID(), arr);


                                 currentNode = customMotions.getCompound("next_heavy");

                                 break;

                            }
                        }

                    }
                    if (currentNode.contains("motion")) {
                        AnimUtils.playAnimation(player, currentNode.getString("motion"));
                    } 

                    else  resetMotions(player);

                }
            }
        }
    }

    static void performPushAttack(Player player) {
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (playerPatch != null && playerPatch.getSkill(SkillSlots.GUARD).getSkill() instanceof GuardSkill
                && !playerPatch.getEntityState().attacking() && playerPatch.getEntityState().canBasicAttack()) {
            float staminatoconsume = playerPatch.getMaxStamina()
                    * (ItemStackUtils.getPushConsumption(player.getMainHandItem()) / 100f);
            float currentstamina = playerPatch.getStamina();
            if (currentstamina >= staminatoconsume) {
                playerPatch.setStamina(currentstamina - staminatoconsume);
                String pushmotion = ItemStackUtils.getPushMotion(player, player.getMainHandItem());
                if (!pushmotion.isEmpty()) {
                    AnimationManager.AnimationAccessor<? extends StaticAnimation> animation = AnimationManager
                            .byKey(pushmotion);
                    if (animation != null) {
                        pushingEntities.add(player);
                        AnimUtils.playAnimation(player, animation);
                        removeEntityFromPushingList(player, (int) (600 * (animation.get().getTotalTime()
                                / animation.get().getPlaySpeed(playerPatch, animation.get()))));
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
