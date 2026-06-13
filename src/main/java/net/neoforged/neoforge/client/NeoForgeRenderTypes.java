package net.neoforged.neoforge.client;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public final class NeoForgeRenderTypes {
    private NeoForgeRenderTypes() {
    }

    public static RenderType getUnlitTranslucent(Identifier texture) {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture);
    }
}
