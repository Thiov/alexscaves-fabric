package com.github.alexmodguy.alexscaves.client.render.item;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.model.layered.ACModelLayers;
import com.github.alexmodguy.alexscaves.client.model.layered.DarknessArmorModel;
import com.github.alexmodguy.alexscaves.client.model.layered.DivingArmorModel;
import com.github.alexmodguy.alexscaves.client.model.layered.GingerbreadArmorModel;
import com.github.alexmodguy.alexscaves.client.model.layered.HazmatArmorModel;
import com.github.alexmodguy.alexscaves.client.model.layered.PrimordialArmorModel;
import com.github.alexmodguy.alexscaves.client.model.layered.RainbounceArmorModel;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.item.DarknessArmorItem;
import com.github.alexmodguy.alexscaves.server.item.DivingArmorItem;
import com.github.alexmodguy.alexscaves.server.item.GingerbreadArmorItem;
import com.github.alexmodguy.alexscaves.server.item.HazmatArmorItem;
import com.github.alexmodguy.alexscaves.server.item.PrimordialArmorItem;
import com.github.alexmodguy.alexscaves.server.item.RainbounceBootsItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Fabric replacement for AC's NeoForge {@code IClientItemExtensions} armor rendering, using AC's own
 * custom 3D {@link HumanoidModel}s (their 128x128 / 64x64 textures are NOT the vanilla 64x32 humanoid
 * layout, so the default equipment renderer garbles them).
 *
 * 26.1's {@code submitModel} is DEFERRED — it stores the model reference and only renders it later, so
 * a single shared model instance mutated per slot ends up rendering every piece with the last slot's
 * visibility (pieces invisible/white). We therefore keep one dedicated model instance PER (armor, slot),
 * its part visibility set once at creation; {@code setupAnim} (run by the deferred renderer from the
 * stored render state) only touches rotations, never visibility, so the cached instances stay correct.
 */
public class ACFabricArmorRenderer {

    private static final Map<String, HumanoidModel<?>> SLOT_MODELS = new HashMap<>();

