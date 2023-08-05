package net.skydimondlox.idontknowmod.register;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.api.EnumMetals;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.block.BaseBlock;
import net.skydimondlox.idontknowmod.block.BlockIDKSlab;
import net.skydimondlox.idontknowmod.block.IBlock;
import net.skydimondlox.idontknowmod.block.custom.AlloyFurnaceBlock;
import net.skydimondlox.idontknowmod.block.custom.ElectricPressBlock;
import net.skydimondlox.idontknowmod.block.entity.AlloyFurnaceBlockEntity;
import net.skydimondlox.idontknowmod.block.entity.ElectricPressBlockEntity;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class IDKBlocks {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Lib.MOD_ID);
    private static final Supplier<Properties> STONE_DECO_PROPS = () -> Block.Properties.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(2, 10);

    private static final Supplier<Properties> STONE_DECO_BRICK_PROPS = () -> Block.Properties.of()
            .sound(SoundType.STONE)
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.75f, 10);
    private static final Supplier<Properties> STONE_DECO_LEADED_PROPS = () -> Block.Properties.of()
            .sound(SoundType.STONE)
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(2, 180);
    private static final Supplier<Properties> STONE_DECO_PROPS_NOT_SOLID = () -> Block.Properties.of()
            .sound(SoundType.STONE)
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(0.5f, 0.5f) //Glass & Tinted Glass are 0.3f,0.3f. These glasses are stronger, thus 0.5f,0.5f
            .noOcclusion();
    private static final Supplier<Properties> SHEETMETAL_PROPERTIES = () -> Block.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(2, 2); //Cauldron props are 2,2 and sheetmetal is similar
    private static final Supplier<Properties> STANDARD_WOOD_PROPERTIES = () -> Block.Properties.of()
            .mapColor(MapColor.WOOD)
            .ignitedByLava()
            .instrument(NoteBlockInstrument.BASS)
            .sound(SoundType.WOOD)
            .strength(2, 5);
    private static final Supplier<Properties> STANDARD_WOOD_PROPERTIES_NO_OVERLAY =
            () -> Block.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .ignitedByLava()
                    .instrument(NoteBlockInstrument.BASS)
                    .sound(SoundType.WOOD)
                    .strength(2, 5)
                    .isViewBlocking((state, blockReader, pos) -> false);
    private static final Supplier<Properties> STANDARD_WOOD_PROPERTIES_NO_OCCLUSION = () -> STANDARD_WOOD_PROPERTIES_NO_OVERLAY.get().noOcclusion();
    private static final Supplier<Properties> DEFAULT_METAL_PROPERTIES = () -> Block.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops()
            .strength(3, 15);
    private static final Supplier<Properties> METAL_PROPERTIES_NO_OVERLAY =
            () -> Block.Properties.of()
                    .mapColor(MapColor.METAL)
                    .sound(SoundType.METAL)
                    .strength(3, 15)
                    .requiresCorrectToolForDrops()
                    .isViewBlocking((state, blockReader, pos) -> false);

    private static final Supplier<Properties> METAL_PROPERTIES_NO_OCCLUSION = () -> METAL_PROPERTIES_NO_OVERLAY.get().noOcclusion();
    private static final Supplier<Properties> METAL_PROPERTIES_DYNAMIC = () -> METAL_PROPERTIES_NO_OCCLUSION.get().dynamicShape();

    public static final BlockEntry<ElectricPressBlock> ELECTRIC_PRESS = new BlockEntry<>("electric_press", METAL_PROPERTIES_DYNAMIC, ElectricPressBlock::new);
    public static final BlockEntry<AlloyFurnaceBlock> ALLOY_FURNACE = new BlockEntry<>("alloy_furnace", METAL_PROPERTIES_DYNAMIC, AlloyFurnaceBlock::new);

    private IDKBlocks()
    {
    }

    public static final Map<ResourceLocation, BlockEntry<SlabBlock>> TO_SLAB = new HashMap<>();

    public static final class MetalBlocks {

        public static final BlockEntry<BaseBlock> TIN_BLOCK = BlockEntry.simple("tin_block", DEFAULT_METAL_PROPERTIES);
        public static final BlockEntry<BaseBlock> BRONZE_BLOCK = BlockEntry.simple("bronze_block", DEFAULT_METAL_PROPERTIES);
        public static final BlockEntry<BaseBlock> ZINC_BLOCK = BlockEntry.simple("zinc_block", DEFAULT_METAL_PROPERTIES);

        private static void init() {
        }
    }

    public static final class idontknow {

        public static final BlockEntry<BaseBlock> IDONTKNOW = BlockEntry.simple("i_dont_know_block", DEFAULT_METAL_PROPERTIES);
        public static final BlockEntry<BaseBlock> ITSABLOCK = BlockEntry.simple("its_a_block", DEFAULT_METAL_PROPERTIES);

        private static void init() {
        }
    }

    public static final class Machines {

        public static final BlockEntry<AlloyFurnaceBlockEntity> ALLOY_FURNACE = new BlockEntry<>(
                "alloy_furnace", BlockBehaviour.Properties, p
        );

        public static final BlockEntry<ElectricPressBlockEntity> ELECTRIC_PRESS = new BlockEntry<>(
                "electric_press", BlockBehaviour.Properties, p
        );

        private static void init() {
        }
    }

    public static final class Metals
    {
        public static final Map<EnumMetals, BlockEntry<Block>> ORES = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, BlockEntry<Block>> DEEPSLATE_ORES = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, BlockEntry<Block>> RAW_ORES = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, BlockEntry<Block>> STORAGE = new EnumMap<>(EnumMetals.class);

        private static void init()
        {
            for(EnumMetals m : EnumMetals.values())
            {
                String name = m.tagName();
                BlockEntry<Block> storage;
                BlockEntry<Block> ore = null;
                BlockEntry<Block> deepslateOre = null;
                BlockEntry<Block> rawOre = null;
                BlockEntry<BaseBlock> sheetmetal = BlockEntry.simple("sheetmetal_"+name, SHEETMETAL_PROPERTIES);
                registerSlab(sheetmetal);
                if(m.shouldAddOre())
                {
                    ore = new BlockEntry<>(BlockEntry.simple("ore_"+name,
                            () -> Block.Properties.of()
                                    .mapColor(MapColor.STONE)
                                    .instrument(NoteBlockInstrument.BASEDRUM)
                                    .strength(3, 3)
                                    .requiresCorrectToolForDrops()));
                    deepslateOre = new BlockEntry<>(BlockEntry.simple("deepslate_ore_"+name,
                            () -> Block.Properties.of()
                                    .mapColor(MapColor.STONE)
                                    .instrument(NoteBlockInstrument.BASEDRUM)
                                    .mapColor(MapColor.DEEPSLATE)
                                    .sound(SoundType.DEEPSLATE)
                                    .strength(4.5f, 3)
                                    .requiresCorrectToolForDrops()));
                    rawOre = new BlockEntry<>(BlockEntry.simple("raw_block_"+name,
                            () -> Block.Properties.of()
                                    .mapColor(MapColor.STONE)
                                    .instrument(NoteBlockInstrument.BASEDRUM)
                                    .strength(5, 6)
                                    .requiresCorrectToolForDrops()));
                }
                if(!m.isVanillaMetal())
                {
                    BlockEntry<BaseBlock> storageidk = BlockEntry.simple(
                            "storage_"+name, () -> Block.Properties.of()
                                    .mapColor(MapColor.METAL)
                                    .sound(m==EnumMetals.STEEL?SoundType.NETHERITE_BLOCK: SoundType.METAL)
                                    .strength(5, 10)
                                    .requiresCorrectToolForDrops());
                    registerSlab(storageidk);
                    storage = new BlockEntry<>(storageidk);
                }
                else if(m==EnumMetals.IRON)
                {
                    storage = new BlockEntry<>(Blocks.IRON_BLOCK);
                    ore = new BlockEntry<>(Blocks.IRON_ORE);
                    deepslateOre = new BlockEntry<>(Blocks.DEEPSLATE_IRON_ORE);
                    rawOre = new BlockEntry<>(Blocks.RAW_IRON_BLOCK);
                }
                else if(m==EnumMetals.GOLD)
                {
                    storage = new BlockEntry<>(Blocks.GOLD_BLOCK);
                    ore = new BlockEntry<>(Blocks.GOLD_ORE);
                    deepslateOre = new BlockEntry<>(Blocks.DEEPSLATE_GOLD_ORE);
                    rawOre = new BlockEntry<>(Blocks.RAW_GOLD_BLOCK);
                }
                else if(m== EnumMetals.COPPER)
                {
                    storage = new BlockEntry<>(Blocks.COPPER_BLOCK);
                    ore = new BlockEntry<>(Blocks.COPPER_ORE);
                    deepslateOre = new BlockEntry<>(Blocks.DEEPSLATE_COPPER_ORE);
                    rawOre = new BlockEntry<>(Blocks.RAW_COPPER_BLOCK);
                }
                else
                    throw new RuntimeException("Unkown vanilla metal: "+m.name());
                STORAGE.put(m, storage);
                if(ore!=null)
                    ORES.put(m, ore);
                if(deepslateOre!=null)
                    DEEPSLATE_ORES.put(m, deepslateOre);
                if(deepslateOre!=null)
                    RAW_ORES.put(m, rawOre);
            }
        }
    }

    private static <T extends Block & IBlock> void registerSlab(BlockEntry<T> fullBlock)
    {
        TO_SLAB.put(fullBlock.getId(), new BlockEntry<>(
                "slab_"+fullBlock.getId().getPath(),
                fullBlock::getProperties,
                p -> new BlockIDKSlab<>(p, fullBlock)
        ));
    }

    private static Supplier<BlockBehaviour.Properties> dynamicShape(Supplier<BlockBehaviour.Properties> baseProps)
    {
        return () -> baseProps.get().dynamicShape();
    }


    private static void init()
    {

        REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        MetalBlocks.init();
        Metals.init();
        idontknow.init();
        Machines.init();

    }

    public static final class BlockEntry<T extends Block> implements Supplier<T>, ItemLike
    {
        public static final Collection<BlockEntry<?>> ALL_ENTRIES = new ArrayList<>();

        private static final RegistryObject<T> regObject;
        private final Supplier<Properties> properties;

        public static BlockEntry<BaseBlock> simple(String name, Supplier<Properties> properties, Consumer<BaseBlock> extra)
        {
            return new BlockEntry<>(name, properties, p -> Util.make(new BaseBlock(p), extra));
        }

        public static BlockEntry<BaseBlock> simple(String name, Supplier<Properties> properties)
        {
            return simple(name, properties, $ -> {
            });
        }

        public static BlockEntry<ScaffoldingBlock> scaffolding(String name, Supplier<Properties> props)
        {
            return new BlockEntry<>(name, props, ScaffoldingBlock::new);
        }

        public static BlockEntry<FenceBlock> fence(String name, Supplier<Properties> props)
        {
            return new BlockEntry<>(name, props, FenceBlock::new);
        }

        public BlockEntry(String name, Supplier<Properties> properties, Function<Properties, T> make)
        {
            this.properties = properties;
            this.regObject = REGISTER.register(name, () -> make.apply(properties.get()));
            ALL_ENTRIES.add(this);
        }

        public BlockEntry(T existing)
        {
            this.properties = () -> Properties.copy(existing);
            this.regObject = RegistryObject.create(BuiltInRegistries.BLOCK.getKey(existing), ForgeRegistries.BLOCKS);
        }

        @SuppressWarnings("unchecked")
        public BlockEntry(BlockEntry<? extends T> toCopy)
        {
            this.properties = toCopy.properties;
            this.regObject = (RegistryObject<T>)toCopy.regObject;
        }

        @Override
        public T get()
        {
            return regObject.get();
        }

        public BlockState defaultBlockState()
        {
            return get().defaultBlockState();
        }

        public static ResourceLocation getId()
        {
            return regObject.getId();
        }

        public Properties getProperties()
        {
            return properties.get();
        }

        @Nonnull
        @Override
        public Item asItem()
        {
            return get().asItem();
        }

        public RegistryObject<? extends Block> getRegObject()
        {
            return regObject;
        }
    }
}