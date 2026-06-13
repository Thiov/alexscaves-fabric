package net.neoforged.neoforge.client.model;

import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.cuboid.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BakedModelWrapper implements BakedModel {

    protected final BakedModel originalModel;

    public BakedModelWrapper(BakedModel originalModel) {
        this.originalModel = originalModel;
    }

    
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource random) {
        return originalModel.getQuads(state, side, random);
    }

    
    public boolean useAmbientOcclusion() {
        return originalModel.useAmbientOcclusion();
    }

    
    public boolean isGui3d() {
        return originalModel.isGui3d();
    }

    
    public boolean usesBlockLight() {
        return originalModel.usesBlockLight();
    }

    
    public boolean isCustomRenderer() {
        return originalModel.isCustomRenderer();
    }

    
    public TextureAtlasSprite getParticleIcon() {
        return originalModel.getParticleIcon();
    }

    
    public ItemTransforms getTransforms() {
        return originalModel.getTransforms();
    }
}
