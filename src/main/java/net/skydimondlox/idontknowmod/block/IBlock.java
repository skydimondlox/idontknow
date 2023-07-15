package net.skydimondlox.idontknowmod.block;

/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */


import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public interface IBlock
{
    boolean hasFlavour();

    String getNameForFlavour();

    void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items);
}