package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.Main;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, lookup, existingFileHelper);
        generator.addProvider(event.includeServer(), new ModLootTableProvider(packOutput));
        generator.addProvider(event.includeServer(), new Recipes(packOutput));
        generator.addProvider(event.includeClient(), new ModBlockModels(packOutput, existingFileHelper));
        //generator.addProvider(new ModItemModels(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookup, blockTagsProvider.contentsGetter(), existingFileHelper));
//        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(generator, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModLang(packOutput));
        generator.addProvider(event.includeClient(), new ModSoundDefinitions(packOutput, existingFileHelper));

    }

}
