# Validation of Grand Theft Neo Port

Date: October 4, 2026.
Target: **Minecraft 1.21.1, NeoForge 21.1.255, Java 21**.

## Completed checks

- Gradle build completed successfully against NeoForge 21.1.255.
- Packaged JAR ZIP integrity, TOML metadata, JSON resources, mod logo, mixin class references, author/credits, and preserved license notices verified.
- All packaged classes have Java 21 class-file version 65.
- All three renamed JARs loaded together into an isolated Minecraft client, opened a flat singleplayer test world, and exited normally following the AFK camera's smoke test.

- Offline mixin audit against the exact NeoForge 21.1.255 patched Minecraft archive: **154 checks, zero failures**.
- Transition dispatch/clock regression: four scenarios, 200 ticks each; clock advances after dispatch and the action runs exactly once.
- Original restricted OGG files are absent from both repository assets and compiled JARs. Seven transition events now reference Minecraft sound events.
- All 66 classes target Java 21.

Not exercised: actual teleport transitions/audio, dedicated multiplayer, Waystones/JourneyMap integration, shaders, or other optional integrations. Combined startup and the isolated regression do not establish those behaviors.

## Artifacts

Version: `1.0.0-neoforge.1`. SHA-256 hashes of the runtime and source JARs are in [SHA256SUMS.txt](SHA256SUMS.txt).

The historical PORT-NOTES and, where present, VALIDATION-ORIGINAL describe the earlier unbranded port and its older target/artifact hashes. This file describes the renamed edition.
