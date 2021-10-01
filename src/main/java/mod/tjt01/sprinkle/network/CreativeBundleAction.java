package mod.tjt01.sprinkle.network;

import mod.tjt01.sprinkle.item.BundleItem;
import net.minecraft.entity.player.ServerPlayerEntity;
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
            if (this.stack.getItem() instanceof BundleItem) {
                stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
                    ItemStack target = player.inventory.getItem(slot);
                    if (target.isEmpty()) {
                        player.inventory.setItem(slot, itemHandler.extractItem(0, itemHandler.getStackInSlot(0).getCount(), false));
                        SprinklePacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SetCarriedItem(stack));
                    } else {
                        ItemStack existing = player.inventory.getItem(slot);
                        int count = existing.getCount() - itemHandler.insertItem(0, existing, true).getCount();
                        if (count > 0) {
                            itemHandler.insertItem(0, player.inventory.removeItem(slot, count), false);
                            SprinklePacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SetCarriedItem(stack));
                        }
                    }
                });
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
