package mod.tjt01.sprinkle.network;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.item.BundleItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.function.Supplier;

public class CreativeBundleAction {
    public int slot;
    public ItemStack stack;

    public CreativeBundleAction(int slot, ItemStack stack) {
        this.slot = slot;
        this.stack = stack;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(slot);
        buffer.writeItem(stack);
    }

    public static CreativeBundleAction decode(PacketBuffer buffer) {
        return new CreativeBundleAction(buffer.readInt(), buffer.readItem());
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayerEntity player = contextSupplier.get().getSender();
            if (player == null || !player.isCreative())
                return;
            ItemStack target = player.inventory.getItem(slot);
            Slot slotObject = player.containerMenu.getSlot(slot);

            if (this.stack.getItem() instanceof BundleItem) {
                BundleItem bundleItem = (BundleItem) this.stack.getItem();
                if (!target.isEmpty() && slotObject.mayPickup(player)) {
                    Main.LOGGER.debug("Pull");

                    ItemStack remainder = bundleItem.addItem(this.stack, target);
                    if (!ItemStack.matches(target, remainder)) {
                        player.inventory.setItem(slot, remainder);
                        SprinklePacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SetCarriedItem(this.stack));
                    }
                } else if(bundleItem.getFullness(this.stack) > 0 && target.isEmpty() && slotObject.mayPlace(bundleItem.getContents(this.stack).get(0))) {
                    Main.LOGGER.debug("Push");
                    player.inventory.setItem(slot, bundleItem.removeItem(this.stack));
                    SprinklePacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SetCarriedItem(this.stack));
                }
            } else if (slotObject.mayPickup(player) && target.getItem() instanceof BundleItem) {
                BundleItem bundleItem = (BundleItem) target.getItem();
                if (this.stack.isEmpty()) {
                    ItemStack bundle = target.copy();
                    player.inventory.setItem(slot, bundle);
                    SprinklePacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SetCarriedItem(bundleItem.removeItem(bundle)));
                } else if (bundleItem.getVolumeOfOne(this.stack) + bundleItem.getFullness(slotObject.getItem()) < BundleItem.MAX_FULLNESS) {
                    ItemStack bundle = target.copy();
                    ItemStack remainder = bundleItem.addItem(bundle, this.stack);
                    player.inventory.setItem(slot, bundle);
                    SprinklePacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SetCarriedItem(remainder));
                }
            };
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
