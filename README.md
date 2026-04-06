# Advanced UI Scale — 1.21.11 Port

Adds fractional GUI scaling (e.g. 2.25x, 2.5x, 2.75x) to Minecraft 1.21.11 Fabric.

A rewrite of the original Advanced UI Scale / BetterUIScale mod, updated to work
after Mojang changed guiScale from a double to an integer in 1.21.6.

## How to build using GitHub Actions (easiest — free)

1. Go to **github.com** and sign in (or create a free account).
2. Click **+** → **New repository**. Name it anything, e.g. `advanced-ui-scale-port`. Make it **Public**.
3. Upload all these files into the repo (drag-and-drop in the GitHub file browser, or use GitHub Desktop).
   Make sure the folder structure is preserved — the `.github/workflows/` folder is important.
4. After uploading, GitHub will automatically start the build. Go to the **Actions** tab.
5. Wait ~3–5 minutes for it to finish (green checkmark ✓).
6. Click the finished run → scroll down to **Artifacts** → download **advanced-ui-scale-1.21.11**.
7. Unzip the download. Put the `.jar` file into your `.minecraft/mods/` folder.

That's it!

## How it works

Since Mojang changed guiScale to an integer in 1.21.6, this port uses a different
approach from the original:

- Your chosen scale is saved to `.minecraft/config/advanced-ui-scale.json`
- Three Window methods are intercepted via Mixin:
  - `getScaleFactor()` → returns your fractional value
  - `getScaledWidth()` → recalculates from physical size ÷ your scale
  - `getScaledHeight()` → recalculates from physical size ÷ your scale
- A slider (1.0x – 5.0x in 0.25 steps) appears in Video Settings, above the Done button

## In-game usage

Open **Options → Video Settings**. At the bottom you'll see a **GUI Scale** slider.
Drag it to your desired value (e.g. 2.5x). The change applies immediately.

Your setting persists across sessions in `.minecraft/config/advanced-ui-scale.json`.
To reset to vanilla integer scaling, set `guiScale` to `-1.0` in that file.

## Requirements

- Minecraft 1.21.11
- Fabric Loader (any recent version)
- Fabric API (matching 1.21.11)
