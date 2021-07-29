package mod.tjt01.sprinkle.data.datagen;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ConditionalFinishedRecipe implements IFinishedRecipe {
    public IFinishedRecipe parent;

    public ConditionalFinishedRecipe(IFinishedRecipe parent) {
        this.parent = parent;
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        parent.serializeRecipeData(json);
    }

    @Override
    public ResourceLocation getId() {
        return parent.getId();
    }

    @Override
    public IRecipeSerializer<?> getType() {
        return parent.getType();
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {

        return parent.serializeAdvancement();
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return parent.getAdvancementId();
    }
}
