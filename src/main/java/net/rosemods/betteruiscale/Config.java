package net.rosemods.betteruiscale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("advanced-ui-scale.json");

    private static Config INSTANCE;

    // -1 means "use vanilla integer scale", any positive value overrides it
    private double guiScale = -1.0;

    public static Config getInstance() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    public double getGuiScale() {
        return guiScale;
    }

    public void setGuiScale(double scale) {
        this.guiScale = scale;
    }

    public static Config load() {
        if (CONFIG_PATH.toFile().exists()) {
            try (Reader reader = new FileReader(CONFIG_PATH.toFile())) {
                Config loaded = GSON.fromJson(reader, Config.class);
                if (loaded != null) {
                    INSTANCE = loaded;
                    return INSTANCE;
                }
            } catch (Exception e) {
                Main.LOGGER.error("Failed to load Advanced UI Scale config: " + e.getMessage());
            }
        }
        INSTANCE = new Config();
        INSTANCE.save();
        return INSTANCE;
    }

    public void save() {
        try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(this, writer);
        } catch (Exception e) {
            Main.LOGGER.error("Failed to save Advanced UI Scale config: " + e.getMessage());
        }
    }
}
