package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.data.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTagsProvider, "sprinkle", existingFileHelper);
    }

    @Override
    protected void addTags() {
        //Minecraft
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        //Quark
        copy(ModTags.Blocks.QUARK_VERTICAL_SLAB, ModTags.Items.QUARK_VERTICAL_SLAB);
    }

    @Override
    public String getName() {
        return "Item Tags for Sprinkle";
    }
}
