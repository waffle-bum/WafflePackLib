package net.wafflebum.wafflepackcore.util.helpers.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.wafflebum.wafflepackcore.resource_definitions.WPC_ResourceTypeSet;
import net.wafflebum.wafflepackcore.util.helpers.tags.WPC_TagFactory;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class WPC_BlockTagProvider extends BlockTagsProvider {
    protected final WPC_TagFactory factory;

    public WPC_BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
        this.factory = new WPC_TagFactory(modId);
    }

    /**
     * The Addon must provide its block registry so the Core knows what exists to be tagged.
     */
    protected abstract Map<String, DeferredBlock<Block>> getRegistryMap();

    /**
     * Automates adding a whole TypeSet to a master tag (like MINEABLE_WITH_PICKAXE).
     */
    protected void addTypeSetToTag(WPC_ResourceTypeSet typeSet, TagKey<Block> tag) {
        var tagBuilder = tag(tag);
        typeSet.getExpandedResources().forEach(expanded -> {
            DeferredBlock<Block> block = getRegistryMap().get(expanded.registryName());
            if (block != null) {
                tagBuilder.add(block.get());
            }
        });
    }

    /**
     * The "Magic" method: Handles Master Tags (c:ores) AND Nested Tags (c:ores/iron) in one go.
     */
    protected void registerNestedTypeSetTags(WPC_ResourceTypeSet typeSet, TagKey<Block> masterTag, String folderName, boolean isCommon) {
        typeSet.getExpandedResources().forEach(expanded -> {
            DeferredBlock<Block> block = getRegistryMap().get(expanded.registryName());
            if (block != null) {
                // 1. Add to the main group (e.g., c:ores)
                tag(masterTag).add(block.get());

                // 2. Add to the specific resource group (e.g., c:ores/iron)
                TagKey<Block> subTag = isCommon
                        ? factory.commonNested(folderName, expanded.sourceName())
                        : factory.nested(folderName, expanded.sourceName());

                tag(subTag).add(block.get());
            }
        });
    }
}
