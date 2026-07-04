package net.neoforged.neoforge.client;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public final class NeoForgeRenderTypes {
    private NeoForgeRenderTypes() {
    }

    public static RenderType getUnlitTranslucent(Identifier texture) {
        // "Unlit" means no diffuse shading and no lightmap dimming — but vanilla's emissive translucent also
        // disables depth writes (book text bled through the cover), so use AC's own unlit clone that keeps
        // the default depth-writing state.
        return com.github.alexmodguy.alexscaves.client.render.ACRenderTypes.getUnlitTranslucent(texture);
    }
}
