package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.data.ModTags;
import mod.tjt01.sprinkle.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Main.MODID, existingFileHelper);
    }

    @Override
    @Nonnull
    public String getName() {
        return "Block Tags for Sprinkle";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //Sprinkle
        tag(ModTags.Blocks.DOUBLE_DOOR_BLACKLIST)
                .addOptionalTag(new ResourceLocation("quark", "non_double_door"));

        //Minecraft
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.PURPUR_BRICKS.get(), ModBlocks.PURPUR_BRICK_STAIRS.get(), ModBlocks.PURPUR_BRICK_SLAB.get(),
                ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), ModBlocks.PURPUR_BRICK_WALL.get(),
                ModBlocks.DETECTOR.get(),
                ModBlocks.NIGHTSHALE.get(), ModBlocks.NIGHTSHALE_STAIRS.get(), ModBlocks.NIGHTSHALE_SLAB.get(),
                ModBlocks.NIGHTSHALE_VERTICAL_SLAB.get(), ModBlocks.NIGHTSHALE_WALL.get(),
                ModBlocks.GLIMMERING_NIGHTSHALE.get(),
                ModBlocks.NIGHTSHALE_BRICKS.get(), ModBlocks.NIGHTSHALE_BRICK_STAIRS.get(), ModBlocks.NIGHTSHALE_BRICK_SLAB.get(),
                ModBlocks.NIGHTSHALE_BRICK_VERTICAL_SLAB.get(), ModBlocks.NIGHTSHALE_BRICK_WALL.get(),
                ModBlocks.GOLD_CHAIN.get(), ModBlocks.GOLD_LANTERN.get(), ModBlocks.GOLD_SOUL_LANTERN.get(),
                ModBlocks.MILK_CAULDRON.get()
        );

        tag(BlockTags.CAULDRONS).add(
                ModBlocks.MILK_CAULDRON.get()
        );

        tag(BlockTags.SLABS).add(ModBlocks.PURPUR_BRICK_SLAB.get(), ModBlocks.NIGHTSHALE_SLAB.get(), ModBlocks.NIGHTSHALE_BRICK_SLAB.get());
        tag(BlockTags.STAIRS).add(ModBlocks.PURPUR_BRICK_STAIRS.get(), ModBlocks.NIGHTSHALE_STAIRS.get(), ModBlocks.NIGHTSHALE_BRICK_STAIRS.get());
        tag(BlockTags.WALLS).add(ModBlocks.PURPUR_BRICK_WALL.get(), ModBlocks.NIGHTSHALE_WALL.get(), ModBlocks.NIGHTSHALE_BRICK_WALL.get());

        tag(BlockTags.PIGLIN_REPELLENTS).add(ModBlocks.GOLD_SOUL_LANTERN.get());
        tag(BlockTags.GUARDED_BY_PIGLINS).add(
                ModBlocks.GOLD_CHAIN.get(), ModBlocks.GOLD_LANTERN.get(),
                ModBlocks.GOLD_SOUL_LANTERN.get()
        );
        //Forge
        //Quark
        tag(ModTags.Blocks.QUARK_VERTICAL_SLAB).add(
                ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), ModBlocks.NIGHTSHALE_VERTICAL_SLAB.get(),
                ModBlocks.NIGHTSHALE_BRICK_VERTICAL_SLAB.get()
        );
    }
}
