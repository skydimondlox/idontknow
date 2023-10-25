package net.skydimondlox.idontknowmod.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.skydimondlox.idontknowmod.idontknowmod;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> STORAGE_BLOCKS_BRONZE = forgeTag("storage_blocks/bronze");
        public static final TagKey<Block> STORAGE_BLOCKS_TIN = forgeTag("storage_blocks/tin");
        public static final TagKey<Block> STORAGE_BLOCKS_ZINC = forgeTag("storage_blocks/zinc");
        public static final TagKey<Block> STORAGE_BLOCKS_STEEL = forgeTag("storage_blocks/steel");
        public static final TagKey<Block> ORES_TIN = forgeTag("ores/tin");
        public static final TagKey<Block> ORES_ZINC = forgeTag("ores/zinc");
        public static final TagKey<Block> ORES = forgeTag("ores");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(idontknowmod.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }


    }
    public static class Items {
        public static final TagKey<Item> INGOTS_BRONZE = forgeTag("ingots/bronze");
        public static final TagKey<Item> INGOTS_ZINC = forgeTag("ingots/tin");
        public static final TagKey<Item> INGOTS_TIN = forgeTag("ingots/zinc");
        public static final TagKey<Item> INGOTS_STEEL = forgeTag("ingots/steel");
        public static final TagKey<Item> ORES_TIN = forgeTag("ores/tin");
        public static final TagKey<Item> ORES_ZINC = forgeTag("ores/zinc");
        public static final TagKey<Item> STORAGE_BLOCKS_BRONZE = forgeTag("storage_blocks/bronze");
        public static final TagKey<Item> STORAGE_BLOCKS_TIN = forgeTag("storage_blocks/tin");
        public static final TagKey<Item> STORAGE_BLOCKS_ZINC = forgeTag("storage_blocks/zinc");
        public static final TagKey<Item> STORAGE_BLOCKS_STEEL = forgeTag("storage_blocks/steel");
        public static final TagKey<Item> GEARS_STONE = forgeTag("gears/stone");
        public static final TagKey<Item> GEARS_IRON = forgeTag("gears/iron");
        public static final TagKey<Item> GEARS_GOLD = forgeTag("gears/gold");
        public static final TagKey<Item> GEARS_DIAMOND = forgeTag("gears/diamond");
        public static final TagKey<Item> GEARS_BRONZE = forgeTag("gears/bronze");
        public static final TagKey<Item> GEARS_COPPER = forgeTag("gears/copper");

        public static final TagKey<Item> MACHINE_FRAMES = tag("machineframes");
        public static final TagKey<Item> MACHINE_FRAME_BASIC = tag("machineframes/basic");
        public static final TagKey<Item> MACHINE_FRAME_INTERMEDIATE = tag("machineframes/intermediate");
        public static final TagKey<Item> MACHINE_FRAME_ADVANCED = tag("machineframes/advanced");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(idontknowmod.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
    public static class Fluids {

        public static final TagKey<Fluid> OIL = forgeTag("oil");

        private static TagKey<Fluid> tag(String name) {
            return FluidTags.create(new ResourceLocation(idontknowmod.MOD_ID, name));
        }

        private static TagKey<Fluid> forgeTag(String name) {
            return FluidTags.create(new ResourceLocation("forge", name));
        }
    }
}
