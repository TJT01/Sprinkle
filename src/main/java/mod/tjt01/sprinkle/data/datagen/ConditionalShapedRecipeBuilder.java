package mod.tjt01.sprinkle.data.datagen;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @deprecated consider using forge's ConditionalRecipe.Builder instead (didn't know that existed)
 */
@Deprecated
public class ConditionalShapedRecipeBuilder extends ShapedRecipeBuilder {
    private List<ICondition> conditions = Lists.newArrayList();

    public ConditionalShapedRecipeBuilder(IItemProvider result, int count) {
        super(result, count);
    }

    public static ConditionalShapedRecipeBuilder shaped(IItemProvider result, int count) {
        return new ConditionalShapedRecipeBuilder(result, count);
    }

    public static ConditionalShapedRecipeBuilder shaped(IItemProvider result) {
        return shaped(result, 1);
    }

    @Override
    public ConditionalShapedRecipeBuilder define(Character p_200469_1_, ITag<Item> p_200469_2_) {
        return (ConditionalShapedRecipeBuilder) super.define(p_200469_1_, p_200469_2_);
    }

    @Override
    public ConditionalShapedRecipeBuilder define(Character p_200462_1_, IItemProvider p_200462_2_) {
        return (ConditionalShapedRecipeBuilder) super.define(p_200462_1_, p_200462_2_);
    }

    @Override
    public ConditionalShapedRecipeBuilder define(Character p_200471_1_, Ingredient p_200471_2_) {
        return (ConditionalShapedRecipeBuilder) super.define(p_200471_1_, p_200471_2_);
    }

    @Override
    public ConditionalShapedRecipeBuilder pattern(String p_200472_1_) {
        return (ConditionalShapedRecipeBuilder) super.pattern(p_200472_1_);
    }

    @Override
    public ConditionalShapedRecipeBuilder unlockedBy(String p_200465_1_, ICriterionInstance p_200465_2_) {
        return (ConditionalShapedRecipeBuilder) super.unlockedBy(p_200465_1_, p_200465_2_);
    }

    @Override
    public ConditionalShapedRecipeBuilder group(String p_200473_1_) {
        return (ConditionalShapedRecipeBuilder) super.group(p_200473_1_);
    }

    public ConditionalShapedRecipeBuilder condition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    //Vanilla copy: use our Result class instead of minecraft's
    @Override
    public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        this.ensureValid(id);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(IRequirementsStrategy.OR);
        consumer.accept(new Result(id, this.result, this.count, this.group == null ? "" : this.group, this.rows, this.key, this.advancement, new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + id.getPath()), conditions));
    }

    public class Result extends ShapedRecipeBuilder.Result {
        private List<ICondition> conditions;
        public Result(ResourceLocation id, Item result, int count, String group, List<String> pattern, Map<Character, Ingredient> key, Advancement.Builder advancement, ResourceLocation advancementId, List<ICondition> conditions) {
            super(id, result, count, group, pattern, key, advancement, advancementId);
            this.conditions = conditions;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            JsonArray conditionArray = new JsonArray();
            for(ICondition condition : conditions) {
                conditionArray.add(CraftingHelper.serialize(condition));
            }
            json.add("conditions", conditionArray);
        }
    }
}
