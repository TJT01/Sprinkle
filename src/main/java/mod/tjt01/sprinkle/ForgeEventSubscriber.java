package mod.tjt01.sprinkle;

import mod.tjt01.sprinkle.item.BundleItem;
import mod.tjt01.sprinkle.network.BundleAction;
import mod.tjt01.sprinkle.network.CreativeBundleAction;
import mod.tjt01.sprinkle.network.SprinklePacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ForgeEventSubscriber {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRightClick(GuiScreenEvent.MouseReleasedEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        Screen gui = minecraft.screen;
        if (gui instanceof ContainerScreen && event.getButton() == 1) {
            ContainerScreen<?> containerScreen = (ContainerScreen<?>) gui;
            Slot hoverSlot = containerScreen.getSlotUnderMouse();
            assert minecraft.player != null;
            ItemStack heldStack = minecraft.player.inventory.getCarried();

            if (hoverSlot != null) {
                if (heldStack.getItem() instanceof BundleItem || hoverSlot.getItem().getItem() instanceof BundleItem) {
                    /*SprinklePacketHandler.INSTANCE.sendToServer(containerScreen instanceof CreativeScreen ?
                            new CreativeBundleAction(hoverSlot.getSlotIndex(), heldStack):
                            new BundleAction(hoverSlot.index));*/
                    SprinklePacketHandler.INSTANCE.sendToServer(new BundleAction(hoverSlot.index));
                    containerScreen.isQuickCrafting = false;
                    event.setCanceled(true);
                }
            }
        }
    }
}
