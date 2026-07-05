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
}
