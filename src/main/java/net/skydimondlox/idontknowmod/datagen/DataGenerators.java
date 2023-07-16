package net.skydimondlox.idontknowmod.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.skydimondlox.idontknowmod.datagen.tags.BlockEntityTags;
import net.skydimondlox.idontknowmod.datagen.tags.IDKBlockTags;
import net.skydimondlox.idontknowmod.datagen.tags.IDKItemTags;
import net.skydimondlox.idontknowmod.idontknowmod;

@Mod.EventBusSubscriber(modid = idontknowmod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        final var packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        final var lookupProvider = event.getLookupProvider();

        BlockTagsProvider blockTags = new IDKBlockTags(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(true, blockTags);
        generator.addProvider(true, new IDKItemTags(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(true, new BlockEntityTags(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(true, new EntityTypeTags(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(true, new ModRecipeProvider(packOutput));
        generator.addProvider(true, ModLootTableProvider.create(packOutput));
        generator.addProvider(true, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));

    }
}