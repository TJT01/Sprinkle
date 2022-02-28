package mod.tjt01.sprinkle.block;

import mod.tjt01.sprinkle.data.QuarkFlagCondition;
import mod.tjt01.lapislib.block.OptionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nullable;

public class VerticalSlabBlock extends OptionalBlock implements SimpleWaterloggedBlock {

    public static final QuarkFlagCondition CONDITION = new QuarkFlagCondition("vertical_slabs");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);

    public VerticalSlabBlock(Properties properties) {
        super(properties, CONDITION::test);
        registerDefaultState(defaultBlockState().setValue(TYPE, VerticalSlabType.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState blockState, FluidState fluidState) {
        return blockState.getValue(TYPE) == VerticalSlabType.DOUBLE && SimpleWaterloggedBlock.super.placeLiquid(world, pos, blockState, fluidState);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter reader, BlockPos pos, BlockState state, Fluid fluid) {
        if (state.getValue(TYPE) == VerticalSlabType.DOUBLE)
            return false;
        return SimpleWaterloggedBlock.super.canPlaceLiquid(reader, pos, state, fluid);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return state.getValue(TYPE).shape;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        ItemStack stack = context.getItemInHand();
        VerticalSlabType type = state.getValue(TYPE);
        return type != VerticalSlabType.DOUBLE && stack.getItem() == this.asItem() &&
                (context.replacingClickedOnBlock() && (context.getClickedFace() == type.direction && getDirectionForPlacement(context) == type.direction)
                || (!context.replacingClickedOnBlock() && context.getClickedFace().getAxis() != type.direction.getAxis()));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
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

    private Direction getDirectionForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        if (direction.getAxis() != Direction.Axis.Y)
            return direction;

        BlockPos pos = context.getClickedPos();
        Vec3 vec = context.getClickLocation().subtract(new Vec3(pos.getX(), pos.getY(), pos.getZ())).subtract(0.5, 0, 0.5);
        double angle = Math.atan2(vec.x, vec.z) * -180 / Math.PI;
        return Direction.fromYRot(angle).getOpposite();
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction direction, BlockState facing, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED))
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
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
    @Override
    public String getDisabledTooltip() {
        return "sprinkle.ui.disabled.quark_missing";
    }

    public enum VerticalSlabType implements StringRepresentable {
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
                shape = Shapes.block();
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
