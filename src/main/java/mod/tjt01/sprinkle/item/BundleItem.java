package mod.tjt01.sprinkle.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BundleItem extends OptionalItem{
    public static final int MAX_FULLNESS = 64;
    public static final String TAG_ITEMS = "Items";

    public BundleItem(Properties properties, Supplier<Boolean> condition) {
        super(properties, condition);
    }

    protected boolean isItemValid(ItemStack stack) {
        return !(stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof ShulkerBoxBlock);
    }

    public int getColumns(ItemStack bundle) {
        return Math.max(2, (int) Math.ceil(Math.sqrt((double) getContents(bundle).size() + 1)));
    }

    public int getRows(ItemStack bundle) {
        return Math.max(1, (int) Math.ceil((getContents(bundle).size() + 1d)/getColumns(bundle)));
    }

    public NonNullList<ItemStack> getContents(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (nbt.contains(TAG_ITEMS, Constants.NBT.TAG_LIST)) {
            ListNBT listNBT = nbt.getList(TAG_ITEMS, Constants.NBT.TAG_COMPOUND);
            NonNullList<ItemStack> items = NonNullList.create();
            for (int i = 0; i < listNBT.size(); i++) {
                ItemStack invStack = ItemStack.of(listNBT.getCompound(i));
                items.add(items.size(), invStack);
            }
            return items;
        };
        return NonNullList.create();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return !this.getContents(stack).isEmpty();
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0 - ((double) this.getFullness(stack))/MAX_FULLNESS;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return 0x6666FF;
    }

    protected void setContents(ItemStack bundle, NonNullList<ItemStack> stacks) {
        CompoundNBT nbt = bundle.getOrCreateTag();
        ListNBT listNBT = new ListNBT();
        for (ItemStack stack : stacks) {
            listNBT.add(stack.save(new CompoundNBT()));
        }
        nbt.put(TAG_ITEMS, listNBT);
    }

    public ItemStack removeItem(ItemStack bundle) {
        NonNullList<ItemStack> contents = getContents(bundle);
        if (contents.isEmpty())
            return ItemStack.EMPTY;
        ItemStack ret = contents.remove(0);
        this.setContents(bundle, contents);
        return ret;
    }

    public int getVolumeOfOne(ItemStack stack) {
        if (stack.isEmpty())
            return 0;
        if (stack.getItem() instanceof BundleItem)
            return 4 + ((BundleItem) stack.getItem()).getFullness(stack);
        return MAX_FULLNESS/stack.getMaxStackSize();
    }

    public int getVolumeOfStack(ItemStack stack) {
        return getVolumeOfOne(stack)*stack.getCount();
    }

    public int getFullness(ItemStack bundle) {
        NonNullList<ItemStack> stacks = getContents(bundle);
        int fullness = 0;
        for (ItemStack stack : stacks) {
            fullness += getVolumeOfStack(stack);
        }
        return fullness;
    }

    public ItemStack addItem(ItemStack bundle, ItemStack toAdd) {
        int fullness = getFullness(bundle);
        if (toAdd.isEmpty())
            return ItemStack.EMPTY;
        if (!this.isItemValid(toAdd) || getVolumeOfOne(toAdd) + fullness > MAX_FULLNESS)
            return toAdd;
        NonNullList<ItemStack> stacks = getContents(bundle);
        int amount = Math.min(toAdd.getCount(), (MAX_FULLNESS-fullness)/getVolumeOfOne(toAdd));
        ItemStack stack = ItemHandlerHelper.copyStackWithSize(toAdd, amount);
        ItemStack remainder = toAdd.copy();
        remainder.shrink(amount);
        for (int i = 0; i < stacks.size();) {
            ItemStack other = stacks.get(i);
            if (ItemHandlerHelper.canItemStacksStack(stack, other)) {
                stacks.remove(i);
                stack.grow(other.getCount());
            } else {
                i ++;
            }
        }
        stacks.add(0, stack);
        setContents(bundle, stacks);
        return remainder;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World level, List<ITextComponent> textComponents, ITooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, textComponents, tooltipFlag);
        textComponents.add(new StringTextComponent(""));
        int rows = getRows(stack);
        for (int i = 0; i < rows; i++) {
            textComponents.add(new StringTextComponent(""));
            textComponents.add(new StringTextComponent(""));
        }
        textComponents.add(new StringTextComponent(this.getFullness(stack) + "/" + MAX_FULLNESS).setStyle(Style.EMPTY.withColor(TextFormatting.GRAY)));
    }

    //TODO: Implement the right click thing
    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        if (level.isClientSide)
            return super.use(level, player, hand);
        return super.use(level, player, hand);
    }
}