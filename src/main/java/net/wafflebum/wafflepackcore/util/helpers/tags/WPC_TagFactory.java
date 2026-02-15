package net.wafflebum.wafflepackcore.util.helpers.tags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class WPC_TagFactory {
    private final String modId;

    public WPC_TagFactory(String modId) {
        this.modId = modId;
    }

    // --- Addon Namespace (Automatic ModID) ---
    public TagKey<Block> block(String name) {
        return WPC_TagHelper.createBlock(modId, name);
    }

    public TagKey<Item> item(String name) {
        return WPC_TagHelper.createItem(modId, name);
    }

    // --- Common Namespace (Fixed "c") ---
    public TagKey<Block> commonBlock(String name) {
        return WPC_TagHelper.createBlock("c", name);
    }

    public TagKey<Item> commonItem(String name) {
        return WPC_TagHelper.createItem("c", name);
    }

    // --- Nested Helpers (folder/resource) ---
    public TagKey<Block> nested(String folder, String res) {
        return WPC_TagHelper.createBlock(modId, WPC_TagHelper.nestedPath(folder, res));
    }

    public TagKey<Block> commonNested(String folder, String res) {
        return WPC_TagHelper.createBlock("c", WPC_TagHelper.nestedPath(folder, res));
    }

    public TagKey<Item> nestedItem(String folder, String res) {
        return WPC_TagHelper.createItem(modId, WPC_TagHelper.nestedPath(folder, res));
    }

    public TagKey<Item> commonNestedItem(String folder, String res) {
        return WPC_TagHelper.createItem("c", WPC_TagHelper.nestedPath(folder, res));
    }
}
