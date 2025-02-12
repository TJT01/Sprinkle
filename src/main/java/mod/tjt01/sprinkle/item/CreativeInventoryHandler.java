package mod.tjt01.sprinkle.item;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.block.ModBlocks;
import mod.tjt01.sprinkle.block.VerticalSlabBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeInventoryHandler {
    private static void addAfter(
            ItemStack after,
            ItemStack add,
            MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries
    ) {
        entries.putAfter(after, add, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void addAfter(
            ItemLike after, ItemLike add, MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries
    ) {
        addAfter(new ItemStack(after), new ItemStack(add), entries);
    }

    private static void addAllBefore(
            ItemStack before, List<ItemLike> add, MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries
    ) {
        for (ItemLike item: add) {
            entries.putBefore(before, new ItemStack(item), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private static void addAllBefore(
            ItemLike before, List<ItemLike> add, MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries
    ) {
        addAllBefore(new ItemStack(before), add, entries);
    }

    private static void addAllAfter(ItemStack after, List<ItemStack> add, MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries) {
        ItemStack last = after;
        for (ItemStack item: add) {
            entries.putAfter(last, item, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            last = item;
        }
    }

    private static void addAllAfter(ItemLike after, List<ItemLike> add, MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries) {
        addAllAfter(new ItemStack(after), add.stream().map(ItemStack::new).toList(), entries);
    }

    private static List<ItemLike> filterVerticalSlabs(ItemLike... items) {
        if (!VerticalSlabBlock.CONDITION.test(ICondition.IContext.TAGS_INVALID)) {
            return Arrays.stream(items)
                    .filter(itemLike -> !(itemLike instanceof VerticalSlabBlock))
                    .toList();
        } else {
            return Arrays.asList(items);
        }
    }

    @SubscribeEvent
    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();

        if (tab.equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            addAfter(Items.LANTERN, ModBlocks.GOLD_LANTERN.get(), entries);
            addAfter(Items.SOUL_LANTERN, ModBlocks.GOLD_SOUL_LANTERN.get(), entries);
            addAfter(Items.CHAIN, ModBlocks.GOLD_CHAIN.get(), entries);
            addAllBefore(
                    Blocks.PURPUR_BLOCK, filterVerticalSlabs(
                            ModBlocks.NIGHTSHALE.get(),
                            ModBlocks.GLIMMERING_NIGHTSHALE.get(),
                            ModBlocks.NIGHTSHALE_STAIRS.get(),
                            ModBlocks.NIGHTSHALE_SLAB.get(),
                            ModBlocks.NIGHTSHALE_VERTICAL_SLAB.get(),
                            ModBlocks.NIGHTSHALE_WALL.get(),
                            ModBlocks.NIGHTSHALE_BRICKS.get(),
                            ModBlocks.NIGHTSHALE_BRICK_STAIRS.get(),
                            ModBlocks.NIGHTSHALE_BRICK_SLAB.get(),
                            ModBlocks.NIGHTSHALE_BRICK_VERTICAL_SLAB.get(),
                            ModBlocks.NIGHTSHALE_BRICK_WALL.get()
                    ), entries
            );
            addAllAfter(
                    Blocks.PURPUR_SLAB, filterVerticalSlabs(
                            ModBlocks.PURPUR_BRICKS.get(),
                            ModBlocks.PURPUR_BRICK_STAIRS.get(),
                            ModBlocks.PURPUR_BRICK_SLAB.get(),
                            ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(),
                            ModBlocks.PURPUR_BRICK_WALL.get()
                    ), entries
            );
            return;
        }
        if (tab.equals(CreativeModeTabs.REDSTONE_BLOCKS)) {
            addAfter(Items.DROPPER, ModBlocks.DETECTOR.get(), entries);
            return;
        }
        if (tab.equals(CreativeModeTabs.FOOD_AND_DRINKS)) {
            addAfter(Items.PUMPKIN_PIE, ModItems.CHEESE.get(), entries);
            return;
        }
    }
}
