package net.skydimondlox.idontknowmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.ItemStackHandler;
import net.skydimondlox.idontknowmod.networking.ModMessages;
import net.skydimondlox.idontknowmod.networking.packet.EnergySyncS2CPacket;
import net.skydimondlox.idontknowmod.recipe.AlloyFurnaceRecipe;
import net.skydimondlox.idontknowmod.screen.AlloyFurnaceMenu;
import net.skydimondlox.idontknowmod.util.ModEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlloyFurnaceBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 256) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int  ENERGY_REQ = 32;
    public static final int NUM_SLOTS = 7;
    public static final int ENERGY_CAPACITY = 16000;



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

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
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

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("alloy_furnace_progress", this.progress);
        pTag.putInt("alloy_furnace.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("alloy_furnace_progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("alloy_furnace.energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, AlloyFurnaceBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }

        if(hasRedstoneInThirdSlot(entity)) {
            entity.ENERGY_STORAGE.receiveEnergy(64, false);
        }

        if(hasRecipe(entity) && hasEnoughEnergy(entity)) {
            entity.progress++;
            extractEnergy(entity);
            setChanged(level, blockPos, state);

            if(entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            setChanged(level, blockPos, state);
        }

    }

    private static void extractEnergy(AlloyFurnaceBlockEntity entity) {
        entity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(AlloyFurnaceBlockEntity entity) {
        return entity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * entity.maxProgress;
    }

    private static boolean hasRedstoneInThirdSlot(AlloyFurnaceBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(3).getItem() == Items.REDSTONE;
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