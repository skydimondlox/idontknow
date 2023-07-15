package net.skydimondlox.idontknowmod.api.crafting.cache;

/*
 * BluSunrize
 * Copyright (c) 2022
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import net.skydimondlox.idontknowmod.api.crafting.IDKSerializableRecipe;

import java.util.List;

public interface IListRecipe
{
    List<? extends IDKSerializableRecipe> getSubRecipes();
}