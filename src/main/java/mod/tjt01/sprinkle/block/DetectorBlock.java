package mod.tjt01.sprinkle.block;

import mod.tjt01.sprinkle.init.ModSoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public class DetectorBlock extends DirectionalBlock {
    public static BooleanProperty POWERED = BlockStateProperties.POWERED;

    public DetectorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    private void updatePowered(LevelAccessor world, BlockPos pos) {
        if (!world.isClientSide())
            world.scheduleTick(pos, this, Block.UPDATE_CLIENTS);
    }

    protected boolean shouldBePowered(BlockGetter world, BlockPos pos, Direction direction) {
        return world.getBlockState(pos.relative(direction)).isFaceSturdy(world, pos.relative(direction), direction.getOpposite());
    }

    protected void updateNeighborsInFront(Level world, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos backPos = pos.relative(direction.getOpposite());
        world.neighborChanged(backPos, this, pos);
        world.updateNeighborsAtExceptFromFacing(backPos, this, direction);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(POWERED) != shouldBePowered(world, pos, state.getValue(FACING))) {
            world.setBlock(pos, state.setValue(POWERED, shouldBePowered(world, pos, state.getValue(FACING))), Block.UPDATE_CLIENTS);
            this.updateNeighborsInFront(world, pos, state);
            SoundEvent soundEvent = shouldBePowered(world, pos, state.getValue(FACING)) ? ModSoundEvents.DETECTOR_CLICK_ON.get() : ModSoundEvents.DETECTOR_CLICK_OFF.get();
            world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1, 1);
        }
    }

    @Override
    @SuppressWarnings("Deprecated")
    public BlockState updateShape(BlockState state, Direction from, BlockState otherState, LevelAccessor world, BlockPos pos, BlockPos otherPos) {
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
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return state.getValue(FACING) == direction && state.getValue(POWERED) ? 15 : 0;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection());
    }

    @Override
    @SuppressWarnings("Deprecated")
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return this.getSignal(state, world, pos, direction);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, @Nullable Direction side) {
        return side != null && side == state.getValue(FACING);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState old, boolean moving) {
        if (!world.isClientSide) {
            final boolean isPowered = this.shouldBePowered(world, pos, state.getValue(FACING));
            if (isPowered) {
                BlockState newState = state.setValue(POWERED, shouldBePowered(world, pos, state.getValue(FACING)));
                world.setBlock(pos, newState, Block.UPDATE_CLIENTS);
                updateNeighborsInFront(world, pos, state);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState other, boolean moving) {
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
