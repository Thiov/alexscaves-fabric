package com.github.alexmodguy.alexscaves.server.misc;

import com.github.alexthe666.citadel.server.entity.IDancesToJukebox;
import com.github.alexthe666.citadel.server.message.DanceJukeboxMessage;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Replacement for the IDancesToJukebox.onClientPlayMusicDisc default that AC's bundled Citadel carried but the
 * standalone Citadel does not. Client-side only; mirrors the original default verbatim.
 */
public final class ACDanceHelper {
    private ACDanceHelper() {
    }

    public static void onClientPlayMusicDisc(IDancesToJukebox dancer, int entityId, BlockPos pos, boolean dancing) {
        PacketDistributor.sendToServer(new DanceJukeboxMessage(entityId, dancing, pos));
        dancer.setDancing(dancing);
        dancer.setJukeboxPos(dancing ? pos : null);
    }
}
