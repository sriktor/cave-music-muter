package net.sriktor.cavemusicmuter;

import net.fabricmc.loader.api.FabricLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    private static final File FILE = new java.io.File(FabricLoader.getInstance().getConfigDir().toFile(), "cave_music_muter.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static ModConfig instance = new ModConfig();

    // Domyślne wartości suwaków i opcji
    public int maxSubterraneanY = 50;
    public boolean checkSkylight = true;
    public int fadeOutSeconds = 5;
    public int fadeInSeconds = 5;

    public static void load() {
        if (FILE.exists()) {
            try (FileReader reader = new FileReader(FILE)) {
                instance = GSON.fromJson(reader, ModConfig.class);
                if (instance == null) instance = new ModConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(instance, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}