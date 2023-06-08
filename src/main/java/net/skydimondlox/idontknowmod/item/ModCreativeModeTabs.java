package net.skydimondlox.idontknowmod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.skydimondlox.idontknowmod.idontknowmod;

@Mod.EventBusSubscriber(modid = idontknowmod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab IDONTKNOWTAB;

    @SubscribeEvent
    public static void  registerCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        IDONTKNOWTAB = event.registerCreativeModeTab(new ResourceLocation(idontknowmod.MOD_ID, "idontknowtab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.I_DONT_KNOW.get()))
                        .title(Component.translatable("creativemodetab.idontknowtab")));
    }
}
