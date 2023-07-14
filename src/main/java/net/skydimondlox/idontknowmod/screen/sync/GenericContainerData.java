
/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */

package net.skydimondlox.idontknowmod.screen.sync;

import net.skydimondlox.idontknowmod.api.IMutableEnergyStorage;
import net.skydimondlox.idontknowmod.screen.sync.GenericDataSerializers.DataSerializer;
import net.skydimondlox.idontknowmod.screen.sync.GenericDataSerializers.DataPair;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GenericContainerData<T>
{
    private final DataSerializer<T> serializer;
    private final Supplier<T> get;
    private final Consumer<T> set;
    private T current;

    public GenericContainerData(DataSerializer<T> serializer, Supplier<T> get, Consumer<T> set)
    {
        this.serializer = serializer;
        this.get = get;
        this.set = set;
    }

    public GenericContainerData(DataSerializer<T> serializer, GetterAndSetter<T> io)
    {
        this.serializer = serializer;
        this.get = io.getter();
        this.set = io.setter();
    }

    public static GenericContainerData<Integer> int32(Supplier<Integer> get, Consumer<Integer> set)
    {
        return new GenericContainerData<>(GenericDataSerializers.INT32, get, set);
    }

    public static GenericContainerData<?> energy(IMutableEnergyStorage storage)
    {
        return int32(storage::getEnergyStored, storage::setStoredEnergy);
    }

    public static GenericContainerData<Boolean> bool(Supplier<Boolean> get, Consumer<Boolean> set)
    {
        return new GenericContainerData<>(GenericDataSerializers.BOOLEAN, get, set);
    }

    public static GenericContainerData<Float> float32(Supplier<Float> get, Consumer<Float> set)
    {
        return new GenericContainerData<>(GenericDataSerializers.FLOAT, get, set);
    }

    public boolean needsUpdate()
    {
        T newValue = get.get();
        if(newValue==null&&current==null)
            return false;
        if(current!=null&&newValue!=null&&serializer.equals().test(current, newValue))
            return false;
        current = serializer.copy().apply(newValue);
        return true;
    }

    public void processSync(Object receivedData)
    {
        current = (T)receivedData;
        set.accept(serializer.copy().apply(current));
    }

    public DataPair<T> dataPair()
    {
        return new DataPair<>(serializer, current);
    }
}