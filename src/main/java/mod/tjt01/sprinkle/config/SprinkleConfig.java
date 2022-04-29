package mod.tjt01.sprinkle.config;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class SprinkleConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    static final CommonConfig COMMON_CONFIG;
    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPairCommon = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_CONFIG = specPairCommon.getLeft();
        COMMON_SPEC = specPairCommon.getRight();
    }

    public static boolean effectiveBlindnessEnabled = true;
    public static double blindnessMultiplier = 0.25;
    public static boolean jukeboxCapabilityEnabled = true;

    public static boolean greenDyeEnabled = true;
    public static boolean brownDyeEnabled = true;

    public static Map<String, Boolean> flags = new HashMap<>();

    public static void bakeCommon(ModConfig config) {
        effectiveBlindnessEnabled = COMMON_CONFIG.effectiveBlindnessEnabled.get();
        blindnessMultiplier = COMMON_CONFIG.blindnessMultiplier.get();
        jukeboxCapabilityEnabled = COMMON_CONFIG.jukeboxTweakEnabled.get();

        greenDyeEnabled = COMMON_CONFIG.greenDyeRecipeEnabled.get();
        brownDyeEnabled = COMMON_CONFIG.brownDyeRecipeEnabled.get();

        flags.put("green_dye", greenDyeEnabled);
        flags.put("brown_dye", brownDyeEnabled);
    }

}
