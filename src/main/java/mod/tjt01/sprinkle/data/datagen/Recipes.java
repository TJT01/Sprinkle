package mod.tjt01.sprinkle.data.datagen;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.init.ModBlocks;
import mod.tjt01.sprinkle.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.ShapedRecipeBuilder;


class Recipes extends RecipeProvider {
	
	private static final int DEFAULT_SMELT_TIME = 10*20;
	private static final int DEFAULT_BLAST_TIME = 5*20;
	
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(ModBlocks.PURPUR_BRICK_STAIRS.get(), 4)
				.pattern("#  ")
				.pattern("## ")
				.pattern("###")
				.define('#', ModBlocks.PURPUR_BRICKS.get())
				.unlockedBy("has_purpur_bricks", has(ModBlocks.PURPUR_BRICKS.get()))
				.save(consumer);
		
		ShapedRecipeBuilder.shaped(ModBlocks.PURPUR_BRICK_SLAB.get(), 6)
				.pattern("###")
				.define('#', ModBlocks.PURPUR_BRICKS.get())
				.unlockedBy("has_purpur_bricks", has(ModBlocks.PURPUR_BRICKS.get()))
				.save(consumer);

		ShapedRecipeBuilder.shaped(ModBlocks.PURPUR_BRICK_WALL.get(), 6)
				.pattern("###")
				.pattern("###")
				.define('#', ModBlocks.PURPUR_BRICKS.get())
				.unlockedBy("has_purpur_bricks", has(ModBlocks.PURPUR_BRICKS.get()))
				.save(consumer);
	}

}
