package com.github.alexmodguy.alexscaves.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import net.minecraft.world.entity.Entity;

/**
 * AC-owned base carrying the {@code young} flag that AC's previously-bundled Citadel added to BasicEntityModel
 * but the standalone Citadel does not. Only the ~10 models that read {@code this.young} extend this.
 */
public abstract class ACAdvancedEntityModel<T extends Entity> extends AdvancedEntityModel<T> {
    public boolean young;

    public void copyPropertiesTo(ACAdvancedEntityModel<?> other) {
        other.young = this.young;
    }
}
