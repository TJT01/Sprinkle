package mod.tjt01.sprinkle.item;

import mod.tjt01.sprinkle.Main;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public class BundleItem extends OptionalItem{
    public static final String TAG_ITEMS = "Items";

    public BundleItem(Properties properties, Supplier<Boolean> condition) {
        super(properties, condition);
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        super.readShareTag(stack, nbt);

        if (nbt != null) {
            IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new RuntimeException("LazyOptional must not be empty!"));
            if (handler instanceof BundleItemHandler) {
                BundleItemHandler bundleItemHandler = (BundleItemHandler) handler;
                ((BundleItemHandler) handler).deserializeNBT(nbt.getList(TAG_ITEMS, Constants.NBT.TAG_COMPOUND));
            } else
                throw new RuntimeException("Bundle item had an item capability, but it wasn't a BundleItemHandler");
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (this.getClass() == BundleItem.class)
            return new BundleCapabilityProvider(stack);
        else
            return super.initCapabilities(stack, nbt);
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        boolean itemDropped = false;
        ItemStack item = player.getItemInHand(hand);
        if (item.getItem() == this) {
            IItemHandler handler = item.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new RuntimeException("LazyOptional must not be empty!"));
            if (handler instanceof BundleItemHandler) {
                BundleItemHandler bundleItemHandler = (BundleItemHandler) handler;
                while (!bundleItemHandler.getStackInSlot(0).isEmpty()) {
                    ItemStack stack = bundleItemHandler.extractItem(0, bundleItemHandler.getStackInSlot(0).getCount(), false);
                    ItemEntity entity = player.drop(stack, false, true);
                    if (entity != null)
                        level.addFreshEntity(entity);
                    itemDropped = true;
                }
            }
        }
        if (itemDropped)
            return ActionResult.success(player.getItemInHand(hand));
        else
            return super.use(level, player, hand);
    }

    public static class BundleItemHandler implements IItemHandler, INBTSerializable<ListNBT> {
        protected NonNullList<ItemStack> stacks = NonNullList.create();
        protected ItemStack owner;

        public BundleItemHandler(ItemStack stack) {
            this.owner = stack;
            CompoundNBT nbt = stack.getOrCreateTag();
            if (nbt.contains(TAG_ITEMS)) {
                this.deserializeNBT(nbt.getList(TAG_ITEMS, Constants.NBT.TAG_COMPOUND));
            }
        }

        @Override
        public ListNBT serializeNBT() {
            ListNBT nbt = new ListNBT();
            for (ItemStack stack : stacks) {
                nbt.add(stack.save(new CompoundNBT()));
            }
            return nbt;
        }

        @Override
        public void deserializeNBT(ListNBT nbt) {
            this.stacks.clear();
            for (int i = 0; i < nbt.size(); i++) {
                this.stacks.add(ItemStack.of(nbt.getCompound(i)));
            }
        }

        @Override
        public int getSlots() {
            return 64;
        }

        @Nonnull
        @Override
        public ItemStack getStackInSlot(int slot) {
            validateSlotIndex(slot);
            if (slot >= stacks.size())
                return ItemStack.EMPTY;
            return stacks.get(slot);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            validateSlotIndex(slot);

            slot = Math.min(slot, stacks.size());

            if (stack.isEmpty())
                return ItemStack.EMPTY;

            if (!isItemValid(slot, stack))
                return stack;

            if (getVolumeOfOne(stack) + getTotalVolume() > 64)
                return stack;

            int limit = Math.min(stack.getCount(), (64 - getTotalVolume()) / getVolumeOfOne(stack));
            boolean reachedLimit = stack.getCount() > limit;

            if (!simulate) {
                if (!ItemHandlerHelper.canItemStacksStack(stack, getStackInSlot(slot))) {
                    for (int i = slot + 1; i < stacks.size(); i++) {
                        if (ItemHandlerHelper.canItemStacksStack(stack, getStackInSlot(i))) {
                            stacks.add(slot, stacks.remove(i));
                        }
                    }
                }
                if (ItemHandlerHelper.canItemStacksStack(stack, getStackInSlot(slot))) {
                    stacks.get(slot).grow(reachedLimit ? limit : stack.getCount());
                } else {
                    stacks.add(Math.min(slot, stacks.size()), reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
                }
                onChanged();
            }

            return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (amount == 0)
                return ItemStack.EMPTY;

            validateSlotIndex(slot);

            ItemStack existing = this.stacks.get(slot);

            if (existing.isEmpty())
                return ItemStack.EMPTY;

            int toExtract = Math.min(amount, existing.getCount());

            if (toExtract < existing.getCount()) {
                if (!simulate) {
                    existing.shrink(toExtract);
                    onChanged();
                }
                return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
            } else {
                if (!simulate) {
                    ItemStack stack = this.stacks.remove(slot);
                    onChanged();
                    return stack;
                } else {
                    return existing.copy();
                }
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (stack.getItem() instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) stack.getItem();
                return !(blockItem.getBlock() instanceof ShulkerBoxBlock);
            }
            return true;
        }

        protected void onChanged() {
            owner.getOrCreateTag().put(TAG_ITEMS, this.serializeNBT());
        }

        public int getVolumeOfOne(@Nonnull ItemStack stack) {
            return 64/stack.getMaxStackSize();
        }

        public int getVolumeOfStack(@Nonnull ItemStack stack) {
            return getVolumeOfOne(stack) * stack.getCount();
        }

        public int getTotalVolume() {
            int total = 0;
            for (ItemStack stack : stacks) {
                total += getVolumeOfStack(stack);
            }
            return total;
        }

        protected void validateSlotIndex(int slot)
        {
            if (slot < 0 || slot >= 64)
                throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
        }
    }

    public static class BundleCapabilityProvider implements ICapabilityProvider {

        protected final BundleItemHandler itemHandler;

        protected final LazyOptional<IItemHandler> lazyOptional;

        public BundleCapabilityProvider(ItemStack stack) {
            this.itemHandler = new BundleItemHandler(stack);
            this.lazyOptional = LazyOptional.of(() -> itemHandler);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return this.lazyOptional.cast();
            } else {
                return LazyOptional.empty();
            }
        }
    }
}