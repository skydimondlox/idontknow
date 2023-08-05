package net.skydimondlox.idontknowmod.api.fluid;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import net.minecraftforge.fluids.FluidType;

public interface IFluidPipe
{
    /**
     * Amount to be transferred through pipes per tick,
     * if the fluid has been pressurized by a machine (such as a pump)
     */
    int AMOUNT_PRESSURIZED = FluidType.BUCKET_VOLUME;

    /**
     * Amount to be transferred through pipes per tick,
     * if the fluid is not pressurized
     */
    int AMOUNT_UNPRESSURIZED = FluidType.BUCKET_VOLUME/20;

    /**
     * NBT Key to indicate a pressurized fluid, increasing its transfer rate in IFluidPipes
     */
    String NBT_PRESSURIZED = "pressurized";

    static int getTransferableAmount(boolean pressurized)
    {
        return pressurized?AMOUNT_PRESSURIZED: AMOUNT_UNPRESSURIZED;
    }

    /**
     * @return whether the NBT tag for pressurization {@link #NBT_PRESSURIZED} should be stripped when inserting here
     */
    default boolean stripPressureTag()
    {
        return false;
    }

    /**
     * This method allows pipes _before_ a pump to still transfer fluids at high speed, by inserting into a pump that
     * can consume power to speed up the fluid.
     *
     * @param consumePower if true, the block should consume power to speed up the transfer
     * @return whether this block has the capability to pressurize a fluid
     */
    default boolean canOutputPressurized(boolean consumePower)
    {
        return false;
    }
}