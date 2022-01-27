package mod.tjt01.sprinkle.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;

import java.util.function.Supplier;

import net.minecraft.world.item.Item.Properties;

public class OptionalItem extends Item {
    public final Supplier<Boolean> condition;

    public OptionalItem(Properties properties, Supplier<Boolean> condition) {
        super(properties);
        this.condition = condition;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
        if (condition.get())
            super.fillItemCategory(group, stacks);
    }
}
