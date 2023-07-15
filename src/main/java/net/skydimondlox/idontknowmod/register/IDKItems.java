package net.skydimondlox.idontknowmod.register;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.api.EnumMetals;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.item.BaseItem;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class IDKItems
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Lib.MOD_ID);

    private IDKItems()
    {
    }

    public static final class Ingredients
    {
        public static final ItemRegObject<BaseItem> STICK_STONE = simple("stick_stone");
        public static final ItemRegObject<BaseItem> STICK_IRON = simple("stick_iron");

        private static void init()
        {
        }
    }

    public static final class Metals
    {
        public static final Map<EnumMetals, ItemRegObject<Item>> INGOTS = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, ItemRegObject<Item>> NUGGETS = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, ItemRegObject<Item>> RAW_ORES = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, ItemRegObject<BaseItem>> DUSTS = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, ItemRegObject<BaseItem>> PLATES = new EnumMap<>(EnumMetals.class);

        private static void init()
        {
            for(EnumMetals m : EnumMetals.values())
            {
                String name = m.tagName();
                ItemRegObject<Item> nugget;
                ItemRegObject<Item> ingot;
                ItemRegObject<Item> rawOre = null;
                if(!m.isVanillaMetal())
                    ingot = register("ingot_"+name, BaseItem::new);
                else if(m==EnumMetals.IRON)
                    ingot = of(Items.IRON_INGOT);
                else if(m==EnumMetals.GOLD)
                    ingot = of(Items.GOLD_INGOT);
                else if(m==EnumMetals.COPPER)
                    ingot = of(Items.COPPER_INGOT);
                else
                    throw new RuntimeException("Unkown vanilla metal: "+m.name());
                if(m.shouldAddNugget())
                    nugget = register("nugget_"+name, BaseItem::new);
                else if(m==EnumMetals.IRON)
                    nugget = of(Items.IRON_NUGGET);
                else if(m==EnumMetals.GOLD)
                    nugget = of(Items.GOLD_NUGGET);
                else
                    throw new RuntimeException("Unkown vanilla metal: "+m.name());
                if(m.shouldAddOre())
                    rawOre = register("raw_"+name, BaseItem::new);
                else if(m==EnumMetals.IRON)
                    rawOre = of(Items.RAW_IRON);
                else if(m==EnumMetals.GOLD)
                    rawOre = of(Items.RAW_GOLD);
                else if(m==EnumMetals.COPPER)
                    rawOre = of(Items.RAW_COPPER);
                NUGGETS.put(m, nugget);
                INGOTS.put(m, ingot);
                if(rawOre!=null)
                    RAW_ORES.put(m, rawOre);
                PLATES.put(m, simple("plate_"+name));
                DUSTS.put(m, simple("dust_"+name));
            }
        }
    }

    public static final class Tools
    {

        public static final ItemRegObject<PickaxeItem> STEEL_PICK = register(
                "pickaxe_steel", Tools.createPickaxe(Lib.MATERIAL_Steel)
        );
        public static final ItemRegObject<ShovelItem> STEEL_SHOVEL = register(
                "shovel_steel", Tools.createShovel(Lib.MATERIAL_Steel)
        );
        public static final ItemRegObject<AxeItem> STEEL_AXE = register(
                "axe_steel", Tools.createAxe(Lib.MATERIAL_Steel)
        );
        public static final ItemRegObject<HoeItem> STEEL_HOE = register(
                "hoe_steel", Tools.createHoe(Lib.MATERIAL_Steel)
        );
        public static final ItemRegObject<SwordItem> STEEL_SWORD = register(
                "sword_steel", Tools.createSword(Lib.MATERIAL_Steel)
        );

    }

    //TODO move all of these somewhere else
    public static final class Misc
    {
        public static final Map<StoneStick, ItemRegObject<StoneStickItem>> STONE_STICK = new LinkedHashMap<>();

        // We can't use an EnumMap here, since Rarity is an "extensible enum" (Forge), so people may add to it later on.
        // And since this map is created during static class init, it may be initialized before another mod has any
        // chance of adding the rarity.


        public static void registerShaderBags()
        {
            for(Rarity r : ShaderRegistry.rarityWeightMap.keySet())
                Items.Misc.SHADER_BAG.put(r, register(
                        "shader_bag_"+r.name().toLowerCase(Locale.US).replace(':', '_'), () -> new ShaderBagItem(r)
                ));
        }
    }

    public static void init()
    {
        REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Load all classes to make sure the static variables are initialized
        Ingredients.init();
        Metals.init();
        Tools.init();
        Misc.init();
    }

    private static <T> Consumer<T> nothing()
    {
        return $ -> {
        };
    }

    private static ItemRegObject<BaseItem> simpleWithStackSize(String name, int maxSize)
    {
        return simple(name, p -> p.stacksTo(maxSize), i -> {
        });
    }

    private static ItemRegObject<BaseItem> simple(String name)
    {
        return simple(name, $ -> {
        }, $ -> {
        });
    }

    private static ItemRegObject<BaseItem> simple(
            String name, Consumer<Properties> makeProps, Consumer<BaseItem> processItem
    )
    {
        return register(
                name, () -> Util.make(new BaseItem(Util.make(new Properties(), makeProps)), processItem)
        );
    }

    static <T extends Item> ItemRegObject<T> register(String name, Supplier<? extends T> make)
    {
        return new ItemRegObject<>(REGISTER.register(name, make));
    }

    private static <T extends Item> ItemRegObject<T> of(T existing)
    {
        return new ItemRegObject<>(RegistryObject.create(Registry.ITEM.getKey(existing), ForgeRegistries.ITEMS));
    }

    public record ItemRegObject<T extends Item>(RegistryObject<T> regObject) implements Supplier<T>, ItemLike
    {
        @Override
        @Nonnull
        public T get()
        {
            return regObject.get();
        }

        @Nonnull
        @Override
        public Item asItem()
        {
            return regObject.get();
        }

        public ResourceLocation getId()
        {
            return regObject.getId();
        }
    }
}
