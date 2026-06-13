package net.neoforged.neoforge.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 26.1 / Fabric API 0.146.1 port shim.
 *
 * Block colors: the old net.minecraft.client.color.block.BlockColor functional interface and the
 * old ColorProviderRegistry are gone. Block tinting now uses BlockTintSource, registered via
 * net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry.register(List, Block...). The old
 * BlockColor lambda takes a trailing tint index; BlockTintSource has no tint index, so we feed the
 * lambda tint index 0 (which is what Alex's Caves' tinted blocks use).
 *
 * Item colors: Fabric API 0.146.1 provides no imperative item-color registry (item tinting is now
 * data-driven via ItemTintSource components). The item-color hook is therefore a no-op.
 *
 * DEGRADATION: modded item color handlers (Cave Tablet/Codex/Gazing Pearl/Jelly Bean/Biome Treat)
 * are not applied on 26.1; their dynamic tint falls back to the model's default colors.
 */
public final class RegisterColorHandlersEvent {
    private RegisterColorHandlersEvent() {
    }

    @FunctionalInterface
    public interface ItemColor {
        int getColor(ItemStack stack, int tintIndex);
    }

    @FunctionalInterface
    public interface BlockColor {
        int getColor(BlockState state, BlockAndTintGetter getter, BlockPos pos, int tintIndex);
    }

    public static class Item {
        public void register(ItemColor color, ItemLike... items) {
            // No-op: see class doc. Item tinting is data-driven in 26.1.
        }
    }

    public static class Block {
        public void register(BlockColor color, net.minecraft.world.level.block.Block... blocks) {
            BlockTintSource source = new BlockTintSource() {
                
                public int color(BlockState state) {
                    return color.getColor(state, null, null, 0);
                }

                
                public int colorInWorld(BlockState state, BlockAndTintGetter getter, BlockPos pos) {
                    return color.getColor(state, getter, pos, 0);
                }
            };
            BlockColorRegistry.register(List.of(source), blocks);
        }
    }
}
