package net.rosemods.betteruiscale.mixin;

import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.rosemods.betteruiscale.FractionalScaleSliderWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoOptionsScreen.class)
public class MixinVideoOptionsScreen {

    /**
     * After the video options screen finishes building its widget list,
     * inject our fractional GUI scale slider just above the Done button.
     *
     * The Done button is placed at (height - 27). We place the slider at
     * (height - 52) so there's a 5-pixel gap above Done.
     */
    @Inject(method = "init", at = @At("TAIL"))
    private void advancedUiScale_addSlider(CallbackInfo ci) {
        VideoOptionsScreen self = (VideoOptionsScreen) (Object) this;
        int sliderWidth = 200;
        int sliderHeight = 20;
        int x = self.width / 2 - sliderWidth / 2;
        int y = self.height - 52;

        FractionalScaleSliderWidget slider = new FractionalScaleSliderWidget(
                x, y, sliderWidth, sliderHeight
        );
        self.addDrawableChild(slider);
    }
}
