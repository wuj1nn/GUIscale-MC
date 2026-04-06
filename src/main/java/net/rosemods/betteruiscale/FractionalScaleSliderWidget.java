package net.rosemods.betteruiscale;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class FractionalScaleSliderWidget extends SliderWidget {

    // Slider covers 1.0x to 5.0x in steps of 0.25
    private static final double MIN_SCALE = 1.0;
    private static final double MAX_SCALE = 5.0;
    private static final double STEP = 0.25;
    private static final int STEPS = (int) ((MAX_SCALE - MIN_SCALE) / STEP); // 16 steps

    public FractionalScaleSliderWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.empty(), toSliderValue(getCurrentScale()));
        this.updateMessage();
    }

    private static double getCurrentScale() {
        double cfg = Config.getInstance().getGuiScale();
        if (cfg > 0) return cfg;
        // Default to current vanilla integer scale
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.options != null) {
            int vanillaScale = client.options.getGuiScale().getValue();
            return vanillaScale > 0 ? vanillaScale : 2.0;
        }
        return 2.0;
    }

    /** Map a real-world scale value (1.0–5.0) to slider position (0.0–1.0) */
    private static double toSliderValue(double scale) {
        double clamped = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scale));
        return (clamped - MIN_SCALE) / (MAX_SCALE - MIN_SCALE);
    }

    /** Map slider position (0.0–1.0) to a snapped scale value */
    private double toScale() {
        // Round to nearest STEP
        double raw = MIN_SCALE + this.value * (MAX_SCALE - MIN_SCALE);
        long snapped = Math.round(raw / STEP);
        return snapped * STEP;
    }

    @Override
    protected void updateMessage() {
        double scale = toScale();
        // Format cleanly: show "2.0x" not "2.000000000001x"
        String label;
        if (scale == Math.floor(scale)) {
            label = String.format("GUI Scale: %.0fx", scale);
        } else {
            label = String.format("GUI Scale: %.2fx", scale).replaceAll("0+$", "");
        }
        this.setMessage(Text.literal(label));
    }

    @Override
    protected void applyValue() {
        double scale = toScale();
        Config cfg = Config.getInstance();
        cfg.setGuiScale(scale);
        cfg.save();

        // Snap the slider knob to grid
        this.value = toSliderValue(scale);

        // Trigger Minecraft to recalculate GUI layout with the new scale
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            client.onResolutionChanged();
        }
    }
}
