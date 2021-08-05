package mod.tjt01.sprinkle.init;

import java.util.function.Supplier;

import mod.tjt01.sprinkle.Main;
import mod.tjt01.sprinkle.block.*;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;

public final class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

	public static RegistryObject<Block> blockNoItem(String name, Supplier<Block> block) {
		return BLOCKS.register(name, block);
	}

	public static RegistryObject<Block> block(String name, Supplier<Block> block, ItemGroup group) {
		RegistryObject<Block> registryObject = blockNoItem(name, block);
		ModItems.ITEMS.register(name, () -> new BlockItem(registryObject.get(), new Item.Properties().tab(group)));
		return registryObject;
	}

	public static RegistryObject<Block> slabBlock(String name, RegistryObject<Block> blockRegistryObject, ItemGroup group) {
		return block(name, () -> new SlabBlock(Block.Properties.copy(blockRegistryObject.get())), group);
	}

	public static RegistryObject<Block> slabBlock(RegistryObject<Block> blockRegistryObject, ItemGroup group) {
		String name = blockRegistryObject.getId().getPath() + "_slab";
		return slabBlock(name, blockRegistryObject, group);
	}

	public static RegistryObject<Block> stairsBlock(String name, RegistryObject<Block> blockRegistryObject, ItemGroup group) {
		return block(name, () -> new StairsBlock(() -> blockRegistryObject.get().defaultBlockState(), Block.Properties.copy(PURPUR_BRICKS.get())), group);
	}

	public static RegistryObject<Block> stairsBlock(RegistryObject<Block> blockRegistryObject, ItemGroup group) {
		String name = blockRegistryObject.getId().getPath() + "_stairs";
		return stairsBlock(name, blockRegistryObject, group);
	}

	public static RegistryObject<Block> wallBlock(String name, RegistryObject<Block> blockRegistryObject, ItemGroup group) {
		return block(name, () -> new WallBlock(AbstractBlock.Properties.copy(blockRegistryObject.get())), group);
	}

	public static RegistryObject<Block> wallBlock(RegistryObject<Block> blockRegistryObject, ItemGroup group) {
		String name = blockRegistryObject.getId().getPath() + "_wall";
		return wallBlock(name, blockRegistryObject, group);
	}

	public static RegistryObject<Block> verticalSlabBlock(String name, RegistryObject<Block> blockRegistryObject, ItemGroup group) {
		return block(name, () -> new VerticalSlabBlock(AbstractBlock.Properties.copy(blockRegistryObject.get())), group);
	}

	//Gold Chain
	public static final RegistryObject<Block> GOLD_CHAIN = block("gold_chain", () -> new ChainBlock(AbstractBlock.Properties.copy(Blocks.CHAIN)), ItemGroup.TAB_DECORATIONS);
	//Gold Lantern
	public static final RegistryObject<Block> GOLD_LANTERN = block("gold_lantern", () -> new LanternBlock(AbstractBlock.Properties.copy(Blocks.LANTERN)), ItemGroup.TAB_DECORATIONS);
	//Gold Lantern
	public static final RegistryObject<Block> GOLD_SOUL_LANTERN = block("gold_soul_lantern", () -> new LanternBlock(AbstractBlock.Properties.copy(Blocks.SOUL_LANTERN)), ItemGroup.TAB_DECORATIONS);
	//Purpur Bricks
	public static final RegistryObject<Block> PURPUR_BRICKS = block("purpur_bricks", () -> new Block(Block.Properties.copy(Blocks.PURPUR_BLOCK)), ItemGroup.TAB_BUILDING_BLOCKS);
	//Purpur Brick Slab
	public static final RegistryObject<Block> PURPUR_BRICK_SLAB = slabBlock("purpur_brick_slab", PURPUR_BRICKS, ItemGroup.TAB_BUILDING_BLOCKS);
	//Purpur Brick Slab
	public static final RegistryObject<Block> VERTICAL_PURPUR_BRICK_SLAB = verticalSlabBlock("purpur_brick_vertical_slab", PURPUR_BRICK_SLAB, ItemGroup.TAB_BUILDING_BLOCKS);
	//Purpur Brick Stairs
	public static final RegistryObject<Block> PURPUR_BRICK_STAIRS = stairsBlock("purpur_brick_stairs", PURPUR_BRICKS, ItemGroup.TAB_BUILDING_BLOCKS);
	//Purpur Brick Wall
	public static final RegistryObject<Block> PURPUR_BRICK_WALL = wallBlock("purpur_brick_wall", PURPUR_BRICKS, ItemGroup.TAB_BUILDING_BLOCKS);

	//Detector
	public static final RegistryObject<Block> DETECTOR = block("detector", () -> new DetectorBlock(AbstractBlock.Properties.copy(Blocks.OBSERVER)), ItemGroup.TAB_REDSTONE);
}
