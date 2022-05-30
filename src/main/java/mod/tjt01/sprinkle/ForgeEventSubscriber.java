package mod.tjt01.sprinkle;

import com.google.common.collect.ImmutableList;
import mod.tjt01.sprinkle.capability.JukeboxCapabilityProvider;
import mod.tjt01.sprinkle.config.SprinkleConfig;
import mod.tjt01.sprinkle.data.ModTags;
import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ForgeEventSubscriber {

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

    @SubscribeEvent
    public static void onAttachBlockEntityCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
        if (!SprinkleConfig.jukeboxCapabilityEnabled)
            return;
        if (event.getObject() instanceof JukeboxBlockEntity jukebox)
            event.addCapability(new ResourceLocation(Main.MODID, "item_handler"), new JukeboxCapabilityProvider(jukebox));
    }
}
