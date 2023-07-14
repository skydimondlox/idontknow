
/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */

package net.skydimondlox.idontknowmod.register;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.idontknowmod;

import java.util.function.Supplier;

public class EntityTypes {

    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(
            ForgeRegistries.ENTITY_TYPES, Lib.MOD_ID
    );



    private static <T extends Entity>
    RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> prepare)
    {
        return REGISTER.register(name, () -> prepare.get().build(idontknowmod.MOD_ID+":"+name));
    }
}
