package mod.tjt01.sprinkle.mixin.client;

import mod.tjt01.sprinkle.config.SprinkleConfig;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class NightVisionScaleMixin {
    @SuppressWarnings("ConstantConditions")
    @Inject(
            at = @At("HEAD"),
            method = "getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F",
            cancellable = true
    )
    private static void getNightVisionScale(
            LivingEntity livingEntity, float nanoTime,
            CallbackInfoReturnable<Float> callback
    ) {
        if (SprinkleConfig.smoothNightVisionEnabled) {
            int duration = livingEntity.getEffect(MobEffects.NIGHT_VISION).getDuration();
            callback.setReturnValue(Mth.clamp(duration/(float)SprinkleConfig.smoothNightVisionFadeTime, 0F, 1F));
        }
    }
}
