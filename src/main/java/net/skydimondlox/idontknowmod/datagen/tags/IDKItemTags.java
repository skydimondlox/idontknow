package net.skydimondlox.idontknowmod.datagen.tags;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import com.google.common.base.Preconditions;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.skydimondlox.idontknowmod.api.EnumMetals;
import net.skydimondlox.idontknowmod.api.IDKTags;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.register.IDKItems.*;
import net.skydimondlox.idontknowmod.api.IDKTags.MetalTags;

import java.util.concurrent.CompletableFuture;

public class IDKItemTags extends ItemTagsProvider {

    public IDKItemTags(
            PackOutput output,
            CompletableFuture<Provider> lookupProvider,
            CompletableFuture<TagLookup<Block>> blocks,
            ExistingFileHelper existingFileHelper
    )

    {
        super(output, lookupProvider, blocks, Lib.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider p_256380_) {

        IDKTags.forAllBlocktags(this::copy);
        for(EnumMetals metal : EnumMetals.values())
        {
            MetalTags tags = IDKTags.getTagsFor(metal);
            if(metal.shouldAddNugget())
            {
                tag(tags.nugget).add(Metals.NUGGETS.get(metal).get());
                tag(Tags.Items.NUGGETS).addTag(tags.nugget);
            }
            if(!metal.isVanillaMetal())
            {
                tag(tags.ingot).add(Metals.INGOTS.get(metal).get());
                tag(Tags.Items.INGOTS).addTag(tags.ingot);
            }
            if(metal.shouldAddOre())
            {
                Preconditions.checkNotNull(tags.rawOre);
                tag(tags.rawOre).add(Metals.RAW_ORES.get(metal).get());
                tag(Tags.Items.RAW_MATERIALS).addTag(tags.rawOre);
            }

        }

        tag(IDKTags.ironRod).add(Misc.STICK_IRON.get());
        tag(IDKTags.stoneStick).add(Ingredients.STICK_STONE.get());
        tag(IDKTags.bronzeRod).add(Misc.STICK_BRONZE.get());
        tag(IDKTags.steelIngot).add(Ingots.INGOT_STEEL.get());
        tag(IDKTags.bronzeIngot).add(Ingots.INGOT_BRONZE.get());
        tag(IDKTags.tinIngot).add(Ingots.INGOT_TIN.get());
        tag(IDKTags.zincIngot).add(Ingots.INGOT_ZINC.get());
        tag(IDKTags.rawtin).add(Raw.RAW_TIN.get());
        tag(IDKTags.rawzinc).add(Raw.RAW_ZINC.get());
        tag(IDKTags.stoneGear).add(Gears.GEAR_STONE.get());
        tag(IDKTags.ironGear).add(Gears.GEAR_IRON.get());
        tag(IDKTags.copperGear).add(Gears.GEAR_COPPER.get());
        tag(IDKTags.goldGear).add(Gears.GEAR_GOLD.get());
        tag(IDKTags.diamondGear).add(Gears.GEAR_DIAMOND.get());
        tag(IDKTags.bronzeGear).add(Gears.GEAR_BRONZE.get());
        tag(IDKTags.pickaxes).add(Tools.STEEL_PICK.get());
        tag(IDKTags.axes).add(Tools.STEEL_AXE.get());
        tag(IDKTags.shovels).add(Tools.STEEL_SHOVEL.get());
        tag(IDKTags.hoes).add(Tools.STEEL_HOE.get());
        tag(IDKTags.basic).add(MachineFrame.MACHINE_FRAME_BASIC.get());
        tag(IDKTags.intermediate).add(MachineFrame.MACHINE_FRAME_INTERMEDIATE.get());
        tag(IDKTags.advanced).add(MachineFrame.MACHINE_FRAME_ADVANCED.get());
        tag(IDKTags.ironplate).add(Plate.IRON_PLATE.get());
        tag(IDKTags.goldplate).add(Plate.GOLD_PLATE.get());
        tag(IDKTags.idontknow).add(IDontKnow.I_DONT_KNOW.get(), IDontKnow.HOW_BOUT_THIS.get(), IDontKnow.ITS_SOMETHING.get());

    }

}