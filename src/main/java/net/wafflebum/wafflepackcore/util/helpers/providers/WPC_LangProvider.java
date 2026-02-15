package net.wafflebum.wafflepackcore.util.helpers.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.wafflebum.wafflepackcore.resource_definitions.WPC_ResourceTypeSet;
import org.apache.commons.lang3.text.WordUtils;

public abstract class WPC_LangProvider extends LanguageProvider {
    protected final String modId;

    public WPC_LangProvider(PackOutput output, String modId, String locale) {
        super(output, modId, locale);
        this.modId = modId;
    }

    /**
     * CORE HELPER: Generates translations for an entire TypeSet.
     */
    protected void addTypeSet(WPC_ResourceTypeSet typeSet) {
        typeSet.getExpandedResources().forEach(expanded -> {
            String localizedName = formatSafeName(expanded.registryName());
            add("block." + modId + "." + expanded.registryName(), localizedName);
            add("item." + modId + "." + expanded.registryName(), localizedName);
        });
    }

    /**
     * CORE HELPER: Simple manual naming for uniques.
     */
    protected void addUnique(String id, String name) {
        add("item." + modId + "." + id, name);
    }

    /**
     * CORE HELPER: Creative Tab naming.
     */
    protected void addTab(String tabId, String name) {
        add("itemGroup." + modId + "." + tabId, name);
    }

    /**
     * Converts "deepslate_iron_ore" to "Deepslate Iron Ore"
     * Protected so Addons can override specific formatting rules if needed.
     */
    protected String formatSafeName(String name) {
        return WordUtils.capitalizeFully(name.replace("_", " "));
    }
}