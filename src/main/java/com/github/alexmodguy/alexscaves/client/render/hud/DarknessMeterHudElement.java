package com.github.alexmodguy.alexscaves.client.render.hud;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.gui.GuiCompat;
import com.github.alexmodguy.alexscaves.server.item.DarknessArmorItem;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Cloak of Darkness charge meter above the hotbar — ported from AC's NeoForge {@code RenderGuiLayerEvent}
 * handler (the whole client-event layer was dropped in this port). Registered via Fabric's 26.1
 * {@link net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry} (the old HudRenderCallback is gone).
 */
public class DarknessMeterHudElement implements HudElement {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(AlexsCaves.MODID, "darkness_meter");
    private static final Identifier ARMOR_HUD_OVERLAYS =
        Identifier.fromNamespaceAndPath(AlexsCaves.MODID, "textures/misc/armor_hud_overlays.png");

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null || minecraft.options.hideGui || minecraft.getCameraEntity() != player) {
            return;
        }
        if (!DarknessArmorItem.hasMeter(player)) {
            return;
        }
        ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int x = screenWidth / 2 - 9;
        int y = screenHeight - 53;
        float progress = DarknessArmorItem.getMeterProgress(stack);
        float invProgress = 1.0F - progress;
        int uvOffset = DarknessArmorItem.canChargeUp(stack) && progress >= 1.0F ? 0 : 18;
        // frame (charged = lit variant at u=0, else dim at u=18)
        GuiCompat.blit(guiGraphics, ARMOR_HUD_OVERLAYS, x, y, uvOffset, 19, 18, 19, 128, 128);
        // depleting overlay from the top, proportional to how much charge is missing
        int overlayHeight = (int) Math.floor(19 * invProgress);
        if (overlayHeight > 0) {
            GuiCompat.blit(guiGraphics, ARMOR_HUD_OVERLAYS, x, y, 0, 0, 18, overlayHeight, 128, 128);
        }
    }
}
