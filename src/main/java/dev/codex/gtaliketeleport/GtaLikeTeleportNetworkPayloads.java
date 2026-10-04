package dev.codex.gtaliketeleport;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

final class GtaLikeTeleportNetworkPayloads {
    static final int SOURCE_EXTERNAL = 1;
    static final int SOURCE_WARP_PLATE = 2;

    private static boolean registered;

    private GtaLikeTeleportNetworkPayloads() {
    }

    // Payload registration belongs to the mod event bus, never to game setup.
    static void register() {}

    static void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1").optional();
        registrar.playToClient(StartServerTeleportPayload.TYPE, StartServerTeleportPayload.CODEC,
                (payload, context) -> GtaLikeTeleportClientNetworking.handleStart(payload));
        registrar.playToServer(ServerTeleportAckPayload.TYPE, ServerTeleportAckPayload.CODEC,
                (payload, context) -> {
                    if (context.player() instanceof ServerPlayer player)
                        GtaLikeTeleportServer.handleTeleportAck(player, payload.requestId());
                });
        registrar.playToServer(BypassNextServerTeleportPayload.TYPE, BypassNextServerTeleportPayload.CODEC,
                (payload, context) -> {
                    if (context.player() instanceof ServerPlayer player)
                        GtaLikeTeleportServer.markNextServerTeleportBypassed(player);
                });
    }

    static boolean canSendToServer() {
        var connection = Minecraft.getInstance().getConnection();
        return connection != null && connection.hasChannel(ServerTeleportAckPayload.TYPE);
    }

    static void sendToServer(CustomPacketPayload payload) {
        if (canSendToServer()) PacketDistributor.sendToServer(payload);
    }

    static void sendStart(ServerPlayer player, long requestId, int source, Vec3 targetFeet, ResourceKey<Level> dimension) {
        String dimensionId = DimensionIds.fromResourceKey(dimension);
        PacketDistributor.sendToPlayer(player, new StartServerTeleportPayload(requestId, source,
                targetFeet.x, targetFeet.y, targetFeet.z, dimensionId == null ? "" : dimensionId));
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("gtalike_teleport", path);
    }

    record StartServerTeleportPayload(long requestId, int source, double x, double y, double z, String dimension) implements CustomPacketPayload {
        static final Type<StartServerTeleportPayload> TYPE = new Type<>(id("start_server_teleport"));
        static final StreamCodec<RegistryFriendlyByteBuf, StartServerTeleportPayload> CODEC = StreamCodec.ofMember(
                StartServerTeleportPayload::write,
                StartServerTeleportPayload::read
        );

        private void write(RegistryFriendlyByteBuf buffer) {
            buffer.writeLong(this.requestId);
            buffer.writeInt(this.source);
            buffer.writeDouble(this.x);
            buffer.writeDouble(this.y);
            buffer.writeDouble(this.z);
            buffer.writeUtf(this.dimension);
        }

        private static StartServerTeleportPayload read(RegistryFriendlyByteBuf buffer) {
            return new StartServerTeleportPayload(
                    buffer.readLong(),
                    buffer.readInt(),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readUtf()
            );
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    record ServerTeleportAckPayload(long requestId) implements CustomPacketPayload {
        static final Type<ServerTeleportAckPayload> TYPE = new Type<>(id("server_teleport_ack"));
        static final StreamCodec<RegistryFriendlyByteBuf, ServerTeleportAckPayload> CODEC = StreamCodec.ofMember(
                ServerTeleportAckPayload::write,
                ServerTeleportAckPayload::read
        );

        private void write(RegistryFriendlyByteBuf buffer) {
            buffer.writeLong(this.requestId);
        }

        private static ServerTeleportAckPayload read(RegistryFriendlyByteBuf buffer) {
            return new ServerTeleportAckPayload(buffer.readLong());
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    record BypassNextServerTeleportPayload() implements CustomPacketPayload {
        static final Type<BypassNextServerTeleportPayload> TYPE = new Type<>(id("bypass_next_server_teleport"));
        static final StreamCodec<RegistryFriendlyByteBuf, BypassNextServerTeleportPayload> CODEC = StreamCodec.unit(
                new BypassNextServerTeleportPayload()
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
