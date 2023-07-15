package net.skydimondlox.idontknowmod.api.crafting;

/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import net.skydimondlox.idontknowmod.api.RecipeTypes;

public abstract class IDKSerializableRecipe implements Recipe<Container>
{
    public static final Lazy<ItemStack> LAZY_EMPTY = of(ItemStack.EMPTY);

    protected final Lazy<ItemStack> outputDummy;
    protected final RecipeType<?> type;
    protected final ResourceLocation id;

    protected <T extends Recipe<?>>
    IDKSerializableRecipe(Lazy<ItemStack> outputDummy, RecipeTypes.TypeWithClass<T> type, ResourceLocation id)
    {
        this.outputDummy = outputDummy;
        this.type = type.get();
        this.id = id;
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return getIDKSerializer().getIcon();
    }

    @Override
    public boolean matches(Container inv, Level worldIn)
    {
        return false;
    }

    @Override
    public ItemStack assemble(Container inv)
    {
        return this.outputDummy.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return false;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return getIDKSerializer();
    }

    protected abstract IDKRecipeSerializer<?> getIDKSerializer();

    @Override
    public RecipeType<?> getType()
    {
        return this.type;
    }

    public static Lazy<ItemStack> of(ItemStack value) {
        return Lazy.of(() -> value);
    }
}
