package mod.tjt01.sprinkle.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;

import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class OptionalBlock extends Block {
    public final Supplier<Boolean> condition;

    public OptionalBlock(Properties properties, Supplier<Boolean> condition) {
        super(properties);
        this.condition = condition;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
        if (condition.get())
            super.fillItemCategory(group, stacks);
    }
}
