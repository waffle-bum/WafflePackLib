package net.wafflebum.wafflepackcore.util.helpers.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import net.wafflebum.wafflepackcore.resource_definitions.WPC_ResourceTypeSet;
import net.wafflebum.wafflepackcore.util.helpers.tags.WPC_TagFactory;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class WPC_ItemTagProvider extends ItemTagsProvider {
    protected final WPC_TagFactory factory;

    public WPC_ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<net.minecraft.world.level.block.Block>> blockTags,
                               String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, modId, existingFileHelper);
        this.factory = new WPC_TagFactory(modId);
    }

    /**
     * The Addon must provide its item registry map.
     */
    protected abstract Map<String, DeferredItem<Item>> getItemRegistryMap();

    /**
     * Automates adding a whole TypeSet to a master item tag.
     */
    protected void addTypeSetToTag(WPC_ResourceTypeSet typeSet, TagKey<Item> tag) {
        var tagBuilder = tag(tag);
        typeSet.getExpandedResources().forEach(expanded -> {
            DeferredItem<Item> item = getItemRegistryMap().get(expanded.registryName());
            if (item != null) {
                tagBuilder.add(item.get());
            }
        });
    }

    /**
     * The "Magic" method for Items: Handles Master Tags (c:ingots)
     * AND Nested Tags (c:ingots/iron) in one go.
     */
    protected void registerNestedTypeSetTags(WPC_ResourceTypeSet typeSet, TagKey<Item> masterTag, String folderName, boolean isCommon) {
        typeSet.getExpandedResources().forEach(expanded -> {
            DeferredItem<Item> item = getItemRegistryMap().get(expanded.registryName());
            if (item != null) {
                // 1. Add to the main group (e.g., c:ingots)
                tag(masterTag).add(item.get());

                // 2. Add to the specific resource group (e.g., c:ingots/iron)
                TagKey<Item> subTag = isCommon
                        ? factory.commonNestedItem(folderName, expanded.sourceName())
                        : factory.nestedItem(folderName, expanded.sourceName());

                tag(subTag).add(item.get());
            }
        });
    }
}
