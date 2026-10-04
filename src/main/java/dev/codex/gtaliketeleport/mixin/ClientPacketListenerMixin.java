package dev.codex.gtaliketeleport.mixin;

import dev.codex.gtaliketeleport.GtaLikeTeleportClient;
import dev.codex.gtaliketeleport.TeleportTransitionController;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientPacketListener.class})
public abstract class ClientPacketListenerMixin {
    @Inject(method={"sendCommand"}, at={@At(value="HEAD")}, cancellable=true)
    private void gtalikeTeleport$interceptCommand(String command, CallbackInfo ci) {
        if (!GtaLikeTeleportClient.interceptOutgoingCommand(command)) {
            ci.cancel();
        }
    }

    @Inject(method={"handleForgetLevelChunk"}, at={@At(value="HEAD")}, cancellable=true)
    private void gtalikeTeleport$retainDepartingChunk(ClientboundForgetLevelChunkPacket packet, CallbackInfo ci) {
        if (TeleportTransitionController.shouldRetainDepartingChunk(packet.pos().x, packet.pos().z)) {
            ci.cancel();
        }
    }
}
