package net.skydimondlox.idontknowmod.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.idontknowmod;
import org.joml.Vector3f;

public class ModFluidTypes {
    public static final ResourceLocation OIL_STILL_RL = new ResourceLocation("block/oil_still");
    public static final ResourceLocation OIL_FLOWING_RL = new ResourceLocation("block/oil_flowing");
    public static final ResourceLocation OIL_OVERLAY_RL = new ResourceLocation("block/oil_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =  DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, idontknowmod.MOD_ID);

    public static final RegistryObject<FluidType> OIL_FLUID_TYPE = registerFluidType("oil_fluid", new BaseFluidType(OIL_STILL_RL, OIL_FLOWING_RL, OIL_OVERLAY_RL, 0xA1E038D0,
            new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), FluidType.Properties.create().lightLevel(2).viscosity(5).density(15)));

    private static RegistryObject<FluidType> registerFluidType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name, () -> fluidType);
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
