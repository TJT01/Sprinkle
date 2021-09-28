/*
package mod.tjt01.sprinkle.item;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BundleItem extends OptionalItem{
    public BundleItem(Properties properties, Supplier<Boolean> condition) {
        super(properties, condition);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new BundleCapabilityProvider(stack, nbt);
    }

    public static class BundleItemHandler extends ItemStackHandler {

        public BundleItemHandler() {
            super(64);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (stack.getItem() instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) stack.getItem();
                return blockItem.getBlock() instanceof ShulkerBoxBlock;
            }
            return super.isItemValid(slot, stack);
        }

        @Override
        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return Math.min(super.getStackLimit(slot, stack), (64 - getUsedVolume()) / getBundleWeight(stack));
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack ret = super.extractItem(slot, amount, simulate);
            this.compressItemStacks();
            return ret;
        }

        @Nonnull
        protected ItemStack rawInsertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return super.insertItem(slot, stack, simulate);
        }

        @Nonnull
        protected ItemStack rawExtractItem(int slot, int amount, boolean simulate) {
            return super.extractItem(slot, amount, simulate);
        }

        @Override
        public CompoundNBT serializeNBT() {
            CompoundNBT nbt = new CompoundNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
        }

        public ItemStack popItemStack() {
            if rawInsertItem()
            for (int slot = 62; slot > 0; slot--) {

            }
        }

        public int getBundleWeight(ItemStack stack) {
            return (int) Math.ceil(stack.getMaxStackSize() / 64.0);
        }

        public int getUsedVolume() {
            int total = 0;
            for (int slot = 0; slot < 64; slot++) {
                total += getBundleWeight(getStackInSlot(slot));
            }
            return total;
        }

        protected void compressItemStacks() {
            for (int slot1 = 0; slot1 < 64-1; slot1++) {
                for (int slot2 = slot1+1; slot2 < 64; slot2++) {
                    ItemStack item1 = getStackInSlot(slot1);
                    ItemStack item2 = getStackInSlot(slot2);
                    if (ItemHandlerHelper.canItemStacksStack(item1, item2)) {
                        this.setStackInSlot(slot2, this.rawInsertItem(slot1, this.rawExtractItem(slot2, this.getStackInSlot(slot2).getCount(), false), false));
                    } else if (item1.isEmpty()) {
                        this.rawInsertItem(slot1, this.rawExtractItem(slot2, this.getStackInSlot(slot2).getCount(), false), false);
                    }
                }
            }
        }
    }

    public class BundleCapabilityProvider implements ICapabilityProvider {

        protected final BundleItemHandler itemHandler = new BundleItemHandler();

        public BundleCapabilityProvider(ItemStack stack, @Nullable CompoundNBT nbt) {

        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {

            } else {
                return null;
            }
        }
    }
}
 */