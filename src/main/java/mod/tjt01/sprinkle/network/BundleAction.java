package mod.tjt01.sprinkle.network;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.item.BundleItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.function.Supplier;

public class BundleAction {
    public int slot;

    public BundleAction(int slot) {
        this.slot = slot;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(slot);
    }

    public static BundleAction decode(PacketBuffer buffer) {
        return new BundleAction(buffer.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayerEntity player = contextSupplier.get().getSender();
            if (player == null)
                return;

            Container container = player.containerMenu;
            Slot slotObject = container.getSlot(this.slot);
            ItemStack carried = player.inventory.getCarried();
            IItemHandler itemHandler = carried.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new RuntimeException("LazyOptional must not be empty!"));

            if (carried.getItem() instanceof BundleItem) {
                if (!slotObject.getItem().isEmpty()) {
                    Main.LOGGER.debug("Pull");

                    ItemStack existing = slotObject.getItem();
                    int count = existing.getCount() - itemHandler.insertItem(0, existing, true).getCount();
                    if (count > 0) {
                        itemHandler.insertItem(0, slotObject.remove(count), false);
                        player.inventory.setCarried(carried);
                        player.ignoreSlotUpdateHack = false;
                        player.broadcastCarriedItem();
                    }
                } else {
                    Main.LOGGER.debug("Push");
                    if (slotObject.mayPlace(itemHandler.extractItem(0, itemHandler.getStackInSlot(0).getCount(), true))) {
                        slotObject.set(itemHandler.extractItem(0, itemHandler.getStackInSlot(0).getCount(), false));
                        player.inventory.setCarried(carried);
                        player.broadcastCarriedItem();
                    }
                }
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
