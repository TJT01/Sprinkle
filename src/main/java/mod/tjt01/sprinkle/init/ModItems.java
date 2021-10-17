package mod.tjt01.sprinkle.init;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.config.SprinkleConfig;
import mod.tjt01.sprinkle.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final RegistryObject<Item> BUNDLE = ITEMS.register("bundle", () -> new BundleItem(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1), () -> SprinkleConfig.bundlesEnabled));
}
