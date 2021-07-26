package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.nio.file.Path;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
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

    }
}
