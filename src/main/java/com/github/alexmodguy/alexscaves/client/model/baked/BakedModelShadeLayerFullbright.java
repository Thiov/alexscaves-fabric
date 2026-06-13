package com.github.alexmodguy.alexscaves.client.model.baked;

import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 26.1: BakedQuad is now an immutable record (no int[] vertex data, no per-quad light override),
 * and baked-model rendering is bypassed in this port (custom renderers go through the render compat
 * helpers). The fullbright override therefore degrades to a pass-through wrapper.
 */
public class BakedModelShadeLayerFullbright extends BakedModelWrapper {

    public BakedModelShadeLayerFullbright(BakedModel originalModel) {
        super(originalModel);
    }

    
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        return originalModel.getQuads(state, side, rand);
    }
}
