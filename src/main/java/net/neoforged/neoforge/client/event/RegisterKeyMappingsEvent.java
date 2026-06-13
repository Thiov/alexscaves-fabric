package net.neoforged.neoforge.client.event;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

/**
 * 26.1 / Fabric API 0.146.1: the keybinding helper moved to
 * net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper and registerKeyBinding became
 * registerKeyMapping.
 */
public class RegisterKeyMappingsEvent {
    public void register(KeyMapping keyMapping) {
        KeyMappingHelper.registerKeyMapping(keyMapping);
    }
}
