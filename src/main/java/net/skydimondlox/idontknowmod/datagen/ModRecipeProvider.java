package net.skydimondlox.idontknowmod.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.skydimondlox.idontknowmod.block.ModBlocks;
import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.item.ModItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.I_DONT_KNOW.get(), RecipeCategory.MISC,
                ModBlocks.IDONTKNOWBLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.ITS_SOMETHING.get(), RecipeCategory.MISC,
                ModItems.HOW_BOUT_THIS.get());

        twoByTwoPacker(consumer, RecipeCategory.MISC, ModBlocks.ITSABLOCK.get(), ModItems.ITS_SOMETHING.get());

        oreSmelting(consumer, List.of(ModItems.ITS_SOMETHING.get()), RecipeCategory.MISC, ModItems.I_DONT_KNOW.get(), 0.7f, 200, "idontknow");
        oreBlasting(consumer, List.of(ModItems.ITS_SOMETHING.get()), RecipeCategory.MISC, ModItems.I_DONT_KNOW.get(), 0.7f, 100, "idontknow");

        //ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ITS_SOMETHING.get(), 4).requires(ModBlocks.ITSABLOCK.get()).unlockedBy("has_itsablock", has(ModBlocks.ITSABLOCK.get())).save(consumer);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_249580_, RecipeCategory p_251203_, ItemLike p_251689_, RecipeCategory p_251376_, ItemLike p_248771_) {
        nineBlockStorageRecipes(p_249580_, p_251203_, p_251689_, p_251376_, p_248771_, getSimpleRecipeName(p_248771_), (String) null, getSimpleRecipeName(p_251689_), (String) null);
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

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_250423_, RecipeCategory p_250083_, ItemLike p_250042_, RecipeCategory p_248977_, ItemLike p_251911_, String p_250475_, @Nullable String p_248641_, String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(p_250083_, p_250042_, 9).requires(p_251911_).group(p_250414_).unlockedBy(getHasName(p_251911_), has(p_251911_)).save(p_250423_, new ResourceLocation(idontknowmod.MOD_ID, p_252237_));
        ShapedRecipeBuilder.shaped(p_248977_, p_251911_).define('#', p_250042_).pattern("###").pattern("###").pattern("###").group(p_248641_).unlockedBy(getHasName(p_250042_), has(p_250042_)).save(p_250423_, new ResourceLocation(idontknowmod.MOD_ID, p_250475_));
    }

    protected static void oreCooking(Consumer<FinishedRecipe> p_250791_, RecipeSerializer<? extends AbstractCookingRecipe> p_251817_, List<ItemLike> p_249619_, RecipeCategory p_251154_, ItemLike p_250066_, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        for (ItemLike itemlike : p_249619_) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), p_251154_, p_250066_, p_251871_, p_251316_, p_251817_).group(p_251450_).unlockedBy(getHasName(itemlike), has(itemlike)).save(p_250791_, getItemName(p_250066_) + p_249236_ + "_" + getItemName(itemlike));
        }
    }
}