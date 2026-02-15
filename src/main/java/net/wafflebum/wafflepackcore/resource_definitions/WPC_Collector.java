package net.wafflebum.wafflepackcore.resource_definitions;

import java.util.*;

public class WPC_Collector {

    /**
     * Collects and flattens multiple inputs into a single ordered list of unique resources.
     * * @param inputs Individual WPCMasterList entries, Collections of entries, or Arrays.
     * @return A List containing unique WPCMasterList resources in the order they were provided.
     */
    public static List<WPC_MasterList> collect(Object... inputs) {
        // LinkedHashSet prevents duplicates (like IRON being in two groups)
        // while preserving the order of insertion for predictable registries.
        Set<WPC_MasterList> resources = new LinkedHashSet<>();

        for (Object input : inputs) {
            if (input instanceof WPC_MasterList resource) {
                resources.add(resource);
            }
            else if (input instanceof Collection<?> collection) {
                for (Object item : collection) {
                    if (item instanceof WPC_MasterList resource) {
                        resources.add(resource);
                    }
                }
            }
            else if (input instanceof Object[] array) {
                for (Object item : array) {
                    if (item instanceof WPC_MasterList resource) {
                        resources.add(resource);
                    }
                }
            }
        }

        return new ArrayList<>(resources);
    }
}
