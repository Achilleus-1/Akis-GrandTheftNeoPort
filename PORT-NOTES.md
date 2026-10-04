# Grand Teleport — NeoForge 1.21.1 port of Forge build 300

Target: Minecraft **1.21.1**, NeoForge **21.1.252**, Java **21**.

## Installation

Use `grand-teleport-1.21.1-neoforge-build300-port2.jar` in your 1.21.1 NeoForge
instance's `mods` directory. Remove the original Forge 1.20.1 JAR and any other
Grand Teleport port from that instance. Keep the original file as a backup.

The mod ID remains `gtalike_teleport`, and settings remain in
`config/grand_teleport.properties` (with migration from `gtalike_teleport.properties`).
Open settings through the NeoForge Mods screen or `/gtp config`.
`/gtp` and `/grandtp` retain the enable, disable, status, and player-freeze commands.

For server-triggered transitions, install the same JAR on the server and the client.
Payload registration is optional so clients and servers without this mod can still
connect. The server checks channel support before delaying a player's teleport.

## Changes

- Port 2 fixes a freeze at full zoom-out. The transition tick counter now advances
  every active tick, including after the teleport is dispatched. An isolated
  regression executes the compiled clock/dispatch branch for 200 ticks in four
  scenarios (before/after dispatch, with/without cross-dimension travel) and checks
  that the action is sent once and the clock continues. Port 1 reproduces the stall.
- Recovered the code from the supplied Forge build 300 JAR using CFR and official
  Minecraft/Forge mappings. Retained its transition controller, settings editor,
  configuration keys, and original bundled assets.
- Replaced Forge lifecycle/tick/command/config integration with NeoForge events.
- Replaced Forge SimpleChannel packets with typed 1.21.1 payloads and stream codecs.
- Updated client custom-payload inspection for JourneyMap and Waystones.
- Updated camera, terrain indexing, rendering, GUI, and chunk-retention APIs.
- Updated the Leawind camera integration to its common 1.21.1 camera event.
- Added the `teleportToTarget` hook used by 1.21.1 Waystones Warp Plates.
- Removed stale 1.20.1 refmaps, set Java compatibility to 21, and updated resource
  pack metadata to format 34.
- Clear pending server transition state when a server stops.

## Validation and limits

The Gradle build compiles against NeoForge 21.1.252 and packages a runtime JAR and
a source JAR. Offline bytecode checks compare vanilla mixin selectors, callback
signatures, invocation anchors, invokers, and shadow fields against the exact
patched 1.21.1 class archive. Resource checks confirm original assets are unchanged.

**Minecraft was never launched.** Startup, visual behavior, multiplayer behavior,
and optional mod combinations still require your in-game testing. Optional hooks
use reflection and version-dependent third-party APIs; their compatibility cannot
be established by compilation alone.

Suggested manual checks: `/tp`, `/teleport`, `/execute ... run tp`, all three
dimensions, `/gtp config`, sound and input-freeze options, third-person release,
Waystones selections/Warp Plates, and JourneyMap teleporting if those mods are used.

## Rebuild

From this directory in PowerShell, with Java 21 installed:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
.\gradlew.bat build --console=plain
```

Gradle will download its build dependencies on the first run. No game run tasks
are configured. The output is in `build/libs/`. Change the Java installation path
in `gradle.properties` if your Java 21 is installed elsewhere.

## Provenance and licensing

Input: `grand-teleport-forge-1.20.1-forge-build300.jar`, provided by the user.

API migration reference:
[hookuru/GrandTeleport](https://github.com/hookuru/GrandTeleport),
including the author's Minecraft 1.21.1 source set.
The [existing NeoForge port](https://github.com/ling-gwdgw2/WT-teleport-1.21.1)
was consulted; this port retains the supplied build 300 code and mod identity.

Optional integration signatures were checked against
[Leawind's 1.21 branch](https://github.com/Leawind/Third-Person/tree/1.21) and
[Waystones' 1.21.1 branch](https://github.com/TwelveIterations/Waystones/tree/1.21.1).

Original author/credits: hookuru_ and Codex. This is an unofficial port.
See `src/main/resources/LICENSE_grand-teleport-forge` for the original license.
Source code is MIT; bundled custom sounds retain the original restrictions and
are included only as part of Grand Teleport.