    public static void register() {
        ArmorRenderer renderer = ACFabricArmorRenderer::render;
        ArmorRenderer.register(renderer,
            ACItemRegistry.PRIMORDIAL_HELMET.get(), ACItemRegistry.PRIMORDIAL_TUNIC.get(), ACItemRegistry.PRIMORDIAL_PANTS.get(),
            ACItemRegistry.HAZMAT_MASK.get(), ACItemRegistry.HAZMAT_CHESTPLATE.get(), ACItemRegistry.HAZMAT_LEGGINGS.get(), ACItemRegistry.HAZMAT_BOOTS.get(),
            ACItemRegistry.DIVING_HELMET.get(), ACItemRegistry.DIVING_CHESTPLATE.get(), ACItemRegistry.DIVING_LEGGINGS.get(), ACItemRegistry.DIVING_BOOTS.get(),
            ACItemRegistry.HOOD_OF_DARKNESS.get(), ACItemRegistry.CLOAK_OF_DARKNESS.get(),
            ACItemRegistry.RAINBOUNCE_BOOTS.get(),
            ACItemRegistry.GINGERBREAD_HELMET.get(), ACItemRegistry.GINGERBREAD_CHESTPLATE.get(), ACItemRegistry.GINGERBREAD_LEGGINGS.get(), ACItemRegistry.GINGERBREAD_BOOTS.get()
        );
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void render(PoseStack poseStack, SubmitNodeCollector collector, ItemStack stack, HumanoidRenderState renderState, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        Item item = stack.getItem();
        HumanoidModel model = modelFor(item, slot);
        if (model == null) {
            return;
        }
        Identifier texture = textureFor(item, slot);
        net.minecraft.client.renderer.rendertype.RenderType renderType = RenderTypes.armorCutoutNoCull(texture);
        int tintedColor = -1;
        // Mirror vanilla EquipmentLayerRenderer's submit EXACTLY. The 8-arg default overload maps its
        // 7th int to outlineColor (it hardcodes tintedColor=-1, sprite=null), so passing -1 there set
        // outlineColor=-1. Since armorCutoutNoCull is AFFECTS_OUTLINE, the deferred ModelFeatureRenderer
        // then re-rendered the model into the entity-outline (glow) target with solid white — invisible
        // in the GUI (which doesn't composite that target) but COMPLETELY WHITE in the world. Calling the
        // core 10-arg overload with outlineColor=0 (like vanilla) suppresses that stray outline pass.
        collector.submitModel(model, renderState, poseStack, renderType,
            light, OverlayTexture.NO_OVERLAY,
            tintedColor,    // DIAGNOSTIC: magenta for diving, -1 (no tint) otherwise
            null,  // sprite (standalone texture lives in the RenderType)
            0,     // outlineColor — no entity-outline pass
            (ModelFeatureRenderer.CrumblingOverlay) null);
    }

    @SuppressWarnings("rawtypes")
    private static HumanoidModel modelFor(Item item, EquipmentSlot slot) {
        String type = typeKey(item);
        if (type == null) {
            return null;
        }
        String key = type + "#" + slot.name();
        HumanoidModel<?> model = SLOT_MODELS.get(key);
        if (model == null) {
            model = build(type);
            if (model == null) {
                return null;
            }
            setPartVisibility(model, slot);
            SLOT_MODELS.put(key, model);
        }
        return (HumanoidModel) model;
    }

    private static String typeKey(Item item) {
        if (item instanceof PrimordialArmorItem) return "primordial";
        if (item instanceof HazmatArmorItem) return "hazmat";
        if (item instanceof DivingArmorItem) return "diving";
        if (item instanceof DarknessArmorItem) return "darkness";
        if (item instanceof RainbounceBootsItem) return "rainbounce";
        if (item instanceof GingerbreadArmorItem) return "gingerbread";
        return null;
    }

    private static HumanoidModel<?> build(String type) {
        EntityModelSet set = Minecraft.getInstance().getEntityModels();
        return switch (type) {
            case "primordial" -> new PrimordialArmorModel(set.bakeLayer(ACModelLayers.PRIMORDIAL_ARMOR));
            case "hazmat" -> new HazmatArmorModel(set.bakeLayer(ACModelLayers.HAZMAT_ARMOR));
            case "diving" -> new DivingArmorModel(set.bakeLayer(ACModelLayers.DIVING_ARMOR));
            case "darkness" -> new DarknessArmorModel(set.bakeLayer(ACModelLayers.DARKNESS_ARMOR));
            case "rainbounce" -> new RainbounceArmorModel(set.bakeLayer(ACModelLayers.RAINBOUNCE_ARMOR));
            case "gingerbread" -> new GingerbreadArmorModel(set.bakeLayer(ACModelLayers.GINGERBREAD_ARMOR));
            default -> null;
        };
    }

    private static void setPartVisibility(HumanoidModel<?> model, EquipmentSlot slot) {
        model.head.visible = false;
        model.hat.visible = false;
        model.body.visible = false;
        model.rightArm.visible = false;
        model.leftArm.visible = false;
        model.rightLeg.visible = false;
        model.leftLeg.visible = false;
        switch (slot) {
            case HEAD -> {
                model.head.visible = true;
                // HumanoidModel.createMesh auto-adds an inflated (deformation.extend(0.5)) vanilla "hat"
                // overlay as a child of head. AC helmets build their shape from the head box + custom child
                // parts and do NOT intend to use this overlay; when a helmet texture leaves it opaque it sits
                // ~1px in front of the head and paints over any visor/porthole (this was the diving-helmet
                // bronze-visor bug). So keep the hat hidden for all AC helmets EXCEPT Darkness, whose hood
                // texture intentionally uses the overlay (and has no visor behind it). Any future visored AC
                // helmet is therefore safe by default.
                model.hat.visible = model instanceof DarknessArmorModel;
            }
            case CHEST -> {
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
            }
            case LEGS -> {
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            case FEET -> {
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            default -> {
            }
        }
    }

    private static Identifier textureFor(Item item, EquipmentSlot slot) {
        // AC's custom 3D armor models use one skin (_0) for the whole model; the vanilla-style _1
        // leggings layer doesn't map to the custom model's UVs (rendered white), so use _0 for all slots.
        String path;
        if (item instanceof PrimordialArmorItem) {
            path = "primordial_armor_0";
        } else if (item instanceof HazmatArmorItem) {
            path = "hazmat_suit_0";
        } else if (item instanceof DivingArmorItem) {
            path = "diving_suit_0";
        } else if (item instanceof GingerbreadArmorItem) {
            path = "gingerbread_armor_0";
        } else if (item instanceof DarknessArmorItem) {
            path = "darkness_armor";
        } else if (item instanceof RainbounceBootsItem) {
            path = "rainbounce_boots";
        } else {
            path = "darkness_armor";
        }
        return Identifier.fromNamespaceAndPath(AlexsCaves.MODID, "textures/armor/" + path + ".png");
    }
}
