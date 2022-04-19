package mod.tjt01.sprinkle;

import mod.tjt01.sprinkle.config.SprinkleConfig;
import mod.tjt01.sprinkle.data.ModTags;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ForgeEventSubscriber {

    @SubscribeEvent
    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (SprinkleConfig.effectiveBlindnessEnabled && !event.getLookingEntity().getType().is(ModTags.EntitiyTypes.BLINDNESS_IMMUNE))
            event.modifyVisibility(SprinkleConfig.blindnessMultiplier);
    }
}
