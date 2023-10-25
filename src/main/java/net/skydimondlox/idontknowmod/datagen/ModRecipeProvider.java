package net.skydimondlox.idontknowmod.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.skydimondlox.idontknowmod.block.ModBlocks;
import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.item.ModItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    private static final List<ItemLike> TIN_SMELTABLES = List.of(ModItems.RAW_TIN.get(),
            ModBlocks.TIN_ORE.get(), ModBlocks.DEEPSLATE_TIN_ORE.get());
    private static final List<ItemLike> ZINC_SMELTABLES = List.of(ModItems.RAW_ZINC.get(),
            ModBlocks.ZINC_ORE.get(), ModBlocks.DEEPSLATE_ZINC_ORE.get());


    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.PRESSED_IRON.get(), RecipeCategory.MISC, ModBlocks.PRESSED_IRON_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.PRESSED_GOLD.get(), RecipeCategory.MISC, ModBlocks.PRESSED_GOLD_BLOCK.get());

        nineBlockStorageRecipesRecipesWithCustomUnpacking(consumer, RecipeCategory.MISC, ModItems.BRONZE_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_BLOCK.get(), "bronze_ingot_from_bronze_block", "bronze_ingot");
        nineBlockStorageRecipesWithCustomPacking(consumer, RecipeCategory.MISC, ModItems.BRONZE_NUGGET.get(), RecipeCategory.MISC, ModItems.BRONZE_INGOT.get(), "bronze_ingot_from_nuggets", "bronze_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(consumer, RecipeCategory.MISC, ModItems.STEEL_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.STEEL_BLOCK.get(), "steel_ingot_from_steel_block", "steel_ingot");
        nineBlockStorageRecipesWithCustomPacking(consumer, RecipeCategory.MISC, ModItems.STEEL_NUGGET.get(), RecipeCategory.MISC, ModItems.STEEL_INGOT.get(), "steel_ingot_from_nuggets", "steel_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(consumer, RecipeCategory.MISC, ModItems.ZINC.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZINC_BLOCK.get(), "zinc_from_zinc_block", "zinc");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(consumer, RecipeCategory.MISC, ModItems.TIN_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.TIN_BLOCK.get(), "tin_ingot_from_tin_block", "tin_ingot");

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MACHINE_FRAME_BASIC.get())
                 .pattern("RSR")
                 .pattern("S S")
                 .pattern("RSR")
                 .define('S', Ingredient.of(ModItems.STEEL_INGOT.get()))
                 .define('R', Ingredient.of(Items.REDSTONE))
                 .unlockedBy(getHasName(ModItems.STEEL_INGOT.get()), has(ModItems.STEEL_INGOT.get()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MACHINE_FRAME_INTERMEDIATE.get())
                 .pattern("RSR")
                 .pattern("SMS")
                 .pattern("RSR")
                 .define('S', Ingredient.of(ModItems.STEEL_INGOT.get()))
                 .define('R', Ingredient.of(Items.REDSTONE))
                 .define('M', Ingredient.of(ModItems.MACHINE_FRAME_BASIC.get()))
                 .unlockedBy(getHasName(ModItems.MACHINE_FRAME_BASIC.get()), has(ModItems.MACHINE_FRAME_BASIC.get()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MACHINE_FRAME_ADVANCED.get())
                 .pattern("RSR")
                 .pattern("SMS")
                 .pattern("RSR")
                 .define('S', Ingredient.of(ModItems.STEEL_INGOT.get()))
                 .define('R', Ingredient.of(Items.REDSTONE))
                 .define('M', Ingredient.of(ModItems.MACHINE_FRAME_INTERMEDIATE.get()))
                 .unlockedBy(getHasName(ModItems.MACHINE_FRAME_INTERMEDIATE.get()), has(ModItems.MACHINE_FRAME_INTERMEDIATE.get()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ELECTRIC_PRESS.get())
                 .pattern("BIB")
                 .pattern("IFI")
                 .pattern("BRB")
                 .define('F', Ingredient.of(Blocks.FURNACE))
                 .define('I', Ingredient.of(Items.IRON_INGOT))
                 .define('R', Ingredient.of(Blocks.REDSTONE_BLOCK))
                 .define('B', Tags.Items.STONE)
                 .unlockedBy(getHasName(Items.FURNACE), has(Items.FURNACE))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALLOY_FURNACE.get())
                 .pattern("ZRZ")
                 .pattern("RER")
                 .pattern("ZBZ")
                 .define('E', Ingredient.of(ModBlocks.ELECTRIC_PRESS.get()))
                 .define('R', Ingredient.of(Items.REDSTONE))
                 .define('Z', Ingredient.of(ModItems.ZINC.get()))
                 .define('B', Ingredient.of(Blocks.IRON_BLOCK))
                 .unlockedBy(getHasName(ModBlocks.ELECTRIC_PRESS.get()), has(ModBlocks.ELECTRIC_PRESS.get()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_GEAR.get())
                 .pattern(" I ")
                 .pattern("I I")
                 .pattern(" I ")
                 .define('I', Ingredient.of(Items.IRON_INGOT))
                 .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_GEAR.get())
                 .pattern(" B ")
                 .pattern("B B")
                 .pattern(" B ")
                 .define('B', Ingredient.of(ModItems.BRONZE_INGOT.get()))
                 .unlockedBy(getHasName(ModItems.BRONZE_INGOT.get()), has(ModItems.BRONZE_INGOT.get()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLD_GEAR.get())
                 .pattern(" G ")
                 .pattern("G G")
                 .pattern(" G ")
                 .define('G', Ingredient.of(Items.GOLD_INGOT))
                 .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_GEAR.get())
                 .pattern(" D ")
                 .pattern("D D")
                 .pattern(" D ")
                 .define('D', Ingredient.of(Items.DIAMOND))
                 .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_GEAR.get())
                 .pattern(" S ")
                 .pattern("S S")
                 .pattern(" S ")
                 .define('S', Ingredient.of(Blocks.STONE))
                 .unlockedBy(getHasName(Blocks.STONE), has(Blocks.STONE))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_GEAR.get())
                 .pattern(" C ")
                 .pattern("C C")
                 .pattern(" C ")
                 .define('C', Ingredient.of(Items.COPPER_INGOT))
                 .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_STICK.get())
                 .pattern(" C ")
                 .pattern(" C ")
                 .define('C', Ingredient.of(Blocks.COBBLESTONE))
                 .unlockedBy(getHasName(Blocks.COBBLESTONE), has(Blocks.COBBLESTONE))
                 .save(consumer);

         ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 9)
                         .requires(Blocks.COBWEB)
                         .unlockedBy(getHasName(Blocks.COBWEB), has(Blocks.COBWEB))
                         .save(consumer, new ResourceLocation(idontknowmod.MOD_ID));

        oreBlasting(consumer, ZINC_SMELTABLES, RecipeCategory.MISC, ModItems.ZINC.get(), 0.7f, 100, "zinc");
        oreSmelting(consumer, ZINC_SMELTABLES, RecipeCategory.MISC, ModItems.ZINC.get(), 0.7f, 200, "zinc");
        oreBlasting(consumer, TIN_SMELTABLES, RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 100, "tin_ingot");
        oreSmelting(consumer, TIN_SMELTABLES, RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 200, "tin_ingot");

    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category1, ItemLike output, RecipeCategory category2, ItemLike input) {
        nineBlockStorageRecipes(consumer, category1, output, category2, input, getSimpleRecipeName(input), (String) null, getSimpleRecipeName(output), (String) null);
    }

    protected static void nineBlockStorageRecipesRecipesWithCustomUnpacking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked, String pUnpackedName, String pUnpackedGroup) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpackedCategory, pUnpacked, pPackedCategory, pPacked, getSimpleRecipeName(pPacked), (String)null, pUnpackedName, pUnpackedGroup);
    }

    protected static void nineBlockStorageRecipesWithCustomPacking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked, String pPackedName, String pPackedGroup) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpackedCategory, pUnpacked, pPackedCategory, pPacked, pPackedName, pPackedGroup, getSimpleRecipeName(pUnpacked), (String)null);
    }

    protected static void fourBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category1, ItemLike output, RecipeCategory category2, ItemLike input) {
        fourBlockStorageRecipes(consumer, category1, output, category2, input, getSimpleRecipeName(input), (String) null, getSimpleRecipeName(output), (String) null);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> consumer, List<ItemLike> output, RecipeCategory category, ItemLike input, float experience, int timeinticks, String groupname) {
        oreCooking(consumer, RecipeSerializer.SMELTING_RECIPE, output, category, input, experience, timeinticks, groupname, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> consumer, List<ItemLike> output, RecipeCategory category, ItemLike input, float experience, int timeinticks, String groupname) {
        oreCooking(consumer, RecipeSerializer.BLASTING_RECIPE, output, category, input, experience, timeinticks, groupname, "_from_blasting");
    }

    protected static void twoByTwoPacker(Consumer<FinishedRecipe> consumer, RecipeCategory recipeCategory, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(recipeCategory, output, 1).define('#', input).pattern("##").pattern("##").unlockedBy(getHasName(input), has(input)).save(consumer);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory p_250083_, ItemLike p_250042_, RecipeCategory p_248977_, ItemLike p_251911_, String p_250475_, @Nullable String p_248641_, String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(p_250083_, p_250042_, 9).requires(p_251911_).group(p_250414_).unlockedBy(getHasName(p_251911_), has(p_251911_)).save(consumer, new ResourceLocation(idontknowmod.MOD_ID, p_252237_));
        ShapedRecipeBuilder.shaped(p_248977_, p_251911_).define('#', p_250042_).pattern("###").pattern("###").pattern("###").group(p_248641_).unlockedBy(getHasName(p_250042_), has(p_250042_)).save(consumer, new ResourceLocation(idontknowmod.MOD_ID, p_250475_));
    }

    protected static void fourBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory p_250083_, ItemLike p_250042_, RecipeCategory p_248977_, ItemLike p_251911_, String p_250475_, @Nullable String p_248641_, String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(p_250083_, p_250042_, 4).requires(p_251911_).group(p_250414_).unlockedBy(getHasName(p_251911_), has(p_251911_)).save(consumer, new ResourceLocation(idontknowmod.MOD_ID, p_252237_));
        ShapedRecipeBuilder.shaped(p_248977_, p_251911_).define('#', p_250042_).pattern("##").pattern("##").group(p_248641_).unlockedBy(getHasName(p_250042_), has(p_250042_)).save(consumer, new ResourceLocation(idontknowmod.MOD_ID,     p_250475_));
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedConsumerRecipe, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedConsumerRecipe, idontknowmod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

}
