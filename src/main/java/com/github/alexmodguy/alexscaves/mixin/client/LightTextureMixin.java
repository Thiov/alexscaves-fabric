package com.github.alexmodguy.alexscaves.mixin.client;

/**
 * 26.1 removed net.minecraft.client.renderer.LightTexture entirely (the light/brightness pipeline
 * was rewritten), so this mixin can no longer target it. It is neutralized to an empty, non-mixin
 * class and removed from the 26.1 alexscaves.mixins.json overlay.
 *
 * DEGRADATION: the biome-ambient-light / Deepsight / primordial-boss brightness boosting that this
 * mixin provided is dropped on 26.1.
 */
public final class LightTextureMixin {
    private LightTextureMixin() {
    }
}
