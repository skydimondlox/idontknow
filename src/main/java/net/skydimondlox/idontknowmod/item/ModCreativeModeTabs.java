package net.skydimondlox.idontknowmod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.idontknowmod;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.f_279569_,
            idontknowmod.MOD_ID);


    public static RegistryObject<CreativeModeTab> IDONTKNOWTAB = CREATIVE_MODE_TABS.register("tutorial_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.I_DONT_KNOW.get()))
                    .title(Component.translatable("creativemodetab.idontknowtab")).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
