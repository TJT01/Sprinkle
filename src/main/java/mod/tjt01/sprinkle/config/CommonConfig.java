package mod.tjt01.sprinkle.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    final ForgeConfigSpec.BooleanValue jukeboxTweakEnabled;
    final ForgeConfigSpec.BooleanValue doubleDoorsEnabled;

    final ForgeConfigSpec.BooleanValue greenDyeRecipeEnabled;
    final ForgeConfigSpec.BooleanValue brownDyeRecipeEnabled;

    final ForgeConfigSpec.BooleanValue smoothNightVisionEnabled;
    final ForgeConfigSpec.IntValue smoothNightVisionFadeTime;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("tweaks");
        doubleDoorsEnabled = builder
                .comment("Allows opening both sides of a double door")
                .translation("config.sprinkle.tweaks.doubleDoorsOpenTogether")
                .define("doubleDoorsOpenTogether", true);
        jukeboxTweakEnabled = builder
                .comment(
                        "Allow hoppers to interact with jukeboxes",
                        "Also works with modded pipes"
                )
                .translation("config.sprinkle.jukebox")
                .define("jukeboxContainerEnabled", true);
        builder.push("smoothNightVision");
        smoothNightVisionEnabled = builder
                .comment("Removes night vision flashing; replacing it with a smooth fadeout")
                .translation("config.sprinkle.tweaks.smoothNightVision.enabled")
                .define("enabled", true);
        smoothNightVisionFadeTime = builder
                .comment("How long the fade lasts, in ticks")
                .translation("config.sprinkle.tweaks.smoothNightVision.fadeTime")
                .defineInRange("fadeTime", 200, 0, Integer.MAX_VALUE);
        builder.pop(2);

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
