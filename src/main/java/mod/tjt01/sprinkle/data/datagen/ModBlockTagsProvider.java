package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.data.ModTags;
import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, "sprinkle", existingFileHelper);
    }

    @Override
    public String getName() {
        return "Block Tags for Sprinkle";
    }

    @Override
    protected void addTags() {
        //Minecraft
        tag(BlockTags.SLABS).add(ModBlocks.PURPUR_BRICK_SLAB.get());
        tag(BlockTags.STAIRS).add(ModBlocks.PURPUR_BRICK_STAIRS.get());
        tag(BlockTags.WALLS).add(ModBlocks.PURPUR_BRICK_WALL.get());
        //Forge
        //Quark
        tag(ModTags.Blocks.QUARK_VERTICAL_SLAB).add(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get());
    }
}
