package mod.tjt01.sprinkle.data.datagen;

import java.util.function.Consumer;

import mod.tjt01.lapislib.data.OptionalRecipeBuilder;
import mod.tjt01.sprinkle.data.FlagCondition;
import mod.tjt01.sprinkle.data.QuarkFlagCondition;
import mod.tjt01.sprinkle.init.ModBlocks;
import mod.tjt01.sprinkle.init.ModItems;
import net.minecraft.data.*;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;

class Recipes extends RecipeProvider {

    private static final int DEFAULT_SMELT_TIME = 10*20;
    private static final int DEFAULT_BLAST_TIME = 5*20;

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    protected ShapedRecipeBuilder twoByTwo(ItemLike result, Ingredient ingredient, int count) {
        return ShapedRecipeBuilder.shaped(result, count)
                .pattern("##")
                .pattern("##")
                .define('#', ingredient);
    }

    protected ShapedRecipeBuilder twoByTwo(ItemLike result, ItemLike ingredient, int count) {
        return this.twoByTwo(result, Ingredient.of(ingredient), count);
    }

    protected ShapedRecipeBuilder oneByThree(ItemLike result, Ingredient ingredient, int count) {
        return ShapedRecipeBuilder.shaped(result, count)
                .pattern("###")
                .define('#', ingredient);
    }

    protected ShapedRecipeBuilder oneByThree(ItemLike result, ItemLike ingredient, int count) {
        return this.oneByThree(result, Ingredient.of(ingredient), count);
    }

    protected ShapedRecipeBuilder stairs(ItemLike result, Ingredient ingredient) {
        return ShapedRecipeBuilder.shaped(result, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ingredient);
    }

    protected ShapedRecipeBuilder stairs(ItemLike result, ItemLike ingredient) {
        return this.stairs(result, Ingredient.of(ingredient));
    }

