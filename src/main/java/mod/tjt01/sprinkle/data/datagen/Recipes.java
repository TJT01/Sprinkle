package mod.tjt01.sprinkle.data.datagen;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.data.FlagCondition;
import mod.tjt01.sprinkle.data.QuarkFlagCondition;
import mod.tjt01.sprinkle.init.ModBlocks;
import mod.tjt01.sprinkle.init.ModItems;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.FalseCondition;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.NotCondition;


class Recipes extends RecipeProvider {
	
	private static final int DEFAULT_SMELT_TIME = 10*20;
	private static final int DEFAULT_BLAST_TIME = 5*20;
	
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(ModBlocks.PURPUR_BRICKS.get(), 4)
				.pattern("##")
				.pattern("##")
				.define('#', Items.PURPUR_BLOCK)
				.unlockedBy("has_purpur_block", has(Items.PURPUR_BLOCK))
				.save(consumer);

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

		{
			ShapedRecipeBuilder vertSlab = ShapedRecipeBuilder.shaped(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), 3)
					.pattern("#")
					.pattern("#")
					.pattern("#")
					.define('#', ModBlocks.PURPUR_BRICK_SLAB.get())
					.unlockedBy("has_purpur_brick_slab", has(ModBlocks.PURPUR_BRICK_SLAB.get()));
			ConditionalRecipe.builder()
					.addCondition(new QuarkFlagCondition("vertical_slabs"))
					.addRecipe(vertSlab::save)
					.generateAdvancement(new ResourceLocation("sprinkle", "recipes/building_blocks/purpur_brick_vertical_slab"))
					.build(consumer, "sprinkle", "purpur_brick_vertical_slab");
		}
		{
			ShapelessRecipeBuilder vertSlabRevert = ShapelessRecipeBuilder.shapeless(ModBlocks.PURPUR_BRICK_SLAB.get())
					.requires(ModBlocks.PURPUR_BRICK_SLAB.get())
					.unlockedBy("has_purpur_brick_slab", has(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get()));
			ConditionalRecipe.builder()
					.addCondition(new QuarkFlagCondition("vertical_slabs"))
					.addRecipe(vertSlabRevert::save)
					.generateAdvancement(new ResourceLocation("sprinkle", "recipes/building_blocks/purpur_brick_slab_from_purpur_brick_vertical_slab"))
					.build(consumer, "sprinkle", "purpur_brick_slab_from_purpur_brick_vertical_slab");
		}
		{
			ShapelessRecipeBuilder greenDye = ShapelessRecipeBuilder.shapeless(ModBlocks.PURPUR_BRICK_SLAB.get())
					.requires(Items.YELLOW_DYE)
					.requires(Items.BLUE_DYE)
					.unlockedBy("has_yellow_dye", has(Items.YELLOW_DYE))
					.unlockedBy("has_blue_dye", has(Items.BLUE_DYE));
			ConditionalRecipe.builder()
					.addCondition(new FlagCondition("green_dye"))
					.addRecipe(greenDye::save)
					.generateAdvancement(new ResourceLocation("sprinkle", "recipes/misc/green_dye_from_yellow_blue"))
					.build(consumer, "sprinkle", "green_dye_from_yellow_blue");
		}
	}
}
