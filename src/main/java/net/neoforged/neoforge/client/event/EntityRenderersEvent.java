package net.neoforged.neoforge.client.event;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import com.github.alexmodguy.alexscaves.client.render.entity.compat.EntityRenderer121X;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.function.Supplier;

/**
 * 26.1: the nested PlayerSkin.Model enum was replaced by the top-level
 * net.minecraft.world.entity.player.PlayerModelType (SLIM / WIDE).
 */
public final class EntityRenderersEvent {
    private EntityRenderersEvent() {
    }

    public static class RegisterLayerDefinitions {
        public void registerLayerDefinition(ModelLayerLocation location, Supplier<LayerDefinition> supplier) {
        }
    }

    public static class AddLayers {
        public List<PlayerModelType> getSkins() {
            return List.of();
        }

        public EntityRenderer121X getSkin(PlayerModelType model) {
            return null;
        }

        public EntityRenderer121X getRenderer(EntityType<?> entityType) {
            return null;
        }
    }
}
