package mod.tjt01.sprinkle.feature;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.block.MilkCauldronBlock;
import mod.tjt01.sprinkle.block.ModBlocks;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CheeseFeature {
    @SubscribeEvent
    public static void onCommonInit(FMLCommonSetupEvent event) {
        event.enqueueWork(
                () -> {
                    CauldronInteraction.addDefaultInteractions(MilkCauldronBlock.MILK_INTERACTION);
                    MilkCauldronBlock.MILK_INTERACTION.put(
                            Items.BUCKET,
                            (blockState, level, blockPos, player, hand, stack) -> CauldronInteraction.fillBucket(
                                    blockState,
                                    level,
                                    blockPos,
                                    player,
                                    hand,
                                    stack,
                                    new ItemStack(Items.MILK_BUCKET),
                                    state -> true,
                                    SoundEvents.BUCKET_FILL
                            )
                    );

                    CauldronInteraction fillMilk = (pBlockState, pLevel, pBlockPos, pPlayer, pHand, pStack) -> CauldronInteraction.emptyBucket(
                            pLevel, pBlockPos,
                            pPlayer, pHand,
                            pStack, ModBlocks.MILK_CAULDRON.get().defaultBlockState(), SoundEvents.BUCKET_EMPTY
                    );

                    CauldronInteraction.EMPTY.put(
                            Items.MILK_BUCKET, fillMilk
                    );
                    CauldronInteraction.WATER.put(
                            Items.MILK_BUCKET, fillMilk
                    );
                    CauldronInteraction.LAVA.put(
                            Items.MILK_BUCKET, fillMilk
                    );
                    CauldronInteraction.POWDER_SNOW.put(
                            Items.MILK_BUCKET, fillMilk
                    );
                    MilkCauldronBlock.MILK_INTERACTION.put(
                            Items.MILK_BUCKET, fillMilk
                    );
                }
        );
    }
}
