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

    public static final RegistryObject<Item> PRESSEDIRON = ITEMS.register("pressed_iron",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PRESSEDGOLD = ITEMS.register("pressed_gold",
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


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }



}
