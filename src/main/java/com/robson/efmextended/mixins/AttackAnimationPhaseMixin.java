package com.robson.efmextended.mixins;

import com.robson.efmextended.events.HurtEvents;
import com.robson.efmextended.utils.ItemStackUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.ArrayList;
import java.util.List;

import static com.robson.efmextended.utils.CustomMotionsHandler.pushingEntities;

@Mixin(targets = "yesman.epicfight.api.animation.types.AttackAnimation$Phase")
public class AttackAnimationPhaseMixin {

    @Inject(
            method = "getCollidingEntities",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void replaceColliderWithCone(
            LivingEntityPatch<?> entitypatch, AttackAnimation animation, float prevElapsedTime, float elapsedTime, float attackSpeed, CallbackInfoReturnable<List<Entity>> cir
    ) {
        if (!shouldUseCone(entitypatch)) {
            return; // deixa o collider normal rodar
        }

        float radius = getConeRadius(entitypatch, animation);
        List<Entity> result = coneCollision(entitypatch, radius);

        cir.setReturnValue(result);
        cir.cancel();
    }

    private boolean shouldUseCone(LivingEntityPatch<?> entitypatch) {
        return entitypatch instanceof PlayerPatch<?> playerPatch && pushingEntities.contains(playerPatch.getOriginal());
    }

    private float getConeRadius(LivingEntityPatch<?> entitypatch, AttackAnimation animation) {
        return ItemStackUtils.getPushRange(entitypatch.getOriginal().getMainHandItem()); // exemplo
    }

    private List<Entity> coneCollision(LivingEntityPatch<?> entitypatch, float radius) {
        LivingEntity owner = entitypatch.getOriginal();


        Vec3 apex = owner.getEyePosition(1.0F);

        Vec3 forward = owner.getLookAngle().normalize();

        forward = new Vec3(forward.x, 0.0, forward.z);
        if (forward.lengthSqr() < 1.0E-8) {
            forward = new Vec3(0.0, 0.0, 1.0);
        }
        forward = forward.normalize();

        double cosLimit = 0.5;

        AABB broadPhase = new AABB(apex, apex).inflate(radius).expandTowards(forward.scale(radius));

        List<Entity> found = owner.level().getEntities(
                owner,
                broadPhase,
                e -> isValidTarget(e, owner)
        );

        List<Entity> result = new ArrayList<>();

        for (Entity target : found) {
            Vec3 center = target.getBoundingBox().getCenter();
            Vec3 toTarget = center.subtract(apex);

            double dy = Math.abs(center.y - apex.y);

            double maxHeightDiff = 2;

            if (dy > maxHeightDiff) {
                continue;
            }

            // cone 2D
            toTarget = new Vec3(toTarget.x, 0.0, toTarget.z);

            double distSqr = toTarget.lengthSqr();

            if (distSqr > radius * radius || distSqr < 1.0E-8) {
                continue;
            }

            Vec3 dir = toTarget.normalize();

            if (forward.dot(dir) >= cosLimit) {
                result.add(target);
            }
        }

        return result;
    }

    private boolean isValidTarget(Entity e, LivingEntity owner) {
        if (e == owner) return false;
        if (!(e instanceof LivingEntity) && !(e instanceof PartEntity)) return false;
        return e.isAlive();
    }
}