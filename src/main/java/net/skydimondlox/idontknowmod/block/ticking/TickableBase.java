package net.skydimondlox.idontknowmod.block.ticking;

/*
 * BluSunrize
 * Copyright (c) 2021
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */

public interface TickableBase
{
    default boolean canTickAny()
    {
        return true;
    }
}