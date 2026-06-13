# Alex's Caves — Fabric (1.21 / 26.1.2)

An unofficial [Fabric](https://fabricmc.net/) port of
[Alex's Caves](https://github.com/AlexModGuy/AlexsCaves) for Minecraft **26.1.2**.

Alex's Caves adds rare, hand-crafted cave biomes hidden beneath the Overworld —
each with its own blocks, mobs, items, and mechanics.

## Requirements

| Dependency   | Version          |
|--------------|------------------|
| Minecraft    | 26.1.2           |
| Java         | 25+              |
| Fabric Loader| 0.19.2+          |
| Fabric API   | 0.146.1+26.1.2   |

[JEI](https://www.curseforge.com/minecraft/mc-mods/jei) is supported but optional.

> **Note:** This build bundles Citadel internally, so it conflicts with the
> standalone Citadel mod (`breaks: citadel`).

## Building

```sh
./gradlew build
```

The mod jar is written to `build/libs/`.

To launch a dev server / client:

```sh
./gradlew runServer
./gradlew runClient
```

## Credits

- **Original mod:** [Alex's Caves](https://github.com/AlexModGuy/AlexsCaves) by
  **Alexthe668** and **Noonyeyz**.
- **Fabric multiversion base:** the porting groundwork builds on the
  [AlexsCavesFabric](https://github.com/q4diffuse/AlexsCavesFabric) effort by q4diffuse.

## License

Licensed under the **GNU Lesser General Public License v3.0 (LGPL-3.0)**, the
same license as the original mod. See [LICENSE](LICENSE).

This is an unofficial port and is not affiliated with or endorsed by the
original authors.
