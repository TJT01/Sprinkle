package mod.tjt01.sprinkle.block;

import mod.tjt01.sprinkle.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;
import java.util.Map;

public class MilkCauldronBlock extends AbstractCauldronBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final ResourceLocation CHEESE_LOOT_ID = Main.location("gameplay/cheese");

    public static final Map<Item, CauldronInteraction> MILK_INTERACTION = CauldronInteraction.newInteractionMap();

    public MilkCauldronBlock(Properties properties) {
        super(properties, MILK_INTERACTION);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(AGE) < 3 && random.nextInt(2) <= 0) {
            level.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), UPDATE_ALL);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(AGE) == 3) {
            if (!level.isClientSide) {
                List<ItemStack> items = level.getServer().getLootData().getLootTable(CHEESE_LOOT_ID).getRandomItems(
                        new LootParams.Builder((ServerLevel) level)
                                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                                .withParameter(LootContextParams.BLOCK_STATE, state)
                                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                                .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                                .create(LootContextParamSets.BLOCK)
                );

                for (ItemStack itemStack: items) {
                    ItemHandlerHelper.giveItemToPlayer(player, itemStack, player.getInventory().selected);
                }

                level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), UPDATE_ALL);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected double getContentHeight(BlockState pState) {
        return 0.9375D;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }
}
