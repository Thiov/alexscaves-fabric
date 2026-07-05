package com.github.alexmodguy.alexscaves.client.render.entity.compat;

import com.github.alexmodguy.alexscaves.client.render.compat.SubmitNodeBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public abstract class EntityRenderer121X<T extends Entity>
        extends net.minecraft.client.renderer.entity.EntityRenderer<T, EntityRenderState> {
    protected EntityRenderer121X(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState121X();
    }

    @Override
    public void extractRenderState(T entity, EntityRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        // Store the live entity + interpolated yaw on the PER-ENTITY state (not a shared renderer field) so
        // each entity submits from ITS OWN data — 26.1 extracts every render state before submitting any, so
        // a shared field held the last-extracted entity and all instances rendered as that one.
        EntityRenderState121X s = (EntityRenderState121X) state;
        s.ac_entity = entity;
        s.ac_partialTicks = partialTicks;
        s.ac_yaw = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        super.submit(state, poseStack, collector, cameraRenderState);
        EntityRenderState121X s = (EntityRenderState121X) state;
        if (s.ac_entity != null) {
            SubmitNodeBufferSource capture = new SubmitNodeBufferSource();
            capture.bindLive(collector, poseStack);
            capture.setCameraState(cameraRenderState);
            this.render((T) s.ac_entity, s.ac_yaw, s.ac_partialTicks, poseStack, capture, state.lightCoords);
            capture.flushInto(collector, poseStack);
        }
    }

    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource source,
            int packedLight) {
    }

    public void render(T entity, double x, double y, double z, float entityYaw, float partialTicks,
            PoseStack poseStack, MultiBufferSource source, int packedLight) {
        this.render(entity, entityYaw, partialTicks, poseStack, source, packedLight);
    }

    public abstract Identifier getTextureLocation(T entity);
}
