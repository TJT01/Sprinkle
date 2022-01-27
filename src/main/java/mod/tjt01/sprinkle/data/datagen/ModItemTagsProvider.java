package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.data.ModTags;
import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
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
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.PIGLIN_REPELLENTS, ItemTags.PIGLIN_REPELLENTS);
        tag(ItemTags.PIGLIN_LOVED).add(ModBlocks.GOLD_CHAIN.get().asItem(), ModBlocks.GOLD_LANTERN.get().asItem());
        //Sprinkle
        //tag(ModTags.Items.BUNDLE_BLACKLIST);
        //Quark
        copy(ModTags.Blocks.QUARK_VERTICAL_SLAB, ModTags.Items.QUARK_VERTICAL_SLAB);
    }

    @Override
    public String getName() {
        return "Item Tags for Sprinkle";
    }
}
