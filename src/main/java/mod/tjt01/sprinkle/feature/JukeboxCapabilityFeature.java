package mod.tjt01.sprinkle.feature;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.capability.JukeboxCapabilityProvider;
import mod.tjt01.sprinkle.config.SprinkleConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class JukeboxCapabilityFeature {
    @SubscribeEvent
    public static void onAttachBlockEntityCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
        if (!SprinkleConfig.jukeboxCapabilityEnabled)
            return;
        if (event.getObject() instanceof JukeboxBlockEntity jukebox)
            event.addCapability(new ResourceLocation(Main.MODID, "item_handler"), new JukeboxCapabilityProvider(jukebox));
    }
}
