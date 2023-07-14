package net.skydimondlox.idontknowmod.api;

/*
 * BluSunrize
 * Copyright (c) 2021
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */

import net.minecraftforge.energy.IEnergyStorage;

public interface IMutableEnergyStorage extends IEnergyStorage
{
    void setStoredEnergy(int stored);

    default void modifyEnergyStored(int changeBy)
    {
        setStoredEnergy(getEnergyStored()+changeBy);
    }
}