package net.sriktor.cavemusicmuter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.util.math.random.Random;

// Rozszerzamy klasę MovingSoundInstance, która mówi grze: "hej, ten dźwięk odświeża się co klatkę!"
public class CaveFadingMusic extends MovingSoundInstance {

    private float fadeMultiplier = 1.0f; // Zaczynamy ze 100% głośności

    public CaveFadingMusic(SoundEvent sound) {
        super(sound, SoundCategory.MUSIC, Random.create());
        this.volume = 1.0f;
        this.relative = true; // Dźwięk leci w głowie gracza, a nie z konkretnego bloku
        this.repeatDelay = 0;
        this.repeat = false;
    }

    // Ta funkcja wykonuje się 20 razy na sekundę (co każdy tick gry)
    @Override
    public void tick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        BlockPos pos = client.player.getBlockPos();

        // Nasz prosty warunek: głębokość poniżej Y=50 i totalny brak światła słonecznego
        boolean inCave = pos.getY() < 50 && client.world.getLightLevel(LightType.SKY, pos) == 0;

        // Jeśli jesteś w jaskini, odejmujemy 1% głośności co klatkę.
        // Po 100 klatkach (czyli 5 sekundach) głośność spadnie do absolutnego zera.
        if (inCave) {
            this.fadeMultiplier -= 0.01f;
        } else {
            this.fadeMultiplier += 0.01f;
        }

        // Upewniamy się, że mnożnik nie wyjdzie poza zakres matematyczny (od 0 do 1)
        this.fadeMultiplier = Math.max(0.0f, Math.min(1.0f, this.fadeMultiplier));

        // Nakładamy wyliczoną głośność na faktyczną muzykę.
        // Nawet jak ma 0%, plik z muzyką wciąż pędzi do przodu w tle!
        this.volume = this.fadeMultiplier;
    }
}