package net.skydimondlox.idontknowmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.idontknowmod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, idontknowmod.MOD_ID);

    public static final RegistryObject<Item> I_DONT_KNOW = ITEMS.register("idontknow",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ITS_SOMETHING = ITEMS.register("itssomething",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HOW_BOUT_THIS = ITEMS.register("howboutthis",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PRESSED_IRON = ITEMS.register("pressed_iron",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PRESSED_GOLD = ITEMS.register("pressed_gold",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GOLD_GEAR = ITEMS.register("gold_gear",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> IRON_GEAR = ITEMS.register("iron_gear",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DIAMOND_GEAR = ITEMS.register("diamond_gear",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STONE_GEAR = ITEMS.register("stone_gear",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COPPER_GEAR = ITEMS.register("copper_gear",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_ZINC = ITEMS.register("raw_zinc",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ZINC = ITEMS.register("zinc",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BRONZE_GEAR = ITEMS.register("bronze_gear",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STONE_STICK = ITEMS.register("stone_stick",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MACHINE_FRAME_BASIC = ITEMS.register("machine_frame_basic",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MACHINE_FRAME_INTERMEDIATE = ITEMS.register("machine_frame_intermediate",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MACHINE_FRAME_ADVANCED = ITEMS.register("machine_frame_advanced",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }



}
