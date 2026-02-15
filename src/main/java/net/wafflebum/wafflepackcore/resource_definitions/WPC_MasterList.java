package net.wafflebum.wafflepackcore.resource_definitions;

import javax.annotation.Nullable;
import java.util.Locale;

public enum WPC_MasterList {
    // Basic Environmental
    DIRT(0xFF3A2616, 1.0f),
    GRAVEL(0xFF6E6E6E, 1.0f),
    SAND(0xFFAE9A72, 1.0f),
    RED_SAND(0xFFBF7D4F, 1.0f),
    CLAY(0xFF616970, 1.0f),
    STONE(0xFF383838, 1.0f),
    COBBLESTONE(null, 1.0f),
    ANDESITE(null, 1.0f),
    DIORITE(null, 1.0f),
    GRANITE(null, 1.0f),
    TUFF(null, 1.0f),
    CALCITE(null, 1.0f),
    DEEPSLATE(null, 1.0f),
    COBBLED_DEEPSLATE(null, 1.0f),
    SMOOTH_STONE(null, 1.0f),
    MOSS_BLOCK(null, 1.0f),
    NETHERRACK(0xFF3C0000, 1.0f),
    END_STONE(0xFFC4C482, 1.0f),
    GLASS(null, 1.0f),
    MAGMA_BLOCK(0xFFFF4500, 1.0f),
    GLOWSTONE(0xFFE1A500, 1.0f),
    OBSIDIAN(0xFF14111E, 1.0f),

    // Vanilla Metals & Resources
    COPPER(0xFF9F4D00, 1.0f),
    IRON(0xFF7C5F4F, 1.0f),
    GOLD(0xFFD5B800, 1.0f),
    NETHER_SCRAP(0xFF4F2A2A, 1.0f),
    COAL(0xFF232323, 1.0f),
    REDSTONE(0xFF990000, 1.0f),
    LAPIS(0xFF1A3B66, 1.0f),
    QUARTZ(0xFFD0D0D0, 1.0f),
    AMETHYST(0xFF9A5CC6, 1.0f),
    DIAMOND(0xFF4F84DC, 1.0f),
    EMERALD(0xFF007F00, 1.0f),

    // Modded Metals & Alloys
    LEAD(0xFF424A6D, 1.0f),
    TIN(0xFF9CB1BB, 1.0f),
    SILVER(0xFFB6B6B6, 1.0f),
    NICKEL(0xFF719173, 1.0f),
    OSMIUM(0xFF6C7B90, 1.0f),
    URANIUM(0xFF9ACD32, 1.0f),
    ZINC(0xFF788B9E, 1.0f),
    ALUMINUM(0xFF91919C, 1.0f),
    TITANIUM(0xFF323232, 1.0f),
    TUNGSTEN(0xFF141414, 1.0f),
    STEEL(0xFF71797E, 1.0f),
    IRIDIUM(0xFF999B9B, 1.0f),
    PLATINUM(0xFF556488, 1.0f),
    BRASS(0xFFE1C16E, 1.0f),
    BRONZE(0xFFCD7F32, 1.0f),
    LITHIUM(0xFFC8C8B4, 1.0f),

    // Progression Tiers
    PRIMITIVE(0xFF8C7356, 1.0f),
    BASIC(0xFF8E9180, 1.0f),
    STURDY(0xFFE8986E, 1.0f),
    REINFORCED(0xFF5B7B8C, 1.0f),
    ENERGIZED(0xFFFFD740, 1.0f),
    HARDENED(0xFF3D503B, 1.0f),
    ADVANCED(0xFF4ECDC4, 1.0f),
    ELITE(0xFF8249B5, 1.0f),
    SUPERCHARGED(0xFFF05E5E, 1.0f),
    RADIANT(0xFFCCFF00, 1.0f),
    TRANSCENDENT(0xFFFFFFFF, 1.0f),
    ASTRAL(0xFF2B1B47, 1.0f),

    // Specialty & Tree-Specific
    COTTONWOOD(0xFFB0B0B0, 1.0f),
    PETRIFIED(0xFF383838, 1.0f),
    BONE(0xFFE0D4C0, 1.0f),
    PROSPERITY(0xFFBEBEBE, 1.0f),
    CERTUS_QUARTZ(0xFF6EC9EA, 1.0f),
    NUTRITIONAL(0xFFE256FF, 1.0f),
    MAPLE(0xFF844D25, 1.0f),
    SULFUR(0xFFE2D35A, 1.0f),
    FLUORITE(0xFF8FC3C3, 1.0f),
    WATER(0xFF3F76E4, 1.0f),
    LAVA(0xFFFF8C00, 1.0f),
    BLAZE(0xFFFFAD1E, 1.0f),
    ENDER(0xFF0B231F, 1.0f),
    ECHO(0xFF0B1217, 1.0f),
    RAINBOW(0xFFFF00FF, 1.0f),
    PRISMARINE(0xFF66A397, 1.0f),
    GREEN_SLIME(0xFF80FF00, 1.0f),
    PINK_SLIME(0xFFFF00A2, 1.0f),
    SLIME_BLOCK(null, 1.0f),
    HONEY_BLOCK(null, 1.0f),
    CHORUS(0xFF8E688E, 1.0f);

    private final String name;
    private final @Nullable Integer color;
    private final float baseBrightness;

    WPC_MasterList(@Nullable Integer color, float baseBrightness) {
        this.name = this.name().toLowerCase(Locale.ROOT);
        this.color = color;
        this.baseBrightness = baseBrightness;
    }

    public String getName() { return name; }
    @Nullable public Integer getColor() { return color; }
    public boolean isSpecific() { return color == null; }
    public float getBaseBrightness() { return baseBrightness; }
}