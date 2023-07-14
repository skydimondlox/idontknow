package net.skydimondlox.idontknowmod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.block.ModBlocks;
import net.skydimondlox.idontknowmod.idontknowmod;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, idontknowmod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.IDONTKNOWBLOCK);
        blockWithItem(ModBlocks.ITSABLOCK);
        blockWithItem(ModBlocks.ITSANORE);
        blockWithItem(ModBlocks.DEEPSLATEITSANORE);
        blockWithItem(ModBlocks.PRESSED_IRON_BLOCK);
        blockWithItem(ModBlocks.PRESSED_GOLD_BLOCK);
        blockWithItem(ModBlocks.ELECTRIC_PRESS);
        blockWithItem(ModBlocks.DEEPSLATE_ZINC_ORE);
        blockWithItem(ModBlocks.ZINC_ORE);
        blockWithItem(ModBlocks.ZINC_BLOCK);
        blockWithItem(ModBlocks.BRONZE_BLOCK);
        blockWithItem(ModBlocks.ALLOY_FURNACE);
        blockWithItem(ModBlocks.TIN_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_TIN_ORE);
        blockWithItem(ModBlocks.TIN_BLOCK);
        blockWithItem(ModBlocks.MACHINE_FRAME_ADVANCED);
        blockWithItem(ModBlocks.MACHINE_FRAME_INTERMEDIATE);
        blockWithItem(ModBlocks.MACHINE_FRAME_BASIC);
    }

    private void blockWithItem(RegistryObject<Block>blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
