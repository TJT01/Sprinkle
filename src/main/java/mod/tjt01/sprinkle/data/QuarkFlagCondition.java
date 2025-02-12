package mod.tjt01.sprinkle.data;

import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.FalseCondition;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.ModList;

public class QuarkFlagCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation("sprinkle", "quark_flag");
    private ICondition wrapped = null;
    private final String flag;

    public QuarkFlagCondition(String flag) {
        this.flag = flag;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        if (wrapped == null && ModList.get().isLoaded("quark")) {
            if (ModList.get().isLoaded("quark")) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("type", "quark:flag");
                jsonObject.addProperty("flag", this.flag);
                wrapped = CraftingHelper.getCondition(jsonObject);
            } else {
                wrapped = FalseCondition.INSTANCE;
            }
        }
        return wrapped.test(context);
    }

    public static class Serializer implements IConditionSerializer<QuarkFlagCondition> {
        private static final ResourceLocation NAME = new ResourceLocation("sprinkle", "quark_flag");
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, QuarkFlagCondition value) {
            json.addProperty("flag", value.flag);
        }

        @Override
        public QuarkFlagCondition read(JsonObject json) {
            return new QuarkFlagCondition(GsonHelper.getAsString(json, "flag"));
        }

        @Override
        public ResourceLocation getID() {
            return NAME;
        }
    }
}
