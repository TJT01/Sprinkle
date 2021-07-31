package mod.tjt01.sprinkle.block;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.init.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Random;

public class DetectorBlock extends DirectionalBlock {
    public static BooleanProperty POWERED = BlockStateProperties.POWERED;

    public DetectorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    private void updatePowered(IWorld world, BlockPos pos) {
        if (!world.isClientSide())
            world.getBlockTicks().scheduleTick(pos, this, Constants.BlockFlags.BLOCK_UPDATE);
    }

    protected boolean shouldBePowered(IBlockReader world, BlockPos pos, Direction direction) {
        return world.getBlockState(pos.relative(direction)).isFaceSturdy(world, pos.relative(direction), direction.getOpposite());
    }

    protected void updateNeighborsInFront(World world, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos backPos = pos.relative(direction.getOpposite());
        world.neighborChanged(backPos, this, pos);
        world.updateNeighborsAtExceptFromFacing(backPos, this, direction);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.getValue(POWERED) != shouldBePowered(world, pos, state.getValue(FACING))) {
            world.setBlock(pos, state.setValue(POWERED, shouldBePowered(world, pos, state.getValue(FACING))), Constants.BlockFlags.BLOCK_UPDATE);
            this.updateNeighborsInFront(world, pos, state);
            SoundEvent soundEvent = shouldBePowered(world, pos, state.getValue(FACING)) ? ModSoundEvents.DETECTOR_CLICK_ON.get() : ModSoundEvents.DETECTOR_CLICK_OFF.get();
            world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1, 1);
        }
    }

    @Override
    @SuppressWarnings("Deprecated")
    public BlockState updateShape(BlockState state, Direction from, BlockState otherState, IWorld world, BlockPos pos, BlockPos otherPos) {
        if (state.getValue(FACING) == from) {
            this.updatePowered(world, pos);
        }
        return super.updateShape(state, from, otherState, world, pos, otherPos);
    }

    @Override
    @SuppressWarnings("Deprecated")
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("Deprecated")
    public int getSignal(BlockState state, IBlockReader world, BlockPos pos, Direction direction) {
        return state.getValue(FACING) == direction && state.getValue(POWERED) ? 15 : 0;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection());
    }

    @Override
    @SuppressWarnings("Deprecated")
    public int getDirectSignal(BlockState state, IBlockReader world, BlockPos pos, Direction direction) {
        return this.getSignal(state, world, pos, direction);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
        return side != null && side == state.getValue(FACING);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState old, boolean moving) {
        if (!world.isClientSide) {
            final boolean isPowered = this.shouldBePowered(world, pos, state.getValue(FACING));
            if (isPowered) {
                BlockState newState = state.setValue(POWERED, shouldBePowered(world, pos, state.getValue(FACING)));
                world.setBlock(pos, newState, Constants.BlockFlags.BLOCK_UPDATE);
                updateNeighborsInFront(world, pos, state);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState other, boolean moving) {
        if (!state.is(other.getBlock())) {
            if (!world.isClientSide() && state.getValue(POWERED)) {
                updateNeighborsInFront(world, pos, state.setValue(POWERED, false));
            }
        }
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }
}
