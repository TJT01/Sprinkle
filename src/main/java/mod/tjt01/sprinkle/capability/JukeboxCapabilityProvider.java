package mod.tjt01.sprinkle.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JukeboxCapabilityProvider implements ICapabilityProvider {
    private final JukeboxItemHandler itemHandler;
    private final LazyOptional<?> itemHandlerLazy;

    public JukeboxCapabilityProvider(JukeboxBlockEntity jukebox) {
        this.itemHandler = new JukeboxItemHandler(jukebox);
        this.itemHandlerLazy = LazyOptional.of(() -> this.itemHandler);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (LazyOptional<T>) itemHandlerLazy;
        return LazyOptional.empty();
    }

    private static class JukeboxItemHandler implements IItemHandler {
        private final JukeboxBlockEntity jukebox;

        private JukeboxItemHandler(JukeboxBlockEntity jukebox) {
            this.jukebox = jukebox;
        }

        @Override
        public int getSlots() {
            return 1;
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return jukebox.getRecord();
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            BlockState state = jukebox.getBlockState();
            BlockPos pos = jukebox.getBlockPos();
            LevelAccessor level = jukebox.getLevel();

            if (!(state.getBlock() instanceof JukeboxBlock jukeboxBlock))
                return stack;
            if (!(isItemValid(slot, stack)))
                return stack;
            if (!jukebox.getRecord().isEmpty())
                return stack;

            ItemStack remainder = ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - 1);
            if (!simulate) {
                ItemStack input = ItemHandlerHelper.copyStackWithSize(stack, 1);
                jukeboxBlock.setRecord(level, pos, state, input);
                level.levelEvent((Player)null, 1010, pos, Item.getId(input.getItem()));
            }
            return remainder;
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            BlockState state = jukebox.getBlockState();
            BlockPos pos = jukebox.getBlockPos();
            LevelAccessor level = jukebox.getLevel();
            ItemStack existing = jukebox.getRecord();

            if (!(state.getBlock() instanceof JukeboxBlock jukeboxBlock))
                return ItemStack.EMPTY;
            if (existing.isEmpty())
                return ItemStack.EMPTY;
            if (!simulate) {
                jukeboxBlock.setRecord(level, pos, state, ItemStack.EMPTY);
                level.levelEvent(1010, pos, 0);
            }

            return existing.copy();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.getItem() instanceof RecordItem;
        }
    }
}
