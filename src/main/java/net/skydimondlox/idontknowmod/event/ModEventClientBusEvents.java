package net.skydimondlox.idontknowmod.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.skydimondlox.idontknowmod.block.entity.ModBlockEntities;
import net.skydimondlox.idontknowmod.block.entity.renderer.AlloyFurnaceBlockEntityRenderer;
import net.skydimondlox.idontknowmod.block.entity.renderer.ElectricPressBlockEntityRenderer;
import net.skydimondlox.idontknowmod.idontknowmod;

@Mod.EventBusSubscriber(modid = idontknowmod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.ELECTRIC_PRESS_BE.get(),
                ElectricPressBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ALLOY_FURNACE_BE.get(),
                AlloyFurnaceBlockEntityRenderer::new);
    }

}