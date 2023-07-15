package net.skydimondlox.idontknowmod.api.utils;

/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.skydimondlox.idontknowmod.api.Lib;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

@EventBusSubscriber(modid = Lib.MOD_ID)
public class SafeChunkUtils
{
    private static final Map<LevelAccessor, Set<ChunkPos>> unloadingChunks = new WeakHashMap<>();

    public static LevelChunk getSafeChunk(LevelAccessor w, BlockPos pos)
    {
        ChunkSource provider = w.getChunkSource();
        ChunkPos chunkPos = new ChunkPos(pos);
        if(unloadingChunks.getOrDefault(w, ImmutableSet.of()).contains(chunkPos))
            return null;
        //TODO does this do what I want?
        return provider.getChunkNow(chunkPos.x, chunkPos.z);
    }

    public static boolean isChunkSafe(LevelAccessor w, BlockPos pos)
    {
        return getSafeChunk(w, pos)!=null;
    }

    public static BlockEntity getSafeBE(LevelAccessor w, BlockPos pos)
    {
        LevelChunk c = getSafeChunk(w, pos);
        if(c==null)
            return null;
        else
            return c.getBlockEntity(pos);
    }

    @Nonnull
    public static BlockState getBlockState(LevelAccessor w, BlockPos pos)
    {
        LevelChunk c = getSafeChunk(w, pos);
        if(c==null)
            return Blocks.AIR.defaultBlockState();
        else
            return c.getBlockState(pos);
    }

    public static int getRedstonePower(Level w, BlockPos pos, Direction d)
    {
        if(!isChunkSafe(w, pos))
            return 0;
        else
            return w.getSignal(pos, d);
    }

    public static int getRedstonePowerFromNeighbors(Level w, BlockPos pos)
    {
        int ret = 0;
        for(Direction d : DirectionUtils.VALUES)
        {
            int atNeighbor = getRedstonePower(w, pos.relative(d), d);
            ret = Math.max(ret, atNeighbor);
            if(ret >= 15)
                break;
        }
        return ret;
    }


    public static void onChunkUnload(ChunkEvent.Unload ev)
    {
        unloadingChunks.computeIfAbsent(ev.getLevel(), w -> new HashSet<>()).add(ev.getChunk().getPos());
    }

    public static void onTick(LevelTickEvent ev)
    {
        if(ev.phase==Phase.START)
            unloadingChunks.remove(ev.level);
    }
}