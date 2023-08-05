package net.skydimondlox.idontknowmod;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.api.ManualHelper;
import blusunrize.immersiveengineering.api.client.ieobj.DefaultCallback;
import blusunrize.immersiveengineering.api.client.ieobj.IEOBJCallbacks;
import blusunrize.immersiveengineering.api.client.ieobj.ItemCallback;
import blusunrize.immersiveengineering.api.shader.ShaderCase;
import blusunrize.immersiveengineering.api.shader.ShaderLayer;
import blusunrize.immersiveengineering.api.shader.ShaderRegistry;
import blusunrize.immersiveengineering.api.tool.conveyor.ConveyorHandler;
import blusunrize.immersiveengineering.api.utils.SetRestrictedField;
import blusunrize.immersiveengineering.client.gui.*;
import blusunrize.immersiveengineering.client.manual.ManualElementBlueprint;
import blusunrize.immersiveengineering.client.manual.ManualElementMultiblock;
import blusunrize.immersiveengineering.client.models.*;
import blusunrize.immersiveengineering.client.models.ModelConveyor.ConveyorLoader;
import blusunrize.immersiveengineering.client.models.ModelCoresample.CoresampleLoader;
import blusunrize.immersiveengineering.client.models.PotionBucketModel.Loader;
import blusunrize.immersiveengineering.client.models.connection.FeedthroughLoader;
import blusunrize.immersiveengineering.client.models.connection.FeedthroughModel;
import blusunrize.immersiveengineering.client.models.mirror.MirroredModelLoader;
import blusunrize.immersiveengineering.client.models.obj.IEOBJLoader;
import blusunrize.immersiveengineering.client.models.obj.callback.DynamicSubmodelCallbacks;
import blusunrize.immersiveengineering.client.models.obj.callback.block.*;
import blusunrize.immersiveengineering.client.models.obj.callback.item.*;
import blusunrize.immersiveengineering.client.models.split.SplitModelLoader;
import blusunrize.immersiveengineering.client.render.ConnectionRenderer;
import blusunrize.immersiveengineering.client.render.IEBipedLayerRenderer;
import blusunrize.immersiveengineering.client.render.IEOBJItemRenderer;
import blusunrize.immersiveengineering.client.render.conveyor.RedstoneConveyorRender;
import blusunrize.immersiveengineering.client.render.conveyor.SplitConveyorRender;
import blusunrize.immersiveengineering.client.render.entity.*;
import blusunrize.immersiveengineering.client.render.tile.*;
import blusunrize.immersiveengineering.client.render.tooltip.RevolverClientTooltip;
import blusunrize.immersiveengineering.client.render.tooltip.RevolverServerTooltip;
import blusunrize.immersiveengineering.client.utils.BasicClientProperties;
import blusunrize.immersiveengineering.client.utils.VertexBufferHolder;
import blusunrize.immersiveengineering.common.CommonProxy;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.ISoundBE;
import blusunrize.immersiveengineering.common.blocks.metal.ConnectorProbeBlockEntity;
import blusunrize.immersiveengineering.common.blocks.metal.ConnectorRedstoneBlockEntity;
import blusunrize.immersiveengineering.common.blocks.metal.conveyors.ConveyorBase;
import blusunrize.immersiveengineering.common.blocks.metal.conveyors.DropConveyor;
import blusunrize.immersiveengineering.common.blocks.metal.conveyors.SplitConveyor;
import blusunrize.immersiveengineering.common.blocks.metal.conveyors.VerticalConveyor;
import blusunrize.immersiveengineering.common.config.IEClientConfig;
import blusunrize.immersiveengineering.common.entities.SkylineHookEntity;
import blusunrize.immersiveengineering.common.gui.IEBaseContainerOld;
import blusunrize.immersiveengineering.common.items.GrindingDiskItem;
import blusunrize.immersiveengineering.common.items.RockcutterItem;
import blusunrize.immersiveengineering.common.register.IEBannerPatterns;
import blusunrize.immersiveengineering.common.register.IEBlockEntities;
import blusunrize.immersiveengineering.common.register.IEEntityTypes;
import blusunrize.immersiveengineering.common.register.IEMenuTypes;
import blusunrize.immersiveengineering.common.register.IEMenuTypes.BEContainer;
import blusunrize.immersiveengineering.common.util.IELogger;
import blusunrize.immersiveengineering.common.util.sound.IEBlockEntitySound;
import blusunrize.immersiveengineering.common.util.sound.SkyhookSound;
import blusunrize.lib.manual.gui.ManualScreen;
import blusunrize.lib.manual.utils.ManualRecipeRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.skydimondlox.idontknowmod.api.API;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.api.client.idkobj.IDKOBJCallbacks;
import net.skydimondlox.idontknowmod.gui.IDKBaseContainerOld;
import net.skydimondlox.idontknowmod.register.IDKMenuTypes;
import net.skydimondlox.idontknowmod.screen.AlloyFurnaceScreen;
import net.skydimondlox.idontknowmod.screen.ElectricPressScreen;
import net.skydimondlox.idontknowmod.util.IDKLogger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static net.skydimondlox.idontknowmod.api.IDKTags.rl;
import static net.skydimondlox.idontknowmod.idontknowmod..MODID;
import static net.skydimondlox.idontknowmod.ClientUtils.mc;
import static net.skydimondlox.idontknowmod.idontknowmod..rl;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MOD_ID, bus = Bus.MOD)
public class ClientProxy extends CommonProxy
{
    public static void modConstruction()
    {
        IDKOBJCallbacks.register(rl("default"), DefaultCallback.INSTANCE);
        IDKOBJCallbacks.register(rl("electric_press"), ElectricPressCallbacks.INSTANCE);
        IDKOBJCallbacks.register(rl("alloy_furnace"), AlloyFurnaceCallbacks.INSTANCE);

        IDKOBJCallbacks.register(rl("submodel"), DynamicSubmodelCallbacks.INSTANCE);

        // Apparently this runs in data generation runs... but registering model loaders causes NPEs there
        if(Minecraft.getInstance()!=null)
            initWithMC();
    }

