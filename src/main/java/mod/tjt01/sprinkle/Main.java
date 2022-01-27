/*
 * +--------+----------+
 * |Sprinkle|Main Class|
 * +--------+----------+
 */

package mod.tjt01.sprinkle;

import mod.tjt01.sprinkle.config.SprinkleConfig;
import mod.tjt01.sprinkle.init.ModSoundEvents;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.tjt01.sprinkle.init.ModBlocks;
import mod.tjt01.sprinkle.init.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("sprinkle")
public class Main {

    public static final String MODID = "sprinkle";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public Main() {
        LOGGER.debug("Sprinkle has joined the game");

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();

        modLoadingContext.registerConfig(ModConfig.Type.COMMON, SprinkleConfig.COMMON_SPEC);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModSoundEvents.SOUND_EVENTS.register(modEventBus);
    }

}
