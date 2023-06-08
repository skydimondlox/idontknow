package net.skydimondlox.idontknowmod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, idontknowmod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.I_DONT_KNOW);
        simpleItem(ModItems.ITS_SOMETHING);
        simpleItem(ModItems.HOW_BOUT_THIS);
        simpleItem(ModItems.PRESSED_IRON);
        simpleItem(ModItems.PRESSED_GOLD);
        simpleItem(ModItems.STONE_GEAR);
        simpleItem(ModItems.IRON_GEAR);
        simpleItem(ModItems.DIAMOND_GEAR);
        simpleItem(ModItems.GOLD_GEAR);
        simpleItem(ModItems.COPPER_GEAR);
        simpleItem(ModItems.RAW_ZINC);
        simpleItem(ModItems.ZINC);
        simpleItem(ModItems.BRONZE_INGOT);
        simpleItem(ModItems.BRONZE_GEAR);
        simpleItem(ModItems.TIN_INGOT);
        simpleItem(ModItems.RAW_TIN);
        handheldItem(ModItems.STONE_STICK);
        handheldItem(ModItems.AMZO_STICK);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(idontknowmod.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(idontknowmod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
