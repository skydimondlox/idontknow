package net.skydimondlox.idontknowmod.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.skydimondlox.idontknowmod.screen.renderer.ITooltipWidget;
import net.skydimondlox.idontknowmod.screen.renderer.InfoArea;
import net.skydimondlox.idontknowmod.util.ResettableLazy;
import net.skydimondlox.idontknowmod.util.TextUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */


public abstract class ContainerScreen<C extends AbstractContainerMenu> extends AbstractContainerScreen<C>
{
    private final ResettableLazy<List<InfoArea>> infoAreas;
    protected final ResourceLocation background;

    public ContainerScreen(C inventorySlotsIn, Inventory inv, Component title, ResourceLocation background) {
        super(inventorySlotsIn, inv, title);
        this.background = background;
        this.infoAreas = new ResettableLazy<>(this::makeInfoAreas);
        this.inventoryLabelY = this.imageHeight-91;
    }

    @Override
    protected void init() {
        super.init();
        this.infoAreas.reset();
    }

    @Nonnull
    protected List<InfoArea> makeInfoAreas() {
        return ImmutableList.of();
    }

    @Override
    protected void renderLabels(PoseStack transform, int mouseX, int mouseY) {
        // Only difference to super version is the text color
        final int color = 0x190b06;
        this.font.draw(transform, title, titleLabelX, titleLabelY, color);
        this.font.draw(transform, playerInventoryTitle, inventoryLabelX, inventoryLabelY, color);
    }

    @Override
    public void render(@Nonnull GuiGraphics transform, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(transform);
        super.render(transform, mouseX, mouseY, partialTicks);
        List<Component> tooltip = new ArrayList<>();
        for (InfoArea area : infoAreas.get())
            area.fillTooltip(mouseX, mouseY, tooltip);
        for (GuiEventListener w : children())
            if (w.isMouseOver(mouseX, mouseY) && w instanceof ITooltipWidget ttw)
                ttw.gatherTooltip(mouseX, mouseY, tooltip);
        gatherAdditionalTooltips(
                mouseX, mouseY, tooltip::add, t -> tooltip.add(TextUtils.applyFormat(t, ChatFormatting.GRAY))
        );
        if (!tooltip.isEmpty())
            renderTooltip(transform, tooltip, Optional.empty(), mouseX, mouseY);
        else
            this.renderTooltip(transform, mouseX, mouseY);
    }

    protected boolean isMouseIn(int mouseX, int mouseY, int x, int y, int w, int h) {
        return mouseX >= leftPos+x&&mouseY >= topPos+y
                &&mouseX < leftPos+x+w&&mouseY < topPos+y+h;
    }

    public void fullInit() {
        super.init(minecraft, width, height);
    }

    @Override
    protected final void renderBg(@Nonnull PoseStack transform, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, background);
        drawBackgroundTexture(transform);
        drawContainerBackgroundPre(transform, partialTicks, x, y);
        for(InfoArea area : infoAreas.get())
            area.draw(transform);
    }

    protected void drawBackgroundTexture(PoseStack transform) {
        blit(transform, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    protected void drawContainerBackgroundPre(@Nonnull PoseStack matrixStack, float partialTicks, int x, int y) {
    }

    protected void gatherAdditionalTooltips(
            int mouseX, int mouseY, Consumer<Component> addLine, Consumer<Component> addGray
    ) {

    }

    public static ResourceLocation makeTextureLocation(String name) {
    }

    protected void sendUpdateToServer(CompoundTag message) {
    }
}