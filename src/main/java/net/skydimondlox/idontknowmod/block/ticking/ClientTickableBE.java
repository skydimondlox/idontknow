package net.skydimondlox.idontknowmod.block.ticking;

/*
 * BluSunrize
 * Copyright (c) 2021
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */


import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface ClientTickableBE extends TickableBase
{
    void tickClient();

    static <T extends BlockEntity> BlockEntityTicker<T> makeTicker() {
        return (level, pos, state, blockEntity) -> {
            ClientTickableBE tickable = (ClientTickableBE) blockEntity;
            if (tickable.canTickAny())
                tickable.tickClient();
        };
    }
}