package net.skydimondlox.idontknowmod.screen.renderer;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */

import net.minecraft.network.chat.Component;

import java.util.List;

public interface ITooltipWidget
{
    void gatherTooltip(int mouseX, int mouseY, List<Component> tooltip);
}