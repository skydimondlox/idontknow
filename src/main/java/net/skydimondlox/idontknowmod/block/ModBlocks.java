package net.skydimondlox.idontknowmod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.block.custom.AlloyFurnaceBlock;
import net.skydimondlox.idontknowmod.block.custom.ElectricPressBlock;
import net.skydimondlox.idontknowmod.idontknowmod;
import net.skydimondlox.idontknowmod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, idontknowmod.MOD_ID);

    public static final  RegistryObject<Block> IDONTKNOWBLOCK = registerBlock("idontknowblock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> ITSABLOCK = registerBlock("itsablock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> ITSANORE = registerBlock("itsanore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> DEEPSLATEITSANORE = registerBlock("deepslateitsanore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> PRESSED_IRON_BLOCK = registerBlock("pressedironblock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> PRESSED_GOLD_BLOCK = registerBlock("pressedgoldblock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> ELECTRIC_PRESS = registerBlock("electric_press",
            () -> new ElectricPressBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()));

    public static final  RegistryObject<Block> ALLOY_FURNACE = registerBlock("alloy_furnace",
            () -> new AlloyFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()));

    public static final  RegistryObject<Block> ZINC_ORE = registerBlock("zinc_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> DEEPSLATE_ZINC_ORE = registerBlock("deepslate_zinc_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> ZINC_BLOCK = registerBlock("zinc_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> BRONZE_BLOCK = registerBlock("bronze_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> TIN_ORE = registerBlock("tin_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final  RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> MACHINE_FRAME_BASIC = registerBlock("machine_frame_basic",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> MACHINE_FRAME_INTERMEDIATE = registerBlock("machine_frame_intermediate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> MACHINE_FRAME_ADVANCED = registerBlock("machine_frame_advanced",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(4f).requiresCorrectToolForDrops()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
