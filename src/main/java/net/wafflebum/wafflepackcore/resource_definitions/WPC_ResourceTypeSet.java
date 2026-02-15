package net.wafflebum.wafflepackcore.resource_definitions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class WPC_ResourceTypeSet {
    private final String pattern;
    private final Supplier<List<WPC_MasterList>> resources;

    public WPC_ResourceTypeSet(String pattern, Supplier<List<WPC_MasterList>> resources) {
        this.pattern = pattern;
        this.resources = resources;
    }

    /**
     * Simple record to hold the final registry name and the original resource.
     * Used by Tag and Texture providers to map logic correctly.
     */
    public record ExpandedResource(String registryName, String sourceName) {}

    /**
     * DEFAULT 1:1 EXPANSION
     * Replaces {} with the resource name.
     * Addons can override this method to return 9x, 12x, or tiered variations.
     */
    public List<ExpandedResource> getExpandedResources() {
        List<ExpandedResource> expanded = new ArrayList<>();
        for (WPC_MasterList res : resources.get()) {
            String name = pattern.replace("{}", res.getName());
            expanded.add(new ExpandedResource(name, res.getName()));
        }
        return expanded;
    }

    public String getPattern() { return pattern; }
    public List<WPC_MasterList> getResources() { return resources.get(); }
}