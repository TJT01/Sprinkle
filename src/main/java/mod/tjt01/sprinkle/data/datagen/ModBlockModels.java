package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.block.DetectorBlock;
import mod.tjt01.sprinkle.block.VerticalSlabBlock;
import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModels extends BlockStateProvider {

    public ModBlockModels(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, "sprinkle", exFileHelper);
    }

    private void simpleItemModel(Item item) {
        itemModels().getBuilder(item.getRegistryName().getPath())
                .parent(itemModels().getExistingFile(mcLoc("item/generated")))
                .texture("layer0", modLoc("item/" + item.getRegistryName().getPath()));
    }

    private void lanternBlock(LanternBlock block, ModelFile model, ModelFile hanging) {
        getVariantBuilder(block)
                .partialState().with(LanternBlock.HANGING, false)
                        .modelForState().modelFile(model).addModel()
                .partialState().with(LanternBlock.HANGING, true)
                        .modelForState().modelFile(hanging).addModel();
    }

    private void axisBlock(RotatedPillarBlock block, ModelFile model) {
        axisBlock(block, model, model);
    }

    private void cubeAllBlock(Block block) {
        ModelFile model = this.cubeAll(block);
        this.simpleBlock(block, model);
        this.simpleBlockItem(block, model);
    }

    private void simpleSlabBlock(SlabBlock block, ResourceLocation doubleslab, ResourceLocation texture) {
        this.slabBlock(block, doubleslab, texture);
        this.simpleBlockItem(block, this.models().generatedModels.get(this.blockTexture(block)));
    }

    public void verticalSlabBlock(VerticalSlabBlock block, ResourceLocation doubleslab, ResourceLocation bottom, ResourceLocation side, ResourceLocation top) {
        getVariantBuilder(block)
                .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.NORTH)
                .addModels(new ConfiguredModel(
                        models().withExistingParent(block.getRegistryName().getPath(), new ResourceLocation("sprinkle", "block/vertical_slab"))
                                .texture("bottom", bottom).texture("side", side).texture("top", top)
                        , 0, 0, true)
                )
                .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.EAST)
                .addModels(new ConfiguredModel(
                        models().withExistingParent(block.getRegistryName().getPath(), new ResourceLocation("sprinkle", "block/vertical_slab"))
                                .texture("bottom", bottom).texture("side", side).texture("top", top)
                        , 0, 90, true)
                )
                .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.SOUTH)
                .addModels(new ConfiguredModel(
                        models().withExistingParent(block.getRegistryName().getPath(), new ResourceLocation("sprinkle", "block/vertical_slab"))
                                .texture("bottom", bottom).texture("side", side).texture("top", top)
                        , 0, 180, true)
                )
                .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.WEST)
                .addModels(new ConfiguredModel(
                        models().withExistingParent(block.getRegistryName().getPath(), new ResourceLocation("sprinkle", "block/vertical_slab"))
                                .texture("bottom", bottom).texture("side", side).texture("top", top)
                        , 0, 270, true)
                )
                .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.DOUBLE)
                .addModels(new ConfiguredModel(models().getExistingFile(doubleslab)));
    }

    private void verticalSlabBlock(VerticalSlabBlock block, ResourceLocation doubleSlab, ResourceLocation texture) {
        verticalSlabBlock(block, doubleSlab, texture, texture, texture);
    }

    private void simpleVerticalSlabBlock(VerticalSlabBlock block, ResourceLocation doubleSlab, ResourceLocation texture) {
        this.verticalSlabBlock(block, doubleSlab, texture);
        this.simpleBlockItem(block, this.models().generatedModels.get(this.blockTexture(block)));
    }

    private void simpleStairsBlock(StairBlock block, ResourceLocation texture) {
        this.stairsBlock(block, texture);
        this.simpleBlockItem(block, this.models().generatedModels.get(this.blockTexture(block)));
    }

    private void simpleWallBlock(WallBlock block, ResourceLocation texture) {
        this.wallBlock(block, texture);
        this.itemModels().wallInventory(block.getRegistryName().getPath(), texture);
    }

    @Override
    protected void registerStatesAndModels() {
        this.cubeAllBlock(ModBlocks.PURPUR_BRICKS.get());
        this.simpleSlabBlock((SlabBlock) ModBlocks.PURPUR_BRICK_SLAB.get(), ModBlocks.PURPUR_BRICKS.getId(), blockTexture(ModBlocks.PURPUR_BRICKS.get()));
        this.simpleStairsBlock((StairBlock) ModBlocks.PURPUR_BRICK_STAIRS.get(), blockTexture(ModBlocks.PURPUR_BRICKS.get()));
        this.simpleWallBlock((WallBlock) ModBlocks.PURPUR_BRICK_WALL.get(), blockTexture(ModBlocks.PURPUR_BRICKS.get()));
        this.simpleVerticalSlabBlock((VerticalSlabBlock) ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), ModBlocks.PURPUR_BRICKS.getId(), blockTexture(ModBlocks.PURPUR_BRICKS.get()));

        this.cubeAllBlock(ModBlocks.UNNAMED_BLOCK.get());
        this.cubeAllBlock(ModBlocks.UNNAMED_STONE.get());

        getVariantBuilder(ModBlocks.DETECTOR.get())
                .forAllStates(state -> {
                    int rX = 0;
                    int rY = 0;
                    switch (state.getValue(DetectorBlock.FACING)) {
                        case UP: rX = -90; break;
                        case DOWN: rX = 90; break;
                        case NORTH: break;
                        case EAST: rY = 90; break;
                        case SOUTH: rY = 180; break;
                        case WEST: rY = 270; break;
                    }
                    ResourceLocation loc = new ResourceLocation("sprinkle", state.getValue(DetectorBlock.POWERED) ? "block/powered_detector" : "block/detector");
                    ModelFile model = models().getExistingFile(loc);
                    return new ConfiguredModel[]{new ConfiguredModel(model, rX, rY, false)};
                });

        this.simpleBlockItem(ModBlocks.DETECTOR.get(), models().getExistingFile(new ResourceLocation("sprinkle", "block/detector")));
        this.axisBlock((RotatedPillarBlock) ModBlocks.GOLD_CHAIN.get(), this.models().getExistingFile(modLoc("gold_chain")));
        this.simpleItemModel(ModBlocks.GOLD_CHAIN.get().asItem());

        this.lanternBlock(
                (LanternBlock) ModBlocks.GOLD_LANTERN.get(),
                models().withExistingParent("gold_lantern", modLoc("block/template_gold_lantern")).texture("lantern", blockTexture(ModBlocks.GOLD_LANTERN.get())),
                models().withExistingParent("hanging_gold_lantern", modLoc("block/template_hanging_gold_lantern")).texture("lantern", blockTexture(ModBlocks.GOLD_LANTERN.get()))
        );
        this.simpleItemModel(ModBlocks.GOLD_LANTERN.get().asItem());
        this.lanternBlock(
                (LanternBlock) ModBlocks.GOLD_SOUL_LANTERN.get(),
                models().withExistingParent("gold_soul_lantern", modLoc("block/template_gold_lantern")).texture("lantern", blockTexture(ModBlocks.GOLD_SOUL_LANTERN.get())),
                models().withExistingParent("hanging_gold_soul_lantern", modLoc("block/template_hanging_gold_lantern")).texture("lantern", blockTexture(ModBlocks.GOLD_SOUL_LANTERN.get()))
        );
        this.simpleItemModel(ModBlocks.GOLD_SOUL_LANTERN.get().asItem());
    }
}
