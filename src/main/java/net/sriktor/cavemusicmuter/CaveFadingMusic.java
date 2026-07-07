package net.sriktor.cavemusicmuter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.util.math.random.Random;


public class CaveFadingMusic extends MovingSoundInstance {

    private float fadeMultiplier = 1.0f;

    public CaveFadingMusic(SoundEvent sound) {
        super(sound, SoundCategory.MUSIC, Random.create());
        this.volume = 1.0f;
        this.relative = true;
        this.repeatDelay = 0;
        this.repeat = false;
    }


    @Override
    public void tick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        BlockPos pos = client.player.getBlockPos();


        boolean inCave = pos.getY() < 50 && client.world.getLightLevel(LightType.SKY, pos) == 0;


        if (inCave) {
            this.fadeMultiplier -= 0.01f;
        } else {
            this.fadeMultiplier += 0.01f;
        }


        this.fadeMultiplier = Math.max(0.0f, Math.min(1.0f, this.fadeMultiplier));


        this.volume = this.fadeMultiplier;
    }
}