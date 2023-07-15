package net.skydimondlox.idontknowmod.api.crafting;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.api.RecipeTypes;
import net.skydimondlox.idontknowmod.api.crafting.cache.CachedRecipeList;

import javax.annotation.Nullable;

public class AlloyFurnaceRecipe extends IDKSerializableRecipe {

    public static RegistryObject<IDKRecipeSerializer<AlloyFurnaceRecipe>> SERIALIZER;
    public static final CachedRecipeList<AlloyFurnaceRecipe> RECIPES = new CachedRecipeList<>(RecipeTypes.ALLOY_FURNACE);

    public final IngredientWithSize input0;
    public final IngredientWithSize input1;
    public final Lazy<ItemStack> output;
    public final int time;

    public AlloyFurnaceRecipe(ResourceLocation id, Lazy<ItemStack> output, IngredientWithSize input0, IngredientWithSize input1, int time)
    {
        super(output, RecipeTypes.ALLOY_FURNACE, id);
        this.output = output;
        this.input0 = input0;
        this.input1 = input1;
        this.time = time;
    }

    @Override
    protected IDKRecipeSerializer<AlloyFurnaceRecipe> getSerializer()
    {
        return SERIALIZER.get();
    }

    @Override
    public ItemStack getResultItem()
    {
        return this.output.get();
    }

    public boolean matches(ItemStack input0, ItemStack input1) {
        if (this.input0.test(input0)&&this.input1.test(input1))
            return true;
        else if (this.input0.test(input1)&&this.input1.test(input0))
            return true;
        else
            return false;
    }

    public static AlloyFurnaceRecipe findRecipe(
            Level level, ItemStack input0, ItemStack input1, @Nullable AlloyFurnaceRecipe hint
    )
    {
        if (input0.isEmpty() || input1.isEmpty())
            return null;
        if (hint != null && hint.matches(input0, input1))
            return hint;
        for(AlloyFurnaceRecipe recipe : RECIPES.getRecipes(level))
            if(recipe.matches(input0, input1))
                return recipe;
        return null;
    }
}