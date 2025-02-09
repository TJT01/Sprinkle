package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, "sprinkle", existingFileHelper);
    }

    private void simpleItemModel(Item item) {
        String id = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
        getBuilder(id)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", modLoc("item/" + id));
    }

    @Override
    protected void registerModels() {
        this.simpleItemModel(ModItems.CHEESE.get());
    }
}
