package net.skydimondlox.idontknowmod;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.InputEvent.MouseScrollingEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.skydimondlox.idontknowmod.api.shader.CapabilityShader;
import net.skydimondlox.idontknowmod.util.EnergyHelper;
import net.skydimondlox.idontknowmod.util.IDKRenderTypes;
import net.skydimondlox.idontknowmod.util.ItemNBTHelper;
import net.skydimondlox.idontknowmod.util.TextUtils;
import net.skydimondlox.idontknowmod.api.Lib;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static net.skydimondlox.idontknowmod.api.IDKTags.rl;

public class ClientEventHandler implements ResourceManagerReloadListener
{
    private boolean shieldToggleButton = false;
    private int shieldToggleTimer = 0;

    @Override
    public void onResourceManagerReload(@Nonnull ResourceManager resourceManager)
    {
        idontknowmod.proxy.clearRenderCaches();
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(event.side==LogicalSide.CLIENT&&event.player!=null&&event.player==ClientUtils.mc().getCameraEntity())
        {
            if(event.phase==Phase.END)
            {
                if(this.shieldToggleTimer > 0)
                    this.shieldToggleTimer--;
                if(IDKKeybinds.keybind_magnetEquip.isDown()&&!this.shieldToggleButton)
                    if(this.shieldToggleTimer <= 0)
                        this.shieldToggleTimer = 7;
                    else
                    {
                        Player player = event.player;
                        ItemStack held = player.getItemInHand(InteractionHand.OFF_HAND);

                        else
                        {
                            for(int i = 0; i < player.getInventory().items.size(); i++)
                            {
                                ItemStack s = player.getInventory().items.get(i);

                            }
                        }
                    }
                if(this.shieldToggleButton!=ClientUtils.mc().options.keyDown.isDown())
                    this.shieldToggleButton = ClientUtils.mc().options.keyDown.isDown();


                if(!IDKKeybinds.keybind_chemthrowerSwitch.isUnbound()&&IDKKeybinds.keybind_chemthrowerSwitch.consumeClick())
                {
                    ItemStack held = event.player.getItemInHand(InteractionHand.MAIN_HAND);
                    if(held.getItem() instanceof IScrollwheel)
                        idontknowmod.packetHandler.sendToServer(new MessageScrollwheelItem(true));
                }

                if(!IDKKeybinds.keybind_railgunZoom.isUnbound()&&IDKKeybinds.keybind_railgunZoom.consumeClick())
                    for(InteractionHand hand : InteractionHand.values())
                    {
                        ItemStack held = event.player.getItemInHand(hand);
                        if(held.getItem() instanceof IZoomTool&&((IZoomTool)held.getItem()).canZoom(held, event.player))
                        {
                            ZoomHandler.isZooming = !ZoomHandler.isZooming;
                            if(ZoomHandler.isZooming)
                            {
                                float[] steps = ((IZoomTool)held.getItem()).getZoomSteps(held, event.player);
                                if(steps!=null&&steps.length > 0)
                                    ZoomHandler.fovZoom = steps[ZoomHandler.getCurrentZoomStep(steps)];
                            }
                        }
                    }
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        LevelStageRenders.FAILED_CONNECTIONS.entrySet().removeIf(entry -> entry.getValue().getSecond().decrementAndGet() <= 0);
        ClientLevel world = Minecraft.getInstance().level;
        if(world!=null)
            GlobalWireNetwork.getNetwork(world).update(world);
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event)
    {
        if(event.getItemStack().isEmpty())
            return;
        event.getItemStack().getCapability(CapabilityShader.SHADER_CAPABILITY).ifPresent(wrapper ->
        {
            ItemStack shader = wrapper.getShaderItem();
            if(!shader.isEmpty())
                event.getToolTip().add(TextUtils.applyFormat(
                        shader.getHoverName(),
                        ChatFormatting.DARK_GRAY
                ));
        });
        if(ItemNBTHelper.hasKey(event.getItemStack(), Lib.NBT_Earmuffs))
        {
            ItemStack earmuffs = ItemNBTHelper.getItemStack(event.getItemStack(), Lib.NBT_Earmuffs);
            if(!earmuffs.isEmpty())
                event.getToolTip().add(TextUtils.applyFormat(
                        earmuffs.getHoverName(),
                        ChatFormatting.GRAY
                ));
        }
        if(ItemNBTHelper.hasKey(event.getItemStack(), Lib.NBT_Powerpack))
        {
            ItemStack powerpack = ItemNBTHelper.getItemStack(event.getItemStack(), Lib.NBT_Powerpack);
            if(!powerpack.isEmpty())
            {
                List<Component> tooltip = event.getToolTip();
                // find gap
                int idx = IntStream.range(0,tooltip.size() ).filter(i -> tooltip.get(i) == CommonComponents.EMPTY).findFirst().orElse(tooltip.size()-1);
                // put tooltip in that gap
                tooltip.add(idx++, CommonComponents.EMPTY);
                tooltip.add(idx++, TextUtils.applyFormat(powerpack.getHoverName(), ChatFormatting.GRAY));
                tooltip.add(idx++, TextUtils.applyFormat(
                        Component.literal(EnergyHelper.getEnergyStored(powerpack)+"/"+ EnergyHelper.getMaxEnergyStored(powerpack)+" IF"),
                        ChatFormatting.GRAY
                ));
                tooltip.add(idx, TextUtils.applyFormat(Component.translatable("desc.immersiveengineering.info.noChargeOnArmor"), ChatFormatting.DARK_GRAY));
            }
        }
        Level clientLevel = ClientUtils.mc().level;
        if(ClientUtils.mc().screen!=null

            ));

        if(IDKClientConfig.tagTooltips.get()&&event.getFlags().isAdvanced())
            event.getItemStack().getItem().builtInRegistryHolder().tags()
                    .map(TagKey::location)
                    .forEach(oid ->
                            event.getToolTip().add(TextUtils.applyFormat(
                                    Component.literal(oid.toString()),
                                    ChatFormatting.GRAY
                            )));
    }

    @SubscribeEvent
    public void onPlaySound(PlaySoundEvent event)
    {
        if(event.getSound()==null)
            return;

    }

    @SubscribeEvent
    public void onRenderItemFrame(RenderItemInFrameEvent event)
    {

    }

    private static void handleSubtitleOffset(boolean pre)
    {
        float offset = 0;
        Player player = ClientUtils.mc().player;
        for(InteractionHand hand : InteractionHand.values())
            if(!player.getItemInHand(hand).isEmpty())
            {
                Item equipped = player.getItemInHand(hand).getItem();

            }
        if(offset!=0)
        {
            if(pre)
                offset *= -1;
            RenderSystem.getModelViewStack().translate(0, offset, 0);
            RenderSystem.applyModelViewMatrix();
        }
    }

    @SubscribeEvent
    public void onRenderOverlayPre(RenderGuiOverlayEvent.Pre event)
    {
        if(event.getOverlay().id().equals(VanillaGuiOverlay.SUBTITLES.id()))
            handleSubtitleOffset(true);
        if(ZoomHandler.isZooming&&event.getOverlay().id().equals(VanillaGuiOverlay.CROSSHAIR.id()))
        {
            event.setCanceled(true);
            if(ZoomHandler.isZooming)
            {
                MultiBufferSource.BufferSource buffers = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
                PoseStack transform = new PoseStack();
                transform.pushPose();
                int width = ClientUtils.mc().getWindow().getGuiScaledWidth();
                int height = ClientUtils.mc().getWindow().getGuiScaledHeight();
                int resMin = Math.min(width, height);
                float offsetX = (width-resMin)/2f;
                float offsetY = (height-resMin)/2f;

                if(resMin==width)
                {
                    GuiHelper.drawColouredRect(0, 0, width, (int)offsetY+1, 0xff000000, buffers, transform);
                    GuiHelper.drawColouredRect(0, (int)offsetY+resMin, width, (int)offsetY+1, 0xff000000, buffers, transform);
                }
                else
                {
                    GuiHelper.drawColouredRect(0, 0, (int)offsetX+1, height, 0xff000000, buffers, transform);
                    GuiHelper.drawColouredRect((int)offsetX+resMin, 0, (int)offsetX+1, height, 0xff000000, buffers, transform);
                }
                transform.translate(offsetX, offsetY, 0);
                VertexConsumer builder = buffers.getBuffer(IDKRenderTypes.getGuiTranslucent(rl("textures/gui/scope.png")));
                GuiHelper.drawTexturedColoredRect(builder, transform, 0, 0, resMin, resMin, 1, 1, 1, 1, 0f, 1f, 0f, 1f);

                builder = buffers.getBuffer(IDKRenderTypes.getGui(rl("textures/gui/hud_elements.png")));
                GuiHelper.drawTexturedColoredRect(builder, transform, 218/256f*resMin, 64/256f*resMin, 24/256f*resMin, 128/256f*resMin, 1, 1, 1, 1, 64/256f, 88/256f, 96/256f, 224/256f);
                ItemStack equipped = ClientUtils.mc().player.getItemInHand(InteractionHand.MAIN_HAND);
                if(!equipped.isEmpty()&&equipped.getItem() instanceof IZoomTool tool)
                {
                    float[] steps = tool.getZoomSteps(equipped, ClientUtils.mc().player);
                    if(steps!=null&&steps.length > 1)
                    {
                        int curStep = -1;
                        float dist = 0;

                        float totalOffset = 0;
                        float stepLength = 118/(float)steps.length;
                        float stepOffset = (stepLength-7)/2f;
                        transform.translate(223/256f*resMin, 64/256f*resMin, 0);
                        transform.translate(0, (5+stepOffset)/256*resMin, 0);
                        for(int i = 0; i < steps.length; i++)
                        {
                            GuiHelper.drawTexturedColoredRect(builder, transform, 0, 0, 8/256f*resMin, 7/256f*resMin, 1, 1, 1, 1, 88/256f, 96/256f, 96/256f, 103/256f);
                            transform.translate(0, stepLength/256*resMin, 0);
                            totalOffset += stepLength;

                            if(curStep==-1||Math.abs(steps[i]-ZoomHandler.fovZoom) < dist)
                            {
                                curStep = i;
                                dist = Math.abs(steps[i]-ZoomHandler.fovZoom);
                            }
                        }
                        transform.translate(0, -totalOffset/256*resMin, 0);

                        if(curStep < steps.length)
                        {
                            transform.translate(6/256f*resMin, curStep*stepLength/256*resMin, 0);
                            GuiHelper.drawTexturedColoredRect(builder, transform, 0, 0, 8/256f*resMin, 7/256f*resMin, 1, 1, 1, 1, 88/256f, 98/256f, 103/256f, 110/256f);
                            ClientUtils.font().drawInBatch((1/steps[curStep])+"x", (int)(16/256f*resMin), 0, 0xffffff, true,
                                    transform.last().pose(), buffers, DisplayMode.NORMAL, 0, 0xf000f0);
                            transform.translate(-6/256f*resMin, -curStep*stepLength/256*resMin, 0);
                        }
                        transform.translate(0, -((5+stepOffset)/256*resMin), 0);
                        transform.translate(-223/256f*resMin, -64/256f*resMin, 0);
                    }
                }

                transform.translate(-offsetX, -offsetY, 0);
                buffers.endBatch();
            }
        }
    }

    @SubscribeEvent()
    public void onRenderOverlayPost(RenderGuiOverlayEvent.Post event)
    {
        int scaledWidth = ClientUtils.mc().getWindow().getGuiScaledWidth();
        int scaledHeight = ClientUtils.mc().getWindow().getGuiScaledHeight();

        if(event.getOverlay().id().equals(VanillaGuiOverlay.SUBTITLES.id()))
            handleSubtitleOffset(false);
        int leftHeight;
        if(Minecraft.getInstance().gui instanceof ForgeGui forgeUI)
            leftHeight = forgeUI.leftHeight;
        else
            leftHeight = 0;
        if(ClientUtils.mc().player!=null&&event.getOverlay().id().equals(VanillaGuiOverlay.ITEM_NAME.id()))
        {
            Player player = ClientUtils.mc().player;
            GuiGraphics graphics = event.getGuiGraphics();
            PoseStack transform = graphics.pose();

            if(ClientUtils.mc().hitResult!=null)
            {
                ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);
                boolean hammer = !held.isEmpty()&&Utils.isHammer(held);
                HitResult mop = ClientUtils.mc().hitResult;
                if(mop instanceof EntityHitResult)
                {
                    Entity entity = ((EntityHitResult)mop).getEntity();
                    if(entity instanceof ItemFrame)
                        BlockOverlayUtils.renderOreveinMapOverlays(graphics, (ItemFrame)entity, mop, scaledWidth, scaledHeight);
                    else if(entity instanceof IDKMinecartEntity<?> ieMinecart&&ieMinecart.getContainedBlockEntity() instanceof IBlockOverlayText overlayText)
                    {
                        Component[] text = overlayText.getOverlayText(player, mop, false);
                        BlockOverlayUtils.drawBlockOverlayText(transform, text, scaledWidth, scaledHeight);
                    }
                }
                else if(mop instanceof BlockHitResult)
                {
                    BlockPos pos = ((BlockHitResult)mop).getBlockPos();
                    Direction face = ((BlockHitResult)mop).getDirection();
                    BlockEntity tileEntity = player.level().getBlockEntity(pos);
                    if(tileEntity instanceof IBlockOverlayText overlayBlock)
                    {
                        Component[] text = overlayBlock.getOverlayText(ClientUtils.mc().player, mop, hammer);
                        BlockOverlayUtils.drawBlockOverlayText(transform, text, scaledWidth, scaledHeight);
                    }
                }
            }
        }
    }

