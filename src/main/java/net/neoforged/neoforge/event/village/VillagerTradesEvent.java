package net.neoforged.neoforge.event.village;

import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.VillagerTrades;
import net.neoforged.bus.api.Event;

import java.util.List;
import java.util.Map;

public class VillagerTradesEvent extends Event {
    private final VillagerProfession type;
    private final Map<Integer, List<com.github.alexmodguy.alexscaves.server.entity.util.ACItemListing>> trades;

    public VillagerTradesEvent(VillagerProfession type, Map<Integer, List<com.github.alexmodguy.alexscaves.server.entity.util.ACItemListing>> trades) {
        this.type = type;
        this.trades = trades;
    }

    public VillagerProfession getType() {
        return type;
    }

    public Map<Integer, List<com.github.alexmodguy.alexscaves.server.entity.util.ACItemListing>> getTrades() {
        return trades;
    }
}
