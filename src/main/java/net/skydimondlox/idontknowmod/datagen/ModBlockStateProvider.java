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
        blockWithItem(ModBlocks.PRESSEDIRONBLOCK);
        blockWithItem(ModBlocks.PRESSEDGOLDBLOCK);
        blockWithItem(ModBlocks.ELECTRIC_PRESS);
    }

    private void blockWithItem(RegistryObject<Block>blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
