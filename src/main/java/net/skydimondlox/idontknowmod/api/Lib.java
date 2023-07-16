/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package net.skydimondlox.idontknowmod.api;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class Lib {

    public static final String MOD_ID = "idkmod";

    public static final String CHAT = "chat."+MOD_ID+".";
    public static final String CHAT_WARN = CHAT+"warning.";
    public static final String CHAT_INFO = CHAT+"info.";
    public static final String CHAT_COMMAND = CHAT+"command.";

    public static final String DESC = "desc."+MOD_ID+".";
    public static final String DESC_INFO = DESC+"info.";
    public static final String DESC_FLAVOUR = DESC+"flavour.";

    public static final String GUI = "gui."+MOD_ID+".";
    public static final String GUI_CONFIG = "gui."+MOD_ID+".config.";

    //GUI IDs

    public static final String GUIID_ElectricPress = "electric_press";
    public static final String GUIID_AlloyFurnace = "alloy_furnace";

    public static final Tier MATERIAL_Steel = new Tier()
    {
        @Override
        public int getUses()
        {
            return 641;
        }

        @Override
        public float getSpeed()
        {
            return 7F;
        }

        @Override
        public float getAttackDamageBonus()
        {
            return 3F;
        }

        @Override
        public int getLevel()
        {
            return 2;
        }

        @Override
        public int getEnchantmentValue()
        {
            return 10;
        }

        @Override
        public Ingredient getRepairIngredient()
        {
            return Ingredient.of(IDKTags.getTagsFor(EnumMetals.STEEL).ingot);
        }
    };

}
