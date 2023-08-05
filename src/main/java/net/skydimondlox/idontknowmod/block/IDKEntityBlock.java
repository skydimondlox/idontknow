package net.skydimondlox.idontknowmod.block;

/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;
import net.skydimondlox.idontknowmod.api.IDKProperties;
import net.skydimondlox.idontknowmod.api.utils.DirectionUtils;
import net.skydimondlox.idontknowmod.block.BlockInterfaces.*;
import net.skydimondlox.idontknowmod.block.ticking.ClientTickableBE;
import net.skydimondlox.idontknowmod.block.ticking.ServerTickableBE;
import net.skydimondlox.idontknowmod.gui.IDKBaseContainerOld;
import net.skydimondlox.idontknowmod.util.Utils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

public class IDKEntityBlock<T extends BlockEntity> extends BaseBlock implements IColouredBlock, EntityBlock
{
    private boolean hasColours = false;
    private final BiFunction<BlockPos, BlockState, T> makeEntity;
    private BEClassInspectedData classData;

    public IDKEntityBlock(BiFunction<BlockPos, BlockState, T> makeEntity, Properties blockProps)
    {
        this(makeEntity, blockProps, true);
    }

    public IDKEntityBlock(
            BiFunction<BlockPos, BlockState, T> makeEntity, Properties blockProps, boolean fitsIntoContainer
    )
    {
        super(blockProps, fitsIntoContainer);
        this.makeEntity = makeEntity;
    }

    public IDKEntityBlock(RegistryObject<BlockEntityType<T>> tileType, Properties blockProps)
    {
        this(tileType, blockProps, true);
    }

    public IDKEntityBlock(RegistryObject<BlockEntityType<T>> tileType, Properties blockProps, boolean fitsIntoContainer)
    {
        this((bp, state) -> tileType.get().create(bp, state), blockProps, fitsIntoContainer);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return makeEntity.apply(pPos, pState);
    }

    @Nullable
    @Override
    public <T2 extends BlockEntity>
    BlockEntityTicker<T2> getTicker(Level world, BlockState state, BlockEntityType<T2> type)
    {
        BlockEntityTicker<T2> baseTicker = getClassData().makeBaseTicker(world.isClientSide);
        return baseTicker;
    }

    private static final List<BooleanProperty> DEFAULT_OFF = ImmutableList.of(
            IDKProperties.MULTIBLOCKSLAVE, IDKProperties.ACTIVE, IDKProperties.MIRRORED
    );