    @SubscribeEvent()
    public void onFogUpdate(ViewportEvent.RenderFog event)
    {
        if(event.getCamera().getEntity() instanceof LivingEntity living&&living.hasEffect(IDKPotions.FLASHED.get()))
        {
            MobEffectInstance effect = living.getEffect(IDKPotions.FLASHED.get());
            int timeLeft = effect.getDuration();
            float saturation = Math.max(0.25f, 1-timeLeft/(float)(80+40*effect.getAmplifier()));//Total Time =  4s + 2s per amplifier

            float f1 = -2.5f+15.0F*saturation;
            if(timeLeft < 20)
                f1 += (event.getFarPlaneDistance()/4)*(1-timeLeft/20f);

            RenderSystem.setShaderFogStart(0.25f*f1);
            RenderSystem.setShaderFogEnd(f1);
        }
    }

    @SubscribeEvent()
    public void onFogColourUpdate(ViewportEvent.ComputeFogColor event)
    {
        Entity e = event.getCamera().getEntity();
        if(e instanceof LivingEntity&&((LivingEntity)e).hasEffect(IDKPotions.FLASHED.get()))
        {
            event.setRed(1);
            event.setGreen(1);
            event.setBlue(1);
        }
    }

    @SubscribeEvent()
    public void onFOVUpdate(ComputeFovModifierEvent event)
    {
        Player player = ClientUtils.mc().player;

        // Check if player is holding a zoom-allowing item
        ItemStack equipped = player.getItemInHand(InteractionHand.MAIN_HAND);
        boolean mayZoom = equipped.getItem() instanceof IZoomTool&&((IZoomTool)equipped.getItem()).canZoom(equipped, player);
        // Set zoom if allowed, otherwise stop zooming
        if(ZoomHandler.isZooming)
        {
            if(mayZoom)
                event.setNewFovModifier(ZoomHandler.fovZoom);
            else
                ZoomHandler.isZooming = false;
        }

        // Concrete feet slow you, but shouldn't break FoV
        if(player.getEffect(IDKPotions.CONCRETE_FEET.get())!=null)
            event.setNewFovModifier(1);
    }

