package mod.tjt01.sprinkle.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    final ForgeConfigSpec.BooleanValue greenDyeRecipeEnabled;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("utilities");
        builder.push("dyeRecipes");
        greenDyeRecipeEnabled = builder
                .comment("Allow green dye to be crafted with yellow and blue dyes")
                .translation("config.sprinkle.greenDyeEnabled")
                .define("greenDyeEnabled", true);
        builder.pop(2);
    }
}