    public static void initWithMC()
    {
        populateAPI();

        ClientEventHandler handler = new ClientEventHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        ReloadableResourceManager reloadableManager = (ReloadableResourceManager)mc().getResourceManager();
        reloadableManager.registerReloadListener(handler);
        reloadableManager.registerReloadListener(new ConnectionRenderer());
    }

    @SubscribeEvent
    public static void registerTooltips(RegisterClientTooltipComponentFactoriesEvent ev)
    {

    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelEvent.RegisterGeometryLoaders ev)
    {
        ev.register(IDKOBJLoader.LOADER_NAME.getPath(), IDKOBJLoader.instance);
        ev.register(ModelConfigurableSides.Loader.NAME.getPath(), new ModelConfigurableSides.Loader());

        BasicClientProperties.initModels();
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent ev)
    {
        if(IDKClientConfig.stencilBufferEnabled.get())
            ev.enqueueWork(() -> Minecraft.getInstance().getMainRenderTarget().enableStencil());
        registerContainersAndScreens();
        ShaderHelper.initShaders();
        IDKDefaultColourHandlers.register();

        IDKManual.addIEManualEntries();
        IDKBannerPatterns.ALL_BANNERS.forEach(entry -> {
            ResourceKey<BannerPattern> pattern = Objects.requireNonNull(entry.pattern().getKey());
            Sheets.BANNER_MATERIALS.put(pattern, new Material(Sheets.BANNER_SHEET, BannerPattern.location(pattern, true)));
            Sheets.SHIELD_MATERIALS.put(pattern, new Material(Sheets.SHIELD_SHEET, BannerPattern.location(pattern, false)));
        });
        ev.enqueueWork(OptifineWarning::warnIfRequired);
    }

    private static <T extends Entity, T2 extends T> void registerEntityRenderingHandler(
            EntityRenderersEvent.RegisterRenderers ev, Supplier<EntityType<T2>> type, EntityRendererProvider<T> renderer
    )
    {
        ev.registerEntityRenderer(type.get(), renderer);
    }

