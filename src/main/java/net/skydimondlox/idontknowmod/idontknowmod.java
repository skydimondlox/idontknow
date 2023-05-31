package net.skydimondlox.idontknowmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
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
import net.skydimondlox.idontknowmod.recipe.ModRecipes;
import net.skydimondlox.idontknowmod.screen.ElectricPressScreen;
import net.skydimondlox.idontknowmod.screen.ModMenuTypes;
import net.skydimondlox.idontknowmod.worldgen.ModConfiguredFeatures;
import net.skydimondlox.idontknowmod.worldgen.ModPlacedFeatures;
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

        //ModConfiguredFeatures.register(modEventBus);
        //ModPlacedFeatures.register(modEventBus);

        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == CreativeModeTabs.INGREDIENTS) {

        }

        if(event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {

        }

        if(event.getTab() == ModCreativeModeTabs.IDONTKNOWTAB) {
            event.accept(ModItems.I_DONT_KNOW);
            event.accept(ModItems.ITS_SOMETHING);
            event.accept(ModBlocks.IDONTKNOWBLOCK);
            event.accept(ModBlocks.ITSABLOCK);
            event.accept(ModItems.HOW_BOUT_THIS);
            event.accept(ModBlocks.ITSANORE);
            event.accept(ModBlocks.DEEPSLATEITSANORE);
            event.accept(ModBlocks.PRESSEDIRONBLOCK);
            event.accept(ModItems.PRESSEDIRON);
            event.accept(ModItems.PRESSEDGOLD);
            event.accept(ModItems.STONE_GEAR);
            event.accept(ModItems.IRON_GEAR);
            event.accept(ModItems.GOLD_GEAR);
            event.accept(ModItems.DIAMOND_GEAR);
            event.accept(ModItems.COPPER_GEAR);
            event.accept(ModBlocks.PRESSEDGOLDBLOCK);
            event.accept(ModBlocks.ELECTRIC_PRESS);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.ELECTRIC_PRESS_MENU.get(), ElectricPressScreen::new);
        }
    }
}
