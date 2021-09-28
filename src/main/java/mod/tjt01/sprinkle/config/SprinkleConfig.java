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

    public static boolean greenDyeEnabled = true;
    public static boolean brownDyeEnabled = true;
    //public static boolean bundlesEnabled = true;

    public static Map<String, Boolean> flags = new HashMap<>();

    public static void bakeCommon(ModConfig config) {
        greenDyeEnabled = COMMON_CONFIG.greenDyeRecipeEnabled.get();
        brownDyeEnabled = COMMON_CONFIG.brownDyeRecipeEnabled.get();

        flags.put("green_dye", greenDyeEnabled);
        flags.put("brown_dye", brownDyeEnabled);
        //flags.put("bundle", bundlesEnabled);
    }

}
