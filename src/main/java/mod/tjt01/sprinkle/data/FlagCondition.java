package mod.tjt01.sprinkle.data;

import com.google.gson.JsonObject;
import mod.tjt01.sprinkle.config.SprinkleConfig;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class FlagCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation("sprinkle", "flag");
    public final String flag;

    public FlagCondition(String flag) {
        this.flag = flag;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        return SprinkleConfig.flags.getOrDefault(flag, false);
    }

    public static class Serializer implements IConditionSerializer<FlagCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, FlagCondition value) {
            json.addProperty("flag", value.flag);
        }

        @Override
        public FlagCondition read(JsonObject json) {
            return new FlagCondition(JSONUtils.getAsString(json, "flag"));
        }

        @Override
        public ResourceLocation getID() {
            return FlagCondition.NAME;
        }
    }
}
