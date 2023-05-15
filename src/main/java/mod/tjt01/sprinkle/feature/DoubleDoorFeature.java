package mod.tjt01.sprinkle.feature;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.data.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class DoubleDoorFeature {
    private static boolean handlingDoorClick = false;

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
}
