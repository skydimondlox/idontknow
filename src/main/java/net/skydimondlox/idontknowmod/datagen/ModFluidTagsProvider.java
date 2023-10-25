package net.skydimondlox.idontknowmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.skydimondlox.idontknowmod.fluid.ModFluids;
import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, idontknowmod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Fluids.OIL)
                .add(ModFluids.SOURCE_OIL.get())
                .add(ModFluids.FLOWING_OIL.get());
    }
}
