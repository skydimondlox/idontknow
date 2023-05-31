package net.skydimondlox.idontknowmod.block.entity;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.block.ModBlocks;
import net.skydimondlox.idontknowmod.idontknowmod;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, idontknowmod.MOD_ID);

    public static final RegistryObject<BlockEntityType<ElectricPressBlockEntity>> ELECTRIC_PRESS =
            BLOCK_ENTITIES.register("electric_press", () ->
                    BlockEntityType.Builder.of(ElectricPressBlockEntity::new,
                            ModBlocks.ELECTRIC_PRESS.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
