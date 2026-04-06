package net.rosemods.betteruiscale;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.rosemods.betteruiscale.mixin.ScreenInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ClientModInitializer {
    public static final String MOD_ID = "advanced-ui-scale";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        Config.load();
        LOGGER.info("Advanced UI Scale initialized. Fractional scale: {}",
                Config.getInstance().getGuiScale() > 0
                        ? Config.getInstance().getGuiScale()
                        : "disabled (using vanilla)");

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!(screen instanceof VideoOptionsScreen)) return;
            int sliderWidth = 200;
            int sliderHeight = 20;
            int x = scaledWidth / 2 - sliderWidth / 2;
            int y = scaledHeight - 52;
            FractionalScaleSliderWidget slider = new FractionalScaleSliderWidget(
                    x, y, sliderWidth, sliderHeight
            );
            ((ScreenInvoker)(Object)screen).invokeAddDrawableChild(slider);
        });
    }
}