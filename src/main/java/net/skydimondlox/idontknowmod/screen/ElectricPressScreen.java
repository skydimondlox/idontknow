package net.skydimondlox.idontknowmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.screen.renderer.EnergyInfoArea;
import net.skydimondlox.idontknowmod.util.MouseUtil;

import java.util.Optional;

public class ElectricPressScreen extends AbstractContainerScreen<ElectricPressMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(idontknowmod.MOD_ID, "textures/gui/electric_press_gui.png");
    private EnergyInfoArea energyInfoArea;

    public ElectricPressScreen(ElectricPressMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 156, y + 13, menu.blockEntity.getEnergyStorage());
    }

    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(guiGraphics, pMouseX, pMouseY, x, y);

    }

    private void renderEnergyAreaTooltips(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 156, 13, 8, 64)) {
            renderTooltip(guiGraphics, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(guiGraphics, x, y, 0, 0, imageWidth, imageHeight);
        renderProgressArrow(guiGraphics, x, y);
        energyInfoArea.draw(guiGraphics);
    }
//hi
    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            blit(guiGraphics, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(guiGraphics, pMouseX, pMouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
