package mod.tjt01.sprinkle.mixin;

import mod.tjt01.sprinkle.config.SprinkleConfig;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Items.class)
public abstract class ItemsMixin {
    @Inject(
            at = @At("HEAD"),
            method = "ifPart2(Ljava/lang/Object;)Ljava/util/Optional;",
            cancellable = true
    )
    private static <T> void ifPart2(T secondaryPart, CallbackInfoReturnable<Optional<T>> callback) {
        if (SprinkleConfig.bundlesEnabled && secondaryPart == CreativeModeTab.TAB_TOOLS) {
            callback.setReturnValue(Optional.of(secondaryPart));
        }
    }
}
