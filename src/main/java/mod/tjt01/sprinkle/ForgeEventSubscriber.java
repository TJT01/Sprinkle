package mod.tjt01.sprinkle;

import com.google.common.collect.ImmutableList;
import mod.tjt01.sprinkle.capability.JukeboxCapabilityProvider;
import mod.tjt01.sprinkle.config.SprinkleConfig;
import mod.tjt01.sprinkle.block.ModBlocks;
import mod.tjt01.sprinkle.data.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ForgeEventSubscriber {
    private static boolean handlingDoorClick = false;

    @SubscribeEvent
    public static void onMissingItem(RegistryEvent.MissingMappings<Item> event) {
        ImmutableList<RegistryEvent.MissingMappings.Mapping<Item>> mappings = event.getMappings(Main.MODID);
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping: mappings) {
            if (mapping.key.getNamespace().equals("sprinkle")) {
                switch (mapping.key.getPath()) {
                    case "bundle" -> mapping.remap(Items.BUNDLE);
                    case "unnamed_stone" -> mapping.remap(ModBlocks.NIGHTSHALE.get().asItem());
                    case "unnamed_block" -> mapping.remap(ModBlocks.NIGHTSHALE_BRICKS.get().asItem());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMissingBlock(RegistryEvent.MissingMappings<Block> event) {
        ImmutableList<RegistryEvent.MissingMappings.Mapping<Block>> mappings = event.getMappings(Main.MODID);
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping: mappings) {
            if (mapping.key.getNamespace().equals("sprinkle")) {
                switch (mapping.key.getPath()) {
                    case "unnamed_stone" -> mapping.remap(ModBlocks.NIGHTSHALE.get());
                    case "unnamed_block" -> mapping.remap(ModBlocks.NIGHTSHALE_BRICKS.get());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (
                handlingDoorClick || event.isCanceled() || event.getResult() == Event.Result.DENY ||
                        event.getUseBlock() == Event.Result.DENY || event.getEntity().isDiscrete()
        ) return;

        LivingEntity livingEntity = event.getEntityLiving();
        Level level = event.getWorld();
        BlockState clicked = level.getBlockState(event.getPos());

        if (
                clicked.is(ModTags.Blocks.DOUBLE_DOOR_BLACKLIST) ||
                        !(clicked.getBlock() instanceof DoorBlock) ||
                        clicked.getMaterial().equals(Material.METAL)
        ) return;

        DoorHingeSide side = clicked.getValue(DoorBlock.HINGE);
        Direction facing = clicked.getValue(DoorBlock.FACING);
        BlockPos other = switch (side) {
            case LEFT -> event.getPos().relative(facing.getClockWise());
            case RIGHT -> event.getPos().relative(facing.getCounterClockWise());
        };
        BlockState otherState = level.getBlockState(other);

        if (
                otherState.is(ModTags.Blocks.DOUBLE_DOOR_BLACKLIST) ||
                        !(otherState.getBlock() instanceof DoorBlock) ||
                        otherState.getMaterial().equals(Material.METAL) ||
                        otherState.getValue(DoorBlock.HINGE).equals(side) ||
                        otherState.getValue(DoorBlock.OPEN) != clicked.getValue(DoorBlock.OPEN)
        ) return;

        BlockHitResult hitResult = new BlockHitResult(
                new Vec3(other.getX() + 0.5, other.getY() + 0.5, other.getZ() + 0.5),
                facing, other, false
        );

        handlingDoorClick = true;
        boolean canceled = MinecraftForge.EVENT_BUS.post(
                new PlayerInteractEvent.RightClickBlock(event.getPlayer(), InteractionHand.MAIN_HAND, other, hitResult)
        );
        handlingDoorClick = false;
        if (!canceled)
            otherState.use(level, event.getPlayer(), InteractionHand.MAIN_HAND, hitResult);
    }

    @SubscribeEvent
    public static void onAttachBlockEntityCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
        if (!SprinkleConfig.jukeboxCapabilityEnabled)
            return;
        if (event.getObject() instanceof JukeboxBlockEntity jukebox)
            event.addCapability(new ResourceLocation(Main.MODID, "item_handler"), new JukeboxCapabilityProvider(jukebox));
    }
}
