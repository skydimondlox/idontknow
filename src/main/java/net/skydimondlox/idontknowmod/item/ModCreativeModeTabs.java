package net.skydimondlox.idontknowmod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.block.ModBlocks;
import net.skydimondlox.idontknowmod.idontknowmod;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, idontknowmod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> I_DONT_KNOW_TAB = CREATIVE_MODE_TABS.register("i_dont_know_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TIN_INGOT.get()))
                    .title(Component.translatable("creativetab.i_dont_know_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.TIN_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_TIN_ORE.get());
                        output.accept(ModItems.RAW_TIN.get());
                        output.accept(ModItems.TIN_INGOT.get());
                        output.accept(ModBlocks.TIN_BLOCK.get());
                        output.accept(ModBlocks.ZINC_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_ZINC_ORE.get());
                        output.accept(ModItems.RAW_ZINC.get());
                        output.accept(ModItems.ZINC.get());
                        output.accept(ModBlocks.ZINC_BLOCK.get());
                        output.accept(ModItems.BRONZE_NUGGET.get());
                        output.accept(ModItems.BRONZE_INGOT.get());
                        output.accept(ModBlocks.BRONZE_BLOCK.get());
                        output.accept(ModItems.STEEL_NUGGET.get());
                        output.accept(ModItems.STEEL_INGOT.get());
                        output.accept(ModItems.PRESSED_IRON.get());
                        output.accept(ModItems.PRESSED_GOLD.get());
                        output.accept(ModItems.STONE_STICK.get());
                        output.accept(ModBlocks.ALLOY_FURNACE.get());
                        output.accept(ModItems.MACHINE_FRAME_BASIC.get());
                        output.accept(ModItems.MACHINE_FRAME_INTERMEDIATE.get());
                        output.accept(ModItems.MACHINE_FRAME_ADVANCED.get());
                    }).build());

    public static void  register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
