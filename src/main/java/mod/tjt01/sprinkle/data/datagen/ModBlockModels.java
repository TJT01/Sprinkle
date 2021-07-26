package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModels extends BlockStateProvider {

    public ModBlockModels(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, "sprinkle", exFileHelper);
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


    private void simpleStairsBlock(StairsBlock block, ResourceLocation texture) {
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
        this.simpleStairsBlock((StairsBlock) ModBlocks.PURPUR_BRICK_STAIRS.get(), blockTexture(ModBlocks.PURPUR_BRICKS.get()));
        this.simpleWallBlock((WallBlock) ModBlocks.PURPUR_BRICK_WALL.get(), blockTexture(ModBlocks.PURPUR_BRICKS.get()));
    }

}
