package net.wafflebum.wafflepackcore.util.helpers.providers;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.wafflebum.wafflepackcore.util.tinting.WPC_TintingMath;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public abstract class WPC_TextureProvider implements DataProvider {
    protected final PackOutput output;
    protected final ExistingFileHelper existingFileHelper;
    protected final String modId;

    public WPC_TextureProvider(PackOutput output, ExistingFileHelper existingFileHelper, String modId) {
        this.output = output;
        this.existingFileHelper = existingFileHelper;
        this.modId = modId;
    }

    /**
     * CORE HELPER: Stacks two images on top of each other.
     */
    protected BufferedImage combineLayers(BufferedImage base, BufferedImage overlay) {
        BufferedImage result = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(base, 0, 0, null);
        g2d.drawImage(overlay, 0, 0, null);
        g2d.dispose();
        return result;
    }

    /**
     * CORE HELPER: Multiplicative Tinting.
     */
    protected BufferedImage applyTint(BufferedImage template, int color, float brightness) {
        BufferedImage result = new BufferedImage(template.getWidth(), template.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < template.getHeight(); y++) {
            for (int x = 0; x < template.getWidth(); x++) {
                result.setRGB(x, y, WPC_TintingMath.applyTint(template.getRGB(x, y), color, brightness));
            }
        }
        return result;
    }

    /**
     * CORE HELPER: Overlay Tinting (for Specific resources).
     */
    protected BufferedImage applyOverlay(BufferedImage template, BufferedImage source, float brightness) {
        BufferedImage result = new BufferedImage(template.getWidth(), template.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < template.getHeight(); y++) {
            for (int x = 0; x < template.getWidth(); x++) {
                result.setRGB(x, y, WPC_TintingMath.applyOverlayTint(template.getRGB(x, y), source.getRGB(x, y), brightness));
            }
        }
        return result;
    }

    /**
     * Loads a texture. If the texture is in 'generated', it looks in the output folder.
     * Otherwise, it looks in the existing resources.
     */
    protected BufferedImage loadImage(ResourceLocation loc) throws IOException {
        // --- 1. PRIORITY: Existing File Helper (Addon Local Files) ---
        try {
            if (existingFileHelper.exists(loc, net.minecraft.server.packs.PackType.CLIENT_RESOURCES)) {
                return ImageIO.read(existingFileHelper.getResource(loc, net.minecraft.server.packs.PackType.CLIENT_RESOURCES).open());
            }
        } catch (Exception ignored) {}

        // --- 2. SECONDARY: Classpath / JAR (Shared Core Files) ---
        String pathWithSlash = "/assets/" + loc.getNamespace() + "/textures/" + loc.getPath() + ".png";
        String pathNoSlash = pathWithSlash.substring(1);

        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();

        // Find the stream and store it in an effectively final variable
        InputStream foundStream = contextLoader.getResourceAsStream(pathWithSlash);
        if (foundStream == null) foundStream = contextLoader.getResourceAsStream(pathNoSlash);
        if (foundStream == null) foundStream = WPC_TextureProvider.class.getResourceAsStream(pathWithSlash);
        if (foundStream == null) foundStream = WPC_TextureProvider.class.getResourceAsStream(pathNoSlash);

        if (foundStream != null) {
            // Use the effectively final foundStream here
            try (InputStream is = foundStream) {
                return ImageIO.read(is);
            }
        }

        // --- 3. FALLBACK: Generated Output (Chained Textures) ---
        Path genPath = this.output.getOutputFolder().resolve("assets/" + loc.getNamespace() + "/textures/" + loc.getPath() + ".png");
        if (Files.exists(genPath)) {
            try (InputStream is = Files.newInputStream(genPath)) {
                return ImageIO.read(is);
            }
        }

        // --- 4. FAILURE ---
        String errorMsg = String.format("Texture not found: %s | Path: %s", loc, pathWithSlash);
        LOGGER.error(errorMsg);
        throw new IOException(errorMsg);
    }

    protected CompletableFuture<?> saveImage(CachedOutput cache, BufferedImage image, String subPath) {
        return CompletableFuture.runAsync(() -> {
            try {
                Path path = this.output.getOutputFolder().resolve("assets/" + modId + "/textures/" + subPath + ".png");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ImageIO.write(image, "png", bytes);
                byte[] data = bytes.toByteArray();
                cache.writeIfNeeded(path, data, com.google.common.hash.Hashing.sha1().hashBytes(data));
            } catch (IOException e) {
                throw new RuntimeException("Save Fail: " + subPath, e);
            }
        });
    }
}
