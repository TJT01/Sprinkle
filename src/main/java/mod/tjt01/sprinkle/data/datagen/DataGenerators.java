package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, existingFileHelper);
		generator.addProvider(new LootTableProvider(generator));
		generator.addProvider(new Recipes(generator));
		generator.addProvider(new ModBlockModels(generator, existingFileHelper));
		generator.addProvider(blockTagsProvider);
		generator.addProvider(new ModItemTagsProvider(generator, blockTagsProvider, existingFileHelper));
		generator.addProvider(new ModLang(generator));
		generator.addProvider(new ModSoundDefinitions(generator, existingFileHelper));

	}
	
}
