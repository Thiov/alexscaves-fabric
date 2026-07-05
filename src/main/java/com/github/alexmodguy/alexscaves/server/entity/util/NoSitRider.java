package com.github.alexmodguy.alexscaves.server.entity.util;

/**
 * Marker for mounts whose rider must NOT use the sitting (leg-tuck) pose.
 *
 * <p>Vanilla Fabric's {@code HumanoidMobRenderer} sets {@code renderState.isPassenger = entity.isPassenger()}
 * unconditionally (the upstream {@code shouldRiderSit()} gate is NeoForge-only), so on Fabric every rider gets
 * the sitting pose and each mount's {@code shouldRiderSit()} override is dead. {@link com.github.alexmodguy.alexscaves.mixin.client.VanillaLivingEntityRendererMixin}
 * consults this marker in {@code extractRenderState} to clear {@code isPassenger} when the vehicle opts out.
 */
public interface NoSitRider {
    default boolean shouldRiderSit() {
        return true;
    }
}
