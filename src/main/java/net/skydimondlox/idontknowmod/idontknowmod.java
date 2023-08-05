package net.skydimondlox.idontknowmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.skydimondlox.idontknowmod.api.API;
import net.skydimondlox.idontknowmod.block.ModBlocks;
import net.skydimondlox.idontknowmod.block.entity.ModBlockEntities;
import net.skydimondlox.idontknowmod.item.ModCreativeModeTabs;
import net.skydimondlox.idontknowmod.networking.ModMessages;
import net.skydimondlox.idontknowmod.recipe.ModRecipes;
import net.skydimondlox.idontknowmod.register.IDKBlocks.*;
import net.skydimondlox.idontknowmod.register.IDKItems.*;
import net.skydimondlox.idontknowmod.screen.AlloyFurnaceScreen;
import net.skydimondlox.idontknowmod.screen.ElectricPressScreen;
import net.skydimondlox.idontknowmod.screen.ModMenuTypes;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.function.Supplier;


@Mod(idontknowmod.MOD_ID)
public class idontknowmod {
    public static final String MOD_ID = "idkmod";
    public static final String VERSION = API.getCurrentVersion();
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final CommonProxy proxy = DistExecutor.safeRunForDist(bootstrapErrorToXCPInDev(() -> ClientProxy::new), bootstrapErrorToXCPInDev(() -> CommonProxy::new));

    public static final SimpleChannel packetHandler = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MOD_ID, "main"))
            .networkProtocolVersion(() -> VERSION)
            .serverAcceptedVersions(VERSION::equals)
            .clientAcceptedVersions(VERSION::equals)
            .simpleChannel();

    public static <T>
    Supplier<T> bootstrapErrorToXCPInDev(Supplier<T> in)
    {
        if(FMLLoader.isProduction())
            return in;
        return () -> {
            try
            {
                return in.get();
            } catch(BootstrapMethodError e)
            {
                throw new RuntimeException(e);
            }
        };
    }

    public idontknowmod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, bootstrapErrorToXCPInDev(() -> ClientProxy::modConstruction));
        ModCreativeModeTabs.register(modEventBus);

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

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(MOD_ID)
    {
        @Override
        @Nonnull
        public ItemStack makeIcon()
        {
            return new ItemStack(Ingredients.STICK_STONE.get());
        }
    };

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {

        }

        if(event.getTab() == ModCreativeModeTabs.IDONTKNOWTAB.get()) {
            //PRESSED
            event.accept(Plate.IRON_PLATE.get());
            event.accept(Plate.GOLD_PLATE.get());
            //GEARS
            event.accept(Gears.GEAR_STONE.get());
            event.accept(Gears.GEAR_IRON.get());
            event.accept(Gears.GEAR_GOLD.get());
            event.accept(Gears.GEAR_DIAMOND.get());
            event.accept(Gears.GEAR_COPPER.get());
            event.accept(Gears.GEAR_BRONZE.get());
            //MACHINES
            event.accept(Machines.ELECTRIC_PRESS);
            event.accept(Machines.ALLOY_FURNACE);
            //ZINC
            event.accept(ModBlocks.ZINC_ORE);
            event.accept(ModBlocks.DEEPSLATE_ZINC_ORE);
            event.accept(MetalBlocks.ZINC_BLOCK.get());
            event.accept(Raw.RAW_ZINC.get());
            event.accept(Ingots.INGOT_ZINC.get());
            //BRONZE
            event.accept(Ingots.INGOT_BRONZE.get());
            event.accept(Misc.STICK_BRONZE.get());
            event.accept(MetalBlocks.BRONZE_BLOCK.get());
            //TIN
            event.accept(ModBlocks.DEEPSLATE_TIN_ORE);
            event.accept(ModBlocks.TIN_ORE);
            event.accept(Ingots.INGOT_TIN.get());
            event.accept(Raw.RAW_TIN.get());
            event.accept(MetalBlocks.TIN_BLOCK.get());
            //MISC
            event.accept(Ingredients.STICK_STONE.get());
            event.accept(Misc.STICK_IRON.get());
            //MACHINE FRAMES
            event.accept(MachineFrame.MACHINE_FRAME_ADVANCED.get());
            event.accept(MachineFrame.MACHINE_FRAME_BASIC.get());
            event.accept(MachineFrame.MACHINE_FRAME_INTERMEDIATE.get());
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
