package net.rosemods.betteruiscale;

import net.fabricmc.api.ClientModInitializer;
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
    }
}
