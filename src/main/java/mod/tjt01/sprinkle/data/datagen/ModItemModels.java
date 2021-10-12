package mod.tjt01.sprinkle.data.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, "sprinkle", existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.singleTexture("bundle", mcLoc("item/generated"), "layer0", modLoc("item/bundle"))
                .override()
                .model(this.singleTexture("bundle_filled", mcLoc("item/generated"), "layer0", modLoc("item/bundle_filled")))
                .predicate(modLoc("filled"), 1e-7f)
                .end();
    }
}
