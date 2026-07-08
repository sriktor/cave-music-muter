package net.sriktor.cavemusicmuter;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("Cave Music Muter Settings"));

            ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            // Slider for Y height
            general.addEntry(entryBuilder.startIntSlider(Text.literal("Max Cave Y Level"), ModConfig.instance.maxSubterraneanY, -64, 320)
                    .setDefaultValue(50)
                    .setSaveConsumer(newValue -> ModConfig.instance.maxSubterraneanY = newValue)
                    .build());

            // Sunlight switch
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Check for Skylight"), ModConfig.instance.checkSkylight)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> ModConfig.instance.checkSkylight = newValue)
                    .build());

            // Slider for mute duration
            general.addEntry(entryBuilder.startIntSlider(Text.literal("Fade Out Duration (seconds)"), ModConfig.instance.fadeOutSeconds, 1, 30)
                    .setDefaultValue(5)
                    .setSaveConsumer(newValue -> ModConfig.instance.fadeOutSeconds = newValue)
                    .build());

            // Slider for the volume increase time
            general.addEntry(entryBuilder.startIntSlider(Text.literal("Fade In Duration (seconds)"), ModConfig.instance.fadeInSeconds, 1, 30)
                    .setDefaultValue(5)
                    .setSaveConsumer(newValue -> ModConfig.instance.fadeInSeconds = newValue)
                    .build());

            builder.setSavingRunnable(ModConfig::save);
            return builder.build();
        };
    }
}