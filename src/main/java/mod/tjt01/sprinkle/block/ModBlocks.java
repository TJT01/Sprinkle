package mod.tjt01.sprinkle.block;

import java.util.function.Supplier;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

    public static final BlockBehaviour.Properties NIGHTSHALE_PROPERTIES =
            BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
                    .sound(SoundType.DEEPSLATE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .mapColor(MapColor.COLOR_BLUE);

    public static RegistryObject<Block> blockNoItem(String name, Supplier<Block> block) {
        return BLOCKS.register(name, block);
    }

    public static RegistryObject<Block> block(String name, Supplier<Block> block) {
        RegistryObject<Block> registryObject = blockNoItem(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(registryObject.get(), new Item.Properties()));
        return registryObject;
    }

    public static RegistryObject<Block> slabBlock(String name, RegistryObject<Block> blockRegistryObject) {
        return block(name, () -> new SlabBlock(Block.Properties.copy(blockRegistryObject.get())));
    }

    public static RegistryObject<Block> slabBlock(RegistryObject<Block> blockRegistryObject) {
        String name = blockRegistryObject.getId().getPath() + "_slab";
        return slabBlock(name, blockRegistryObject);
    }

    public static RegistryObject<Block> stairsBlock(String name, RegistryObject<Block> blockRegistryObject) {
        return block(name, () -> new StairBlock(() -> blockRegistryObject.get().defaultBlockState(), Block.Properties.copy(blockRegistryObject.get())));
    }

    public static RegistryObject<Block> stairsBlock(RegistryObject<Block> blockRegistryObject) {
        String name = blockRegistryObject.getId().getPath() + "_stairs";
        return stairsBlock(name, blockRegistryObject);
    }

    public static RegistryObject<Block> wallBlock(String name, RegistryObject<Block> blockRegistryObject) {
        return block(name, () -> new WallBlock(BlockBehaviour.Properties.copy(blockRegistryObject.get())));
    }

    public static RegistryObject<Block> wallBlock(RegistryObject<Block> blockRegistryObject) {
        String name = blockRegistryObject.getId().getPath() + "_wall";
        return wallBlock(name, blockRegistryObject);
    }

    public static RegistryObject<Block> verticalSlabBlock(String name, RegistryObject<Block> blockRegistryObject) {
        return block(name, () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(blockRegistryObject.get())));
    }

    ///BUILDING\\\
    //Gold Chain
    public static final RegistryObject<Block> GOLD_CHAIN = block("gold_chain", () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)));
    //Gold Lantern
    public static final RegistryObject<Block> GOLD_LANTERN = block("gold_lantern", () -> new LanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)));
    //Gold Soul Lantern
    public static final RegistryObject<Block> GOLD_SOUL_LANTERN = block("gold_soul_lantern", () -> new LanternBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_LANTERN)));

    //Purpur Bricks
    public static final RegistryObject<Block> PURPUR_BRICKS = block("purpur_bricks", () -> new Block(Block.Properties.copy(Blocks.PURPUR_BLOCK)));
    //Purpur Brick Slab
    public static final RegistryObject<Block> PURPUR_BRICK_SLAB = slabBlock("purpur_brick_slab", PURPUR_BRICKS);
    //Purpur Brick Vertical Slab
    public static final RegistryObject<Block> VERTICAL_PURPUR_BRICK_SLAB = verticalSlabBlock("purpur_brick_vertical_slab", PURPUR_BRICK_SLAB);
    //Purpur Brick Stairs
    public static final RegistryObject<Block> PURPUR_BRICK_STAIRS = stairsBlock("purpur_brick_stairs", PURPUR_BRICKS);
    //Purpur Brick Wall
    public static final RegistryObject<Block> PURPUR_BRICK_WALL = wallBlock("purpur_brick_wall", PURPUR_BRICKS);

    //Nightshale
    public static final RegistryObject<Block> NIGHTSHALE = block("nightshale", () -> new Block(NIGHTSHALE_PROPERTIES));
    //Nightshale Slab
    public static final RegistryObject<Block> NIGHTSHALE_SLAB = slabBlock(NIGHTSHALE);
    //Nightshale Vertical Slab
    public static final RegistryObject<Block> NIGHTSHALE_VERTICAL_SLAB = verticalSlabBlock("nightshale_vertical_slab", NIGHTSHALE_SLAB);
    //Nightshale Stairs
    public static final RegistryObject<Block> NIGHTSHALE_STAIRS = stairsBlock(NIGHTSHALE);
    //Nightshale Wall
    public static final RegistryObject<Block> NIGHTSHALE_WALL = wallBlock(NIGHTSHALE);
    //Glimmering Nightshale
    public static final RegistryObject<Block> GLIMMERING_NIGHTSHALE = block("glimmering_nightshale", () -> new Block(
            BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
                    .lightLevel((state) -> 9)
                    .sound(SoundType.DEEPSLATE)
                    .mapColor(MapColor.LAPIS)
                    .instrument(NoteBlockInstrument.BASEDRUM)
            )
    );
    //Nightshale Bricks
    public static final RegistryObject<Block> NIGHTSHALE_BRICKS = block("nightshale_bricks", () -> new Block(NIGHTSHALE_PROPERTIES));
    //Nightshale Brick Slab
    public static final RegistryObject<Block> NIGHTSHALE_BRICK_SLAB = slabBlock("nightshale_brick_slab", NIGHTSHALE_BRICKS);
    //Nightshale Brick Vertical Slab
    public static final RegistryObject<Block> NIGHTSHALE_BRICK_VERTICAL_SLAB = verticalSlabBlock("nightshale_vertical_brick_slab", NIGHTSHALE_BRICK_SLAB);
    //Nightshale Brick Stairs
    public static final RegistryObject<Block> NIGHTSHALE_BRICK_STAIRS = stairsBlock("nightshale_brick_stairs", NIGHTSHALE_BRICKS);
    //Nightshale Brick Wall
    public static final RegistryObject<Block> NIGHTSHALE_BRICK_WALL = wallBlock("nightshale_brick_wall", NIGHTSHALE_BRICKS);

    ///REDSTONE\\\
    //Detector
    public static final RegistryObject<Block> DETECTOR = block("detector", () ->
            new DetectorBlock(
                    BlockBehaviour.Properties.of()
                            .strength(3.0F)
                            .requiresCorrectToolForDrops()
                            .isRedstoneConductor((pState, pLevel, pPos) -> false)
                            .mapColor(MapColor.STONE)
                            .instrument(NoteBlockInstrument.BASEDRUM)
            )
    );

    public static final RegistryObject<Block> MILK_CAULDRON = blockNoItem(
            "milk_cauldron", () -> new MilkCauldronBlock(
                    BlockBehaviour.Properties.copy(Blocks.CAULDRON)
                            .randomTicks()
            )
    );

    public static final RegistryObject<Block> CHEESE_BLOCK = block(
            "cheese_block", () -> new Block(
                    BlockBehaviour.Properties.of()
                            .strength(0.5F)
                            .sound(SoundType.WOOL)
                            .mapColor(MapColor.COLOR_YELLOW)
            )
    );
}
