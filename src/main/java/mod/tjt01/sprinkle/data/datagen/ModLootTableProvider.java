package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.block.MilkCauldronBlock;
import mod.tjt01.sprinkle.block.VerticalSlabBlock;
import mod.tjt01.sprinkle.block.ModBlocks;
import mod.tjt01.sprinkle.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput output) {
        super(
                output, Set.of(
                        MilkCauldronBlock.CHEESE_LOOT_ID
                ),
                List.of(
                        new SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                        new SubProviderEntry(ModGameplayLoot::new, LootContextParamSets.BLOCK)
                )
        );
    }

    public static class ModBlockLootTables extends BlockLootSubProvider {
        public ModBlockLootTables() {
            super(Set.of(), FeatureFlags.DEFAULT_FLAGS);
        }

        private LootTable.Builder createVerticalSlabItemTable(Block block) {
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
        protected void generate() {
            this.dropSelf(ModBlocks.GOLD_CHAIN.get());
            this.dropSelf(ModBlocks.GOLD_LANTERN.get());
            this.dropSelf(ModBlocks.GOLD_SOUL_LANTERN.get());

            this.dropSelf(ModBlocks.PURPUR_BRICKS.get());
            this.add(ModBlocks.PURPUR_BRICK_SLAB.get(), this::createSlabItemTable);
            this.dropSelf(ModBlocks.PURPUR_BRICK_STAIRS.get());
            this.dropSelf(ModBlocks.PURPUR_BRICK_WALL.get());
            this.add(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), this::createVerticalSlabItemTable);

            this.dropSelf(ModBlocks.NIGHTSHALE.get());
            this.add(ModBlocks.NIGHTSHALE_SLAB.get(), this::createSlabItemTable);
            this.dropSelf(ModBlocks.NIGHTSHALE_STAIRS.get());
            this.dropSelf(ModBlocks.NIGHTSHALE_WALL.get());
            this.add(ModBlocks.NIGHTSHALE_VERTICAL_SLAB.get(), this::createVerticalSlabItemTable);
            this.dropSelf(ModBlocks.GLIMMERING_NIGHTSHALE.get());

            this.dropSelf(ModBlocks.NIGHTSHALE_BRICKS.get());
            this.add(ModBlocks.NIGHTSHALE_BRICK_SLAB.get(), this::createSlabItemTable);
            this.dropSelf(ModBlocks.NIGHTSHALE_BRICK_STAIRS.get());
            this.dropSelf(ModBlocks.NIGHTSHALE_BRICK_WALL.get());
            this.add(ModBlocks.NIGHTSHALE_BRICK_VERTICAL_SLAB.get(), this::createVerticalSlabItemTable);

            this.dropSelf(ModBlocks.DETECTOR.get());

            this.dropOther(ModBlocks.MILK_CAULDRON.get(), Items.CAULDRON);
            this.dropSelf(ModBlocks.CHEESE_BLOCK.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream()
                    .filter(
                            entry -> Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(entry))
                                    .getNamespace()
                                    .equals("sprinkle")
                    )
                    .collect(Collectors.toList());
        }
    }

    public static class ModGameplayLoot implements LootTableSubProvider {
        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
            output.accept(
                    MilkCauldronBlock.CHEESE_LOOT_ID, LootTable.lootTable()
                            .withPool(
                                    LootPool.lootPool().add(
                                            LootItem.lootTableItem(ModItems.CHEESE.get()).apply(
                                                    SetItemCountFunction.setCount(UniformGenerator.between(2,4))
                                            )
                                    )
                            )
            );
        }
    }
}
