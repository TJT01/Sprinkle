package mod.tjt01.sprinkle;

import com.google.common.collect.ImmutableList;
import mod.tjt01.sprinkle.config.SprinkleConfig;
import mod.tjt01.sprinkle.data.ModTags;
import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
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

    @SubscribeEvent
    public static void onMissingItem(RegistryEvent.MissingMappings<Item> event) {
        ImmutableList<RegistryEvent.MissingMappings.Mapping<Item>> mappings = event.getMappings(Main.MODID);
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping: mappings) {
            if (mapping.key.getNamespace().equals("sprinkle")) {
                switch (mapping.key.getPath()) {
                    case "bundle" -> mapping.remap(Items.BUNDLE);
                    case "unnamed_stone" -> mapping.remap(ModBlocks.NIGHTSHALE.get().asItem());
                    case "unnamed_block" -> mapping.remap(ModBlocks.NIGHTSHALE_BRICKS.get().asItem());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMissingBlock(RegistryEvent.MissingMappings<Block> event) {
        ImmutableList<RegistryEvent.MissingMappings.Mapping<Block>> mappings = event.getMappings(Main.MODID);
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping: mappings) {
            if (mapping.key.getNamespace().equals("sprinkle")) {
                switch (mapping.key.getPath()) {
                    case "unnamed_stone" -> mapping.remap(ModBlocks.NIGHTSHALE.get());
                    case "unnamed_block" -> mapping.remap(ModBlocks.NIGHTSHALE_BRICKS.get());
                }
            }
        }
    }
}
