package dev.codex.gtaliketeleport;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@Mod("gtalike_teleport")
public final class GtaLikeTeleportForge {
    public static final String MOD_ID = "gtalike_teleport";
    public GtaLikeTeleportForge(IEventBus modBus, ModContainer container) {
        GtaLikeTeleportServer.initialize();
        modBus.addListener(GtaLikeTeleportNetworkPayloads::registerPayloads);
        NeoForge.EVENT_BUS.addListener(GtaLikeTeleportForge::onServerTick);
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.server.ServerStoppedEvent event) -> GtaLikeTeleportServer.clear());
        if (FMLEnvironment.dist == Dist.CLIENT) GtaLikeTeleportForgeClient.register(modBus, container);
    }
    private static void onServerTick(ServerTickEvent.Post event) { GtaLikeTeleportServer.tick(event.getServer()); }
}
