package net.skydimondlox.idontknowmod.datagen.tags;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.api.EnumMetals;
import net.skydimondlox.idontknowmod.api.IDKTags;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.api.IDKTags.MetalTags;
import net.skydimondlox.idontknowmod.fluids.IDKFluidBlock;
import net.skydimondlox.idontknowmod.register.IDKBlocks;
import net.skydimondlox.idontknowmod.register.IDKBlocks.*;
import net.skydimondlox.idontknowmod.util.IDKLogger;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class IDKBlockTags extends BlockTagsProvider
{

    public IDKBlockTags(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existing)
    {
        super(output, lookupProvider, Lib.MOD_ID, existing);
    }

    @Override
    protected void addTags(Provider p_256380_)
    {

        tag(IDKTags.tinblock).add(MetalBlocks.TIN_BLOCK.get());
        tag(IDKTags.zincblock).add(MetalBlocks.ZINC_BLOCK.get());
        tag(IDKTags.bronzeblock).add(MetalBlocks.BRONZE_BLOCK.get());

        for(EnumMetals metal : EnumMetals.values())
        {
            MetalTags tags = IDKTags.getTagsFor(metal);
            if(!metal.isVanillaMetal())
            {
                tag(tags.storage).add(IDKBlocks.Metals.STORAGE.get(metal).get());
                tag(Tags.Blocks.STORAGE_BLOCKS).addTag(tags.storage);
                if(metal.shouldAddOre())
                {
                    Preconditions.checkNotNull(tags.ore);
                    tag(tags.ore)
                            .add(IDKBlocks.Metals.ORES.get(metal).get())
                            .add(IDKBlocks.Metals.DEEPSLATE_ORES.get(metal).get());
                    tag(Tags.Blocks.ORES).addTag(tags.ore);
                    Preconditions.checkNotNull(tags.rawBlock);
                    tag(tags.rawBlock).add(Metals.RAW_ORES.get(metal).get());
                    tag(Tags.Blocks.STORAGE_BLOCKS).addTag(tags.rawBlock);
                    tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(Metals.ORES.get(metal).get());
                    tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(Metals.DEEPSLATE_ORES.get(metal).get());
                    tag(Tags.Blocks.ORE_RATES_SINGULAR).add(Metals.ORES.get(metal).get())
                            .add(Metals.DEEPSLATE_ORES.get(metal).get());
                }


        }

        registerPickaxeMineable();
        registerAxeMineable();

        checkAllRegisteredForBreaking();
        }

    }

    // TODO rockcutter and grinding disk tags are nowhere near complete at this point, they were determined based on
    //  block material in <=1.19.4

    private void registerAxeMineable()
    {
        IntrinsicTagAppender<Block> tag = tag(BlockTags.MINEABLE_WITH_AXE);
        registerMineable(
                tag

        );

    }

    private <T extends Block> void registerMineable(IntrinsicTagAppender<Block> tag, Map<?, BlockEntry<T>> entries)
    {
        registerMineable(tag, new ArrayList<>(entries.values()));
    }

    private void registerMineable(IntrinsicTagAppender<Block> tag, BlockEntry<?>... entries)
    {
        registerMineable(tag, Arrays.asList(entries));
    }

    private void registerMineable(IntrinsicTagAppender<Block> tag, List<BlockEntry<?>> entries)
    {
        entries.sort(Comparator.comparing(BlockEntry::getId));
        for(BlockEntry<?> entry : entries)
        {
            tag.add(entry.get());
            BlockEntry<?> slab = IDKBlocks.TO_SLAB.get(entry.getId());
            if(slab!=null)
                tag.add(slab.get());
        }
    }

    private void registerPickaxeMineable()
    {
        IntrinsicTagAppender<Block> tag = tag(BlockTags.MINEABLE_WITH_PICKAXE);

        registerMineable(
                tag

        );

        setOreMiningLevel(EnumMetals.ALUMINUM, Tiers.STONE);
        setOreMiningLevel(EnumMetals.NICKEL, Tiers.IRON);
        setStorageMiningLevel(EnumMetals.ALUMINUM, Tiers.STONE);
        setStorageMiningLevel(EnumMetals.NICKEL, Tiers.IRON);
        setStorageMiningLevel(EnumMetals.STEEL, Tiers.IRON);
    }

    private void setOreMiningLevel(EnumMetals metal, Tiers level)
    {
        final BlockEntry<Block> ore = Metals.ORES.get(metal);
        final BlockEntry<Block> deepslateOre = Metals.DEEPSLATE_ORES.get(metal);
        final BlockEntry<Block> rawOre = Metals.RAW_ORES.get(metal);
        setMiningLevel(ore, level);
        setMiningLevel(deepslateOre, level);
        setMiningLevel(rawOre, level);
        registerMineable(tag(BlockTags.MINEABLE_WITH_PICKAXE), ore, deepslateOre, rawOre);
    }

    private void setStorageMiningLevel(EnumMetals metal, Tiers level)
    {
        final BlockEntry<Block> storage = Metals.STORAGE.get(metal);
        setMiningLevel(storage, level);
        registerMineable(tag(BlockTags.MINEABLE_WITH_PICKAXE), storage);
    }

    private void setMiningLevel(Supplier<Block> block, Tiers level)
    {
        TagKey<Block> tag = switch(level)
        {
            case STONE -> BlockTags.NEEDS_STONE_TOOL;
            case IRON -> BlockTags.NEEDS_IRON_TOOL;
            case DIAMOND -> BlockTags.NEEDS_DIAMOND_TOOL;
            default -> throw new IllegalArgumentException("No tag available for "+level.name());
        };
        tag(tag).add(block.get());
    }

    private void checkAllRegisteredForBreaking()
    {
        List<TagKey<Block>> knownHarvestTags = ImmutableList.of(
                BlockTags.MINEABLE_WITH_AXE,
                BlockTags.MINEABLE_WITH_PICKAXE,
                BlockTags.MINEABLE_WITH_SHOVEL
        );
        Set<ResourceLocation> harvestable = knownHarvestTags.stream()
                .map(this::tag)
                .map(TagAppender::getInternalBuilder)
                .flatMap(b -> b.build().stream())
                .map(Object::toString)
                .map(ResourceLocation::tryParse)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<ResourceLocation> knownNonHarvestable = Stream.of(

                )
                .map(BlockEntry::getId)
                .collect(Collectors.toSet());
        Set<ResourceLocation> registered = IDKBlocks.REGISTER.getEntries().stream()
                .map(RegistryObject::get)
                .filter(b -> !(b instanceof IDKFluidBlock))
                .map(BuiltInRegistries.BLOCK::getKey)
                .filter(name -> !knownNonHarvestable.contains(name))
                .collect(Collectors.toSet());
        Set<ResourceLocation> notHarvestable = Sets.difference(registered, harvestable);
        if(!notHarvestable.isEmpty())
        {
            notHarvestable.forEach(rl -> IDKLogger.logger.error("Not harvestable: {}", rl));
            throw new RuntimeException();
        }
    }
}
