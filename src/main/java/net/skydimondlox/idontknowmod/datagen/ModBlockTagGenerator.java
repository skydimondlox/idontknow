package net.skydimondlox.idontknowmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.skydimondlox.idontknowmod.block.ModBlocks;
import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, idontknowmod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        //MY TAGS



        //FORGE TAGS

        this.tag(ModTags.Blocks.ORES_TIN)
                .add(ModBlocks.TIN_ORE.get(), ModBlocks.DEEPSLATE_TIN_ORE.get());
        this.tag(ModTags.Blocks.ORES_ZINC)
                .add(ModBlocks.ZINC_ORE.get(), ModBlocks.DEEPSLATE_ZINC_ORE.get());
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_ZINC)
                .add(ModBlocks.ZINC_BLOCK.get());
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_TIN)
                .add(ModBlocks.TIN_BLOCK.get());
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_BRONZE)
                .add(ModBlocks.BRONZE_BLOCK.get());
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_STEEL)
                .add(ModBlocks.STEEL_BLOCK.get());
        this.tag(ModTags.Blocks.ORES)
                .add(ModBlocks.ZINC_ORE.get(), ModBlocks.TIN_ORE.get(), ModBlocks.DEEPSLATE_ZINC_ORE.get(), ModBlocks.DEEPSLATE_TIN_ORE.get());

        //MINECRAFT TAGS

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.ITSABLOCK.get(), ModBlocks.ZINC_ORE.get(), ModBlocks.DEEPSLATE_ZINC_ORE.get(), ModBlocks.ZINC_BLOCK.get(),
                        ModBlocks.TIN_ORE.get(), ModBlocks.DEEPSLATE_TIN_ORE.get(), ModBlocks.TIN_BLOCK.get(), ModBlocks.STEEL_BLOCK.get() , ModBlocks.ALLOY_FURNACE.get(), ModBlocks.ELECTRIC_PRESS.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.PRESSED_GOLD_BLOCK.get(), ModBlocks.PRESSED_IRON_BLOCK.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ITSABLOCK.get(), ModBlocks.BRONZE_BLOCK.get(), ModBlocks.TIN_BLOCK.get(), ModBlocks.ZINC_BLOCK.get(), ModBlocks.TIN_ORE.get(),
                        ModBlocks.ZINC_ORE.get(), ModBlocks.DEEPSLATE_TIN_ORE.get(), ModBlocks.DEEPSLATE_TIN_ORE.get(), ModBlocks.ALLOY_FURNACE.get(), ModBlocks.ELECTRIC_PRESS.get(),
                        ModBlocks.PRESSED_GOLD_BLOCK.get(), ModBlocks.PRESSED_IRON_BLOCK.get(), ModBlocks.STEEL_BLOCK.get());
    }
}
