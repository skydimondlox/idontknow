package net.skydimondlox.idontknowmod.screen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.block.BaseBlockEntity;
import net.skydimondlox.idontknowmod.screen.sync.GenericContainerData;
import net.skydimondlox.idontknowmod.screen.sync.GenericDataSerializers.DataPair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = Lib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public abstract class ContainerMenu extends AbstractContainerMenu
{
    private final List<GenericContainerData<?>> genericData = new ArrayList<>();
    private final List<ServerPlayer> usingPlayers = new ArrayList<>();
    private final Runnable setChanged;
    private final Predicate<Player> isValid;
    public int ownSlotCount;

    protected ContainerMenu(MenuContext ctx)
    {
        super(ctx.type, ctx.id);
        this.setChanged = ctx.setChanged;
        this.isValid = ctx.isValid;
    }

    public void addGenericData(GenericContainerData<?> newData)
    {
        genericData.add(newData);
    }

    @Override
    public void broadcastChanges()
    {
        super.broadcastChanges();
        List<Pair<Integer, DataPair<?>>> toSync = new ArrayList<>();
        for(int i = 0; i < genericData.size(); i++)
        {
            GenericContainerData<?> data = genericData.get(i);
            if(data.needsUpdate())
                toSync.add(Pair.of(i, data.dataPair()));
        }
        if(!toSync.isEmpty())
            for(ServerPlayer player : usingPlayers)
    }

    public void receiveSync(List<Pair<Integer, DataPair<?>>> synced)
    {
        for(Pair<Integer, DataPair<?>> syncElement : synced)
            genericData.get(syncElement.getFirst()).processSync(syncElement.getSecond().data());
    }

    @Override
    public void clicked(int id, int dragType, ClickType clickType, Player player)
    {
        Slot slot = id < 0?null: this.slots.get(id);
        if(!(slot instanceof Slot.ItemHandlerGhost))
        {
            super.clicked(id, dragType, clickType, player);
            return;
        }
        //Spooky Ghost Slots!!!!
        ItemStack stackSlot = slot.getItem();

        if(dragType==2)
            slot.set(ItemStack.EMPTY);
        else if(dragType==0||dragType==1)
        {
            ItemStack stackHeld = getCarried();
            int amount = Math.min(slot.getMaxStackSize(), stackHeld.getCount());
            if(dragType==1)
                amount = 1;
            if(stackSlot.isEmpty())
            {
                if(!stackHeld.isEmpty()&&slot.mayPlace(stackHeld))
                    slot.set(ItemHandlerHelper.copyStackWithSize(stackHeld, amount));
            }
            else if(stackHeld.isEmpty())
                slot.set(ItemStack.EMPTY);
            else if(slot.mayPlace(stackHeld))
            {
                if(ItemStack.isSame(stackSlot, stackHeld))
                    stackSlot.grow(amount);
                else
                    slot.set(ItemHandlerHelper.copyStackWithSize(stackHeld, amount));
            }
            if(stackSlot.getCount() > slot.getMaxStackSize())
                stackSlot.setCount(slot.getMaxStackSize());
        }
        else if(dragType==5)
        {
            ItemStack stackHeld = getCarried();
            int amount = Math.min(slot.getMaxStackSize(), stackHeld.getCount());
            if(!slot.hasItem())
                slot.set(ItemHandlerHelper.copyStackWithSize(stackHeld, amount));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(Player player, int slot)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slotObject = this.slots.get(slot);
        if(slotObject!=null&&slotObject.hasItem())
        {
            ItemStack itemstack1 = slotObject.getItem();
            itemstack = itemstack1.copy();
            if(slot < ownSlotCount)
            {
                if(!this.moveItemStackTo(itemstack1, ownSlotCount, this.slots.size(), true))
                    return ItemStack.EMPTY;
            }
            else if(!this.moveItemStackToWithMayPlace(itemstack1, 0, ownSlotCount))
                return ItemStack.EMPTY;

            if(itemstack1.isEmpty())
                slotObject.set(ItemStack.EMPTY);
            else
                slotObject.setChanged();
        }

        return itemstack;
    }

    protected boolean moveItemStackToWithMayPlace(ItemStack pStack, int pStartIndex, int pEndIndex)
    {
        return moveItemStackToWithMayPlace(slots, this::moveItemStackTo, pStack, pStartIndex, pEndIndex);
    }

    public static boolean moveItemStackToWithMayPlace(
            List<Slot> slots, MoveItemsFunc move, ItemStack pStack, int pStartIndex, int pEndIndex
    )
    {
        boolean inAllowedRange = true;
        int allowedStart = pStartIndex;
        for(int i = pStartIndex; i < pEndIndex; i++)
        {
            boolean mayplace = slots.get(i).mayPlace(pStack);
            if(inAllowedRange&&!mayplace)
            {
                if(move.moveItemStackTo(pStack, allowedStart, i, false))
                    return true;
                inAllowedRange = false;
            }
            else if(!inAllowedRange&&mayplace)
            {
                allowedStart = i;
                inAllowedRange = true;
            }
        }
        return inAllowedRange&&move.moveItemStackTo(pStack, allowedStart, pEndIndex, false);
    }

    public void receiveMessageFromScreen(CompoundTag nbt)
    {
    }

    @Override
    public void removed(@Nonnull Player player)
    {
        super.removed(player);
        setChanged.run();
    }

    @Override
    public boolean stillValid(@Nonnull Player pPlayer)
    {
        return isValid.test(pPlayer);
    }

    @SubscribeEvent
    public static void onContainerOpened(PlayerContainerEvent.Open ev)
    {
        if(ev.getContainer() instanceof ContainerMenu ieContainer&&ev.getEntity() instanceof ServerPlayer serverPlayer)
        {
            ieContainer.usingPlayers.add(serverPlayer);
            List<Pair<Integer, DataPair<?>>> list = new ArrayList<>();
            for(int i = 0; i < ieContainer.genericData.size(); i++)
                list.add(Pair.of(i, ieContainer.genericData.get(i).dataPair()));
        }
    }

    @SubscribeEvent
    public static void onContainerClosed(PlayerContainerEvent.Close ev)
    {
        if(ev.getContainer() instanceof ContainerMenu ieContainer&&ev.getEntity() instanceof ServerPlayer serverPlayer)
            ieContainer.usingPlayers.remove(serverPlayer);
    }

    public static MenuContext blockCtx(@Nullable MenuType<?> pMenuType, int pContainerId, BlockEntity be)
    {
        return new MenuContext(pMenuType, pContainerId, () -> {
            be.setChanged();
            if(be instanceof BaseBlockEntity BE)
                BE.markContainingBlockForUpdate(null);
        }, p -> {
            BlockPos pos = be.getBlockPos();
            Level level = be.getLevel();
            if(level==null||level.getBlockEntity(pos)!=be)
                return false;
            else
                return !(p.distanceToSqr(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D) > 64.0D);
        });
    }

    public static MenuContext itemCtx(
            @Nullable MenuType<?> pMenuType, int pContainerId, Inventory playerInv, EquipmentSlot slot, ItemStack stack
    )
    {
        return new MenuContext(pMenuType, pContainerId, () -> {
        }, p -> {
            if(p!=playerInv.player)
                return false;
            return ItemStack.isSame(p.getItemBySlot(slot), stack);
        });
    }

    public static MenuContext clientCtx(@Nullable MenuType<?> pMenuType, int pContainerId)
    {
        return new MenuContext(pMenuType, pContainerId, () -> {
        }, $ -> true);
    }

    protected record MenuContext(
            MenuType<?> type, int id, Runnable setChanged, Predicate<Player> isValid
    )
    {
    }

    public interface MoveItemsFunc
    {
        boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection);
    }
}