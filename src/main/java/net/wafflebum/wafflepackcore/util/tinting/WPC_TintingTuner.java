package net.wafflebum.wafflepackcore.util.tinting;

import net.wafflebum.wafflepackcore.resource_definitions.WPC_MasterList;

import java.util.HashMap;
import java.util.Map;

public class WPC_TintingTuner {

    // A map to hold tuning logic from other mods, keyed by the Type name (e.g., "INGOTS")
    private static final Map<String, ITuningProvider> TINTING_TUNERS = new HashMap<>();

    /**
     * Functional interface for sub-mods to provide their own tuning logic.
     */
    @FunctionalInterface
    public interface ITuningProvider {
        float getBrightness(WPC_MasterList resource);
    }

    /**
     * Sub-mods call this to register their tuning files (like WPMTuningRegistry).
     */
    public static void registerTuner(String type, ITuningProvider provider) {
        TINTING_TUNERS.put(type.toUpperCase(), provider);
    }

    /**
     * The master method to calculate the final brightness.
     * Hierarchy: MasterList Base * Type/Specific Provider Override.
     */
    public static float getFinalBrightness(String type, WPC_MasterList resource) {
        float base = resource.getBaseBrightness();

        ITuningProvider provider = TINTING_TUNERS.get(type.toUpperCase());
        float override = (provider != null) ? provider.getBrightness(resource) : 1.0f;

        return base * override;
    }
}
