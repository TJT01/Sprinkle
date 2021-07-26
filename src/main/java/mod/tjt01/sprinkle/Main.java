/*
 * +--------+----------+
 * |Sprinkle|Main Class|
 * +--------+----------+
 */

package mod.tjt01.sprinkle;

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
		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
	}
	
}
