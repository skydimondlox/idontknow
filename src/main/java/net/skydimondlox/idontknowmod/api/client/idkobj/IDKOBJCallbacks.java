package net.skydimondlox.idontknowmod.api.client.idkobj;

/*
 * BluSunrize
 * Copyright (c) 2023
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

public class IDKOBJCallbacks
{
    private static final BiMap<ResourceLocation, IDKOBJCallback<?>> CALLBACKS = HashBiMap.create();
    private static final Map<IDKOBJCallback<?>, ModelProperty<?>> MODEL_PROPERTIES = new IdentityHashMap<>();

    public static void register(ResourceLocation name, IDKOBJCallback<?> callback)
    {
        CALLBACKS.put(name, callback);
        MODEL_PROPERTIES.put(callback, new ModelProperty<>());
    }

    @Nullable
    public static IDKOBJCallback<?> getCallback(ResourceLocation name)
    {
        return CALLBACKS.get(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> ModelProperty<T> getModelProperty(IDKOBJCallback<?> callback)
    {
        return (ModelProperty<T>)MODEL_PROPERTIES.get(callback);
    }

    public static ResourceLocation getName(IDKOBJCallback<?> callback)
    {
        return Objects.requireNonNull(CALLBACKS.inverse().get(callback));
    }
}
