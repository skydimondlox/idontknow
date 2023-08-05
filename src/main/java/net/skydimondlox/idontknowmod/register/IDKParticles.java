package net.skydimondlox.idontknowmod.register;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.fx.*;

public class IDKParticles
{
    public static final DeferredRegister<ParticleType<?>> REGISTER = DeferredRegister.create(
            ForgeRegistries.PARTICLE_TYPES, Lib.MOD_ID
    );

    public static final RegistryObject<ParticleType<FluidSplashOptions>> FLUID_SPLASH = REGISTER.register(
            "fluid_splash", () -> new IDKParticleType<>(false, new FluidSplashOptions.DataDeserializer(), FluidSplashOptions.CODEC)
    );
    public static final RegistryObject<ParticleType<FractalOptions>> FRACTAL = REGISTER.register(
            "fractal", () -> new IDKParticleType<>(false, new FractalOptions.DataDeserializer(), FractalOptions.CODEC)
    );
    public static final RegistryObject<SimpleParticleType> IE_BUBBLE = REGISTER.register(
            "ie_bubble", () -> new SimpleParticleType(false)
    );

    @EventBusSubscriber(modid = idontknowmod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
    private static class Client
    {
        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event)
        {
            event.registerSprite(IDKParticles.FLUID_SPLASH.get(), new FluidSplashOptions.factory());
        }
    }
}
