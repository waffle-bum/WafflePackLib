package net.wafflebum.wafflepackcore.util.helpers;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class WPC_Tooltips {

    /**
     * Data structure for tooltip translations.
     * In records, fields are accessed via fieldName() (e.g., langKey()).
     */
    public record TooltipData(String langKey, String displayText) {}

    /**
     * Every addon (Materials, Trees, Machines) will register their tooltips here.
     * We use Supplier<? extends Item> to stay compatible with DeferredItem.
     */
    public static final Map<Supplier<? extends Item>, TooltipData> GLOBAL_TOOLTIP_MAP = new HashMap<>();

    /**
     * Standard registration for Items (e.g., Muddy Stick).
     */
    public static void add(DeferredItem<Item> item, String langKey, String text) {
        GLOBAL_TOOLTIP_MAP.put(item, new TooltipData(langKey, text));
    }

    /**
     * Overloaded registration for Blocks (e.g., Compressed Stone).
     * Internally maps the Block to its Item representation.
     */
    public static void add(DeferredBlock<Block> block, String langKey, String text) {
        // We use block::asItem to provide the Item representation of the block
        GLOBAL_TOOLTIP_MAP.put(block::asItem, new TooltipData(langKey, text));
    }

    /**
     * ADDED: Iteration helper for Language Providers.
     * This allows Addons to pull all registered tooltips into their lang files
     * without needing to know the internal Map or Record structure.
     *
     * @param consumer A BiConsumer typically provided by the LangProvider's 'add' method.
     */
    public static void forEachTooltip(BiConsumer<String, String> consumer) {
        GLOBAL_TOOLTIP_MAP.values().forEach(data ->
                consumer.accept(data.langKey(), data.displayText())
        );
    }
}