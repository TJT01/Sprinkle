package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.block.VerticalSlabBlock;
import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootTables {
    private static LootTable.Builder createVerticalSlabItemTable(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                .add(applyExplosionDecay(block, ItemLootEntry.lootTableItem(block)
                        .apply(SetCount.setCount(ConstantRange.exactly(2))
                                .when(BlockStateProperty.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(
                                        VerticalSlabBlock.TYPE,
                                        VerticalSlabBlock.VerticalSlabType.DOUBLE))
                                )
                        )
                )));
    }

    @Override
    protected void addTables() {
        Main.LOGGER.debug("AAAAA");
        this.dropSelf(ModBlocks.PURPUR_BRICKS.get());
        this.add(ModBlocks.PURPUR_BRICK_SLAB.get(), ModBlockLootTables::createSlabItemTable);
        this.dropSelf(ModBlocks.PURPUR_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.PURPUR_BRICK_WALL.get());
        this.add(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), ModBlockLootTables::createVerticalSlabItemTable);
        this.dropSelf(ModBlocks.DETECTOR.get());
        this.dropSelf(ModBlocks.GOLD_CHAIN.get());
        this.dropSelf(ModBlocks.GOLD_LANTERN.get());
        this.dropSelf(ModBlocks.GOLD_SOUL_LANTERN.get());
        Main.LOGGER.debug("BBBBB");
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(entry -> entry.getRegistryName().getNamespace().equals("sprinkle"))
                .collect(Collectors.toList());
    }
}
