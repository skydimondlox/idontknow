package net.skydimondlox.idontknowmod.api.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public record DirectionalBlockPos(BlockPos position, Direction side)
{}