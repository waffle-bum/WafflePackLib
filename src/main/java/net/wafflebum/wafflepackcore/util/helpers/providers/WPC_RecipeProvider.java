package net.wafflebum.wafflepackcore.util.helpers.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import net.wafflebum.wafflepackcore.resource_definitions.WPC_ResourceTypeSet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public abstract class WPC_RecipeProvider extends RecipeProvider {
    protected final String modId;

    public WPC_RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, lookupProvider);
        this.modId = modId;
    }

    protected abstract Map<String, DeferredItem<Item>> getItemRegistryMap();

    // --- Cooking Helpers ---

    protected void addSmeltingSet(RecipeOutput output, WPC_ResourceTypeSet inputSet, WPC_ResourceTypeSet outputSet, float xp, int time) {
        processCookingSet(output, inputSet, outputSet, xp, time,
                (in, out) -> SimpleCookingRecipeBuilder.smelting(Ingredient.of(in), RecipeCategory.MISC, out, xp, time), "smelting");
    }

    protected void addBlastingSet(RecipeOutput output, WPC_ResourceTypeSet inputSet, WPC_ResourceTypeSet outputSet, float xp, int time) {
        processCookingSet(output, inputSet, outputSet, xp, time,
                (in, out) -> SimpleCookingRecipeBuilder.blasting(Ingredient.of(in), RecipeCategory.MISC, out, xp, time), "blasting");
    }

    /**
     * INTERNAL ENGINE: Cooking
     */
    private void processCookingSet(RecipeOutput output, WPC_ResourceTypeSet inputSet, WPC_ResourceTypeSet outputSet,
                                   float xp, int time, BiFunction<ItemLike, ItemLike, SimpleCookingRecipeBuilder> builderFunc, String folder) {
        List<WPC_ResourceTypeSet.ExpandedResource> inputs = inputSet.getExpandedResources();
        List<WPC_ResourceTypeSet.ExpandedResource> outputs = outputSet.getExpandedResources();

        for (int i = 0; i < inputs.size(); i++) {
            ItemLike inputItem = getItemRegistryMap().get(inputs.get(i).registryName());
            ItemLike outputItem = getItemRegistryMap().get(outputs.get(i).registryName());

            if (inputItem != null && outputItem != null) {
                String path = folder + "/" + outputs.get(i).registryName() + "_from_" + inputs.get(i).registryName();
                builderFunc.apply(inputItem, outputItem)
                        .unlockedBy(getHasName(inputItem), has(inputItem))
                        .save(output, ResourceLocation.fromNamespaceAndPath(this.modId, path));
            }
        }
    }

    /**
     * PATTERN HELPER: Shaped Crafting
     * Path: recipes/shaped/<output>.json
     */
    protected void shapedTypeSet(RecipeOutput output, WPC_ResourceTypeSet inputSet, WPC_ResourceTypeSet outputSet, List<String> pattern, char keyChar) {
        List<WPC_ResourceTypeSet.ExpandedResource> inputs = inputSet.getExpandedResources();
        List<WPC_ResourceTypeSet.ExpandedResource> outputs = outputSet.getExpandedResources();

        for (int i = 0; i < inputs.size(); i++) {
            ItemLike inputItem = getItemRegistryMap().get(inputs.get(i).registryName());
            ItemLike outputItem = getItemRegistryMap().get(outputs.get(i).registryName());

            if (inputItem != null && outputItem != null) {
                String path = "shaped/" + outputs.get(i).registryName();
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, outputItem)
                        .pattern(pattern.get(0)).pattern(pattern.get(1)).pattern(pattern.get(2))
                        .define(keyChar, inputItem)
                        .unlockedBy(getHasName(inputItem), has(inputItem))
                        .save(output, ResourceLocation.fromNamespaceAndPath(this.modId, path));
            }
        }
    }

    /**
     * SHAPELESS HELPER: Great for de-compression (1 block -> 9 ingots)
     * Path: recipes/shapeless/<output>_from_<input>.json
     */
    protected void shapelessTypeSet(RecipeOutput output, WPC_ResourceTypeSet inputSet, WPC_ResourceTypeSet outputSet, int inputCount, int outputCount) {
        List<WPC_ResourceTypeSet.ExpandedResource> inputs = inputSet.getExpandedResources();
        List<WPC_ResourceTypeSet.ExpandedResource> outputs = outputSet.getExpandedResources();

        for (int i = 0; i < inputs.size(); i++) {
            ItemLike inputItem = getItemRegistryMap().get(inputs.get(i).registryName());
            ItemLike outputItem = getItemRegistryMap().get(outputs.get(i).registryName());

            if (inputItem != null && outputItem != null) {
                // Shapeless path includes input name to distinguish things like
                // "iron_ingot from iron_block" vs "iron_ingot from iron_nugget"
                String path = "shapeless/" + outputs.get(i).registryName() + "_from_" + inputs.get(i).registryName();

                ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, outputItem, outputCount);
                for(int j = 0; j < inputCount; j++) {
                    builder.requires(inputItem);
                }

                builder.unlockedBy(getHasName(inputItem), has(inputItem))
                        .save(output, ResourceLocation.fromNamespaceAndPath(this.modId, path));
            }
        }
    }
}