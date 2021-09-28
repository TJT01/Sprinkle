package mod.tjt01.sprinkle.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.function.Supplier;

public class OptionalItem extends Item {
    public final Supplier<Boolean> condition;

    public OptionalItem(Properties properties, Supplier<Boolean> condition) {
        super(properties);
        this.condition = condition;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> stacks) {
        if (condition.get())
            super.fillItemCategory(group, stacks);
    }
}
