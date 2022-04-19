package mod.tjt01.sprinkle.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    final ForgeConfigSpec.BooleanValue effectiveBlindnessEnabled;
    final ForgeConfigSpec.DoubleValue blindnessMultiplier;

    final ForgeConfigSpec.BooleanValue greenDyeRecipeEnabled;
    final ForgeConfigSpec.BooleanValue brownDyeRecipeEnabled;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("tweaks");
        builder.push("effectiveBlindness");
        effectiveBlindnessEnabled = builder
                .comment(
                        "If enabled, make the Blindness effect work on mobs",
                        "Specific mobs can be blacklisted using the #sprinkle:blindness_immune tag"
                        )
                .translation("config.sprinkle.effectiveBlindness.Enabled")
                .define("enabled", true);
        blindnessMultiplier = builder
                .comment("The multiplier to apply to an entity's visibility if the looking entity has blindness")
                .translation("config.sprinkle.effectiveBlindness.Multiplier")
                .defineInRange("visibilityFactor", 0.25D, 0.0D, Double.POSITIVE_INFINITY);
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
