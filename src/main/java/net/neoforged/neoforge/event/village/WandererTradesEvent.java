package net.neoforged.neoforge.event.village;

import net.minecraft.world.item.trading.VillagerTrades;
import net.neoforged.bus.api.Event;

import java.util.List;

public class WandererTradesEvent extends Event {
    private final List<com.github.alexmodguy.alexscaves.server.entity.util.ACItemListing> genericTrades;

    public WandererTradesEvent(List<com.github.alexmodguy.alexscaves.server.entity.util.ACItemListing> genericTrades) {
        this.genericTrades = genericTrades;
    }

    public List<com.github.alexmodguy.alexscaves.server.entity.util.ACItemListing> getGenericTrades() {
        return genericTrades;
    }
}
