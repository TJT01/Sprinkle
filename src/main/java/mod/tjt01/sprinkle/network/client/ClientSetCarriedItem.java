package mod.tjt01.sprinkle.network.client;

import mod.tjt01.sprinkle.network.SetCarriedItem;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientSetCarriedItem {
    public static void handle(SetCarriedItem message, Supplier<NetworkEvent.Context> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.player.inventory.setCarried(message.stack);
    }
}
