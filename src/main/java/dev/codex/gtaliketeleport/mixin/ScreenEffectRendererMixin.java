package dev.codex.gtaliketeleport.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.codex.gtaliketeleport.TeleportTransitionController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ScreenEffectRenderer.class})
public abstract class ScreenEffectRendererMixin {
    @Inject(method={"renderScreenEffect"}, at={@At(value="HEAD")}, cancellable=true)
    private static void gtalikeTeleport$suppressScreenEffects(Minecraft minecraft, PoseStack poseStack, CallbackInfo ci) {
        if (TeleportTransitionController.shouldSuppressScreenEffects()) {
            ci.cancel();
        }
    }
}
