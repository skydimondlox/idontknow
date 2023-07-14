package net.skydimondlox.idontknowmod.register;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.block.entity.AlloyFurnaceBlockEntity;
import net.skydimondlox.idontknowmod.block.entity.ElectricPressBlockEntity;
import net.skydimondlox.idontknowmod.screen.AlloyFurnaceMenu;
import net.skydimondlox.idontknowmod.screen.ElectricPressMenu;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import net.skydimondlox.idontknowmod.api.Lib;
import net.skydimondlox.idontknowmod.screen.ContainerMenu;

public class MenuTypes {
        public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Lib.MOD_ID);

        public static final BEContainer<AlloyFurnaceBlockEntity, AlloyFurnaceMenu> ALLOY_FURNACE = registerBENew(
                Lib.GUIID_AlloyFurnace, AlloyFurnaceMenu::makeServer, AlloyFurnaceMenu::makeClient
        );
        public static final BEContainer<ElectricPressBlockEntity, ElectricPressMenu> ELECTRIC_PRESS = registerBENew(
                Lib.GUIID_ElectricPress, ElectricPressMenu::makeServer, ElectricPressMenu::makeClient
        );

        public static <T extends BlockEntity, C extends ContainerMenu>
            BEContainer<T, C> registerBENew(
            String name, BEContainerConstructor<T, C> container, ClientContainerConstructor<C> client
    )
        {
            RegistryObject<MenuType<C>> typeRef = registerType(name, client);
            return new BEContainer<>(typeRef, container);
        }

        public static <C extends ContainerMenu>
            ItemContainerTypeNew<C> registerItemNew(
            String name, NewItemContainerConstructor<C> container, ClientContainerConstructor<C> client
    )
        {
            RegistryObject<MenuType<C>> typeRef = registerType(name, client);
            return new ItemContainerTypeNew<>(typeRef, container);
        }

        private static <C extends ContainerMenu>
            RegistryObject<MenuType<C>> registerType(String name, ClientContainerConstructor<C> client)
        {
            return REGISTER.register(
                    name, () -> {
                        Mutable<MenuType<C>> typeBox = new MutableObject<>();
                        MenuType<C> type = new MenuType<>((id, inv) -> client.construct(typeBox.getValue(), id, inv));
                        typeBox.setValue(type);
                        return type;
                    }
            );
        }

        public static <T extends BlockEntity, C extends BaseContainerOld<? super T>>
        BEContainer<T, C> register(String name, BEContainerConstructor<T, C> container)
        {
            RegistryObject<MenuType<C>> typeRef = REGISTER.register(
                    name, () -> {
                        Mutable<MenuType<C>> typeBox = new MutableObject<>();
                        MenuType<C> type = new MenuType<>((IContainerFactory<C>)(windowId, inv, data) -> {
                            BlockPos pos = data.readBlockPos();
                            BlockEntity te = world.getBlockEntity(pos);
                            return container.construct(typeBox.getValue(), windowId, inv, (T)te);
                        });
                        typeBox.setValue(type);
                        return type;
                    }
            );
            return new BEContainer<>(typeRef, container);
        }

        public static <C extends AbstractContainerMenu>
            ItemContainerType<C> register(String name, ItemContainerConstructor<C> container)
        {
            RegistryObject<MenuType<C>> typeRef = REGISTER.register(
                    name, () -> {
                        Mutable<MenuType<C>> typeBox = new MutableObject<>();
                        MenuType<C> type = new MenuType<>((IContainerFactory<C>)(windowId, inv, data) -> {
                            // Matches IEBaseItem#openGui
                            int slotOrdinal = data.readInt();
                            EquipmentSlot slot = EquipmentSlot.values()[slotOrdinal];
                            return container.construct(typeBox.getValue(), windowId, inv, world, slot, stack);
                        });
                        typeBox.setValue(type);
                        return type;
                    }
            );
            return new ItemContainerType<>(typeRef, container);
        }

        public static <M extends AbstractContainerMenu>
            RegistryObject<MenuType<M>> registerSimple(String name, SimpleContainerConstructor<M> factory)
        {
            return REGISTER.register(
                    name, () -> {
                        Mutable<MenuType<M>> typeBox = new MutableObject<>();
                        MenuType<M> type = new MenuType<>((id, inv) -> factory.construct(typeBox.getValue(), id, inv));
                        typeBox.setValue(type);
                        return type;
                    }
            );
        }

        public static class BEContainer<T extends BlockEntity, C extends ContainerMenu>
        {
            private final RegistryObject<MenuType<C>> type;
            private final BEContainerConstructor<T, C> factory;

            private BEContainer(RegistryObject<MenuType<C>> type, BEContainerConstructor<T, C> factory)
            {
                this.type = type;
                this.factory = factory;
            }

            public C create(int windowId, Inventory playerInv, T tile)
            {
                return factory.construct(getType(), windowId, playerInv, tile);
            }

            public MenuType<C> getType()
            {
                return type.get();
            }
        }


        public record ItemContainerType<C extends AbstractContainerMenu>(
                RegistryObject<MenuType<C>> type, ItemContainerConstructor<C> factory
        )
        {
            public C create(int id, Inventory inv, Level w, EquipmentSlot slot, ItemStack stack)
            {
                return factory.construct(getType(), id, inv, w, slot, stack);
            }

            public MenuType<C> getType()
            {
                return type.get();
            }
        }

        public record ItemContainerTypeNew<C extends AbstractContainerMenu>(
                RegistryObject<MenuType<C>> type, NewItemContainerConstructor<C> factory
        )
        {
            public C create(int id, Inventory inv, EquipmentSlot slot, ItemStack stack)
            {
                return factory.construct(getType(), id, inv, slot, stack);
            }

            public MenuType<C> getType()
            {
                return type.get();
            }
        }

        public interface BEContainerConstructor<T extends BlockEntity, C extends ContainerMenu>
        {
            C construct(MenuType<C> type, int windowId, Inventory inventoryPlayer, T te);
        }

        public interface ClientContainerConstructor<C extends ContainerMenu>
        {
            C construct(MenuType<C> type, int windowId, Inventory inventoryPlayer);
        }

        public interface ItemContainerConstructor<C extends AbstractContainerMenu>
        {
            C construct(MenuType<C> type, int windowId, Inventory inventoryPlayer, Level world, EquipmentSlot slot, ItemStack stack);
        }

        public interface NewItemContainerConstructor<C extends AbstractContainerMenu>
        {
            C construct(MenuType<C> type, int windowId, Inventory inventoryPlayer, EquipmentSlot slot, ItemStack stack);
        }

        public interface SimpleContainerConstructor<C extends AbstractContainerMenu>
        {
            C construct(MenuType<?> type, int windowId, Inventory inventoryPlayer);
        }
    }
