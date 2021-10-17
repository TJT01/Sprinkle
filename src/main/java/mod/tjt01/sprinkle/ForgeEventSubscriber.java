package mod.tjt01.sprinkle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.tjt01.sprinkle.item.BundleItem;
import mod.tjt01.sprinkle.network.BundleAction;
import mod.tjt01.sprinkle.network.CreativeBundleAction;
import mod.tjt01.sprinkle.network.SprinklePacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ForgeEventSubscriber {
    private static final ResourceLocation TEX_BUNDLE_GUI = new ResourceLocation(Main.MODID, "textures/gui/container/bundle.png");

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
                    Main.LOGGER.debug("poke");
                    SprinklePacketHandler.INSTANCE.sendToServer(containerScreen instanceof CreativeScreen ?
                            new CreativeBundleAction(hoverSlot.getSlotIndex(), heldStack) :
                            new BundleAction(hoverSlot.index));
                    containerScreen.isQuickCrafting = false;
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRightClickStart(GuiScreenEvent.MouseClickedEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        Screen gui = minecraft.screen;
        if (gui instanceof ContainerScreen && event.getButton() == 1) {
            ContainerScreen<?> containerScreen = (ContainerScreen<?>) gui;
            Slot hoverSlot = containerScreen.getSlotUnderMouse();
            assert minecraft.player != null;
            ItemStack heldStack = minecraft.player.inventory.getCarried();

            if (hoverSlot != null && heldStack.isEmpty()) {
                if (hoverSlot.getItem().getItem() instanceof BundleItem) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onTooltipRender(RenderTooltipEvent.PostText event) {
        Minecraft minecraft = Minecraft.getInstance();
        MatrixStack matrixStack = event.getMatrixStack();
        ItemStack itemStack = event.getStack();
        int x = event.getX();
        int y = event.getY() + 12;
        if (itemStack.getItem() instanceof BundleItem) {
            minecraft.getTextureManager().bind(TEX_BUNDLE_GUI);
            BundleItem bundleItem = (BundleItem) itemStack.getItem();
            NonNullList<ItemStack> contents = bundleItem.getContents(itemStack);
            boolean isFull = bundleItem.getFullness(itemStack) >= BundleItem.MAX_FULLNESS;
            int columns = bundleItem.getColumns(itemStack);
            int rows = bundleItem.getRows(itemStack);
            for (int ofsY = 0; ofsY < rows; ofsY++) {
                AbstractGui.blit(matrixStack, x, y + 1 + ofsY*20, 0, 20, 1, 20, 128, 128);
                for (int ofsX = 0; ofsX < columns; ofsX++) {
                    AbstractGui.blit(matrixStack, x + 1 + ofsX*18, y + 1 + ofsY*20, 0, isFull && ofsX + (ofsY*columns) >= contents.size() ? 40 : 0, 18, 20, 128, 128);
                }
                AbstractGui.blit(matrixStack, x + 1 + columns*18, y + 1 + ofsY*20, 0, 20, 1, 20, 128, 128);
            }
            for (int ofsX = 0; ofsX < columns; ofsX++) {
                AbstractGui.blit(matrixStack, x + ofsX*18, y, 0, 20, 18, 1, 128, 128);
                AbstractGui.blit(matrixStack, x + ofsX*18, y + rows * 20, 0, 60, 18, 1, 128, 128);
            }
            AbstractGui.blit(matrixStack, x + columns*18, y, 0, 20, 2, 1, 128, 128);
            AbstractGui.blit(matrixStack, x + columns*18, y + rows * 20, 0, 60, 2, 1, 128, 128);
            final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            matrixStack.pushPose();
            matrixStack.translate(0, 0, 700);
            itemRenderer.blitOffset = 700;
            AbstractGui.fill(matrixStack, x + 2, y + 2, x + 16 + 2, y + 16 + 2, 0x80ffffff);

            RenderSystem.enableDepthTest();
            for (int i = 0; i < contents.size(); i++){
                ItemStack invStack = contents.get(i);
                int px = x + 2 + ((i%columns)*18);
                int py = y + 2+ ((i/columns)*20);

                if (!invStack.isEmpty()) {
                    itemRenderer.renderAndDecorateItem(invStack, px, py);
                    itemRenderer.renderGuiItemDecorations(minecraft.font, invStack, px, py);
                }
            }
            matrixStack.popPose();
        }
    }
}
