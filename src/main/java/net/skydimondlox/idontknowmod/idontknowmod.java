package net.skydimondlox.idontknowmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.skydimondlox.idontknowmod.block.ModBlocks;
import net.skydimondlox.idontknowmod.block.entity.ModBlockEntities;
import net.skydimondlox.idontknowmod.item.ModCreativeModeTabs;
import net.skydimondlox.idontknowmod.item.ModItems;
import net.skydimondlox.idontknowmod.networking.ModMessages;
import net.skydimondlox.idontknowmod.recipe.ModRecipes;
import net.skydimondlox.idontknowmod.screen.AlloyFurnaceScreen;
import net.skydimondlox.idontknowmod.screen.ElectricPressScreen;
import net.skydimondlox.idontknowmod.screen.ModMenuTypes;
import org.slf4j.Logger;

@Mod(idontknowmod.MOD_ID)
public class idontknowmod {
    public static final String MOD_ID = "idkmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public idontknowmod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

        if(event.getTab() == ModCreativeModeTabs.IDONTKNOWTAB) {
            //PRESSED
            event.accept(ModBlocks.PRESSED_GOLD_BLOCK);
            event.accept(ModBlocks.PRESSED_IRON_BLOCK);
            event.accept(ModItems.PRESSED_IRON);
            event.accept(ModItems.PRESSED_GOLD);
            //GEARS
            event.accept(ModItems.STONE_GEAR);
            event.accept(ModItems.IRON_GEAR);
            event.accept(ModItems.GOLD_GEAR);
            event.accept(ModItems.DIAMOND_GEAR);
            event.accept(ModItems.COPPER_GEAR);
            event.accept(ModItems.BRONZE_GEAR);
            //MACHINES
            event.accept(ModBlocks.ELECTRIC_PRESS);
            event.accept(ModBlocks.ALLOY_FURNACE);
            //ZINC
            event.accept(ModBlocks.ZINC_ORE);
            event.accept(ModBlocks.DEEPSLATE_ZINC_ORE);
            event.accept(ModBlocks.ZINC_BLOCK);
            event.accept(ModItems.RAW_ZINC);
            event.accept(ModItems.ZINC);
            //BRONZE
            event.accept(ModItems.BRONZE_INGOT);
            event.accept(ModBlocks.BRONZE_BLOCK);
            //TIN
            event.accept(ModBlocks.DEEPSLATE_TIN_ORE);
            event.accept(ModBlocks.TIN_ORE);
            event.accept(ModItems.TIN_INGOT);
            event.accept(ModItems.RAW_TIN);
            event.accept(ModBlocks.TIN_BLOCK);
            //MISC
            event.accept(ModItems.STONE_STICK);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.ELECTRIC_PRESS_MENU.get(), ElectricPressScreen::new);
            MenuScreens.register(ModMenuTypes.ALLOY_FURNACE_MENU.get(), AlloyFurnaceScreen::new);
        }
    }
}
