package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.data.ModTags;
import mod.tjt01.sprinkle.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, providerCompletableFuture, blockTags, Main.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
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
