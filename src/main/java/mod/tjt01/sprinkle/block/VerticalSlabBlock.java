package mod.tjt01.sprinkle.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class VerticalSlabBlock extends Block implements IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);

    public VerticalSlabBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(TYPE, VerticalSlabType.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(IWorld world, BlockPos pos, BlockState blockState, FluidState fluidState) {
        return blockState.getValue(TYPE) == VerticalSlabType.DOUBLE && IWaterLoggable.super.placeLiquid(world, pos, blockState, fluidState);
    }

    @Override
    public boolean canPlaceLiquid(IBlockReader reader, BlockPos pos, BlockState state, Fluid fluid) {
        if (state.getValue(TYPE) == VerticalSlabType.DOUBLE)
            return false;
        return IWaterLoggable.super.canPlaceLiquid(reader, pos, state, fluid);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return state.getValue(TYPE).shape;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext context) {
        ItemStack stack = context.getItemInHand();
        VerticalSlabType type = state.getValue(TYPE);
        return type != VerticalSlabType.DOUBLE && stack.getItem() == this.asItem() &&
                (context.replacingClickedOnBlock() && (context.getClickedFace() == type.direction && getDirectionForPlacement(context) == type.direction)
                || (!context.replacingClickedOnBlock() && context.getClickedFace().getAxis() != type.direction.getAxis()));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockPos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(blockPos);
        if (state.getBlock() == this)
            return state.setValue(TYPE, VerticalSlabType.DOUBLE).setValue(WATERLOGGED, false);

        FluidState fluidState = context.getLevel().getFluidState(blockPos);
        BlockState returnState = this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        Direction direction = this.getDirectionForPlacement(context);
        VerticalSlabType type = VerticalSlabType.fromDirection(direction);
        return returnState.setValue(TYPE, type);
    }

    private Direction getDirectionForPlacement(BlockItemUseContext context) {
        Direction direction = context.getClickedFace();
        if (direction.getAxis() != Direction.Axis.Y)
            return direction;

        BlockPos pos = context.getClickedPos();
        Vector3d vec = context.getClickLocation().subtract(new Vector3d(pos.getX(), pos.getY(), pos.getZ())).subtract(0.5, 0, 0.5);
        double angle = Math.atan2(vec.x, vec.z) * -180 / Math.PI;
        return Direction.fromYRot(angle).getOpposite();
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction direction, BlockState facing, IWorld world, BlockPos pos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED))
            world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        return super.updateShape(state, direction, facing, world, pos, facingPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.getValue(TYPE) == VerticalSlabType.DOUBLE ? state : state.setValue(TYPE, VerticalSlabType.fromDirection(mirror.mirror(state.getValue(TYPE).direction)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.getValue(TYPE) == VerticalSlabType.DOUBLE ? state : state.setValue(TYPE, VerticalSlabType.fromDirection(rotation.rotate(state.getValue(TYPE).direction)));
    }

    public enum VerticalSlabType implements IStringSerializable {
        NORTH(Direction.NORTH),
        SOUTH(Direction.SOUTH),
        EAST(Direction.EAST),
        WEST(Direction.WEST),
        DOUBLE(null);

        private final String name;
        @Nullable
        public final Direction direction;
        public final VoxelShape shape;

        VerticalSlabType(@Nullable Direction direction) {
            this.name = direction == null ? "double" : direction.getName();
            this.direction = direction;

            if (direction == null)
                shape = VoxelShapes.block();
            else {
                double min = 0;
                double max = 8;
                if (direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {
                    min = 8;
                    max = 16;
                }

                if (direction.getAxis() == Direction.Axis.X)
                    shape = Block.box(min, 0, 0, max, 16, 16);
                else
                    shape = Block.box(0, 0, min, 16, 16, max);
            }


        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Nullable
        public static VerticalSlabType fromDirection(Direction direction) {
            for (VerticalSlabType type : VerticalSlabType.values())
                if (type.direction != null && direction == type.direction)
                    return type;

            return null;
        }
    }
}
