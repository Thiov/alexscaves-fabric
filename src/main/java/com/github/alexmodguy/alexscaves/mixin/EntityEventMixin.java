package com.github.alexmodguy.alexscaves.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.TeleportTransition;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// 26.1: Entity#changeDimension(TeleportTransition) was renamed to Entity#teleport(TeleportTransition)
// (still returns Entity, so the existing CallbackInfoReturnable<Entity> handler still fits).
// refreshDimensions / dimensions / eyeHeight / getDimensions / reapplyPosition /
// fudgePositionAfterSizeChange are all unchanged in 26.1.
@Mixin(Entity.class)
public abstract class EntityEventMixin {

    @Shadow
    private EntityDimensions dimensions;

    @Shadow
    private float eyeHeight;

    @Shadow
    protected boolean firstTick;

    @Shadow
    public boolean noPhysics;

    @Shadow
    public abstract Pose getPose();

    @Shadow
    public abstract EntityDimensions getDimensions(Pose pose);

    @Shadow
    protected abstract void reapplyPosition();

    @Shadow
    public abstract boolean fudgePositionAfterSizeChange(EntityDimensions oldDimensions);

    @Inject(method = "teleport", at = @At("HEAD"))
    private void ac_postDimensionTravelEvent(TeleportTransition dimensionTransition, CallbackInfoReturnable<Entity> cir) {
        NeoForge.EVENT_BUS.post(new EntityTravelToDimensionEvent((Entity) (Object) this));
    }

    @Inject(method = "refreshDimensions", at = @At("HEAD"), cancellable = true)
    private void ac_postResizeEvent(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        EntityDimensions oldDimensions = this.dimensions;
        EntityDimensions newDimensions = this.getDimensions(this.getPose());
        EntityEvent.Size event = new EntityEvent.Size(entity, oldDimensions, newDimensions);
        NeoForge.EVENT_BUS.post(event);
        newDimensions = event.getNewSize();
        this.dimensions = newDimensions;
        this.eyeHeight = newDimensions.eyeHeight();
        this.reapplyPosition();

        boolean canAdjustPosition = newDimensions.width() <= 4.0F && newDimensions.height() <= 4.0F;
        if (!entity.level().isClientSide()
            && !this.firstTick
            && !this.noPhysics
            && canAdjustPosition
            && (newDimensions.width() > oldDimensions.width() || newDimensions.height() > oldDimensions.height())
            && !(entity instanceof Player)) {
            this.fudgePositionAfterSizeChange(oldDimensions);
        }
        ci.cancel();
    }
}
