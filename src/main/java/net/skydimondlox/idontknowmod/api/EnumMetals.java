package net.skydimondlox.idontknowmod.api;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import java.util.Locale;

public enum EnumMetals
{
    COPPER(Type.VANILLA_NO_NUGGET, 0.3f),
    ALUMINUM(0.3F),
    LEAD(0.7F),
    NICKEL(1.0F),
    STEEL(Type.ALLOY, Float.NaN),
    IRON(Type.VANILLA, 0.7F),
    GOLD(Type.VANILLA, 1);

    private final Type type;
    public final float smeltingXP;

    EnumMetals(Type t, float xp)
    {
        this.type = t;
        this.smeltingXP = xp;
    }

    EnumMetals(float xp)
    {
        smeltingXP = xp;
        this.type = Type.PURE;
    }

    public boolean isVanillaMetal()
    {
        return type==Type.VANILLA||type==Type.VANILLA_NO_NUGGET;
    }

    public boolean isAlloy()
    {
        return type==Type.ALLOY;
    }

    public boolean shouldAddOre()
    {
        return !isVanillaMetal()&&!isAlloy();
    }

    public boolean shouldAddNugget()
    {
        return !isVanillaMetal()||type==Type.VANILLA_NO_NUGGET;
    }

    public String tagName()
    {
        return name().toLowerCase(Locale.US);
    }

    private enum Type
    {
        VANILLA,
        VANILLA_NO_NUGGET,
        PURE,
        ALLOY
    }
}
