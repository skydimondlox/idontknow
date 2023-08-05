package net.skydimondlox.idontknowmod.util;

/*
 * BluSunrize
 * Copyright (c) 2023
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.idontknowmod;

import java.io.IOException;

@EventBusSubscriber(value = Dist.CLIENT, modid = Lib.MOD_ID, bus = Bus.MOD)
public class IDKGLShaders
{
    private static ShaderInstance blockFullbrightShader;
    private static ShaderInstance vboShader;
    private static ShaderInstance pointShader;

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent ev) throws IOException
    {
        ev.registerShader(
                new ShaderInstance(ev.getResourceProvider(), idontknowmod.rl("block_fullbright"), DefaultVertexFormat.BLOCK),
                shader -> blockFullbrightShader = shader
        );
        ev.registerShader(
                new ShaderInstance(ev.getResourceProvider(), idontknowmod.rl("rendertype_vbo"), VertexBufferHolder.BUFFER_FORMAT),
                shader -> vboShader = shader
        );
        ev.registerShader(
                new ShaderInstance(ev.getResourceProvider(), idontknowmod.rl("rendertype_point"), DefaultVertexFormat.POSITION_COLOR_NORMAL),
                shader -> pointShader = shader
        );
    }

    public static ShaderInstance getBlockFullbrightShader()
    {
        return blockFullbrightShader;
    }

    public static ShaderInstance getVboShader()
    {
        return vboShader;
    }

    public static ShaderInstance getPointShader()
    {
        return pointShader;
    }
}