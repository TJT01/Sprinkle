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
import net.minecraftforge.common.Tags;
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

		ShapedRecipeBuilder.shaped(ModBlocks.DETECTOR.get())
				.pattern("###")
				.pattern("RRI")
				.pattern("###")
				.define('#', Tags.Items.COBBLESTONE)
				.define('R', Tags.Items.DUSTS_REDSTONE)
				.define('I', Tags.Items.INGOTS_IRON)
				.unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
				.save(consumer);

		SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.PURPUR_BLOCK), ModBlocks.PURPUR_BRICKS.get())
				.unlocks("has_purpur_block", has(Items.PURPUR_BLOCK))
				.save(consumer, new ResourceLocation("sprinkle", "purpur_bricks_from_purpur_block_stonecutting"));

		SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.PURPUR_BLOCK), ModBlocks.PURPUR_BRICK_SLAB.get(), 2)
				.unlocks("has_purpur_block", has(Items.PURPUR_BLOCK))
				.save(consumer, new ResourceLocation("sprinkle", "purpur_brick_slab_from_purpur_block_stonecutting"));

		SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.PURPUR_BLOCK), ModBlocks.PURPUR_BRICK_STAIRS.get())
				.unlocks("has_purpur_block", has(Items.PURPUR_BLOCK))
				.save(consumer, new ResourceLocation("sprinkle", "purpur_brick_stairs_from_purpur_block_stonecutting"));

		SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.PURPUR_BLOCK), ModBlocks.PURPUR_BRICK_WALL.get())
				.unlocks("has_purpur_block", has(Items.PURPUR_BLOCK))
				.save(consumer, new ResourceLocation("sprinkle", "purpur_brick_wall_from_purpur_block_stonecutting"));

		SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.PURPUR_BRICKS.get()), ModBlocks.PURPUR_BRICK_SLAB.get(), 2)
				.unlocks("has_purpur_block", has(ModBlocks.PURPUR_BRICKS.get()))
				.save(consumer, new ResourceLocation("sprinkle", "purpur_brick_slab_from_purpur_bricks_stonecutting"));

		SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.PURPUR_BLOCK), ModBlocks.PURPUR_BRICK_STAIRS.get())
				.unlocks("has_purpur_block", has(Items.PURPUR_BLOCK))
				.save(consumer, new ResourceLocation("sprinkle", "purpur_brick_stairs_from_purpur_bricks_stonecutting"));

		SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.PURPUR_BLOCK), ModBlocks.PURPUR_BRICK_WALL.get())
				.unlocks("has_purpur_block", has(Items.PURPUR_BLOCK))
				.save(consumer, new ResourceLocation("sprinkle", "purpur_brick_wall_from_purpur_bricks_stonecutting"));

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
			String name = "purpur_brick_vertical_slab_from_purpur_block_stonecutting";
			SingleItemRecipeBuilder vertSlab = SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.PURPUR_BLOCK), ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), 2)
					.unlocks("has_purpur_brick", has(Items.PURPUR_BLOCK));
			ConditionalRecipe.builder()
					.addCondition(new QuarkFlagCondition("vertical_slabs"))
					.addRecipe((recipeConsumer) -> vertSlab.save(recipeConsumer, new ResourceLocation("sprinkle", name)))
					.generateAdvancement(new ResourceLocation("sprinkle", "recipes/building_blocks/purpur_brick_vertical_slab_from_purpur_block_stonecutting"))
					.build(consumer, "sprinkle", name);
		}
		{
			String name = "purpur_brick_vertical_slab_from_purpur_bricks_stonecutting";
			SingleItemRecipeBuilder vertSlab = SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.PURPUR_BLOCK), ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), 2)
					.unlocks("has_purpur_brick", has(Items.PURPUR_BLOCK));
			ConditionalRecipe.builder()
					.addCondition(new QuarkFlagCondition("vertical_slabs"))
					.addRecipe((recipeConsumer) -> vertSlab.save(recipeConsumer, new ResourceLocation("sprinkle", name)))
					.generateAdvancement(new ResourceLocation("sprinkle", "recipes/building_blocks/purpur_brick_vertical_slab_from_purpur_block_stonecutting"))
					.build(consumer, "sprinkle", name);
		}
		{
			String name = "purpur_brick_slab_from_purpur_brick_vertical_slab";
			ShapelessRecipeBuilder vertSlabRevert = ShapelessRecipeBuilder.shapeless(ModBlocks.PURPUR_BRICK_SLAB.get())
					.requires(ModBlocks.PURPUR_BRICK_SLAB.get())
					.unlockedBy("has_purpur_brick_slab", has(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get()));
			ConditionalRecipe.builder()
					.addCondition(new QuarkFlagCondition("vertical_slabs"))
					.addRecipe((recipeConsumer) -> vertSlabRevert.save(recipeConsumer, new ResourceLocation("sprinkle", name)))
					.generateAdvancement(new ResourceLocation("sprinkle", "recipes/building_blocks/" + name))
					.build(consumer, "sprinkle", name);
		}
		{
			String name = "green_dye_from_yellow_blue";
			ShapelessRecipeBuilder greenDye = ShapelessRecipeBuilder.shapeless(Items.GREEN_DYE)
					.requires(Items.YELLOW_DYE)
					.requires(Items.BLUE_DYE)
					.unlockedBy("has_yellow_dye", has(Items.YELLOW_DYE))
					.unlockedBy("has_blue_dye", has(Items.BLUE_DYE));
			ConditionalRecipe.builder()
					.addCondition(new FlagCondition("green_dye"))
					.addRecipe((recipeConsumer) -> greenDye.save(recipeConsumer, new ResourceLocation("sprinkle", name)))
					.generateAdvancement(new ResourceLocation("sprinkle", "recipes/misc/" + name))
					.build(consumer, "sprinkle", name);
		}
		{
			String name = "brown_dye_from_orange_blue";
			ShapelessRecipeBuilder greenDye = ShapelessRecipeBuilder.shapeless(Items.BROWN_DYE)
					.requires(Items.ORANGE_DYE)
					.requires(Items.BLUE_DYE)
					.unlockedBy("has_orange_dye", has(Items.ORANGE_DYE))
					.unlockedBy("has_blue_dye", has(Items.BLUE_DYE));
			ConditionalRecipe.builder()
					.addCondition(new FlagCondition("green_dye"))
					.addRecipe((recipeConsumer) -> greenDye.save(recipeConsumer, new ResourceLocation("sprinkle", name)))
					.generateAdvancement(new ResourceLocation("sprinkle", "recipes/misc/" + name))
					.build(consumer, "sprinkle", name);
		}
	}
}
