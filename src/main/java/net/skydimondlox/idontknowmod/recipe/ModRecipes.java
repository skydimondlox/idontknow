package net.skydimondlox.idontknowmod.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.idontknowmod;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, idontknowmod.MOD_ID);

        public static final RegistryObject<RecipeSerializer<ElectricPressRecipe>> ELECTRIC_PRESS_SERIALIZER =
                SERIALIZERS.register("electric_press", () -> ElectricPressRecipe.Serializer.INSTANCE);

        public static void register(IEventBus eventBus) {
            SERIALIZERS.register(eventBus);
        }
}
