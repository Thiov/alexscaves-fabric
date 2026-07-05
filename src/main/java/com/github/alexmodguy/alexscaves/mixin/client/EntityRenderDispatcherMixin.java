package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.server.entity.util.NoSitRider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * No-sit rider pose. On Fabric, 26.1's {@code HumanoidMobRenderer} sets
 * {@code HumanoidRenderState.isPassenger = entity.isPassenger()} UNCONDITIONALLY (the
 * {@code shouldRiderSit()} gate is a NeoForge-only {@code Entity} patch), which forces the vanilla seated
 * leg-tuck on riders of mounts that should hold them upright (Subterranodon grip, Gum Worm). The per-renderer
 * {@code extractRenderState} sets {@code isPassenger} AFTER {@code super}, so clearing it in the base renderer
 * TAIL was overwritten — this clears it once the full state is built, at the dispatcher's extractEntity return.
 */
@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "extractEntity", at = @At("RETURN"))
    private void ac_clearNoSitRiderPose(Entity entity, float partialTicks, CallbackInfoReturnable<EntityRenderState> cir) {
        if (cir.getReturnValue() instanceof HumanoidRenderState hrs
                && entity.getVehicle() instanceof NoSitRider nsr && !nsr.shouldRiderSit()) {
            hrs.isPassenger = false;
        }
    }

    /**
     * Suppress the normal world-pass render of a passenger whose mount draws it itself at a perch
     * (see {@link com.github.alexmodguy.alexscaves.server.entity.util.RendersOwnRider}). Without this the
     * rider is drawn twice: once perched by the mount's rider layer, once floating at its raw positionRider anchor.
     */
    @Inject(method = "shouldRender(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z", at = @At("HEAD"), cancellable = true)
    private <E extends net.minecraft.world.entity.Entity> void ac_suppressPerchedRider(E entity, net.minecraft.client.renderer.culling.Frustum culler, double camX, double camY, double camZ, org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Boolean> cir) {
        if (entity.isPassenger() && entity.getVehicle() instanceof com.github.alexmodguy.alexscaves.server.entity.util.RendersOwnRider) {
            cir.setReturnValue(false);
        }
    }
}
