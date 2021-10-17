package mod.tjt01.sprinkle.network;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.item.BundleItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

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
            Main.LOGGER.debug("lets a go");
            Container container = player.containerMenu;
            Slot slotObject = container.getSlot(this.slot);
            ItemStack carried = player.inventory.getCarried();

            if (carried.getItem() instanceof BundleItem) {
                BundleItem bundleItem = (BundleItem) carried.getItem();
                if (slotObject.hasItem() && slotObject.mayPickup(player)) {
                    Main.LOGGER.debug("Pull");

                    ItemStack existing = slotObject.getItem();
                    ItemStack remainder = bundleItem.addItem(carried, existing.copy());
                    if (!ItemStack.matches(existing, remainder)) {
                        slotObject.set(remainder);
                        player.ignoreSlotUpdateHack = false;
                        player.broadcastCarriedItem();
                    }
                } else if(bundleItem.getFullness(carried) > 0 && !slotObject.hasItem() && slotObject.mayPlace(bundleItem.getContents(carried).get(0))) {
                    Main.LOGGER.debug("Push");
                    slotObject.set(bundleItem.removeItem(carried));
                    player.ignoreSlotUpdateHack = false;
                    player.broadcastCarriedItem();
                }
            } else if (slotObject.mayPickup(player) && slotObject.getItem().getItem() instanceof BundleItem) {
                BundleItem bundleItem = (BundleItem) slotObject.getItem().getItem();
                if (carried.isEmpty()) {
                    ItemStack bundle = slotObject.getItem().copy();
                    player.inventory.setCarried(bundleItem.removeItem(bundle));
                    slotObject.set(bundle);
                    player.ignoreSlotUpdateHack = false;
                    player.broadcastCarriedItem();
                } else if (bundleItem.getVolumeOfOne(carried) + bundleItem.getFullness(slotObject.getItem()) < BundleItem.MAX_FULLNESS) {
                    ItemStack bundle = slotObject.getItem().copy();
                    ItemStack remainder = bundleItem.addItem(bundle, carried);
                    player.inventory.setCarried(remainder);
                    slotObject.set(bundle);
                    player.ignoreSlotUpdateHack = false;
                    player.broadcastCarriedItem();
                }
            };
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
