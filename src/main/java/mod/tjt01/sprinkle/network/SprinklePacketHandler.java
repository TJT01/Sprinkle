package mod.tjt01.sprinkle.network;

import mod.tjt01.sprinkle.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class SprinklePacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Main.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(id ++, BundleAction.class, BundleAction::encode, BundleAction::decode, BundleAction::handle);
        INSTANCE.registerMessage(id ++, CreativeBundleAction.class, CreativeBundleAction::encode, CreativeBundleAction::decode, CreativeBundleAction::handle);
        INSTANCE.registerMessage(id ++, SetCarriedItem.class, SetCarriedItem::encode, SetCarriedItem::decode, SetCarriedItem::handle);
    }
}
