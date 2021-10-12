package mod.tjt01.sprinkle.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    final ForgeConfigSpec.BooleanValue greenDyeRecipeEnabled;
    final ForgeConfigSpec.BooleanValue brownDyeRecipeEnabled;
    final ForgeConfigSpec.BooleanValue bundlesEnabled;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("utilities");
        builder.push("dyeRecipes");
        greenDyeRecipeEnabled = builder
                .comment("Allow green dye to be crafted with yellow and blue dyes")
                .translation("config.sprinkle.greenDyeEnabled")
                .define("greenDyeEnabled", true);
        brownDyeRecipeEnabled = builder
                .comment("Allow brown dye to be crafted with orange and blue dyes")
                .translation("config.sprinkle.brownDyeEnabled")
                .define("brownDyeEnabled", true);
        builder.pop();
        builder.push("bundles");
        bundlesEnabled = builder
                .comment("Backports bundles from 1.17 and later")
                .translation("config.sprinkle.bundlesEnabled")
                .define("bundlesEnabled", true);
        builder.pop();
    }
}
