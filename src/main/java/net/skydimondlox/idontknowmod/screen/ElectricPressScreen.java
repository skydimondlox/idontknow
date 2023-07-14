package net.skydimondlox.idontknowmod.screen;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.screen.renderer.EnergyInfoArea;
import net.skydimondlox.idontknowmod.screen.renderer.InfoArea;
import javax.annotation.Nonnull;
import java.util.List;

public class ElectricPressScreen extends ContainerScreen<ElectricPressMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(idontknowmod.MOD_ID, "textures/gui/electric_press_gui.png");

    public ElectricPressScreen(ElectricPressMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, TEXTURE);
    }

    @Nonnull
    @Override
    protected List<InfoArea> makeInfoAreas() {
        return ImmutableList.of(
                new EnergyInfoArea(leftPos+158, topPos+22, menu.energyStorage)
        );
    }

}
