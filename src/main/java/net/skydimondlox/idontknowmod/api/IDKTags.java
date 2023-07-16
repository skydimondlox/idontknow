package net.skydimondlox.idontknowmod.api;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.common.Tags.Items;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import static net.skydimondlox.idontknowmod.api.utils.TagUtils.*;


public class IDKTags
{

    private static final Map<TagKey<Block>, TagKey<Item>> toItemTag = new HashMap<>();
    private static final Map<EnumMetals, MetalTags> metals = new EnumMap<>(EnumMetals.class);

    static
    {
        toItemTag.put(Blocks.STORAGE_BLOCKS, Items.STORAGE_BLOCKS);
        toItemTag.put(Blocks.ORES, Items.ORES);
        toItemTag.put(Blocks.ORES_IN_GROUND_STONE, Items.ORES_IN_GROUND_STONE);
        toItemTag.put(Blocks.ORES_IN_GROUND_DEEPSLATE, Items.ORES_IN_GROUND_DEEPSLATE);
        toItemTag.put(Blocks.ORE_RATES_SINGULAR, Items.ORE_RATES_SINGULAR);
        toItemTag.put(BlockTags.PLANKS, ItemTags.PLANKS);
        toItemTag.put(Blocks.GRAVEL, Items.GRAVEL);
    }

    public static final TagKey<Item> ironRod = createItemWrapper(getRod("iron"));
    public static final TagKey<Item> stoneStick = createItemWrapper(getRod("stone"));
    public static final TagKey<Item> bronzeRod = createItemWrapper(getRod("bronze"));
    public static final TagKey<Item> steelIngot = createItemWrapper(getIngot("steel"));
    public static final TagKey<Item> bronzeIngot = createItemWrapper(getIngot("bronze"));
    public static final TagKey<Item> tinIngot = createItemWrapper(getIngot("tin"));
    public static final TagKey<Item> zincIngot = createItemWrapper(getIngot("zinc"));
    public static final TagKey<Item> stoneGear = createItemWrapper(getGear("stone"));
    public static final TagKey<Item> copperGear = createItemWrapper(getGear("copper"));
    public static final TagKey<Item> ironGear = createItemWrapper(getGear("iron"));
    public static final TagKey<Item> goldGear = createItemWrapper(getGear("gold"));
    public static final TagKey<Item> diamondGear = createItemWrapper(getGear("diamond"));
    public static final TagKey<Item> bronzeGear = createItemWrapper(getGear("bronze"));
    public static final TagKey<Item> machineframes = createItemWrapper(forgeLoc("machine_frames"));
    public static final TagKey<Item> basic = createItemWrapper(forgeLoc("machine_frames/basic"));
    public static final TagKey<Item> intermediate = createItemWrapper(forgeLoc("machine_frames/intermediate"));
    public static final TagKey<Item> advanced = createItemWrapper(forgeLoc("machine_frames/advanced"));
    public static final TagKey<Item> tools = createItemWrapper(forgeLoc("tools"));
    public static final TagKey<Item> pickaxes = createItemWrapper(forgeLoc("tools/pickaxes"));
    public static final TagKey<Item> shovels = createItemWrapper(forgeLoc("tools/shovels"));
    public static final TagKey<Item> axes = createItemWrapper(forgeLoc("tools/axes"));
    public static final TagKey<Item> hoes = createItemWrapper(forgeLoc("tools/hoes"));
    public static final TagKey<Item> rawzinc = createItemWrapper(getRawOre("zinc"));
    public static final TagKey<Item> rawtin = createItemWrapper(getRawOre("tin"));
    public static final TagKey<Item> ironplate = createItemWrapper(getPlate("iron"));
    public static final TagKey<Item> goldplate = createItemWrapper(getPlate("gold"));
    public static final TagKey<Item> idontknow = createItemWrapper(forgeLoc("idontknow"));
    public static final TagKey<Block> idontknowblock = createBlockWrapper(forgeLoc("idontknowblock"));
    public static final TagKey<Block> tinblock = createBlockWrapper(getStorageBlock("tin"));
    public static final TagKey<Block> zincblock = createBlockWrapper(getStorageBlock("zinc"));
    public static final TagKey<Block> bronzeblock = createBlockWrapper(getStorageBlock("bronze"));



    public static final TagKey<Item> forbiddenInCrates = createItemWrapper(rl("forbidden_in_crates"));

    @Deprecated(forRemoval = true)
    public static final TagKey<Biome> hasMineralVeins = createBiomeWrapper(rl("has_mineral_veins"));

