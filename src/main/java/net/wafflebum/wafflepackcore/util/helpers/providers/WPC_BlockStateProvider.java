package net.wafflebum.wafflepackcore.util.helpers.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class WPC_BlockStateProvider extends BlockStateProvider {
    protected final ExistingFileHelper existingFileHelper;

    public WPC_BlockStateProvider(PackOutput output, String modId, ExistingFileHelper exFileHelper) {
        super(output, modId, exFileHelper);
        this.existingFileHelper = exFileHelper;
    }

    /**
     * CORE HELPER: Generates blockstate, model, and item model for a cube-all block.
     * Automatically tracks the texture to prevent validation errors.
     * * @param block The block to register
     * @param modelName The name for the JSON model (usually registry name)
     * @param texturePath The mod-relative path (e.g., "block/generated/ores/iron_ore")
     * @param renderType The render type (e.g., "cutout", "translucent", "solid")
     */
    protected void simpleCubeAll(Block block, String modelName, String texturePath, String renderType) {
        // 1. Track the texture so DataGen doesn't complain it's missing
        this.existingFileHelper.trackGenerated(modLoc(texturePath),
                PackType.CLIENT_RESOURCES, ".png", "textures");

        // 2. Build the model
        BlockModelBuilder modelBuilder = models()
                .cubeAll(modelName, modLoc(texturePath))
                .renderType(renderType);

        // 3. Create the State and the Item Model
        simpleBlock(block, modelBuilder);
        simpleBlockItem(block, modelBuilder);
    }
}