package net.wafflebum.wafflepackcore.util.helpers.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class WPC_ItemModelProvider extends ItemModelProvider {

    public WPC_ItemModelProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper) {
        super(output, modId, existingFileHelper);
    }

    /**
     * OPTION A: Full Control.
     * You provide the exact path (e.g., "item/basic/muddy_stick").
     */
    protected void simpleItem(String name, String texturePath) {
        this.existingFileHelper.trackGenerated(modLoc(texturePath),
                PackType.CLIENT_RESOURCES, ".png", "textures");

        withExistingParent(name, mcLoc("item/generated"))
                .texture("layer0", modLoc(texturePath));
    }

    /**
     * OPTION B: The Shortcut.
     * Specifically for your TypeSet logic.
     * Automatically prepends "item/generated/" to the path.
     */
    protected void simpleGeneratedItem(String name, String folderName) {
        String fullPath = "item/generated/" + folderName + "/" + name;
        simpleItem(name, fullPath);
    }
}
