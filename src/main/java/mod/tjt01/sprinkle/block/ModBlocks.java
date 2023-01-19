package mod.tjt01.sprinkle.block;

import java.util.function.Supplier;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

    public static RegistryObject<Block> blockNoItem(String name, Supplier<Block> block) {
        return BLOCKS.register(name, block);
    }

    public static RegistryObject<Block> block(String name, Supplier<Block> block, CreativeModeTab group) {
        RegistryObject<Block> registryObject = blockNoItem(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(registryObject.get(), new Item.Properties().tab(group)));
        return registryObject;
    }

    public static RegistryObject<Block> slabBlock(String name, RegistryObject<Block> blockRegistryObject, CreativeModeTab group) {
        return block(name, () -> new SlabBlock(Block.Properties.copy(blockRegistryObject.get())), group);
    }

    public static RegistryObject<Block> slabBlock(RegistryObject<Block> blockRegistryObject, CreativeModeTab group) {
        String name = blockRegistryObject.getId().getPath() + "_slab";
        return slabBlock(name, blockRegistryObject, group);
    }

    public static RegistryObject<Block> stairsBlock(String name, RegistryObject<Block> blockRegistryObject, CreativeModeTab group) {
        return block(name, () -> new StairBlock(() -> blockRegistryObject.get().defaultBlockState(), Block.Properties.copy(blockRegistryObject.get())), group);
    }

    public static RegistryObject<Block> stairsBlock(RegistryObject<Block> blockRegistryObject, CreativeModeTab group) {
        String name = blockRegistryObject.getId().getPath() + "_stairs";
        return stairsBlock(name, blockRegistryObject, group);
    }

    public static RegistryObject<Block> wallBlock(String name, RegistryObject<Block> blockRegistryObject, CreativeModeTab group) {
        return block(name, () -> new WallBlock(BlockBehaviour.Properties.copy(blockRegistryObject.get())), group);
    }

    public static RegistryObject<Block> wallBlock(RegistryObject<Block> blockRegistryObject, CreativeModeTab group) {
        String name = blockRegistryObject.getId().getPath() + "_wall";
        return wallBlock(name, blockRegistryObject, group);
    }

    public static RegistryObject<Block> verticalSlabBlock(String name, RegistryObject<Block> blockRegistryObject, CreativeModeTab group) {
        return block(name, () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(blockRegistryObject.get())), group);
    }

    ///BUILDING\\\
    //Gold Chain
    public static final RegistryObject<Block> GOLD_CHAIN = block("gold_chain", () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)), CreativeModeTab.TAB_DECORATIONS);
    //Gold Lantern
    public static final RegistryObject<Block> GOLD_LANTERN = block("gold_lantern", () -> new LanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)), CreativeModeTab.TAB_DECORATIONS);
    //Gold Soul Lantern
    public static final RegistryObject<Block> GOLD_SOUL_LANTERN = block("gold_soul_lantern", () -> new LanternBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_LANTERN)), CreativeModeTab.TAB_DECORATIONS);

    //Purpur Bricks
    public static final RegistryObject<Block> PURPUR_BRICKS = block("purpur_bricks", () -> new Block(Block.Properties.copy(Blocks.PURPUR_BLOCK)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Purpur Brick Slab
    public static final RegistryObject<Block> PURPUR_BRICK_SLAB = slabBlock("purpur_brick_slab", PURPUR_BRICKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Purpur Brick Vertical Slab
    public static final RegistryObject<Block> VERTICAL_PURPUR_BRICK_SLAB = verticalSlabBlock("purpur_brick_vertical_slab", PURPUR_BRICK_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Purpur Brick Stairs
    public static final RegistryObject<Block> PURPUR_BRICK_STAIRS = stairsBlock("purpur_brick_stairs", PURPUR_BRICKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Purpur Brick Wall
    public static final RegistryObject<Block> PURPUR_BRICK_WALL = wallBlock("purpur_brick_wall", PURPUR_BRICKS, CreativeModeTab.TAB_DECORATIONS);

    //Nightshale
    public static final RegistryObject<Block> NIGHTSHALE = block("nightshale", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Nightshale Slab
    public static final RegistryObject<Block> NIGHTSHALE_SLAB = slabBlock(NIGHTSHALE, CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Nightshale Vertical Slab
    public static final RegistryObject<Block> NIGHTSHALE_VERTICAL_SLAB = verticalSlabBlock("nightshale_vertical_slab", NIGHTSHALE_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Nightshale Stairs
    public static final RegistryObject<Block> NIGHTSHALE_STAIRS = stairsBlock(NIGHTSHALE, CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Nightshale Wall
    public static final RegistryObject<Block> NIGHTSHALE_WALL = wallBlock(NIGHTSHALE, CreativeModeTab.TAB_DECORATIONS);
    //Glimmering Nightshale
    public static final RegistryObject<Block> GLIMMERING_NIGHTSHALE = block("glimmering_nightshale", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.LAPIS).requiresCorrectToolForDrops().strength(1.5F, 6.0F).lightLevel((state) -> 9)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Nightshale Bricks
    public static final RegistryObject<Block> NIGHTSHALE_BRICKS = block("nightshale_bricks", () -> new Block(BlockBehaviour.Properties.copy(NIGHTSHALE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Nightshale Brick Slab
    public static final RegistryObject<Block> NIGHTSHALE_BRICK_SLAB = slabBlock("nightshale_brick_slab", NIGHTSHALE_BRICKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Nightshale Brick Vertical Slab
    public static final RegistryObject<Block> NIGHTSHALE_BRICK_VERTICAL_SLAB = verticalSlabBlock("nightshale_vertical_brick_slab", NIGHTSHALE_BRICK_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Nightshale Brick Stairs
    public static final RegistryObject<Block> NIGHTSHALE_BRICK_STAIRS = stairsBlock("nightshale_brick_stairs", NIGHTSHALE_BRICKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    //Nightshale Brick Wall
    public static final RegistryObject<Block> NIGHTSHALE_BRICK_WALL = wallBlock("nightshale_brick_wall", NIGHTSHALE_BRICKS, CreativeModeTab.TAB_DECORATIONS);

    ///REDSTONE\\\
    //Detector
    public static final RegistryObject<Block> DETECTOR = block("detector", () -> new DetectorBlock(BlockBehaviour.Properties.copy(Blocks.OBSERVER)), CreativeModeTab.TAB_REDSTONE);
}
