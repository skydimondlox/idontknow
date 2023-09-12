package net.skydimondlox.idontknowmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.item.ModItems;
import net.skydimondlox.idontknowmod.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, idontknowmod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        //MY TAGS

        this.tag(ModTags.Items.MACHINE_FRAMES)
                .add(ModItems.MACHINE_FRAME_BASIC.get(), ModItems.MACHINE_FRAME_INTERMEDIATE.get(), ModItems.MACHINE_FRAME_ADVANCED.get());
        this.tag(ModTags.Items.MACHINE_FRAME_BASIC)
                .add(ModItems.MACHINE_FRAME_BASIC.get());
        this.tag(ModTags.Items.MACHINE_FRAME_INTERMEDIATE)
                .add(ModItems.MACHINE_FRAME_INTERMEDIATE.get());
        this.tag(ModTags.Items.MACHINE_FRAME_ADVANCED)
                .add(ModItems.MACHINE_FRAME_ADVANCED.get());

        //FORGE TAGS

        /* ITEMS */

        this.tag(ModTags.Items.INGOTS_BRONZE)
                .add(ModItems.BRONZE_INGOT.get());
        this.tag(ModTags.Items.INGOTS_TIN)
                .add(ModItems.TIN_INGOT.get());
        this.tag(ModTags.Items.INGOTS_ZINC)
                .add(ModItems.ZINC.get());
        this.tag(ModTags.Items.INGOTS_STEEL)
                .add(ModItems.STEEL_INGOT.get());
        this.tag(ModTags.Items.GEARS_STONE)
                .add(ModItems.STONE_GEAR.get());
        this.tag(ModTags.Items.GEARS_IRON)
                .add(ModItems.IRON_GEAR.get());
        this.tag(ModTags.Items.GEARS_GOLD)
                .add(ModItems.GOLD_GEAR.get());
        this.tag(ModTags.Items.GEARS_DIAMOND)
                .add(ModItems.DIAMOND_GEAR.get());
        this.tag(ModTags.Items.GEARS_BRONZE)
                .add(ModItems.BRONZE_GEAR.get());
        this.tag(ModTags.Items.GEARS_COPPER)
                .add(ModItems.COPPER_GEAR.get());


        /* BLOCKS */

        this.copy(ModTags.Blocks.ORES_ZINC, ModTags.Items.ORES_ZINC);
        this.copy(ModTags.Blocks.ORES_TIN, ModTags.Items.ORES_TIN);
        this.copy(ModTags.Blocks.STORAGE_BLOCKS_BRONZE, ModTags.Items.STORAGE_BLOCKS_BRONZE);
        this.copy(ModTags.Blocks.STORAGE_BLOCKS_TIN, ModTags.Items.STORAGE_BLOCKS_TIN);
        this.copy(ModTags.Blocks.STORAGE_BLOCKS_ZINC, ModTags.Items.STORAGE_BLOCKS_ZINC);
        this.copy(ModTags.Blocks.STORAGE_BLOCKS_STEEL, ModTags.Items.STORAGE_BLOCKS_STEEL);

    }
}
