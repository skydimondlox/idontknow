
/*
 * BluSunrize
 * Copyright (c) 2021
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */

package net.skydimondlox.idontknowmod.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.skydimondlox.idontknowmod.api.Lib;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MissingMappingsHelper
{
    private static final Map<ResourceKey<?>, Supplier<?>> REMAPPERS = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static void handleRemapping(MissingMappingsEvent event)
    {
        handleRemapping(event, Registry.ITEM_REGISTRY);
        handleRemapping(event, Registry.BLOCK_REGISTRY);
        handleRemapping(event, Registry.FLUID_REGISTRY);
    }

    private static <T>
    void handleRemapping(MissingMappingsEvent event, ResourceKey<Registry<T>> key)
    {
        var mappings = event.getMappings(key, Lib.MOD_ID);
        mappings.forEach(mapping -> {
            var supplier = (Supplier<T>)REMAPPERS.get(ResourceKey.create(key, mapping.getKey()));
            if(supplier!=null)
                mapping.remap(supplier.get());
        });
    }

    public static <T> void addRemapping(
            Registry<T> registry, ResourceLocation oldKey, Supplier<T> newSupplier
    )
    {
        REMAPPERS.put(ResourceKey.create(registry.key(), oldKey), newSupplier);
    }

}