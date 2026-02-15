package net.wafflebum.wafflepackcore.util.tinting;

public class WPC_TintingMath {

    /**
     * Standard clamp to keep RGB values within the valid 0-255 range.
     */
    public static int clamp(int val) {
        return Math.max(0, Math.min(255, val));
    }

    /**
     * Multiplicative Tinting: Used for Resources with Hex Colors.
     * Formula: (Template Pixel * (Tint Color / 255.0) * Brightness)
     */
    public static int applyTint(int templatePixel, int tintColor, float brightness) {
        int a = (templatePixel >> 24) & 0xFF;
        if (a == 0) return 0;

        int r = clamp((int) (((templatePixel >> 16) & 0xFF) * (((tintColor >> 16) & 0xFF) / 255.0) * brightness));
        int g = clamp((int) (((templatePixel >> 8) & 0xFF) * (((tintColor >> 8) & 0xFF) / 255.0) * brightness));
        int b = clamp((int) ((templatePixel & 0xFF) * ((tintColor & 0xFF) / 255.0) * brightness));

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Overlay Blend Mode: Used for "SPECIFIC" resources.
     * Mimics professional image editing software to preserve highlights/shadows.
     */
    public static int applyOverlayTint(int templatePixel, int sourcePixel, float brightness) {
        int a = (templatePixel >> 24) & 0xFF;
        if (a == 0) return 0;

        int r = clamp((int) (calculateOverlay((sourcePixel >> 16) & 0xFF, (templatePixel >> 16) & 0xFF) * brightness));
        int g = clamp((int) (calculateOverlay((sourcePixel >> 8) & 0xFF, (templatePixel >> 8) & 0xFF) * brightness));
        int b = clamp((int) (calculateOverlay(sourcePixel & 0xFF, templatePixel & 0xFF) * brightness));

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * The actual Overlay algorithm:
     * If background < 0.5, use 2 * bg * fg.
     * If background >= 0.5, use 1 - 2 * (1 - bg) * (1 - fg).
     */
    private static int calculateOverlay(int bg, int fg) {
        double b = bg / 255.0;
        double f = fg / 255.0;
        double res = (b < 0.5) ? (2 * b * f) : (1 - 2 * (1 - b) * (1 - f));
        return (int) (res * 255);
    }
}
