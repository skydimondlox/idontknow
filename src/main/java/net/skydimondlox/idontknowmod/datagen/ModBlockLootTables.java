package net.skydimondlox.idontknowmod.datagen;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.block.ModBlocks;
import net.skydimondlox.idontknowmod.item.ModItems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.IDONTKNOWBLOCK.get());
        dropSelf(ModBlocks.ITSABLOCK.get());
        dropSelf(ModBlocks.PRESSED_IRON_BLOCK.get());
        dropSelf(ModBlocks.PRESSED_GOLD_BLOCK.get());
        dropSelf(ModBlocks.ELECTRIC_PRESS.get());
        dropSelf(ModBlocks.ZINC_BLOCK.get());
        dropSelf(ModBlocks.BRONZE_BLOCK.get());
        dropSelf(ModBlocks.ALLOY_FURNACE.get());
        dropSelf(ModBlocks.TIN_BLOCK.get());

        add(ModBlocks.ITSANORE.get(),
                (block) -> createOreDrop(ModBlocks.ITSANORE.get(), ModItems.ITS_SOMETHING.get()));
        add(ModBlocks.DEEPSLATEITSANORE.get(),
                (block) -> createOreDrop(ModBlocks.DEEPSLATEITSANORE.get(), ModItems.ITS_SOMETHING.get()));
        add(ModBlocks.DEEPSLATE_ZINC_ORE.get(),
                (block) -> createOreDrop(ModBlocks.DEEPSLATE_ZINC_ORE.get(), ModItems.RAW_ZINC.get()));
        add(ModBlocks.ZINC_ORE.get(),
                (block) -> createOreDrop(ModBlocks.ZINC_ORE.get(), ModItems.RAW_ZINC.get()));
        add(ModBlocks.TIN_ORE.get(),
                (block) -> createOreDrop(ModBlocks.TIN_ORE.get(), ModItems.RAW_TIN.get()));
        add(ModBlocks.DEEPSLATE_TIN_ORE.get(),
                (block) -> createOreDrop(ModBlocks.DEEPSLATE_TIN_ORE.get(), ModItems.RAW_TIN.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
