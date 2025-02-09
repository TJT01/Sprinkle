package mod.tjt01.sprinkle.item;

import mod.tjt01.sprinkle.Main;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static RegistryObject<Item> CHEESE = ITEMS.register(
            "cheese", () -> new Item(new Item.Properties().food(ModFoods.CHEESE))
    );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