    //TODO are these here rather than in ClientEventHandler for any particular reason???
    @SubscribeEvent
    public static void textureStichPre(TextureStitchEvent.Pre event)
    {
        ResourceLocation sheet = event.getAtlas().location();
        if(sheet.equals(Sheets.BANNER_SHEET)||sheet.equals(Sheets.SHIELD_SHEET))
            IDKBannerPatterns.ALL_BANNERS.forEach(entry -> {
                ResourceKey<BannerPattern> pattern = Objects.requireNonNull(entry.pattern().getKey());
                event.addSprite(BannerPattern.location(pattern, sheet.equals(Sheets.BANNER_SHEET)));
            });

        if(!sheet.equals(InventoryMenu.BLOCK_ATLAS))
            return;
        for(ShaderRegistry.ShaderRegistryEntry entry : ShaderRegistry.shaderRegistry.values())
            for(ShaderCase sCase : entry.getCases())
                if(sCase.stitchIntoSheet())
                    for(ShaderLayer layer : sCase.getLayers())
                        if(layer.getTexture()!=null)
                            event.addSprite(layer.getTexture());

    }

    @SubscribeEvent
    public static void textureStichPost(TextureStitchEvent.Post event)
    {
        if(!event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS))
            return;
        idontknowmod.proxy.clearRenderCaches();
    }

    private final Map<BlockPos, IDKBlockEntitySound> tileSoundMap = new HashMap<>();

    @Override
    public void handleTileSound(
            Supplier<SoundEvent> soundEvent, BlockEntity tile, boolean tileActive, float volume, float pitch
    )
    {
        BlockPos pos = tile.getBlockPos();
        IDKBlockEntitySound sound = tileSoundMap.get(pos);
        if((sound==null||!soundEvent.get().getLocation().equals(sound.getLocation()))&&tileActive)
        {
            if(sound!=null)
                stopTileSound(null, tile);
            if(tile instanceof ISoundBE&&mc().player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > ((ISoundBE)tile).getSoundRadiusSq())
                return;
            sound = ClientUtils.generatePositionedIESound(soundEvent.get(), volume, pitch, true, 0, pos);
            tileSoundMap.put(pos, sound);
        }
        else if(sound!=null&&(sound.donePlaying||!tileActive))
        {
            stopTileSound(null, tile);
        }
    }

    @Override
    public void stopTileSound(String soundName, BlockEntity tile)
    {
        IDKBlockEntitySound sound = tileSoundMap.get(tile.getBlockPos());
        if(sound!=null)
        {
            sound.donePlaying = true;
            mc().getSoundManager().stop(sound);
            tileSoundMap.remove(tile.getBlockPos());
        }
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.AddLayers ev)
    {
        for(EntityRenderer<?> render : Minecraft.getInstance().getEntityRenderDispatcher().renderers.values())
        {
            if(render instanceof HumanoidMobRenderer<?, ?> hmr)
                addIELayer(hmr, ev.getEntityModels());
            else if(render instanceof ArmorStandRenderer asr)
                addIELayer(asr, ev.getEntityModels());
        }
        for(String skin : ev.getSkins())
        {
            LivingEntityRenderer<?, ?> render = ev.getSkin(skin);
            if(render!=null)
                addIELayer(render, ev.getEntityModels());
        }
        ShaderMinecartRenderer.overrideMinecartModels();
    }

    private static <T extends LivingEntity, M extends EntityModel<T>>
    void addIELayer(LivingEntityRenderer<T, M> render, EntityModelSet models)
    {
        render.addLayer(new IDKBipedLayerRenderer<>(render, models));
    }

    @Override
    public Level getClientWorld()
    {
        return mc().level;
    }

    @Override
    public Player getClientPlayer()
    {
        return mc().player;
    }

    @Override
    public void reInitGui()
    {
        Screen currentScreen = mc().screen;
        if(currentScreen instanceof IDKContainerScreen)
            currentScreen.init(mc(), currentScreen.width, currentScreen.height);
    }

    @Override
    public void resetManual()
    {
        if(mc().screen instanceof ManualScreen)
            mc().setScreen(null);
        if(ManualHelper.getManual()!=null)
            ManualHelper.getManual().reset();
    }

    static
    {
        API.renderCacheClearers.add(AlloyFurnaceRenderer::reset);
        API.renderCacheClearers.add(ElectricPressRenderer::reset);
    }

    @Override
    public void clearRenderCaches()
    {
        for(Runnable r : API.renderCacheClearers)
            r.run();
    }

    @Override
    public void openManual()
    {
        Minecraft.getInstance().setScreen(ManualHelper.getManual().getGui());
    }

    @Override
    public void openTileScreen(String guiId, BlockEntity tileEntity)
    {
        if(guiId.equals(Lib.GUIID_ElectricPress)&&tileEntity instanceof ConnectorRedstoneBlockEntity)
            Minecraft.getInstance().setScreen(new RedstoneConnectorScreen((ConnectorRedstoneBlockEntity)tileEntity, tileEntity.getBlockState().getBlock().getName()));

        if(guiId.equals(Lib.GUIID_AlloyFurnace)&&tileEntity instanceof ConnectorProbeBlockEntity)
            Minecraft.getInstance().setScreen(new RedstoneProbeScreen((ConnectorProbeBlockEntity)tileEntity, tileEntity.getBlockState().getBlock().getName()));
    }


    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers event)
    {
        registerBERenders(event);
        registerEntityRenders(event);
    }

    private static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event)
    {

    }

    private static void registerContainersAndScreens()
    {
        MenuScreens.register(IDKMenuTypes.ELECTRIC_PRESS.getType(), ElectricPressScreen::new);
        MenuScreens.register(IDKMenuTypes.ALLOY_FURNACE.getType(), AlloyFurnaceScreen::new);
    }

    private static <T extends BlockEntity>
    void registerBERenderNoContext(
            RegisterRenderers event, BlockEntityType<? extends T> type, Supplier<BlockEntityRenderer<T>> render
    )
    {
        event.registerBlockEntityRenderer(type, $ -> render.get());
    }

    public static void registerBERenders(RegisterRenderers event)
    {
        registerBERenderNoContext(event, IDKBlockEntities.CHARGING_STATION.get(), ChargingStationRenderer::new);
        registerBERenderNoContext(event, IDKBlockEntities.SAMPLE_DRILL.master(), SampleDrillRenderer::new);

    }

    public static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>>
    void registerScreen(IDKMenuTypes.ItemContainerType<C> type, ScreenConstructor<C, S> factory)
    {
        MenuScreens.register(type.getType(), factory);
    }

    public static <C extends IDKBaseContainerOld<?>, S extends Screen & MenuAccess<C>>
    void registerTileScreen(BEContainer<?, C> type, ScreenConstructor<C, S> factory)
    {
        MenuScreens.register(type.getType(), factory);
    }

    private static void requestModelsAndTextures()
    {

    }

    public static void populateAPI()
    {
        SetRestrictedField.startInitializing(true);
        VertexBufferHolder.addToAPI();
        ManualHelper.MAKE_BLUEPRINT_ELEMENT_NEW.setValue(
                stacks -> new ManualElementBlueprint(ManualHelper.getManual(), stacks)
        );
        ManualHelper.MAKE_BLUEPRINT_ELEMENT.setValue(stacks -> {
            ManualRecipeRef[] refs = Arrays.stream(stacks)
                    .map(ManualRecipeRef::new)
                    .toArray(ManualRecipeRef[]::new);
            return ManualHelper.MAKE_BLUEPRINT_ELEMENT_NEW.getValue().create(refs);
        });
        IDKManual.initManual();
        ItemCallback.DYNAMIC_IEOBJ_RENDERER.setValue(new IDKOBJItemRenderer(
                Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()
        ));
        SetRestrictedField.lock(true);
    }
}
