package net.sriktor.cavemusicmuter.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.MusicSound;
import net.sriktor.cavemusicmuter.CaveFadingMusic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public class CaveMusicMuterMixin {

    @Shadow private SoundInstance current;


    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private void replaceVanillaMusic(MusicSound type, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();


        this.current = new CaveFadingMusic(type.sound().value());


        client.getSoundManager().play(this.current);


        ci.cancel();
    }
}