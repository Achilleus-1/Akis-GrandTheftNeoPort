package dev.codex.gtaliketeleport;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.ClientTickEvent;
final class GtaLikeTeleportForgeClient {
    static void register(IEventBus modBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new GtaLikeTeleportConfigScreen(parent));
        modBus.addListener(GtaLikeTeleportForgeClient::setup);
        NeoForge.EVENT_BUS.addListener(GtaLikeTeleportForgeClient::tick);
        NeoForge.EVENT_BUS.addListener(GtaLikeTeleportForgeClient::commands);
    }
    private static void setup(FMLClientSetupEvent event) { event.enqueueWork(GtaLikeTeleportClient::initializeClient); }
    private static void tick(ClientTickEvent.Post event) { GtaLikeTeleportClient.tick(Minecraft.getInstance()); }
    private static void commands(RegisterClientCommandsEvent event) { GtaLikeTeleportClient.registerClientCommands(event.getDispatcher()); }
}
