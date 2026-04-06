package net.rosemods.betteruiscale.mixin;

import net.minecraft.client.util.Window;
import net.rosemods.betteruiscale.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Window.class)
public class MixinWindow {

    /**
     * Override the scale factor returned to the rendering engine.
     * In 1.21.6+, guiScale is an integer internally, but we return our
     * fractional value here so all rendering uses it.
     */
    @Inject(method = "getScaleFactor", at = @At("RETURN"), cancellable = true)
    private void advancedUiScale_getScaleFactor(CallbackInfoReturnable<Double> cir) {
        double scale = Config.getInstance().getGuiScale();
        if (scale > 0) {
            cir.setReturnValue(scale);
        }
    }

    /**
     * Override the scaled (GUI-space) width so layout calculations use the
     * fractional scale rather than the cached integer-based value.
     */
    @Inject(method = "getScaledWidth", at = @At("RETURN"), cancellable = true)
    private void advancedUiScale_getScaledWidth(CallbackInfoReturnable<Integer> cir) {
        double scale = Config.getInstance().getGuiScale();
        if (scale > 0) {
            Window self = (Window) (Object) this;
            cir.setReturnValue((int) Math.ceil(self.getFramebufferWidth() / scale));
        }
    }

    /**
     * Override the scaled (GUI-space) height so layout calculations use the
     * fractional scale rather than the cached integer-based value.
     */
    @Inject(method = "getScaledHeight", at = @At("RETURN"), cancellable = true)
    private void advancedUiScale_getScaledHeight(CallbackInfoReturnable<Integer> cir) {
        double scale = Config.getInstance().getGuiScale();
        if (scale > 0) {
            Window self = (Window) (Object) this;
            cir.setReturnValue((int) Math.ceil(self.getFramebufferHeight() / scale));
        }
    }
}
