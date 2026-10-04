package dev.codex.gtaliketeleport.mixin;

import dev.codex.gtaliketeleport.TeleportStepEffectRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Gui.class})
abstract class GuiMixin {
    GuiMixin() {
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void gtalikeTeleport$renderEffectAfterHud(GuiGraphics graphics, net.minecraft.client.DeltaTracker deltaTracker, CallbackInfo ci) {
        TeleportStepEffectRenderer.render(graphics, deltaTracker.getGameTimeDeltaPartialTick(true));
    }
}
