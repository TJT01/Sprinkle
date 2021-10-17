package mod.tjt01.sprinkle.data.datagen;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import mod.tjt01.sprinkle.block.VerticalSlabBlock;
import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return Lists.newArrayList(
                Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationTracker) {
        map.forEach((key, lootTable) -> LootTableManager.validate(validationTracker, key, lootTable));
    }

    public static class ModBlockLootTables extends BlockLootTables {
        public ModBlockLootTables() {
            super();
        }

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
            this.dropSelf(ModBlocks.GOLD_CHAIN.get());
            this.dropSelf(ModBlocks.PURPUR_BRICKS.get());
            this.add(ModBlocks.PURPUR_BRICK_SLAB.get(), ModBlockLootTables::createSlabItemTable);
            this.dropSelf(ModBlocks.PURPUR_BRICK_STAIRS.get());
            this.dropSelf(ModBlocks.PURPUR_BRICK_WALL.get());
            this.add(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), ModBlockLootTables::createVerticalSlabItemTable);
            this.dropSelf(ModBlocks.DETECTOR.get());
            this.dropSelf(ModBlocks.GOLD_LANTERN.get());
            this.dropSelf(ModBlocks.GOLD_SOUL_LANTERN.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream()
                    .filter(entry -> entry.getRegistryName().getNamespace().equals("sprinkle"))
                    .collect(Collectors.toList());
        }
    }

}
