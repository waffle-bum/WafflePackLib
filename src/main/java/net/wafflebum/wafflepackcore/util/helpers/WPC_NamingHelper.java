package net.wafflebum.wafflepackcore.util.helpers;

import net.minecraft.resources.ResourceLocation;
import net.wafflebum.wafflepackcore.resource_definitions.WPC_MasterList;

public class WPC_NamingHelper {

    /**
     * Standardizes the creation of a registry name string.
     * @param pattern The pattern from the TypeSet (e.g., "{}_ingot" or "raw_{}")
     * @param resource The resource from the Master List
     * @return The formatted name (e.g., "iron_ingot")
     */
    public static String createName(String pattern, WPC_MasterList resource) {
        return pattern.replace("{}", resource.getName());
    }

    /**
     * Creates a full ResourceLocation (ModID:Name).
     * Since the Addon defines the Type, it knows its own ModID.
     */
    public static ResourceLocation createLocation(String modId, String pattern, WPC_MasterList resource) {
        return ResourceLocation.fromNamespaceAndPath(modId, createName(pattern, resource));
    }

    /**
     * Resolves the texture template name automatically from the type pattern.
     * Logic: "raw_{}_block" -> "template_raw_block.png"
     */
    public static String createTemplateName(String pattern) {
        String cleanPattern = pattern.replace("{}", "")
                .replace("__", "_")
                .replaceAll("^_|_$", "");
        return "template_" + cleanPattern + ".png";
    }
}
