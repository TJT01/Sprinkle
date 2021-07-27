package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLang extends LanguageProvider {
    public ModLang(DataGenerator gen) {
        super(gen, "sprinkle", "en-us");
    }

    @Override
    public String getName() {
        return "Sprinkle language: en-us";
    }

    @Override
    protected void addTranslations() {
        //Blocks
        add(ModBlocks.PURPUR_BRICKS.get(), "Purpur Bricks");
        add(ModBlocks.PURPUR_BRICK_SLAB.get(), "Purpur Brick Slab");
        add(ModBlocks.PURPUR_BRICK_STAIRS.get(), "Purpur Brick Stairs");
        add(ModBlocks.PURPUR_BRICK_WALL.get(), "Purpur Brick Wall");
        add(ModBlocks.VERTICAL_PURPUR_BRICK_SLAB.get(), "Purpur Brick Vertical Slab");
    }
}
