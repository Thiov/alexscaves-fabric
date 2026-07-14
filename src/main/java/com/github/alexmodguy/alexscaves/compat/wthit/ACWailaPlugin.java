package com.github.alexmodguy.alexscaves.compat.wthit;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.entity.PartEntity;

/**
 * Optional WTHIT (What The Hell Is That) compat. Only classloaded when WTHIT is installed (declared via
 * wthit_plugins.json). AC's multipart mobs (Tremorzilla, Luxtructosaurus, Magnetron, Hullbreaker, ...) expose
 * invisible hitbox part entities; without this override WTHIT's raycast shows the raw part (correct name via
 * the shared EntityType, but no health bar / living data). Redirecting to {@link PartEntity#getParent()} makes
 * the tooltip show the parent mob with its full name + health. One registration on the shim base class covers
 * every part entity in the mod.
 */
public class ACWailaPlugin implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        // Never let an optional-compat registration take the whole game down at startup (a WTHIT API/version
        // mismatch or an internal WTHIT change would otherwise crash on launch when both mods are present).
        try {
            registrar.addOverride(new PartOverrideProvider(), PartEntity.class);
        } catch (Throwable t) {
            com.github.alexmodguy.alexscaves.AlexsCaves.LOGGER.warn("Failed to register Alex's Caves WTHIT compat (multipart tooltip override); the rest of WTHIT is unaffected.", t);
        }
    }

    private static final class PartOverrideProvider implements IEntityComponentProvider {
        @Override
        public Entity getOverride(IEntityAccessor accessor, IPluginConfig config) {
            if (accessor.getEntity() instanceof PartEntity<?> part) {
                return part.getParent();
            }
            return null;
        }
    }
}
