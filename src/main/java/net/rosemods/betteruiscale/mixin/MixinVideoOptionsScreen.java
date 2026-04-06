package net.rosemods.betteruiscale.mixin;

import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.rosemods.betteruiscale.FractionalScaleSliderWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoOptionsScreen.class)
public class MixinVideoOptionsScreen {

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
        ((ScreenInvoker)(Object)self).invokeAddDrawableChild(slider);
    }
}