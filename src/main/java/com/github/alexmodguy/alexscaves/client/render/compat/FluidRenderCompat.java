package com.github.alexmodguy.alexscaves.client.render.compat;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;

/**
 * Bridges the removed Fabric SimpleFluidRenderHandler/FluidRenderHandlerRegistry
 * registration style onto the 26.1 FluidRenderingRegistry + FluidModel API.
 */
public class FluidRenderCompat {

    public static void register(Fluid still, Fluid flowing, Identifier[] textures) {
        FluidModel.Unbaked model = new FluidModel.Unbaked(
                new Material(textures[0]),
                new Material(textures[1]),
                new Material(textures[0]),
                BlockTintSources.constant(-1));
        FluidRenderingRegistry.register(still, flowing, model);
    }

    public static Identifier[] handler(Identifier stillTexture, Identifier flowingTexture) {
        return new Identifier[]{stillTexture, flowingTexture};
    }
}