    @SubscribeEvent
    public void onMouseEvent(MouseScrollingEvent event)
    {
        Player player = ClientUtils.mc().player;
        if(event.getScrollDelta()!=0&&ClientUtils.mc().screen==null&&player!=null)
        {
            ItemStack equipped = player.getItemInHand(InteractionHand.MAIN_HAND);
            // Handle zoom steps
            if(equipped.getItem() instanceof IZoomTool&&((IZoomTool)equipped.getItem()).canZoom(equipped, player)&&ZoomHandler.isZooming)
            {
                float[] steps = ((IZoomTool)equipped.getItem()).getZoomSteps(equipped, player);
                if(steps!=null&&steps.length > 0)
                {
                    int curStep = ZoomHandler.getCurrentZoomStep(steps);
                    int newStep = curStep+(event.getScrollDelta() > 0?-1: 1);
                    if(newStep >= 0&&newStep < steps.length)
                        ZoomHandler.fovZoom = steps[newStep];
                    event.setCanceled(true);
                }
            }

            // Handle sneak + scrolling
            if(player.isShiftKeyDown())
            {
                if(IDKServerConfig.TOOLS.chemthrower_scroll.get()&&equipped.getItem() instanceof IScrollwheel)
                {
                    idontknowmod.packetHandler.sendToServer(new MessageScrollwheelItem(event.getScrollDelta() < 0));
                    event.setCanceled(true);
                }

            }
        }
    }

    @SubscribeEvent()
    public void onRenderLivingPre(RenderLivingEvent.Pre event)
    {
        if(event.getEntity().getPersistentData().contains("headshot"))
            enableHead(event.getRenderer(), false);
    }

    @SubscribeEvent()
    public void onRenderLivingPost(RenderLivingEvent.Post event)
    {
        if(event.getEntity().getPersistentData().contains("headshot"))
            enableHead(event.getRenderer(), true);
    }

    private static void enableHead(LivingEntityRenderer renderer, boolean shouldEnable)
    {
        EntityModel m = renderer.getModel();
        if(m instanceof HeadedModel)
            ((HeadedModel)m).getHead().visible = shouldEnable;
    }

    @SubscribeEvent
    public void onEntityJoiningWorld(EntityJoinLevelEvent event)
    {
        if(event.getEntity().level().isClientSide&&event.getEntity() instanceof AbstractMinecart&&
                event.getEntity().getCapability(CapabilityShader.SHADER_CAPABILITY).isPresent())
            idontknowmod.packetHandler.sendToServer(new MessageMinecartShaderSync(event.getEntity()));
    }
}
