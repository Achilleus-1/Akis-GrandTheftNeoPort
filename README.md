# Grand Theft Neo Port

**[Product of Achilleus](https://linktr.ee/achilleus_)** — NeoForge port and maintenance by [Achilleus (Achilleus-1)](https://github.com/Achilleus-1).

Cinematic teleport transitions. Settings: NeoForge Mods screen or `/gtp config`. Uses Minecraft sound replacements; original restricted custom audio is excluded.

## Installation

For **Minecraft 1.21.1**, **NeoForge 21.1.255**, and **Java 21**. Download `grand-theft-neo-port-1.21.1-1.0.0-neoforge.2.jar` from [Releases](https://github.com/Achilleus-1/Akis-GrandTheftNeoPort/releases/latest) and place it in your `mods` folder. Remove older copies first.

## Development

Source code is in `src/main/java/`. Mod resources, logos, translations, and metadata are in `src/main/resources/`. Existing internal IDs and configuration paths are preserved.

Build with a Java 21 JDK: `./gradlew build` on Linux/macOS or `.\gradlew.bat build` on Windows. The installable JAR is written to `build/libs/`.

Launch the development client with `./gradlew runClient` or `.\gradlew.bat runClient`. Mod information and Minecraft/NeoForge versions are configured in `gradle.properties`. GitHub Actions checks the build on pushes and pull requests.

## Credits and license

Based on [Grand Teleport](https://github.com/hookuru/GrandTeleport) by hookuru_ (upstream credits Codex-assisted development). Original copyright and license notices are retained in [LICENSE](LICENSE) and packaged resources. Port modifications are MIT licensed; Achilleus supplied the replacement branding.
