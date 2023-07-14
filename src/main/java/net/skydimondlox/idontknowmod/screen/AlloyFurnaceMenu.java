package net.skydimondlox.idontknowmod.screen;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.skydimondlox.idontknowmod.api.energy.MutableEnergyStorage;
import net.skydimondlox.idontknowmod.block.entity.AlloyFurnaceBlockEntity;
import net.skydimondlox.idontknowmod.screen.sync.GenericContainerData;
import net.skydimondlox.idontknowmod.screen.sync.GenericDataSerializers;
import net.skydimondlox.idontknowmod.screen.sync.GetterAndSetter;

import static net.skydimondlox.idontknowmod.block.entity.AlloyFurnaceBlockEntity.*;

public class AlloyFurnaceMenu extends ContainerMenu
{
    public final EnergyStorage energyStorage;
    public final GetterAndSetter<Float> guiProgress;

    public static AlloyFurnaceMenu makeServer(
            MenuType<?> type, int id, Inventory invPlayer, AlloyFurnaceBlockEntity be
    )
    {
        return new AlloyFurnaceMenu(
                blockCtx(type, id, be), invPlayer, new ItemStackHandler(be.getInventory()),
                be.energyStorage,
                GetterAndSetter.getterOnly(be::getGuiProgress)
        );
    }

    public static AlloyFurnaceMenu makeClient(MenuType<?> type, int id, Inventory invPlayer)
    {
        return new AlloyFurnaceMenu(
                clientCtx(type, id), invPlayer, new ItemStackHandler(NUM_SLOTS),
                new MutableEnergyStorage(ENERGY_CAPACITY), GetterAndSetter.standalone(0f),
                GetterAndSetter.standalone(0f)
        );
    }

    private AlloyFurnaceMenu(
            MenuContext ctx, Inventory inventoryPlayer, IItemHandler inv,
            MutableEnergyStorage energyStorage, FluidTank tank,
            GetterAndSetter<Float> guiProgress
    )
    {
        super(ctx);
        this.energyStorage = energyStorage;
        this.guiProgress = guiProgress;
        Level level = inventoryPlayer.player.level;
        for(int i = 0; i < 4; i++)
            this.addSlot(new Slot.NewOutput(inv, 3+i, 116+i%2*18, 34+i/2*18)
            {
                @Override
                public void onTake(Player pPlayer, ItemStack pStack)
                {
                    super.onTake(pPlayer, pStack);
                    if(pStack.getItem()==Items.CHORUS_FRUIT
                }
            });

        this.ownSlotCount = 7;

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 9; j++)
                addSlot(new Slot(inventoryPlayer, j+i*9+9, 8+j*18, 85+i*18));
        for(int i = 0; i < 9; i++)
            addSlot(new Slot(inventoryPlayer, i, 8+i*18, 143));
        addGenericData(GenericContainerData.energy(energyStorage));
        addGenericData(new GenericContainerData<>(GenericDataSerializers.FLOAT, guiProgress));
    }
}