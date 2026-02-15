package net.wafflebum.wafflepackcore.util.helpers;

import net.minecraft.resources.ResourceLocation;

public class WPC_ResourcePaths {
    public static final String CORE_ID = "wafflepackcore";

    /**
     * Points to the shared specific textures in the core mod.
     */
    public static ResourceLocation sharedSpecific(String name) {
        return ResourceLocation.fromNamespaceAndPath(CORE_ID, "textures/shared_specific/" + name + ".png");
    }
}