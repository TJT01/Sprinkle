package mod.tjt01.sprinkle.data;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class ModTags {
    public static class Blocks {
        public static final ITag.INamedTag<Block> QUARK_VERTICAL_SLAB = BlockTags.bind("quark:vertical_slab");
    }

    public static class Items {
        public static final ITag.INamedTag<Item> BUNDLE_BLACKLIST = ItemTags.bind("sprinkle:bundle_blacklist");

        public static final ITag.INamedTag<Item> QUARK_VERTICAL_SLAB = ItemTags.bind("quark:vertical_slab");
    }
}
