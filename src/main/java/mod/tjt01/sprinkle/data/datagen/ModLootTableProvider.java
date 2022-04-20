package mod.tjt01.sprinkle.data.datagen;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import mod.tjt01.sprinkle.block.VerticalSlabBlock;
import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return Lists.newArrayList(
                Pair.of(ModBlockLootTables::new, LootContextParamSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
        map.forEach((key, lootTable) -> LootTables.validate(validationTracker, key, lootTable));
    }

    public static class ModBlockLootTables extends BlockLoot {
        public ModBlockLootTables() {
            super();
        }

        private static LootTable.Builder createVerticalSlabItemTable(Block block) {
            return LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(applyExplosionDecay(block, LootItem.lootTableItem(block)
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
                                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
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
            this.dropSelf(ModBlocks.GOLD_LANTERN.get());
            this.dropSelf(ModBlocks.GOLD_SOUL_LANTERN.get());

            this.dropSelf(ModBlocks.PURPUR_BRICKS.get());
            this.add(ModBlocks.PURPUR_BRICK_SLAB.get(), ModBlockLootTables::createSlabItemTable);
            this.dropSelf(ModBlocks.PURPUR_BRICK_STAIRS.get());
            this.dropSelf(ModBlocks.PURPUR_BRICK_WALL.get());
            this.add(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), ModBlockLootTables::createVerticalSlabItemTable);

            this.dropSelf(ModBlocks.NIGHTSHALE.get());
            this.add(ModBlocks.NIGHTSHALE_SLAB.get(), ModBlockLootTables::createSlabItemTable);
            this.dropSelf(ModBlocks.NIGHTSHALE_STAIRS.get());
            this.dropSelf(ModBlocks.NIGHTSHALE_WALL.get());
            this.add(ModBlocks.NIGHTSHALE_VERTICAL_SLAB.get(), ModBlockLootTables::createVerticalSlabItemTable);
            this.dropSelf(ModBlocks.GLIMMERING_NIGHTSHALE.get());

            this.dropSelf(ModBlocks.NIGHTSHALE_BRICKS.get());

            this.dropSelf(ModBlocks.DETECTOR.get());

        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream()
                    .filter(entry -> entry.getRegistryName().getNamespace().equals("sprinkle"))
                    .collect(Collectors.toList());
        }
    }

}
