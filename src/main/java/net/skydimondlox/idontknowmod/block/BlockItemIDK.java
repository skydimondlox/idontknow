package net.skydimondlox.idontknowmod.block;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.api.IDKProperties;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.api.client.TextUtils;
import net.skydimondlox.idontknowmod.util.EnergyHelper;
import net.skydimondlox.idontknowmod.util.ItemNBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.skydimondlox.idontknowmod.api.IDKProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class BlockItemIDK extends BlockItem
{
    private int burnTime;

    public BlockItemIDK(Block b, Item.Properties props)
    {
        super(b, props);
    }

    public BlockItemIDK(Block b)
    {
        this(b, new Item.Properties());
    }

    @Override
    public String getDescriptionId(ItemStack stack)
    {
        return getBlock().getDescriptionId();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag advanced)
    {
        if(getBlock() instanceof IBlock idkBlock&&idkBlock.hasFlavour())
        {
            String flavourKey = Lib.DESC_FLAVOUR+idkBlock.getNameForFlavour();
            tooltip.add(TextUtils.applyFormat(Component.translatable(flavourKey), ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, world, tooltip, advanced);
        if(ItemNBTHelper.hasKey(stack, EnergyHelper.ENERGY_KEY))
            tooltip.add(TextUtils.applyFormat(Component.translatable(Lib.DESC_INFO+"energyStored",
                            ItemNBTHelper.getInt(stack,  EnergyHelper.ENERGY_KEY)),
                    ChatFormatting.GRAY));
        if(ItemNBTHelper.hasKey(stack, "tank"))
        {
            FluidStack fs = FluidStack.loadFluidStackFromNBT(ItemNBTHelper.getTagCompound(stack, "tank"));
            if(fs!=null)
                tooltip.add(TextUtils.applyFormat(
                        Component.translatable(Lib.DESC_INFO+"fluidStored", fs.getDisplayName(), fs.getAmount()),
                        ChatFormatting.GRAY));
        }
    }


    public BlockItemIDK setBurnTime(int burnTime)
    {
        this.burnTime = burnTime;
        return this;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, RecipeType<?> type)
    {
        return this.burnTime;
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState newState)
    {
        Block b = newState.getBlock();
        if(b instanceof BaseBlock idkBlock)
        {
            if(!idkBlock.canIDKBlockBePlaced(newState, context))
                return false;
            boolean ret = super.placeBlock(context, newState);
            if(ret)
                idkBlock.onIDKBlockPlacedBy(context, newState);
            return ret;
        }
        else
            return super.placeBlock(context, newState);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level worldIn, @Nullable Player player, ItemStack stack, BlockState state)
    {
        // Skip reading the tile from NBT if the block is a (general) multiblock
        if(!state.hasProperty(IDKProperties.MULTIBLOCKSLAVE))
            return super.updateCustomBlockEntityTag(pos, worldIn, player, stack, state);
        else
            return false;
    }

    @Nonnull
    @Override
    public Optional<TooltipComponent> getTooltipImage(@Nonnull ItemStack stack)
    {
        if(stack.hasTag())
        {
            CompoundTag tag = stack.getOrCreateTag();
            if(tag.contains("Items"))
            {
                // manual readout, skipping empty slots
                ListTag list = tag.getList("Items", 10);
                NonNullList<ItemStack> items = NonNullList.create();
                list.forEach(e -> {
                    ItemStack s = ItemStack.of((CompoundTag)e);
                    if(!s.isEmpty())
                        items.add(s);
                });
                return Optional.of(new BundleTooltip(items, 0));
            }
        }
        return super.getTooltipImage(stack);
    }

    @Override
    public boolean canFitInsideContainerItems()
    {
        return !(getBlock() instanceof BaseBlock idkBlock)||idkBlock.fitsIntoContainer();
    }

    public static class BlockItemIDKNoInventory extends BlockItemIDK
    {
        public BlockItemIDKNoInventory(Block b)
        {
            super(b);
        }

        @Nullable
        @Override
        public CompoundTag getShareTag(ItemStack stack)
        {
            CompoundTag ret = super.getShareTag(stack);
            if(ret!=null)
            {
                ret = ret.copy();
                ret.remove("Items");
            }
            return ret;
        }
    }
}
