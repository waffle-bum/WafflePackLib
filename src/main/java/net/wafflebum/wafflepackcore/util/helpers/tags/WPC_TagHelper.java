package net.wafflebum.wafflepackcore.util.helpers.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class WPC_TagHelper {

    /**
     * The single source of truth for creating any TagKey.
     */
    public static TagKey<Block> createBlock(String namespace, String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static TagKey<Item> createItem(String namespace, String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    /**
     * Handles the string formatting for nested folders (e.g., folder/name).
     */
    public static String nestedPath(String folder, String name) {
        return folder + "/" + name;
    }
}
