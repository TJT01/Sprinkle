package mod.tjt01.sprinkle.data;

import mod.tjt01.sprinkle.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> DOUBLE_DOOR_BLACKLIST = modTag("double_door_blacklist");

        public static final TagKey<Block> QUARK_VERTICAL_SLAB = quark("vertical_slab");

        private static TagKey<Block> modTag(String name) {
            return tag(new ResourceLocation(Main.MODID, name));
        }

        private static TagKey<Block> quark(String name) {
            return tag(new ResourceLocation("quark", name));
        }

        private static TagKey<Block> tag(ResourceLocation name) {
            return BlockTags.create(name);
        }
    }

    public static class Items {
        public static final TagKey<Item> QUARK_VERTICAL_SLAB = quark("vertical_slab");

        private static TagKey<Item> quark(String name) {
            return tag(new ResourceLocation("quark", name));
        }

        private static TagKey<Item> tag(ResourceLocation name) {
            return ItemTags.create(name);
        }
    }
}
