package net.skydimondlox.idontknowmod.register;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.block.BaseBlock;
import net.skydimondlox.idontknowmod.block.custom.AlloyFurnaceBlock;
import net.skydimondlox.idontknowmod.block.custom.ElectricPressBlock;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Blocks {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Lib.MOD_ID);

    private static final Supplier<Properties> METAL_PROPERTIES_NO_OVERLAY =
            () -> Block.Properties.of()
                    .sound(SoundType.METAL)
                    .strength(3, 15)
                    .requiresCorrectToolForDrops()
                    .isViewBlocking((state, blockReader, pos) -> false);

    private static final Supplier<Properties> METAL_PROPERTIES_NO_OCCLUSION = () -> METAL_PROPERTIES_NO_OVERLAY.get().noOcclusion();
    private static final Supplier<Properties> METAL_PROPERTIES_DYNAMIC = () -> METAL_PROPERTIES_NO_OCCLUSION.get().dynamicShape();

    public static final BlockEntry<ElectricPressBlock> ELECTRIC_PRESS = new BlockEntry<>("electric_press", METAL_PROPERTIES_DYNAMIC, ElectricPressBlock::new);
    public static final BlockEntry<AlloyFurnaceBlock> ALLOY_FURNACE = new BlockEntry<>("alloy_furnace", METAL_PROPERTIES_DYNAMIC, AlloyFurnaceBlock::new);



    private static void init()
    {

    }

    public static final class BlockEntry<T extends Block> implements Supplier<T>, ItemLike
    {
        public static final Collection<BlockEntry<?>> ALL_ENTRIES = new ArrayList<>();

        private final RegistryObject<T> regObject;
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

        public BlockEntry(String name, Supplier<Properties> properties, Function<Properties, T> make)
         {
            this.properties = properties;
            this.regObject = REGISTER.register(name, () -> make.apply(properties.get()));
            ALL_ENTRIES.add(this);
        }

        public BlockEntry(T existing)
        {
            this.properties = () -> Properties.copy(existing);
            this.regObject = RegistryObject.create(Registry.BLOCK.getKey(existing), ForgeRegistries.BLOCKS);
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

        public ResourceLocation getId()
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
    }
}

}