    static
    {
        for(EnumMetals m : EnumMetals.values())
            metals.put(m, new MetalTags(m));
    }

    public static TagKey<Item> getItemTag(TagKey<Block> blockTag)
    {
        Preconditions.checkArgument(toItemTag.containsKey(blockTag));
        return toItemTag.get(blockTag);
    }

    public static MetalTags getTagsFor(EnumMetals metal)
    {
        return metals.get(metal);
    }

    private static TagKey<Block> createBlockTag(ResourceLocation name)
    {
        TagKey<Block> blockTag = createBlockWrapper(name);
        toItemTag.put(blockTag, createItemWrapper(name));
        return blockTag;
    }

    public static void forAllBlocktags(BiConsumer<TagKey<Block>, TagKey<Item>> out)
    {
        for(Entry<TagKey<Block>, TagKey<Item>> entry : toItemTag.entrySet())
            out.accept(entry.getKey(), entry.getValue());
    }

    public static class MetalTags
    {
        public final TagKey<Item> ingot;
        public final TagKey<Item> nugget;
        @Nullable
        public final TagKey<Item> rawOre;
        public final TagKey<Item> plate;
        public final TagKey<Item> dust;
        public final TagKey<Block> storage;
        @Nullable
        public final TagKey<Block> ore;
        @Nullable
        public final TagKey<Block> rawBlock;

        private MetalTags(EnumMetals m)
        {
            String name = m.tagName();
            TagKey<Block> ore = null;
            TagKey<Item> rawOre = null;
            TagKey<Block> rawBlock = null;
            if(m.shouldAddOre())
            {
                ore = createBlockTag(getOre(name));
                rawOre = createItemWrapper(getRawOre(name));
                rawBlock = createBlockTag(getRawBlock(name));
            }
            if(!m.isVanillaMetal())
                storage = createBlockTag(getStorageBlock(name));
            else if(m==EnumMetals.COPPER)
            {
                storage = Blocks.STORAGE_BLOCKS_COPPER;
                ore = Blocks.ORES_COPPER;
                rawBlock = Blocks.STORAGE_BLOCKS_RAW_COPPER;
            }
            else if(m==EnumMetals.IRON)
            {
                storage = Blocks.STORAGE_BLOCKS_IRON;
                ore = Blocks.ORES_IRON;
                rawBlock = Blocks.STORAGE_BLOCKS_RAW_IRON;
            }
            else if(m== EnumMetals.GOLD)
            {
                storage = Blocks.STORAGE_BLOCKS_GOLD;
                ore = Blocks.ORES_GOLD;
                rawBlock = Blocks.STORAGE_BLOCKS_RAW_GOLD;
            }
            else
                throw new RuntimeException("Unkown vanilla metal: "+m.name());
            nugget = createItemWrapper(getNugget(name));
            ingot = createItemWrapper(getIngot(name));
            plate = createItemWrapper(getPlate(name));
            dust = createItemWrapper(getDust(name));
            this.ore = ore;
            this.rawOre = rawOre;
            this.rawBlock = rawBlock;
        }
    }

    private static ResourceLocation forgeLoc(String path)
    {
        return new ResourceLocation("forge", path);
    }

    public static ResourceLocation getOre(String type)
    {
        return forgeLoc("ores/"+type);
    }

    public static ResourceLocation getRawOre(String type)
    {
        return forgeLoc("raw_materials/"+type);
    }

    public static ResourceLocation getNugget(String type)
    {
        return forgeLoc("nuggets/"+type);
    }

    public static ResourceLocation getIngot(String type)
    {
        return forgeLoc("ingots/"+type);
    }

    public static ResourceLocation getGem(String type)
    {
        return forgeLoc("gems/"+type);
    }

    public static ResourceLocation getStorageBlock(String type)
    {
        return forgeLoc("storage_blocks/"+type);
    }

    public static ResourceLocation getRawBlock(String type)
    {
        return getStorageBlock("raw_"+type);
    }

    public static ResourceLocation getDust(String type)
    {
        return forgeLoc("dusts/"+type);
    }

    public static ResourceLocation getPlate(String type)
    {
        return forgeLoc("plates/"+type);
    }

    public static ResourceLocation getRod(String type)
    {
        return forgeLoc("rods/"+type);
    }

    public static ResourceLocation getGear(String type)
    {
        return forgeLoc("gears/"+type);
    }

    private static ResourceLocation rl(String path)
    {
        return new ResourceLocation(Lib.MOD_ID, path);
    }
}