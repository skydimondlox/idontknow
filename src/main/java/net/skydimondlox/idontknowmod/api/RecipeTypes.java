package net.skydimondlox.idontknowmod.api;

/*
 * BluSunrize
 * Copyright (c) 2022
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.api.crafting.AlloyFurnaceRecipe;
import net.skydimondlox.idontknowmod.api.crafting.ElectricPressRecipe;

import java.util.function.Supplier;

public class RecipeTypes
{
    private static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(
            Registry.RECIPE_TYPE_REGISTRY, Lib.MOD_ID
    );
    public static final TypeWithClass<AlloyFurnaceRecipe> ALLOY_FURNACE = register("alloy_furnace", AlloyFurnaceRecipe.class);
    public static final TypeWithClass<ElectricPressRecipe> ELECTRIC_PRESS = register("electric_press", ElectricPressRecipe.class);

    private static <T extends Recipe<?>>
    TypeWithClass<T> register(String name, Class<T> type)
    {
        RegistryObject<RecipeType<T>> regObj = REGISTER.register(name, () -> new RecipeType<>()
        {
        });
        return new TypeWithClass<>(regObj, type);
    }

    public static void init()
    {
        REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public record TypeWithClass<T extends Recipe<?>>(
            RegistryObject<RecipeType<T>> type, Class<T> recipeClass
    ) implements Supplier<RecipeType<T>>
    {
        public RecipeType<T> get()
        {
            return type.get();
        }
    }
}