    protected void simpleStonecutting(ItemLike ingredient, ItemLike output, int count, Consumer<FinishedRecipe> consumer) {
        String ingredientName = ingredient.asItem().getRegistryName().getPath();
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), output, count)
                .unlockedBy("has_" + ingredientName, has(ingredient))
                .save(consumer, new ResourceLocation("sprinkle", output.asItem().getRegistryName().getPath() + "_from_" + ingredientName + "_stonecutting"));
    }

    protected void simpleStonecutting(ItemLike ingredient, ItemLike output, Consumer<FinishedRecipe> consumer) {
        this.simpleStonecutting(ingredient, output, 1, consumer);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        this.twoByTwo(ModBlocks.PURPUR_BRICKS.get(), Items.PURPUR_BLOCK, 4)
                .unlockedBy("has_purpur_block", has(Items.PURPUR_BLOCK))
                .save(consumer);

        this.stairs(ModBlocks.PURPUR_BRICK_STAIRS.get(), ModBlocks.PURPUR_BRICKS.get())
                .unlockedBy("has_purpur_bricks", has(ModBlocks.PURPUR_BRICKS.get()))
                .save(consumer);

        this.oneByThree(ModBlocks.PURPUR_BRICK_SLAB.get(), ModBlocks.PURPUR_BRICKS.get(), 6)
                .unlockedBy("has_purpur_bricks", has(ModBlocks.PURPUR_BRICKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.PURPUR_BRICK_WALL.get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.PURPUR_BRICKS.get())
                .unlockedBy("has_purpur_bricks", has(ModBlocks.PURPUR_BRICKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.NIGHTSHALE.get(), 4)
                .pattern("###")
                .pattern("#I#")
                .pattern("###")
                .define('#', Items.FLINT)
                .define('I', Tags.Items.GEMS_LAPIS)
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(consumer);

        this.stairs(ModBlocks.NIGHTSHALE_STAIRS.get(), ModBlocks.NIGHTSHALE.get())
                .unlockedBy("has_nightshale", has(ModBlocks.NIGHTSHALE.get()))
                .save(consumer);

        this.oneByThree(ModBlocks.NIGHTSHALE_SLAB.get(), ModBlocks.NIGHTSHALE.get(), 6)
                .unlockedBy("has_nightshale", has(ModBlocks.NIGHTSHALE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.NIGHTSHALE_WALL.get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.NIGHTSHALE.get())
                .unlockedBy("has_nightshale", has(ModBlocks.NIGHTSHALE.get()))
                .save(consumer);

        this.twoByTwo(ModBlocks.NIGHTSHALE_BRICKS.get(), ModBlocks.NIGHTSHALE_BRICKS.get(), 4)
                .unlockedBy("has_nightshale", has(ModBlocks.NIGHTSHALE_BRICKS.get()))
                .save(consumer);

        this.stairs(ModBlocks.NIGHTSHALE_BRICK_STAIRS.get(), ModBlocks.NIGHTSHALE_BRICKS.get())
                .unlockedBy("has_nightshale", has(ModBlocks.NIGHTSHALE_BRICKS.get()))
                .save(consumer);

        this.oneByThree(ModBlocks.NIGHTSHALE_BRICK_SLAB.get(), ModBlocks.NIGHTSHALE_BRICKS.get(), 6)
                .unlockedBy("has_nightshale", has(ModBlocks.NIGHTSHALE_BRICKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.NIGHTSHALE_BRICK_WALL.get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.NIGHTSHALE_BRICKS.get())
                .unlockedBy("has_nightshale", has(ModBlocks.NIGHTSHALE_BRICKS.get()))
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

        ShapedRecipeBuilder.shaped(ModBlocks.GOLD_LANTERN.get())
                .pattern("###")
                .pattern("#I#")
                .pattern("###")
                .define('#', Tags.Items.NUGGETS_GOLD)
                .define('I', Items.TORCH)
                .unlockedBy("has_gold_nugget", has(Tags.Items.NUGGETS_GOLD))
                .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.GOLD_SOUL_LANTERN.get())
                .pattern("###")
                .pattern("#I#")
                .pattern("###")
                .define('#', Tags.Items.NUGGETS_GOLD)
                .define('I', Items.TORCH)
                .unlockedBy("has_soul_torch", has(Items.SOUL_TORCH))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.GOLD_CHAIN.get())
                .pattern(".")
                .pattern("#")
                .pattern(".")
                .define('#', Tags.Items.INGOTS_GOLD)
                .define('.', Tags.Items.NUGGETS_GOLD)
                .unlockedBy("has_gold_nugget", has(Tags.Items.NUGGETS_GOLD))
                .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
                .save(consumer);

        this.simpleStonecutting(Items.PURPUR_BLOCK, ModBlocks.PURPUR_BRICKS.get(), consumer);
        this.simpleStonecutting(Items.PURPUR_BLOCK, ModBlocks.PURPUR_BRICK_SLAB.get(), 2, consumer);
        this.simpleStonecutting(Items.PURPUR_BLOCK, ModBlocks.PURPUR_BRICK_STAIRS.get(), consumer);
        this.simpleStonecutting(Items.PURPUR_BLOCK, ModBlocks.PURPUR_BRICK_WALL.get(), consumer);
        this.simpleStonecutting(ModBlocks.PURPUR_BRICKS.get(), ModBlocks.PURPUR_BRICK_SLAB.get(), 2, consumer);
        this.simpleStonecutting(ModBlocks.PURPUR_BRICKS.get(), ModBlocks.PURPUR_BRICK_STAIRS.get(), consumer);
        this.simpleStonecutting(ModBlocks.PURPUR_BRICKS.get(), ModBlocks.PURPUR_BRICK_WALL.get(), consumer);

        this.simpleStonecutting(ModBlocks.NIGHTSHALE.get(), ModBlocks.NIGHTSHALE_BRICKS.get(), consumer);
        this.simpleStonecutting(ModBlocks.NIGHTSHALE.get(), ModBlocks.NIGHTSHALE_BRICK_SLAB.get(), 2, consumer);
        this.simpleStonecutting(ModBlocks.NIGHTSHALE.get(), ModBlocks.NIGHTSHALE_BRICK_STAIRS.get(), consumer);
        this.simpleStonecutting(ModBlocks.NIGHTSHALE.get(), ModBlocks.NIGHTSHALE_BRICK_WALL.get(), consumer);
        this.simpleStonecutting(ModBlocks.NIGHTSHALE_BRICKS.get(), ModBlocks.NIGHTSHALE_BRICK_SLAB.get(), 2, consumer);
        this.simpleStonecutting(ModBlocks.NIGHTSHALE_BRICKS.get(), ModBlocks.NIGHTSHALE_BRICK_STAIRS.get(), consumer);
        this.simpleStonecutting(ModBlocks.NIGHTSHALE_BRICKS.get(), ModBlocks.NIGHTSHALE_BRICK_WALL.get(), consumer);

        //TODO fix in LapisLib
//        OptionalRecipeBuilder.optional(finishedRecipeConsumer -> {
//            ShapedRecipeBuilder.shaped(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), 3)
//                    .pattern("#")
//                    .pattern("#")
//                    .pattern("#")
//                    .define('#', ModBlocks.PURPUR_BRICK_SLAB.get())
//                    .unlockedBy("has_purpur_brick_slab", has(ModBlocks.PURPUR_BRICK_SLAB.get()))
//                    .save(finishedRecipeConsumer);
//        })
//                .addCondition(new QuarkFlagCondition("vertical_slabs"))
//                .save(consumer);
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
                    .unlockedBy("has_purpur_brick", has(Items.PURPUR_BLOCK));
            ConditionalRecipe.builder()
                    .addCondition(new QuarkFlagCondition("vertical_slabs"))
                    .addRecipe((recipeConsumer) -> vertSlab.save(recipeConsumer, new ResourceLocation("sprinkle", name)))
                    .generateAdvancement(new ResourceLocation("sprinkle", "recipes/building_blocks/purpur_brick_vertical_slab_from_purpur_block_stonecutting"))
                    .build(consumer, "sprinkle", name);
        }
        {
            String name = "purpur_brick_vertical_slab_from_purpur_bricks_stonecutting";
            SingleItemRecipeBuilder vertSlab = SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.PURPUR_BRICKS.get()), ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), 2)
                    .unlockedBy("has_purpur_brick", has(Items.PURPUR_BLOCK));
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
            ShapelessRecipeBuilder greenDye = ShapelessRecipeBuilder.shapeless(Items.GREEN_DYE, 2)
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
            ShapelessRecipeBuilder greenDye = ShapelessRecipeBuilder.shapeless(Items.BROWN_DYE, 2)
                    .requires(Items.ORANGE_DYE)
                    .requires(Items.BLUE_DYE)
                    .unlockedBy("has_orange_dye", has(Items.ORANGE_DYE))
                    .unlockedBy("has_blue_dye", has(Items.BLUE_DYE));
            ConditionalRecipe.builder()
                    .addCondition(new FlagCondition("brown_dye"))
                    .addRecipe((recipeConsumer) -> greenDye.save(recipeConsumer, new ResourceLocation("sprinkle", name)))
                    .generateAdvancement(new ResourceLocation("sprinkle", "recipes/misc/" + name))
                    .build(consumer, "sprinkle", name);
        }
        /*TODO: bundle recipe?
        {
            String name = "bundle";
            ShapedRecipeBuilder bundle = ShapedRecipeBuilder.shaped(ModItems.BUNDLE.get())
                    .pattern("S#S")
                    .pattern("# #")
                    .pattern("###")
                    .define('S', Items.STRING)
                    .define('#', Items.RABBIT_HIDE)
                    .unlockedBy("has_rabbit_hide", has(Items.RABBIT_HIDE));
            ConditionalRecipe.builder()
                    .addCondition(new FlagCondition("bundle"))
                    .addRecipe(recipeConsumer -> bundle.save(recipeConsumer, new ResourceLocation("sprinkle", name)))
                    .generateAdvancement(new ResourceLocation("sprinkle", "recipes/misc/" + name))
                    .build(consumer, "sprinkle", name);
        }
         */
    }
}
