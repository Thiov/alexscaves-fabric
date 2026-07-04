package com.github.alexmodguy.alexscaves.client.render.entity.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;

/**
 * 26.1 removed the immediate-mode vanilla {@code ArrowRenderer}. Upstream arrow renderers
 * (SeekingArrow) extended it to draw the vanilla arrow geometry; the port's stub used to draw
 * nothing, making those arrows invisible in flight. This reproduces vanilla
 * {@code ArrowRenderer#submit} + {@code ArrowModel#setupAnim} by baking the built-in
 * {@link ModelLayers#ARROW} part and drawing it through the port's submit-capture pipeline.
 */
public abstract class ArrowRenderer121X<T extends AbstractArrow> extends EntityRenderer121X<T> {

    private final ModelPart model;

    protected ArrowRenderer121X(EntityRendererProvider.Context context) {
        super(context);
        this.model = context.bakeLayer(ModelLayers.ARROW);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource source, int packedLight) {
        poseStack.pushPose();
        float yRot = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
        float xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(xRot));
        float shake = (float) entity.shakeTime - partialTicks;
        if (shake > 0.0F) {
            float pow = -Mth.sin(shake * 3.0F) * shake;
            poseStack.mulPose(Axis.ZP.rotationDegrees(pow));
        }
        VertexConsumer consumer = source.getBuffer(RenderTypes.entityCutoutCull(this.getTextureLocation(entity)));
        this.model.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, source, packedLight);
    }
}
