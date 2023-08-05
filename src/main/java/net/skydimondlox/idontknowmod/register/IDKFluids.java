package net.skydimondlox.idontknowmod.register;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */

import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.fluids.IDKFluid;
import net.skydimondlox.idontknowmod.fluids.IDKFluidBlock;
import net.skydimondlox.idontknowmod.register.IDKBlocks.BlockEntry;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.skydimondlox.idontknowmod.fluids.IDKFluid.createBuilder;

public class IDKFluids
{
    public static final DeferredRegister<Fluid> REGISTER = DeferredRegister.create(ForgeRegistries.FLUIDS, Lib.MOD_ID);
    public static final DeferredRegister<FluidType> TYPE_REGISTER = DeferredRegister.create(
            ForgeRegistries.Keys.FLUID_TYPES, Lib.MOD_ID
    );
    public static final List<FluidEntry> ALL_ENTRIES = new ArrayList<>();
    public static final Set<BlockEntry<? extends LiquidBlock>> ALL_FLUID_BLOCKS = new HashSet<>();

    public record FluidEntry(
            RegistryObject<IDKFluid> flowing,
            RegistryObject<IDKFluid> still,
            BlockEntry<IDKFluidBlock> block,
            RegistryObject<BucketItem> bucket,
            RegistryObject<FluidType> type,
            List<Property<?>> properties
    )
    {
        private static FluidEntry make(String name, ResourceLocation stillTex, ResourceLocation flowingTex)
        {
            return make(name, 0, stillTex, flowingTex);
        }

        private static FluidEntry make(
                String name, ResourceLocation stillTex, ResourceLocation flowingTex, Consumer<FluidType.Properties> buildAttributes
        )
        {
            return make(name, 0, stillTex, flowingTex, buildAttributes);
        }

        private static FluidEntry make(String name, int burnTime, ResourceLocation stillTex, ResourceLocation flowingTex)
        {
            return make(name, burnTime, stillTex, flowingTex, null);
        }

        private static FluidEntry make(
                String name, int burnTime,
                ResourceLocation stillTex, ResourceLocation flowingTex,
                @Nullable Consumer<FluidType.Properties> buildAttributes
        )
        {
            return make(
                    name, burnTime, stillTex, flowingTex, IDKFluid::new, IDKFluid.Flowing::new, buildAttributes,
                    ImmutableList.of()
            );
        }

        private static FluidEntry make(
                String name, ResourceLocation stillTex, ResourceLocation flowingTex,
                Function<FluidEntry, ? extends IDKFluid> makeStill, Function<FluidEntry, ? extends IDKFluid> makeFlowing,
                @Nullable Consumer<FluidType.Properties> buildAttributes, ImmutableList<Property<?>> properties
        )
        {
            return make(name, 0, stillTex, flowingTex, makeStill, makeFlowing, buildAttributes, properties);
        }

        private static FluidEntry make(
                String name, int burnTime,
                ResourceLocation stillTex, ResourceLocation flowingTex,
                Function<FluidEntry, ? extends IDKFluid> makeStill, Function<FluidEntry, ? extends IDKFluid> makeFlowing,
                @Nullable Consumer<FluidType.Properties> buildAttributes, List<Property<?>> properties)
        {
            FluidType.Properties builder = FluidType.Properties.create()
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY);
            if(buildAttributes!=null)
                buildAttributes.accept(builder);
            RegistryObject<FluidType> type = TYPE_REGISTER.register(
                    name, () -> makeTypeWithTextures(builder, stillTex, flowingTex)
            );
            Mutable<FluidEntry> thisMutable = new MutableObject<>();
            RegistryObject<IDKFluid> still = REGISTER.register(name, () -> IDKFluid.makeFluid(
                    makeStill, thisMutable.getValue()
            ));
            RegistryObject<IDKFluid> flowing = REGISTER.register(name+"_flowing", () -> IDKFluid.makeFluid(
                    makeFlowing, thisMutable.getValue()
            ));
            BlockEntry<IDKFluidBlock> block = new IDKBlocks.BlockEntry<>(
                    name+"_fluid_block",
                    () -> Properties.copy(Blocks.WATER),
                    p -> new IDKFluidBlock(thisMutable.getValue(), p)
            );
            RegistryObject<BucketItem> bucket = IDKItems.REGISTER.register(name+"_bucket", () -> makeBucket(still, burnTime));
            FluidEntry entry = new FluidEntry(flowing, still, block, bucket, type, properties);
            thisMutable.setValue(entry);
            ALL_FLUID_BLOCKS.add(block);
            ALL_ENTRIES.add(entry);
            return entry;
        }

        private static FluidType makeTypeWithTextures(
                FluidType.Properties builder, ResourceLocation stillTex, ResourceLocation flowingTex
        )
        {
            return new FluidType(builder)
            {
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
                {
                    consumer.accept(new IClientFluidTypeExtensions()
                    {
                        @Override
                        public ResourceLocation getStillTexture()
                        {
                            return stillTex;
                        }

                        @Override
                        public ResourceLocation getFlowingTexture()
                        {
                            return flowingTex;
                        }
                    });
                }
            };
        }

        public IDKFluid getFlowing()
        {
            return flowing.get();
        }

        public IDKFluid getStill()
        {
            return still.get();
        }

        public IDKFluidBlock getBlock()
        {
            return block.get();
        }

        public BucketItem getBucket()
        {
            return bucket.get();
        }

        private static BucketItem makeBucket(RegistryObject<IDKFluid> still, int burnTime)
        {
            return new BucketItem(
                    still, new Item.Properties()
                    .stacksTo(1)
                    .craftRemainder(Items.BUCKET))
            {
                @Override
                public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
                {
                    return new FluidBucketWrapper(stack);
                }

                @Override
                public int getBurnTime(ItemStack itemStack, RecipeType<?> type)
                {
                    return burnTime;
                }
            };
        }

        public RegistryObject<IDKFluid> getStillGetter()
        {
            return still;
        }
    }
}