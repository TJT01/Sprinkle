package mod.tjt01.sprinkle.data.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ConditionalShapedRecipeBuilder extends ShapedRecipeBuilder {
    public ConditionalShapedRecipeBuilder(IItemProvider result, int count) {
        super(result, count);
    }

    @Override
    public void save(Consumer<IFinishedRecipe> p_200467_1_, ResourceLocation p_200467_2_) {
        //this.ensureValid(p_200467_2_);
        //this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_200467_2_)).rewards(AdvancementRewards.Builder.recipe(p_200467_2_)).requirements(IRequirementsStrategy.OR);
        //p_200467_1_.accept(new ShapedRecipeBuilder.Result(p_200467_2_, this.result, this.count, this.group == null ? "" : this.group, this.rows, this.key, this.advancement, new ResourceLocation(p_200467_2_.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + p_200467_2_.getPath())));

    }

    public class Result extends ShapedRecipeBuilder.Result {
        public Result(ResourceLocation id, Item result, int count, String group, List<String> pattern, Map<Character, Ingredient> key, Advancement.Builder advancement, ResourceLocation advancementId) {
            super(id, result, count, group, pattern, key, advancement, advancementId);
        }
    }
}
