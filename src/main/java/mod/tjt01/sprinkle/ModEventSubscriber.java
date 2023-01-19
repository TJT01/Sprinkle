package mod.tjt01.sprinkle;

import mod.tjt01.sprinkle.config.SprinkleConfig;
import mod.tjt01.sprinkle.data.FlagCondition;
import mod.tjt01.sprinkle.data.QuarkFlagCondition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.tjt01.sprinkle.block.ModBlocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

    public static final Logger LOGGER = LogManager.getLogger(Main.MODID + " Loading");

    @SubscribeEvent
    public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        CraftingHelper.register(FlagCondition.Serializer.INSTANCE);
        CraftingHelper.register(QuarkFlagCondition.Serializer.INSTANCE);
    }

    @SubscribeEvent
    public static void onModConfigEvent(ModConfigEvent event) {
        ModConfig modConfig = event.getConfig();
        if (modConfig.getSpec() == SprinkleConfig.COMMON_SPEC)
            SprinkleConfig.bakeCommon(modConfig);
    }

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        //Render layers
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_CHAIN.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_SOUL_LANTERN.get(), RenderType.cutout());
        //Item Property Overrides
        /*
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.BUNDLE.get(), new ResourceLocation(Main.MODID, "filled"), (stack, level, entity) -> {
                BundleItem bundleItem = (BundleItem) stack.getItem();
                return bundleItem.getFullness(stack)/(float)BundleItem.MAX_FULLNESS;
            });
        });
         */
    }

}
