package mod.tjt01.sprinkle.data;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.EntityTypeTags;

public class ModTags {
    public static class Blocks {
        public static final Tag.Named<Block> QUARK_VERTICAL_SLAB = BlockTags.bind("quark:vertical_slab");
    }

    public static class Items {
        //public static final Tag.Named<Item> BUNDLE_BLACKLIST = ItemTags.bind("sprinkle:bundle_blacklist");

        public static final Tag.Named<Item> QUARK_VERTICAL_SLAB = ItemTags.bind("quark:vertical_slab");
    }

    public static class EntitiyTypes {
        public static final Tag.Named<EntityType<?>> BLINDNESS_IMMUNE = EntityTypeTags.bind("sprinkle:blindness_immune");
    }
}
