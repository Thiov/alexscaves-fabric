package com.github.alexmodguy.alexscaves.client.render.entity.compat;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;

/**
 * Per-entity render state for the 1.21-style compat renderers ({@link EntityRenderer121X}). Holds the live
 * entity + interpolated yaw + partialTicks so each submitted entity renders from ITS OWN data.
 *
 * <p>The bridge previously stashed these in shared fields on the RENDERER (currentEntity/…). 26.1 extracts
 * EVERY entity's render state first and only then submits them all, so at submit time the shared field held
 * the LAST-extracted entity — every entity of a type rendered as that last one (e.g. all Candicorns sharing
 * the newest one's colour variant + body facing, flipping as the view angle changed which was "last").
 * Storing the data on the per-entity state (distinct instance per entity, exactly like vanilla render
 * states) fixes it.</p>
 */
public class EntityRenderState121X extends EntityRenderState {
    public Entity ac_entity;
    public float ac_yaw;
    public float ac_partialTicks;
}
