package com.github.alexmodguy.alexscaves.mixin.client;

/**
 * 26.1 removed net.minecraft.client.renderer.LightTexture entirely (the light/brightness pipeline
 * was rewritten), so this mixin can no longer target it. It is neutralized to an empty, non-mixin
 * class and removed from the 26.1 alexscaves.mixins.json overlay.
 *
 * Its brightness boosting (biome ambient light / Deepsight / primordial boss) was RESTORED in
 * LightmapRenderStateExtractorMixin (v2.0.2-29/-34) — this class remains only as a placeholder.
 */
public final class LightTextureMixin {
    private LightTextureMixin() {
    }
}
