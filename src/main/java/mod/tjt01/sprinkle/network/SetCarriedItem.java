package mod.tjt01.sprinkle.network;

import mod.tjt01.sprinkle.network.client.ClientSetCarriedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SetCarriedItem {
    public final ItemStack stack;

    public SetCarriedItem(ItemStack stack) {
        this.stack = stack;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeItem(stack);
    }

    public static SetCarriedItem decode(PacketBuffer buffer) {
        return new SetCarriedItem(buffer.readItem());
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientSetCarriedItem.handle(this, contextSupplier));
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
