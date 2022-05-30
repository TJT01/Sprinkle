package mod.tjt01.sprinkle.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    final ForgeConfigSpec.BooleanValue jukeboxTweakEnabled;

    final ForgeConfigSpec.BooleanValue greenDyeRecipeEnabled;
    final ForgeConfigSpec.BooleanValue brownDyeRecipeEnabled;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("tweaks");
        jukeboxTweakEnabled = builder
                .comment(
                        "Allow hoppers to interact with jukeboxes",
                        "Also works with modded pipes"
                )
                .translation("config.sprinkle.jukebox")
                .define("jukeboxContainerEnabled", true);
        builder.pop();

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
        builder.pop(2);
    }
}