    @Override
    protected BlockState getInitDefaultState()
    {
        BlockState ret = super.getInitDefaultState();
        if(ret.hasProperty(IDKProperties.FACING_ALL))
            ret = ret.setValue(IDKProperties.FACING_ALL, getDefaultFacing());
        else if(ret.hasProperty(IDKProperties.FACING_HORIZONTAL))
            ret = ret.setValue(IDKProperties.FACING_HORIZONTAL, getDefaultFacing());
        for(BooleanProperty defaultOff : DEFAULT_OFF)
            if(ret.hasProperty(defaultOff))
                ret = ret.setValue(defaultOff, false);
        return ret;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        BlockEntity tile = world.getBlockEntity(pos);
        if(state.getBlock()!=newState.getBlock())
        {
            if(tile instanceof BaseBlockEntity)
                ((BaseBlockEntity)tile).setOverrideState(state);
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, BlockEntity tile, ItemStack stack)
    {
        if(tile instanceof IAdditionalDrops)
        {
            //TODO remove or turn into loot entries?
            Collection<ItemStack> stacks = ((IAdditionalDrops)tile).getExtraDrops(player, state);
            if(stacks!=null&&!stacks.isEmpty())
                for(ItemStack s : stacks)
                    if(!s.isEmpty())
                        popResource(world, pos, s);
        }
        super.playerDestroy(world, player, pos, state, tile, stack);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity)
    {
        BlockEntity tile = world.getBlockEntity(pos);
        if(tile instanceof IEntityProof)
            return ((IEntityProof)tile).canEntityDestroy(entity);
        return super.canEntityDestroy(state, world, pos, entity);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player)
    {
        BlockEntity tile = world.getBlockEntity(pos);
        if(tile instanceof IBlockEntityDrop&&target instanceof BlockHitResult)
        {
            ItemStack s = ((IBlockEntityDrop)tile).getPickBlock(player, world.getBlockState(pos), target);
            if(!s.isEmpty())
                return s;
        }
        Item item = this.asItem();
        return item==Items.AIR?ItemStack.EMPTY: new ItemStack(item, 1);
    }


    @Override
    public boolean triggerEvent(BlockState state, Level worldIn, BlockPos pos, int eventID, int eventParam)
    {
        super.triggerEvent(state, worldIn, pos, eventID, eventParam);
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        return tileentity!=null&&tileentity.triggerEvent(eventID, eventParam);
    }

    protected Direction getDefaultFacing()
    {
        return Direction.NORTH;
    }

    @Override
    public void onIDKBlockPlacedBy(BlockPlaceContext context, BlockState state)
    {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockEntity tile = world.getBlockEntity(pos);
        Player placer = context.getPlayer();
        Direction side = context.getClickedFace();
        float hitX = (float)context.getClickLocation().x-pos.getX();
        float hitY = (float)context.getClickLocation().y-pos.getY();
        float hitZ = (float)context.getClickLocation().z-pos.getZ();

        if(tile instanceof IDirectionalBE directionalBE)
        {
            Direction f = directionalBE.getFacingForPlacement(context);
            directionalBE.setFacing(f);
            if(tile instanceof IAdvancedDirectionalBE advDirectional)
                advDirectional.onDirectionalPlacement(side, hitX, hitY, hitZ, placer);
        }
        if(tile instanceof IPlacementInteraction placementInteractionBE)
            placementInteractionBE.onBEPlaced(context);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if(!world.isClientSide)
        {
            BlockEntity tile = world.getBlockEntity(pos);
            if(tile instanceof BaseBlockEntity)
                ((BaseBlockEntity)tile).onNeighborBlockChange(fromPos);
        }
    }

    public IDKEntityBlock setHasColours()
    {
        this.hasColours = true;
        return this;
    }

    @Override
    public boolean hasCustomBlockColours()
    {
        return hasColours;
    }

    @Override
    public int getRenderColour(BlockState state, @Nullable BlockGetter worldIn, @Nullable BlockPos pos, int tintIndex)
    {
        if(worldIn!=null&&pos!=null)
        {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if(tile instanceof IColouredBE)
                return ((IColouredBE)tile).getRenderColour(tintIndex);
        }
        return 0xffffff;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        if(state.getBlock()==this)
        {
            BlockEntity te = world.getBlockEntity(pos);
            if(te instanceof ISelectionBounds)
                return ((ISelectionBounds)te).getSelectionShape(context);
        }
        return super.getShape(state, world, pos, context);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        if(getClassData().customCollisionBounds())
        {
            BlockEntity te = world.getBlockEntity(pos);
            if(te instanceof ICollisionBounds collisionBounds)
                return collisionBounds.getCollisionShape(context);
            else
                // Temporary hack: The vanilla Entity#isInWall passes nonsense positions to this method (always the head
                // center rather than the actual block). This stops our blocks from suffocating people when this happens
                return Shapes.empty();
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos)
    {
        if(world.getBlockState(pos).getBlock()==this)
        {
            BlockEntity te = world.getBlockEntity(pos);
            if(te instanceof ISelectionBounds)
                return ((ISelectionBounds)te).getSelectionShape(null);
        }
        return super.getInteractionShape(state, world, pos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return getClassData().hasComparatorOutput;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos)
    {
        BlockEntity te = world.getBlockEntity(pos);
        if(te instanceof BlockInterfaces.IComparatorOverride compOverride)
            return compOverride.getComparatorInputOverride();
        return 0;
    }


    @Override
    @SuppressWarnings("deprecation")
    public int getSignal(BlockState blockState, BlockGetter world, BlockPos pos, Direction side)
    {
        BlockEntity te = world.getBlockEntity(pos);
        if(te instanceof BlockInterfaces.IRedstoneOutput rsOutput)
            return rsOutput.getWeakRSOutput(side);
        return 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getDirectSignal(BlockState blockState, BlockGetter world, BlockPos pos, Direction side)
    {
        BlockEntity te = world.getBlockEntity(pos);
        if(te instanceof BlockInterfaces.IRedstoneOutput rsOutput)
            return rsOutput.getStrongRSOutput(side);
        return 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isSignalSource(BlockState state)
    {
        return getClassData().emitsRedstone();
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side)
    {
        BlockEntity te = world.getBlockEntity(pos);
        if(te instanceof BlockInterfaces.IRedstoneOutput rsOutput)
            return rsOutput.canConnectRedstone(side);
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity)
    {
        BlockEntity te = world.getBlockEntity(pos);
        if(te instanceof BaseBlockEntity)
            ((BaseBlockEntity)te).onEntityCollision(world, entity);
    }

    public static boolean areAllReplaceable(BlockPos start, BlockPos end, BlockPlaceContext context)
    {
        Level w = context.getLevel();
        return BlockPos.betweenClosedStream(start, end).allMatch(
                pos -> {
                    BlockPlaceContext subContext = BlockPlaceContext.at(context, pos, context.getClickedFace());
                    return w.getBlockState(pos).canBeReplaced(subContext);
                });
    }

    private BEClassInspectedData getClassData()
    {
        if(this.classData==null)
        {
            T tempBE = makeEntity.apply(BlockPos.ZERO, getInitDefaultState());
            this.classData = new BEClassInspectedData(
                    tempBE instanceof ServerTickableBE,
                    tempBE instanceof ClientTickableBE,
                    tempBE instanceof IComparatorOverride,
                    tempBE instanceof IRedstoneOutput,
                    tempBE instanceof ICollisionBounds
            );
        }
        return this.classData;
    }

    private record BEClassInspectedData(
            boolean serverTicking,
            boolean clientTicking,
            boolean hasComparatorOutput,
            boolean emitsRedstone,
            boolean customCollisionBounds
    )
    {
        @Nullable
        public <T extends BlockEntity> BlockEntityTicker<T> makeBaseTicker(boolean isClient)
        {
            if(serverTicking&&!isClient)
                return ServerTickableBE.makeTicker();
            else if(clientTicking&&isClient)
                return ClientTickableBE.makeTicker();
            else
                return null;
        }
    }
}