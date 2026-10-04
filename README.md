# Grand Theft Neo Port

**NeoForge port and maintenance by [Achilleus (Achilleus-1)](https://github.com/Achilleus-1).**
An independently maintained port of [Grand Teleport](https://github.com/hookuru/GrandTeleport) by hookuru_.

Adds a GTA-style camera pull-out, travel, and return around teleports. Includes configurable zoom stages, input freezing, cross-dimension transitions, and optional Waystones/JourneyMap integrations. Transition audio uses Minecraft sounds in this edition.

## Installation

Requires **Minecraft 1.21.1**, **NeoForge 21.1.255** (dependency range: 21.1.255 to below 21.2), and **Java 21**.

Download `grand-theft-neo-port-1.21.1-1.0.0-neoforge.1.jar` from [Releases](https://github.com/Achilleus-1/Akis-GrandTheftNeoPort/releases) and place it in your instance's `mods` folder. Remove older/original copies of Grand Teleport before installing this port.

Open settings from the NeoForge Mods screen or `/gtp config`. Commands `/gtp` and `/grandtp` retain `on`, `off`, `status`, and `player_freeze` controls.

For server-triggered transitions, install on both client and server. The optional network channel also permits connections where the other side does not have the mod.

Settings remain in `config/grand_teleport.properties`, with migration from `gtalike_teleport.properties`.

## Build from source

With a Java 21 JDK installed, set `JAVA_HOME` to that JDK and run from this repository:

```powershell
.\gradlew.bat build --console=plain
```

Linux/macOS: `./gradlew build --console=plain`. Build outputs are in `build/libs/`; dependencies download on the first build. Original mod JARs and decompilers are not needed to rebuild.

## Compatibility and validation

The original internal mod ID `gtalike_teleport` and resource/config namespaces are preserved to retain compatibility. The displayed name, distribution filename, repository, and maintainer credits use the new branding.

See [VALIDATION.md](VALIDATION.md) for verification of this edition. [PORT-NOTES.md](PORT-NOTES.md) records the earlier port's migration and historical testing, including its older filenames and NeoForge target; it does not establish runtime results for this edition.

## Credits and license

See [CREDITS.md](CREDITS.md) and [LICENSE](LICENSE). Achilleus maintains the NeoForge port; original authors retain credit for their work. This is an unofficial port. Original logos remain temporarily until replacement branding is supplied.
