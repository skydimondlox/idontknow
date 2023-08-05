package net.skydimondlox.idontknowmod.gui;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.skydimondlox.idontknowmod.util.inventory.IInventory;
import net.skydimondlox.idontknowmod.block.BlockInterfaces.IInteractionObjectIDK;

public class BlockEntityInventory implements Container
{
    final BlockEntity tile;
    final IInventory inv;
    final AbstractContainerMenu eventHandler;

    public BlockEntityInventory(BlockEntity tile, AbstractContainerMenu eventHandler)
    {
        this.tile = tile;
        this.inv = (IInventory)tile;
        this.eventHandler = eventHandler;
    }

    @Override
    public int getContainerSize()
    {
        return inv.getInventory().size();
    }

    @Override
    public boolean isEmpty()
    {
        for(ItemStack stack : inv.getInventory())
        {
            if(!stack.isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index)
    {
        return inv.getInventory().get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count)
    {
        ItemStack stack = inv.getInventory().get(index);
        if(!stack.isEmpty())
        {
            if(stack.getCount() <= count)
                inv.getInventory().set(index, ItemStack.EMPTY);
            else
            {
                stack = stack.split(count);
                if(stack.getCount()==0)
                    inv.getInventory().set(index, ItemStack.EMPTY);
            }
            eventHandler.slotsChanged(this);
        }
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index)
    {
        ItemStack ret = inv.getInventory().get(index).copy();
        inv.getInventory().set(index, ItemStack.EMPTY);
        return ret;
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        inv.getInventory().set(index, stack);
        eventHandler.slotsChanged(this);
    }

    @Override
    public int getMaxStackSize()
    {
        return 64;
    }

    @Override
    public void setChanged()
    {
        tile.setChanged();
    }

    @Override
    public boolean stillValid(Player player)
    {
        return isValidForPlayer(tile, player);
    }

    public static boolean isValidForPlayer(BlockEntity bEntity, Player player)
    {
        if(bEntity instanceof IInteractionObjectIDK<?> interactionObject&&!interactionObject.canUseGui(player))
            return false;
        return !bEntity.isRemoved()&&Vec3.atCenterOf(bEntity.getBlockPos()).distanceToSqr(player.position()) < 64;
    }

    @Override
    public void startOpen(Player player)
    {
    }

    @Override
    public void stopOpen(Player player)
    {
        inv.doGraphicalUpdates();
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack)
    {
        return inv.isStackValid(index, stack);
    }

    @Override
    public void clearContent()
    {
        for(int i = 0; i < inv.getInventory().size(); i++)
            inv.getInventory().set(i, ItemStack.EMPTY);
    }

}