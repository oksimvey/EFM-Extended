package com.robson.efmextended.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.client.input.EpicFightKeyMappings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.robson.efmextended.utils.CustomMotionsHandler.AttackType;

import static com.robson.efmextended.utils.CustomMotionsHandler.ACTIVE_HEAVY;
import static com.robson.efmextended.utils.CustomMotionsHandler.pushingEntities;
import static yesman.epicfight.client.events.engine.ControlEngine.isKeyDown;

public class CustomKey {

    private boolean isPressed = false;

    private byte presscounter = 0;

    private final byte counterforstart;

    protected boolean longPressTriggered = false;

    public CustomKey(byte counterforstart) {
        this.counterforstart = counterforstart;
    }

    public byte getPressCounter() {
        return this.presscounter;
    }

    public void onPressTick(Player player) {

        List<CustomMotionsHandler.AttackType> arr = CustomMotionsHandler.MOTIONS_HANDLER.getOrDefault(player.getUUID(),
                new ArrayList<>());

        if (!this.isPressed) {

            this.presscounter = 0;
            this.isPressed = true;
            if (isKeyDown(EpicFightKeyMappings.GUARD)) {

                CustomMotionsHandler.performPushAttack(player);
                return;
            }
        }
        if (pushingEntities.contains(player)) {
            return;
        }

        if (longPressTriggered && ACTIVE_HEAVY.containsKey(player.getUUID())) {
            ACTIVE_HEAVY.put(player.getUUID(), (byte) Math.min(ACTIVE_HEAVY.get(player.getUUID()) + 1, 60));
        }

        if (this.isPressed() && !longPressTriggered) {

            this.presscounter++;

            if (this.presscounter >= this.counterforstart) {
                this.presscounter = 0;
                this.isPressed = false;

                arr.add(AttackType.HEAVY);

                CustomMotionsHandler.MOTIONS_HANDLER.put(player.getUUID(), arr);

                
                CustomMotionsHandler.performCustomMotionHeavyAttack(player);

                String attacks = "";

                for (CustomMotionsHandler.AttackType attackType : arr) {
                    attacks += attackType + " ";
                }
                player.sendSystemMessage(Component.literal(attacks));

                this.longPressTriggered = true;

                Scheduler.schedule(() -> ACTIVE_HEAVY.put(player.getUUID(), (byte) 1), 50, TimeUnit.MILLISECONDS);

            }
        }
    }

    public boolean isPressed() {
        return this.isPressed;
    }

    public void onRelease(Player player) {
        List<CustomMotionsHandler.AttackType> arr = CustomMotionsHandler.MOTIONS_HANDLER.getOrDefault(player.getUUID(),
                new ArrayList<>());
        if (!this.longPressTriggered && this.isPressed) {

            arr.add(AttackType.LIGHT);
            CustomMotionsHandler.MOTIONS_HANDLER.put(player.getUUID(), arr);

             CustomMotionsHandler.performCustomMotionLightAttack(player);

           
            String attacks = "";

            for (CustomMotionsHandler.AttackType attackType : arr) {
                attacks += attackType + " ";
            }

            player.sendSystemMessage(Component.literal(attacks));
        }
        this.isPressed = false;
        this.presscounter = 0;
        this.longPressTriggered = false;
        ACTIVE_HEAVY.remove(player.getUUID());

    }
}