package dev.codex.gtaliketeleport;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

final class TeleportSounds {
    static final SoundEvent CAMERA_IN = TeleportSounds.create("teleport.camera_in");
    static final SoundEvent CAMERA_OUT = TeleportSounds.create("teleport.camera_out");
    static final SoundEvent TELEPORT = TeleportSounds.create("teleport.teleport");
    static final SoundEvent ZOOM_IN_LONG = TeleportSounds.create("teleport.zoom_in_long");
    static final SoundEvent ZOOM_IN_SHORT = TeleportSounds.create("teleport.zoom_in_short");
    static final SoundEvent ZOOM_OUT_LONG = TeleportSounds.create("teleport.zoom_out_long");
    static final SoundEvent ZOOM_OUT_SHORT = TeleportSounds.create("teleport.zoom_out_short");

    private TeleportSounds() {
    }

    private static SoundEvent create(String path) {
        return SoundEvent.createVariableRangeEvent((ResourceLocation)ResourceLocation.fromNamespaceAndPath("gtalike_teleport", path));
    }
}
