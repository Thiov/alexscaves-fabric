package net.neoforged.neoforge.client.extensions.common;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public interface IClientFluidTypeExtensions {

    default Identifier getStillTexture() {
        return null;
    }

    default Identifier getFlowingTexture() {
        return null;
    }

    default Identifier getRenderOverlayTexture(Minecraft minecraft) {
        return null;
    }
}
