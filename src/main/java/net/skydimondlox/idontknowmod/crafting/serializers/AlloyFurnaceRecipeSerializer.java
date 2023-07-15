package net.skydimondlox.idontknowmod.crafting.serializers;

/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.ICondition.IContext;
import net.minecraftforge.common.util.Lazy;
import net.skydimondlox.idontknowmod.api.crafting.AlloyFurnaceRecipe;
import net.skydimondlox.idontknowmod.api.crafting.IDKRecipeSerializer;
import net.skydimondlox.idontknowmod.api.crafting.IngredientWithSize;

import javax.annotation.Nullable;

public class AlloyFurnaceRecipeSerializer extends IDKRecipeSerializer<AlloyFurnaceRecipe>
{
    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(Blocks.ACACIA_PLANKS);
    }

    @Override
    public AlloyFurnaceRecipe readFromJson(ResourceLocation recipeId, JsonObject json, IContext context)
    {
        Lazy<ItemStack> output = readOutput(json.get("result"));
        IngredientWithSize input0 = IngredientWithSize.deserialize(json.get("input0"));
        IngredientWithSize input1 = IngredientWithSize.deserialize(json.get("input1"));
        int time = GsonHelper.getAsInt(json, "time", 200);
        return new AlloyFurnaceRecipe(recipeId, output, input0, input1, time);
    }

    @Nullable
    @Override
    public AlloyFurnaceRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
    {
        Lazy<ItemStack> output = readLazyStack(buffer);
        IngredientWithSize input0 = IngredientWithSize.read(buffer);
        IngredientWithSize input1 = IngredientWithSize.read(buffer);
        int time = buffer.readInt();
        return new AlloyFurnaceRecipe(recipeId, output, input0, input1, time);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, AlloyFurnaceRecipe recipe)
    {
        writeLazyStack(buffer, recipe.output);
        recipe.input0.write(buffer);
        recipe.input1.write(buffer);
        buffer.writeInt(recipe.time);
    }
}