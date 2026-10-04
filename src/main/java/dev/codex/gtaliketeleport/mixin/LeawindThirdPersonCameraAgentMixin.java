package dev.codex.gtaliketeleport.mixin;

import dev.codex.gtaliketeleport.TeleportTransitionController;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Leawind 1.21.1 uses a common camera event, rather than the old Forge listener. */
@Pseudo
@Mixin(targets = "com.github.leawind.thirdperson.core.CameraAgent", remap = false)
public abstract class LeawindThirdPersonCameraAgentMixin {
    @Inject(method = "onCameraSetup", at = @At("TAIL"), require = 0)
    private void gtalikeTeleport$restoreCameraAfterLeawind(@Coerce Object event, CallbackInfo ci) {
        if (!TeleportTransitionController.shouldApplyPostReleaseCameraOverrideAfterLeawind()) return;
        try {
            Class<?> type = event.getClass();
            Vec3 leawindPos = (Vec3) type.getField("pos").get(event);
            float yaw = type.getField("yRot").getFloat(event);
            float pitch = type.getField("xRot").getFloat(event);
            float partialTick = type.getField("partialTick").getFloat(event);
            var frame = TeleportTransitionController.getCameraFrame(partialTick);
            if (frame == null || leawindPos == null) return;
            Vec3 position = TeleportTransitionController.stabilizeCameraInsideBlock(frame.pos());
            boolean preserveRotation = TeleportTransitionController.shouldPreservePostReleaseCameraRotation();
            if (preserveRotation) position = TeleportTransitionController.followPostReleaseCameraPosition(position, leawindPos);
            type.getMethod("setPosition", Vec3.class).invoke(event, position);
            type.getMethod("setRotation", float.class, float.class).invoke(event,
                    preserveRotation ? pitch : frame.pitch(), preserveRotation ? yaw : frame.yaw());
            TeleportTransitionController.rememberTransitionCameraPosition(position, leawindPos,
                    preserveRotation ? Float.NaN : yaw, preserveRotation ? Float.NaN : pitch);
            TeleportTransitionController.requestTerrainVisibilityUpdate(position);
        } catch (ReflectiveOperationException ignored) {
            // Optional integration: the primary camera hook still handles the transition.
        }
    }
}
