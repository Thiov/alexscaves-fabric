package com.github.alexmodguy.alexscaves.mixin.client;


import com.github.alexmodguy.alexscaves.fabric.MultipartEntityLevel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import net.neoforged.neoforge.entity.PartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// 26.1: ClientLevel#getSkyColor(Vec3, float) and getSkyDarken(float) were removed (sky color/darken
// now comes from the dimension environment-attribute layers, not ClientLevel). The biome sky-override
// hooks have no drop-in equivalent on ClientLevel and are dropped here so the mixin applies cleanly.
// The getEntity hook (needed so multipart PartEntities resolve by id on the client) is retained.
@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level {

    protected ClientLevelMixin(WritableLevelData levelData, ResourceKey<Level> levelKey, RegistryAccess registryAccess,
                               Holder<DimensionType> dimensionType, boolean isClientSide, boolean isDebug,
                               long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, levelKey, registryAccess, dimensionType, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Inject(method = "getEntity", at = @At("RETURN"), cancellable = true)
    private void alexscaves$getEntity(int id, CallbackInfoReturnable<Entity> cir) {
        if (cir.getReturnValue() != null) {
            return;
        }
        PartEntity<?> part = ((MultipartEntityLevel) this).alexscaves$getPartEntityMap().get(id);
        if (part != null) {
            cir.setReturnValue(part);
        }
    }
}
