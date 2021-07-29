package mod.tjt01.sprinkle;

import java.util.HashMap;

import mod.tjt01.sprinkle.config.SprinkleConfig;
import mod.tjt01.sprinkle.data.FlagCondition;
import mod.tjt01.sprinkle.data.QuarkFlagCondition;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

//@SuppressWarnings("unused")
@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {
	
	public static final Logger LOGGER = LogManager.getLogger(Main.MODID + " Loading");

	@SubscribeEvent
	public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		CraftingHelper.register(FlagCondition.Serializer.INSTANCE);
		CraftingHelper.register(QuarkFlagCondition.Serializer.INSTANCE);
	}

	@SubscribeEvent
	public static void onModConfigEvent(ModConfig.ModConfigEvent event) {
		ModConfig modConfig = event.getConfig();
		if (modConfig.getSpec() == SprinkleConfig.COMMON_SPEC)
			SprinkleConfig.bakeCommon(modConfig);
	}

}
