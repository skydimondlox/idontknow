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
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.skydimondlox.idontknowmod.item.IDKTools;
import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class IDKItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Lib.MOD_ID);

    private IDKItems()
    {
    }

    public static final class Ingredients {
        public static final ItemRegObject<BaseItem> STICK_STONE = simple("stick_stone");


        private static void init() {
        }
    }

    public static final class Metals {
        public static final Map<EnumMetals, ItemRegObject<Item>> INGOTS = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, ItemRegObject<Item>> NUGGETS = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, ItemRegObject<Item>> RAW_ORES = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, ItemRegObject<BaseItem>> DUSTS = new EnumMap<>(EnumMetals.class);
        public static final Map<EnumMetals, ItemRegObject<BaseItem>> PLATES = new EnumMap<>(EnumMetals.class);

        private static void init() {
            for(EnumMetals m : EnumMetals.values()) {
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

    public static final class Tools {

        public static final ItemRegObject<PickaxeItem> STEEL_PICK = register(
                "pickaxe_steel", IDKTools.createPickaxe(Lib.MATERIAL_Steel)
        );
        public static final ItemRegObject<ShovelItem> STEEL_SHOVEL = register(
                "shovel_steel", IDKTools.createShovel(Lib.MATERIAL_Steel)
        );
        public static final ItemRegObject<AxeItem> STEEL_AXE = register(
                "axe_steel", IDKTools.createAxe(Lib.MATERIAL_Steel)
        );
        public static final ItemRegObject<HoeItem> STEEL_HOE = register(
                "hoe_steel", IDKTools.createHoe(Lib.MATERIAL_Steel)
        );
        public static final ItemRegObject<SwordItem> STEEL_SWORD = register(
                "sword_steel", IDKTools.createSword(Lib.MATERIAL_Steel)
        );

        private static void init() {
        }

    }

    public static final class Misc {
        public static final ItemRegObject<BaseItem> STICK_IRON = simple("iron_rod");
        public static final ItemRegObject<BaseItem> STICK_BRONZE = simple("bronze_rod");

        private static void init() {
        }
    }

    public static final class Ingots {
        public static final ItemRegObject<BaseItem> INGOT_STEEL = simple("steel_ingot");
        public static final ItemRegObject<BaseItem> INGOT_BRONZE = simple("bronze_ingot");
        public static final ItemRegObject<BaseItem> INGOT_TIN = simple("tin_ingot");
        public static final ItemRegObject<BaseItem> INGOT_ZINC = simple("zinc");

        private static void init() {
        }
    }

    public static final class Gears {
        public static final ItemRegObject<BaseItem> GEAR_IRON = simple("iron_gear");
        public static final ItemRegObject<BaseItem> GEAR_GOLD = simple("gold_gear");
        public static final ItemRegObject<BaseItem> GEAR_STONE = simple("stone_gear");
        public static final ItemRegObject<BaseItem> GEAR_BRONZE = simple("bronze_gear");
        public static final ItemRegObject<BaseItem> GEAR_COPPER = simple("copper_gear");
        public static final ItemRegObject<BaseItem> GEAR_DIAMOND = simple("diamond_gear");

        private static void init() {
        }
    }

    public static final class Raw {
        public static final ItemRegObject<BaseItem> RAW_TIN = simple("raw_tin");
        public static final ItemRegObject<BaseItem> RAW_ZINC = simple("raw_zinc");

        private static void init() {
        }
    }

    public static final class MachineFrame {
        public static final ItemRegObject<BaseItem> MACHINE_FRAME_BASIC = simple("machine_frame_basic");
        public static final ItemRegObject<BaseItem> MACHINE_FRAME_INTERMEDIATE = simple("machine_frame_intermediate");
        public static final ItemRegObject<BaseItem> MACHINE_FRAME_ADVANCED = simple("machine_frame_advanced");

        private static void init() {
        }
    }

    public static final class Plate {

        public static final ItemRegObject<BaseItem> IRON_PLATE = simple("iron_plate");
        public static final ItemRegObject<BaseItem> GOLD_PLATE = simple("gold_plate");

        private static void init() {
        }
    }

    public static final class IDontKnow {
        public static final ItemRegObject<BaseItem> I_DONT_KNOW = simple("i_dont_know");
        public static final ItemRegObject<BaseItem> ITS_SOMETHING = simple("its_something");
        public static final ItemRegObject<BaseItem> HOW_BOUT_THIS = simple("how_bout_this");

        private static void init() {
        }
    }

    public static void init() {
        REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Load all classes to make sure the static variables are initialized
        Ingredients.init();
        Ingots.init();
        Gears.init();
        Plate.init();
        Raw.init();
        MachineFrame.init();
        IDontKnow.init();
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
        return new ItemRegObject<>(RegistryObject.create(BuiltInRegistries.ITEM.getKey(existing), ForgeRegistries.ITEMS));
    }

    public record ItemRegObject<T extends Item>(RegistryObject<T> regObject) implements Supplier<T>, ItemLike {
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