package com.github.alexmodguy.alexscaves.server.block.blockentity;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ACBlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> DEF_REG = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AlexsCaves.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VolcanicCoreBlockEntity>> VOLCANIC_CORE = DEF_REG.register("volcanic_core", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(VolcanicCoreBlockEntity::new, ACBlockRegistry.VOLCANIC_CORE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MagnetBlockEntity>> MAGNET = DEF_REG.register("magnet", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(MagnetBlockEntity::new, ACBlockRegistry.SCARLET_MAGNET.get(), ACBlockRegistry.AZURE_MAGNET.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TeslaBulbBlockEntity>> TESLA_BULB = DEF_REG.register("tesla_bulb", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(TeslaBulbBlockEntity::new, ACBlockRegistry.TESLA_BULB.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HologramProjectorBlockEntity>> HOLOGRAM_PROJECTOR = DEF_REG.register("hologram_projector", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(HologramProjectorBlockEntity::new, ACBlockRegistry.HOLOGRAM_PROJECTOR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<QuarryBlockEntity>> QUARRY = DEF_REG.register("quarry", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(QuarryBlockEntity::new, ACBlockRegistry.QUARRY.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AmbersolBlockEntity>> AMBERSOL = DEF_REG.register("ambersol", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(AmbersolBlockEntity::new, ACBlockRegistry.AMBERSOL.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AmberMonolithBlockEntity>> AMBER_MONOLITH = DEF_REG.register("amber_monolith", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(AmberMonolithBlockEntity::new, ACBlockRegistry.AMBER_MONOLITH.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GeothermalVentBlockEntity>> GEOTHERMAL_VENT = DEF_REG.register("geothermal_vent", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(GeothermalVentBlockEntity::new, ACBlockRegistry.GEOTHERMAL_VENT.get(), ACBlockRegistry.GEOTHERMAL_VENT_MEDIUM.get(), ACBlockRegistry.GEOTHERMAL_VENT_THIN.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NuclearFurnaceBlockEntity>> NUCLEAR_FURNACE = DEF_REG.register("nuclear_furnace", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(NuclearFurnaceBlockEntity::new, ACBlockRegistry.NUCLEAR_FURNACE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SirenLightBlockEntity>> SIREN_LIGHT = DEF_REG.register("siren_light", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(SirenLightBlockEntity::new, ACBlockRegistry.SIREN_LIGHT.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NuclearSirenBlockEntity>> NUCLEAR_SIREN = DEF_REG.register("nuclear_siren", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(NuclearSirenBlockEntity::new, ACBlockRegistry.NUCLEAR_SIREN.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MetalBarrelBlockEntity>> METAL_BARREL = DEF_REG.register("metal_barrel", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(MetalBarrelBlockEntity::new, ACBlockRegistry.METAL_BARREL.get(), ACBlockRegistry.RUSTY_BARREL.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AbyssalAltarBlockEntity>> ABYSSAL_ALTAR = DEF_REG.register("abyssal_altar", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(AbyssalAltarBlockEntity::new, ACBlockRegistry.ABYSSAL_ALTAR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CopperValveBlockEntity>> COPPER_VALVE = DEF_REG.register("copper_valve", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(CopperValveBlockEntity::new, ACBlockRegistry.COPPER_VALVE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnigmaticEngineBlockEntity>> ENIGMATIC_ENGINE = DEF_REG.register("enigmatic_engine", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(EnigmaticEngineBlockEntity::new, ACBlockRegistry.ENIGMATIC_ENGINE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BeholderBlockEntity>> BEHOLDER = DEF_REG.register("beholder", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(BeholderBlockEntity::new, ACBlockRegistry.BEHOLDER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GobthumperBlockEntity>> GOBTHUMPER = DEF_REG.register("gobthumper", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(GobthumperBlockEntity::new, ACBlockRegistry.GOBTHUMPER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ConversionCrucibleBlockEntity>> CONVERSION_CRUCIBLE = DEF_REG.register("conversion_crucible", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(ConversionCrucibleBlockEntity::new, ACBlockRegistry.CONVERSION_CRUCIBLE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GingerbarrelBlockEntity>> GINGERBARREL = DEF_REG.register("gingerbarrel", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(GingerbarrelBlockEntity::new, ACBlockRegistry.GINGERBARREL.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ConfectionOvenBlockEntity>> CONFECTION_OVEN = DEF_REG.register("confection_oven", () -> net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create(ConfectionOvenBlockEntity::new, ACBlockRegistry.CONFECTION_OVEN.get()).build(null));

    public static void expandVanillaDefinitions() {
        // Vanilla 1.21.1 keeps the sign valid-block set private. Revisit with a mixin or access widener.
    }
}
