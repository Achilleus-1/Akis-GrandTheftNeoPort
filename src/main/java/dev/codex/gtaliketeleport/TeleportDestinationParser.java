package dev.codex.gtaliketeleport;

import dev.codex.gtaliketeleport.DimensionIds;
import dev.codex.gtaliketeleport.TeleportCommandMatcher;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

final class TeleportDestinationParser {
    private TeleportDestinationParser() {
    }

    static Vec3 parse(String command, LocalPlayer player) {
        String argumentString = TeleportCommandMatcher.getArgumentString(command);
        if (argumentString == null || argumentString.isBlank()) {
            return null;
        }
        String[] args = argumentString.split("\\s+");
        if (args.length >= 3 && TeleportDestinationParser.areCoordinateTriple(args, 0)) {
            return TeleportDestinationParser.readCoordinateTriple(args, 0, player);
        }
        if (args.length >= 4 && TeleportDestinationParser.areCoordinateTriple(args, 1)) {
            return TeleportDestinationParser.readCoordinateTriple(args, 1, player);
        }
        for (int i = 0; i <= args.length - 3; ++i) {
            if (!TeleportDestinationParser.areCoordinateTriple(args, i)) continue;
            return TeleportDestinationParser.readCoordinateTriple(args, i, player);
        }
        return null;
    }

    static boolean usesRelativeCoordinates(String command) {
        String argumentString = TeleportCommandMatcher.getArgumentString(command);
        if (argumentString == null || argumentString.isBlank()) {
            return false;
        }
        String[] args = argumentString.split("\\s+");
        if (args.length >= 3 && TeleportDestinationParser.areCoordinateTriple(args, 0)) {
            return TeleportDestinationParser.hasRelativeCoordinate(args, 0);
        }
        if (args.length >= 4 && TeleportDestinationParser.areCoordinateTriple(args, 1)) {
            return TeleportDestinationParser.hasRelativeCoordinate(args, 1);
        }
        for (int i = 0; i <= args.length - 3; ++i) {
            if (!TeleportDestinationParser.areCoordinateTriple(args, i)) continue;
            return TeleportDestinationParser.hasRelativeCoordinate(args, i);
        }
        return false;
    }

    static String parseDimension(String command) {
        if (command == null || command.isBlank()) {
            return null;
        }
        String[] args = command.trim().split("\\s+");
        for (int i = 0; i < args.length - 1; ++i) {
            if (!args[i].equalsIgnoreCase("in")) continue;
            return DimensionIds.normalize(args[i + 1]);
        }
        return null;
    }

    private static boolean areCoordinateTriple(String[] args, int start) {
        return TeleportDestinationParser.isCoordinate(args[start]) && TeleportDestinationParser.isCoordinate(args[start + 1]) && TeleportDestinationParser.isCoordinate(args[start + 2]);
    }

    private static boolean hasRelativeCoordinate(String[] args, int start) {
        return args[start].startsWith("~") || args[start + 1].startsWith("~") || args[start + 2].startsWith("~");
    }

    private static Vec3 readCoordinateTriple(String[] args, int start, LocalPlayer player) {
        return new Vec3(TeleportDestinationParser.readCoordinate(args[start], player.getX()), TeleportDestinationParser.readCoordinate(args[start + 1], player.getY()), TeleportDestinationParser.readCoordinate(args[start + 2], player.getZ()));
    }

    private static boolean isCoordinate(String token) {
        if (token.isEmpty() || token.charAt(0) == '^') {
            return false;
        }
        if (token.charAt(0) == '~') {
            return token.length() == 1 || TeleportDestinationParser.isDouble(token.substring(1));
        }
        return TeleportDestinationParser.isDouble(token);
    }

    private static double readCoordinate(String token, double base) {
        if (token.charAt(0) == '~') {
            if (token.length() == 1) {
                return base;
            }
            return base + Double.parseDouble(token.substring(1));
        }
        return Double.parseDouble(token);
    }

    private static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        }
        catch (NumberFormatException ignored) {
            return false;
        }
    }
}
