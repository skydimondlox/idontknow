
/*
 * BluSunrize
 * Copyright (c) 2021
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */

package net.skydimondlox.idontknowmod.api.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TagUtils
{
    public static boolean isNonemptyItemTag(RegistryAccess tags, ResourceLocation name)
    {
        return holderStream(tags, Registry.ITEM_REGISTRY, name).findAny().isPresent();
    }

    private static List<ResourceLocation> getTags(Reference<?> ref) {
        return ref.tags().map(TagKey::location).toList();
    }

    public static Collection<ResourceLocation> getMatchingTagNames(RegistryAccess tags, ItemStack stack)
    {
        Collection<ResourceLocation> ret = new HashSet<>(getTags(stack.getItem().builtInRegistryHolder()));
        Block b = Block.byItem(stack.getItem());
        if(b!=Blocks.AIR)
            ret.addAll(getTags(b.builtInRegistryHolder()));
        return ret;
    }

    public static String[] getMatchingPrefixAndRemaining(RegistryAccess tags, ItemStack stack, String... componentTypes)
    {
        for(ResourceLocation name : getMatchingTagNames(tags, stack))
        {
            for(String componentType : componentTypes)
                if(name.getPath().startsWith(componentType))
                {
                    String material = name.getPath().substring(componentType.length());
                    if(material.startsWith("/"))
                        material = material.substring(1);
                    if(material.length() > 0)
                        return new String[]{componentType, material};
                }
        }
        return null;
    }

    public static boolean isIngot(RegistryAccess tags, ItemStack stack)
    {
        var registry = tags.registryOrThrow(Registry.ITEM_REGISTRY);
        var tag = registry.getTag(Tags.Items.INGOTS);
        if (tag.isPresent())
            return tag.get().contains(Holder.direct(stack.getItem()));
        return false;
    }

    public static <T> Stream<T> elementStream(RegistryAccess tags, ResourceKey<Registry<T>> registry, ResourceLocation tag) {
        return holderStream(tags, registry, tag).map(Holder::value);
    }

    public static <T> Stream<T> elementStream(RegistryAccess tags, TagKey<T> key) {
        return holderStream(tags.registryOrThrow(key.registry()), key).map(Holder::value);
    }

    public static <T> Stream<T> elementStream(Registry<T> registry, TagKey<T> tag) {
        return holderStream(registry, tag).map(Holder::value);
    }

    public static <T> Stream<Holder<T>> holderStream(RegistryAccess tags, ResourceKey<Registry<T>> registry, ResourceLocation tag) {
        return holderStream(tags.registryOrThrow(registry), TagKey.create(registry, tag));
    }

    public static <T> Stream<Holder<T>> holderStream(Registry<T> registry, TagKey<T> tag) {
        return StreamSupport.stream(registry.getTagOrEmpty(tag).spliterator(), false);
    }

    public static TagKey<Item> createItemWrapper(ResourceLocation name)
    {
        return TagKey.create(Registry.ITEM_REGISTRY, name);
    }

    public static TagKey<Block> createBlockWrapper(ResourceLocation name)
    {
        return TagKey.create(Registry.BLOCK_REGISTRY, name);
    }

    public static TagKey<EntityType<?>> createEntityWrapper(ResourceLocation name)
    {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, name);
    }

    public static TagKey<Biome> createBiomeWrapper(ResourceLocation name)
    {
        return TagKey.create(Registry.BIOME_REGISTRY, name);
    }
}