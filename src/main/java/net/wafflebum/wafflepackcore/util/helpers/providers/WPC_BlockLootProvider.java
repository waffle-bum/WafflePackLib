package net.wafflebum.wafflepackcore.util.helpers.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public abstract class WPC_BlockLootProvider extends BlockLootSubProvider {

    protected WPC_BlockLootProvider(HolderLookup.Provider registries) {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    /**
     * CORE HELPER: Registers a collection of block suppliers (like DeferredBlocks) to drop themselves.
     */
    protected void dropSelf(Collection<? extends Supplier<? extends Block>> blocks) {
        for (Supplier<? extends Block> block : blocks) {
            this.dropSelf(block.get());
        }
    }

    /**
     * CORE HELPER: Varargs version for individual blocks or manual lists.
     */
    @SafeVarargs
    protected final void dropSelf(Supplier<? extends Block>... blocks) {
        for (Supplier<? extends Block> block : blocks) {
            this.dropSelf(block.get());
        }
    }
}