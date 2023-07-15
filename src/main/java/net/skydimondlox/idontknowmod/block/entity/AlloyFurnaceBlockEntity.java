package net.skydimondlox.idontknowmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.skydimondlox.idontknowmod.api.crafting.AlloyFurnaceRecipe;
import net.skydimondlox.idontknowmod.api.energy.MutableEnergyStorage;
import net.skydimondlox.idontknowmod.api.utils.CapabilityReference;
import net.skydimondlox.idontknowmod.api.utils.DirectionalBlockPos;
import net.skydimondlox.idontknowmod.block.BaseBlockEntity;
import net.skydimondlox.idontknowmod.block.BlockInterfaces.*;
import net.skydimondlox.idontknowmod.block.ticking.ClientTickableBE;
import net.skydimondlox.idontknowmod.block.ticking.ServerTickableBE;
import net.skydimondlox.idontknowmod.screen.AlloyFurnaceMenu;
import net.skydimondlox.idontknowmod.util.CachedRecipe;
import net.skydimondlox.idontknowmod.util.inventory.IInventory;
import net.skydimondlox.idontknowmod.api.client.IModelOffsetProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static net.skydimondlox.idontknowmod.config.ServerConfig.*;

public class AlloyFurnaceBlockEntity extends BaseBlockEntity implements ServerTickableBE, ClientTickableBE,
        IStateBasedDirectional, IInventory, IInteractionObject<AlloyFurnaceBlockEntity>, IModelOffsetProvider {

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_INPUT_2 = 1;
    public static final int SLOT_OUTPUT = 2;
    public static final int NUM_SLOTS = 7;
    public static final int ENERGY_CAPACITY = 16000;

    public int dummy = 0;
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);

    public MutableEnergyStorage energyStorage = new MutableEnergyStorage(
            ENERGY_CAPACITY, Math.max(256, getOrDefault(MACHINES.alloyFurnace_consumption))
    );
    public final Supplier<AlloyFurnaceRecipe> cachedRecipe = CachedRecipe.cached(
            AlloyFurnaceRecipe::findRecipe, () -> level, () -> inventory.get(SLOT_INPUT_1), () -> inventory.get(SLOT_INPUT_2)
    );

    public boolean renderActive = false;

    public AlloyFurnaceBlockEntity(BlockEntityType<AlloyFurnaceBlockEntity> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    private final CapabilityReference<IItemHandler> output = CapabilityReference.forBlockEntityAt(this,
            () -> new DirectionalBlockPos(worldPosition.above().relative(getFacing().getOpposite()), getFacing()),
            ForgeCapabilities.ITEM_HANDLER);


    @Override
    public boolean canTickAny()
    {
        return !isRSPowered();
    }

    @Override
    public void tickClient()
    {
        ItemStack input1 = inventory.get(SLOT_INPUT_1);
        ItemStack input2 = inventory.get(SLOT_INPUT_2);
        if(renderActive)
        {
            AlloyFurnaceRecipe recipe = cachedRecipe.get();

        }
    }

    @Override
    public void tickServer()
    {
        ItemStack input1 = inventory.get(SLOT_INPUT_1);
        ItemStack input2 = inventory.get(SLOT_INPUT_2);
        if(!input1.isEmpty())
        {
            AlloyFurnaceRecipe recipe = cachedRecipe.get();
            int consumption = MACHINES.alloyFurnace_consumption.get();
            if(recipe!=null&&fertilizerAmount > 0&&energyStorage.extractEnergy(consumption, true)==consumption)
            {
                boolean consume = false;
                if(growth >= recipe.getTime(input1, input2))
                {
                    List<Lazy<ItemStack>> outputs = recipe.getOutputs(input1, input2);
                    int canFit = 0;
                    boolean[] emptySlotsUsed = new boolean[4];
                    for(Lazy<ItemStack> output : outputs)
                        if(!output.get().isEmpty())
                            for(int j = 3; j < 7; j++)
                            {
                                ItemStack existing = inventory.get(j);
                                if((existing.isEmpty()&&!emptySlotsUsed[j-3])||(ItemHandlerHelper.canItemStacksStack(existing, output.get())&&existing.getCount()+output.get().getCount() <= existing.getMaxStackSize()))
                                {
                                    canFit++;
                                    if(existing.isEmpty())
                                        emptySlotsUsed[j-3] = true;
                                    break;
                                }
                            }
                    if(canFit >= outputs.size())
                    {
                        for(Lazy<ItemStack> output : outputs)
                            for(int j = 3; j < 7; j++)
                            {
                                ItemStack existing = inventory.get(j);
                                if(existing.isEmpty())
                                {
                                    inventory.set(j, output.get().copy());
                                    break;
                                }
                                else if(ItemHandlerHelper.canItemStacksStack(existing, output.get())&&existing.getCount()+output.get().getCount() <= existing.getMaxStackSize())
                                {
                                    existing.grow(output.get().getCount());
                                    break;
                                }
                            }
                        growth = 0;
                        consume = true;
                    }
                }
                if(consume)
                {
                    energyStorage.extractEnergy(consumption, false);
                    energyAmount--;
                    if(!renderActive)
                    {
                        renderActive = true;
                        sendSyncPacket();
                    }
                }
                else if(renderActive)
                {
                    renderActive = false;
                    sendSyncPacket();
                }
            }
            else
                growth = 0;
        }
        else
            growth = 0;

        if(level.getGameTime()%8==0)
        {
            IItemHandler outputHandler = output.getNullable();
            if(outputHandler!=null)
                for(int j = 3; j < 7; j++)
                {
                    ItemStack outStack = inventory.get(j);
                    if(!outStack.isEmpty())
                    {
                        int outCount = Math.min(outStack.getCount(), 16);
                        ItemStack stack = ItemHandlerHelper.copyStackWithSize(outStack, outCount);
                        stack = ItemHandlerHelper.insertItem(outputHandler, stack, false);
                        if(!stack.isEmpty())
                            outCount -= stack.getCount();
                        outStack.shrink(outCount);
                        if(outStack.getCount() <= 0)
                            this.inventory.set(j, ItemStack.EMPTY);
                    }
                }
        }
    }


    private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.empty();

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 86;

    public AlloyFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALLOY_FURNACE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> AlloyFurnaceBlockEntity.this.progress;
                    case 1 -> AlloyFurnaceBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> AlloyFurnaceBlockEntity.this.progress = pValue;
                    case 1 -> AlloyFurnaceBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Alloy Furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AlloyFurnaceMenu(pContainerId, pPlayerInventory, this, this.data);
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(AlloyFurnaceBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<AlloyFurnaceRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AlloyFurnaceRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.extractItem(1, 1, false);
            pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().getResultItem().getItem(),
                    pEntity.itemHandler.getStackInSlot(2).getCount() + 1));

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(AlloyFurnaceBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AlloyFurnaceRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AlloyFurnaceRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem());


    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

}