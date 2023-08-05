package net.skydimondlox.idontknowmod.fx;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraft.core.particles.ParticleType;

import javax.annotation.Nonnull;

public class IDKParticleType<T extends ParticleOptions> extends ParticleType<T>
{
    private final Codec<T> codec;

    public IDKParticleType(boolean alwaysShow, Deserializer<T> deserializer, Codec<T> codec)
    {
        super(alwaysShow, deserializer);
        this.codec = codec;
    }

    @Nonnull
    @Override
    public Codec<T> codec()
    {
        return codec;
    }
}

