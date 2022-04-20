package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.init.ModBlocks;
import mod.tjt01.sprinkle.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLang extends LanguageProvider {
    public ModLang(DataGenerator gen) {
        super(gen, "sprinkle", "en_us");
    }

    @Override
    public String getName() {
        return "Sprinkle language: en_us";
    }

    @Override
    protected void addTranslations() {

        //Blocks
        add(ModBlocks.PURPUR_BRICKS.get(), "Purpur Bricks");
        add(ModBlocks.PURPUR_BRICK_SLAB.get(), "Purpur Brick Slab");
        add(ModBlocks.PURPUR_BRICK_STAIRS.get(), "Purpur Brick Stairs");
        add(ModBlocks.PURPUR_BRICK_WALL.get(), "Purpur Brick Wall");
        add(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), "Purpur Brick Vertical Slab");
        add(ModBlocks.DETECTOR.get(), "Detector");
        add(ModBlocks.GOLD_CHAIN.get(), "Gold Chain");
        add(ModBlocks.GOLD_LANTERN.get(), "Gold Lantern");
        add(ModBlocks.GOLD_SOUL_LANTERN.get(), "Gold Soul Lantern");
        add(ModBlocks.NIGHTSHALE.get(), "Nightshale");
        add(ModBlocks.NIGHTSHALE_SLAB.get(), "Nightshale Slab");
        add(ModBlocks.NIGHTSHALE_STAIRS.get(), "Nightshale Stairs");
        add(ModBlocks.NIGHTSHALE_WALL.get(), "Nightshale Wall");
        add(ModBlocks.NIGHTSHALE_VERTICAL_SLAB.get(), "Nightshale Vertical Slab");
        add(ModBlocks.NIGHTSHALE_BRICKS.get(), "Nightshale Bricks");

        //Items
        //add(ModItems.BUNDLE.get(), "Bundle");

        //Subtitles
        add("subtitle.sprinkle.block.detector.click", "Detector clicks");

        //Other
        add("sprinkle.ui.disabled", "Disabled");
        add("sprinkle.ui.disabled.quark_missing", "Disabled; install Quark to enable");
    }
}
