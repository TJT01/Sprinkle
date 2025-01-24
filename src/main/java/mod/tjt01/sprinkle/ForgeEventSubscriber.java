package mod.tjt01.sprinkle;

import com.google.common.collect.ImmutableList;
import mod.tjt01.sprinkle.block.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ForgeEventSubscriber {
    @SubscribeEvent
    public static void onMissingRegistry(MissingMappingsEvent event) {
        remapItems(event.getMappings(ForgeRegistries.Keys.ITEMS, Main.MODID));
        remapBlocks(event.getMappings(ForgeRegistries.Keys.BLOCKS, Main.MODID));
    }

    private static void remapItems(List<MissingMappingsEvent.Mapping<Item>> mappings) {
        for (MissingMappingsEvent.Mapping<Item> mapping: mappings) {
            if (mapping.getKey().getNamespace().equals("sprinkle")) {
                switch (mapping.getKey().getPath()) {
                    case "bundle" -> mapping.remap(Items.BUNDLE);
                    case "unnamed_stone" -> mapping.remap(ModBlocks.NIGHTSHALE.get().asItem());
                    case "unnamed_block" -> mapping.remap(ModBlocks.NIGHTSHALE_BRICKS.get().asItem());
                }
            }
        }
    }

    private static void remapBlocks(List<MissingMappingsEvent.Mapping<Block>> mappings) {
        for (MissingMappingsEvent.Mapping<Block> mapping: mappings) {
            if (mapping.getKey().getNamespace().equals("sprinkle")) {
                switch (mapping.getKey().getPath()) {
                    case "unnamed_stone" -> mapping.remap(ModBlocks.NIGHTSHALE.get());
                    case "unnamed_block" -> mapping.remap(ModBlocks.NIGHTSHALE_BRICKS.get());
                }
            }
        }
    }
}
