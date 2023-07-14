package net.skydimondlox.idontknowmod.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
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
import net.skydimondlox.idontknowmod.util.ModTags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.PRESSED_IRON.get(), RecipeCategory.MISC,
                ModBlocks.PRESSED_IRON_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.PRESSED_GOLD.get(), RecipeCategory.MISC,
                ModBlocks.PRESSED_GOLD_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.ZINC.get(), RecipeCategory.MISC,
                ModBlocks.ZINC_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.BRONZE_INGOT.get(), RecipeCategory.MISC,
                ModBlocks.BRONZE_BLOCK.get());
       nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.TIN_INGOT.get(), RecipeCategory.MISC,
                ModBlocks.TIN_BLOCK.get());

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ELECTRIC_PRESS.get())
                 .pattern("BIB")
                 .pattern("IFI")
                 .pattern("BRB")
                 .define('F', Ingredient.of(Blocks.FURNACE))
                 .define('I', Ingredient.of(Items.IRON_INGOT))
                 .define('R', Ingredient.of(Blocks.REDSTONE_BLOCK))
                 .define('B', Tags.Items.STONE)
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(Blocks.FURNACE).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALLOY_FURNACE.get())
                 .pattern("ZRZ")
                 .pattern("RER")
                 .pattern("ZBZ")
                 .define('E', Ingredient.of(ModBlocks.ELECTRIC_PRESS.get()))
                 .define('R', Ingredient.of(Items.REDSTONE))
                 .define('Z', Ingredient.of(ModItems.ZINC.get()))
                 .define('B', Ingredient.of(ModTags.Items.GEARS_IRON))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(ModBlocks.ELECTRIC_PRESS.get()).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_GEAR.get())
                 .pattern(" I ")
                 .pattern("I I")
                 .pattern(" I ")
                 .define('I', Ingredient.of(Items.IRON_INGOT))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(Items.IRON_INGOT).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_GEAR.get())
                 .pattern(" B ")
                 .pattern("B B")
                 .pattern(" B ")
                 .define('B', Ingredient.of(ModItems.BRONZE_INGOT.get()))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(ModItems.BRONZE_INGOT.get()).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLD_GEAR.get())
                 .pattern(" G ")
                 .pattern("G G")
                 .pattern(" G ")
                 .define('G', Ingredient.of(Items.GOLD_INGOT))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(Items.GOLD_INGOT).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_GEAR.get())
                 .pattern(" D ")
                 .pattern("D D")
                 .pattern(" D ")
                 .define('D', Ingredient.of(Items.DIAMOND))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(Items.DIAMOND).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_GEAR.get())
                 .pattern(" S ")
                 .pattern("S S")
                 .pattern(" S ")
                 .define('S', Ingredient.of(Blocks.STONE))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(Blocks.STONE).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_GEAR.get())
                 .pattern(" C ")
                 .pattern("C C")
                 .pattern(" C ")
                 .define('C', Ingredient.of(Items.COPPER_INGOT))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(Items.COPPER_INGOT).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_STICK.get())
                 .pattern(" C ")
                 .pattern(" C ")
                 .define('C', Ingredient.of(Blocks.COBBLESTONE))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(Blocks.COBBLESTONE).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_ROD.get())
                 .pattern(" B ")
                 .pattern(" B ")
                 .define('B', Ingredient.of(ModItems.BRONZE_INGOT.get()))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(ModItems.BRONZE_INGOT.get()).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_ROD.get())
                 .pattern(" I ")
                 .pattern(" I ")
                 .define('I', Ingredient.of(Items.IRON_INGOT))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(Items.IRON_INGOT).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MACHINE_FRAME_BASIC.get())
                 .pattern("@$@")
                 .pattern("$#$")
                 .pattern("@$@")
                 .define('#', Ingredient.of(ModTags.Items.GEARS_IRON))
                 .define('$', Ingredient.of(ItemTags.PLANKS))
                 .define('@', Ingredient.of(ItemTags.STONE_CRAFTING_MATERIALS))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(ModTags.Items.GEARS_IRON).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MACHINE_FRAME_INTERMEDIATE.get())
                 .pattern("@$@")
                 .pattern("$#$")
                 .pattern("@$@")
                 .define('#', Ingredient.of(ModBlocks.MACHINE_FRAME_BASIC.get()))
                 .define('$', Ingredient.of(Tags.Items.INGOTS_IRON))
                 .define('@', Ingredient.of(ModTags.Items.STORAGE_BLOCKS_ZINC))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(ModBlocks.MACHINE_FRAME_BASIC.get()).build()))
                 .save(consumer);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MACHINE_FRAME_ADVANCED.get())
                 .pattern("@$@")
                 .pattern("$#$")
                 .pattern("@$@")
                 .define('#', Ingredient.of(ModBlocks.MACHINE_FRAME_INTERMEDIATE.get()))
                 .define('$', Ingredient.of(Tags.Items.GEMS_DIAMOND))
                 .define('@', Ingredient.of(Tags.Items.STORAGE_BLOCKS_DIAMOND))
                 .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(ModBlocks.MACHINE_FRAME_INTERMEDIATE.get()).build()))
                 .save(consumer);

         ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 9)
                         .requires(Blocks.COBWEB)
                         .unlockedBy("has_items", inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.COBWEB).build()))
                         .save(consumer, new ResourceLocation(idontknowmod.MOD_ID));

        twoByTwoPacker(consumer, RecipeCategory.MISC, ModBlocks.ITSABLOCK.get(), ModItems.ITS_SOMETHING.get());

        oreBlasting(consumer, List.of(ModBlocks.ZINC_ORE.get()), RecipeCategory.MISC, ModItems.ZINC.get(), 0.7f, 100, "zinc");
        oreSmelting(consumer, List.of(ModBlocks.ZINC_ORE.get()), RecipeCategory.MISC, ModItems.ZINC.get(), 0.7f, 200, "zinc");
        oreBlasting(consumer, List.of(ModBlocks.DEEPSLATE_ZINC_ORE.get()), RecipeCategory.MISC, ModItems.ZINC.get(), 0.7f, 100, "zinc");
        oreSmelting(consumer, List.of(ModBlocks.DEEPSLATE_ZINC_ORE.get()), RecipeCategory.MISC, ModItems.ZINC.get(), 0.7f, 200, "zinc");
        oreBlasting(consumer, List.of(ModItems.RAW_ZINC.get()), RecipeCategory.MISC, ModItems.ZINC.get(), 0.7f, 100, "zinc");
        oreSmelting(consumer, List.of(ModItems.RAW_ZINC.get()), RecipeCategory.MISC, ModItems.ZINC.get(), 0.7f, 200, "zinc");
        oreBlasting(consumer, List.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 100, "zinc");
        oreSmelting(consumer, List.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 200, "zinc");
        oreBlasting(consumer, List.of(ModBlocks.DEEPSLATE_TIN_ORE.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 100, "zinc");
        oreSmelting(consumer, List.of(ModBlocks.DEEPSLATE_TIN_ORE.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 200, "zinc");
        oreBlasting(consumer, List.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 100, "zinc");
        oreSmelting(consumer, List.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 200, "zinc");

    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory category1, ItemLike output, RecipeCategory category2, ItemLike input) {
        nineBlockStorageRecipes(consumer, category1, output, category2, input, getSimpleRecipeName(input), (String) null, getSimpleRecipeName(output), (String) null);
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

    protected static void oreCooking(Consumer<FinishedRecipe> p_250791_, RecipeSerializer<? extends AbstractCookingRecipe> p_251817_, List<ItemLike> p_249619_, RecipeCategory p_251154_, ItemLike p_250066_, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        for(ItemLike itemlike : p_249619_) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), p_251154_, p_250066_, p_251871_, p_251316_, p_251817_).group(p_251450_)
                    .unlockedBy(getHasName(itemlike), has(itemlike)).save(p_250791_, new ResourceLocation(idontknowmod.MOD_ID, getItemName(p_250066_)) + p_249236_ + "_" + getItemName(itemlike));
        }
    }

    //protected static void pressCrafting(Consumer<FinishedRecipe> consumer, List<ItemLike> output, RecipeCategory category, ItemLike input, )
}
