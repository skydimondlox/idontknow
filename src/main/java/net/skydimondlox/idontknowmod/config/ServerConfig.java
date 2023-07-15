package net.skydimondlox.idontknowmod.config;

/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */

import com.electronwill.nightconfig.core.Config;
import com.google.common.base.Preconditions;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.skydimondlox.idontknowmod.idontknowmod;

@SuppressWarnings("WeakerAccess")
@EventBusSubscriber(modid = idontknowmod.MOD_ID, bus = Bus.MOD)
public class ServerConfig
{

    public static class Machines
    {
        Machines(ForgeConfigSpec.Builder builder)
        {
            builder.push("machines");
            {
            builder.push("electricpress");
            electricPress_time = addPositive(builder, "time", 200, "The length in ticks it takes for the Electric Press to make a recipe");
            electricPress_consumption = addPositive(builder, "consumption", 40, "The RF per tick consumed by the Electric Press");
            builder.pop();
            }
            {
            builder.push("alloyfurnace");
            alloyFurnace_time = addPositive(builder, "time", 200, "The length in ticks it takes for the Alloy Furnace to make a recipe");
            alloyFurnace_consumption = addPositive(builder, "consumption", 40, "The RF per tick consumed by the Alloy Furnace");
            builder.pop();
            }
            builder.pop();
        }

        public final IntValue electricPress_time;
        public final IntValue electricPress_consumption;

        public final IntValue alloyFurnace_time;
        public final IntValue alloyFurnace_consumption;

    }

    private static IntValue addPositive(Builder builder, String name, int defaultVal, String... desc)
    {
        return builder
                .comment(desc)
                .defineInRange(name, defaultVal, 1, Integer.MAX_VALUE);
    }

    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final Machines MACHINES;

    static
    {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        MACHINES = new Machines(builder);

        CONFIG_SPEC = builder.build();
    }

    private static Config rawConfig;

    public static Config getRawConfig()
    {
        return Preconditions.checkNotNull(rawConfig);
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent ev)
    {
        if(CONFIG_SPEC==ev.getConfig().getSpec())
        {
            rawConfig = ev.getConfig().getConfigData();
            refresh();
        }
    }

    public static int getOrDefault(IntValue value)
    {
        return CONFIG_SPEC.isLoaded()?value.get(): value.getDefault();
    }

    public static void refresh()
    {

    }
}