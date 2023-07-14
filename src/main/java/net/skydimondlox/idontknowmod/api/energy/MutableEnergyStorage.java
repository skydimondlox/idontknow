package net.skydimondlox.idontknowmod.api.energy;
/*
 * BluSunrize
 * Copyright (c) 2021
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */


import net.minecraftforge.energy.EnergyStorage;
import net.skydimondlox.idontknowmod.api.IMutableEnergyStorage;

public class MutableEnergyStorage extends EnergyStorage implements IMutableEnergyStorage
{
    public MutableEnergyStorage(int capacity)
    {
        super(capacity);
    }

    public MutableEnergyStorage(int capacity, int maxIO)
    {
        super(capacity, maxIO);
    }

    public MutableEnergyStorage(int capacity, int maxInsert, int maxExtract)
    {
        super(capacity, maxInsert, maxExtract);
    }

    @Override
    public void setStoredEnergy(int stored)
    {
        this.energy = stored;
    }
}