package kewaiigamer.everythinggrowablemob;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.common.IFMLSidedHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;


public class EntityEverythingSheep extends EntityAnimal implements IShearable {

    public EntityEverythingSheep(World worldIn) {
        super(worldIn);
    }

    public static final ResourceLocation LOOT = new ResourceLocation(EverythingGrowableMob.MODID, "entities/everything_sheep");


    EntityEverythingSheep entityEverythingSheep;

    private static final DataParameter<Optional<IBlockState>> GROWABLE_BLOCK = EntityDataManager.<Optional<IBlockState>>createKey(EntityEverythingSheep.class, DataSerializers.OPTIONAL_BLOCK_STATE);
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.<Boolean>createKey(EntityCreeper.class, DataSerializers.BOOLEAN);
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getMaxHealtDouble());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(getMovementSpeedDouble());
    }

    public double getMaxHealtDouble() {
        return 8.0D;
    }

    public double getMovementSpeedDouble() {
        return 0.23000000417232513D;
    }

    @Override
    public EntityEverythingSheep createChild(EntityAgeable ageable) {
        return new EntityEverythingSheep(this.worldObj);
    }


    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        IBlockState iblockstate = this.getGrowableBlock();
        compound.setBoolean("shared", this.getSheared());
        if (iblockstate != null) {
            compound.setShort("growable", (short) Block.getIdFromBlock(iblockstate.getBlock()));
            compound.setShort("growableData", (short) iblockstate.getBlock().getMetaFromState(iblockstate));
        }
    }


    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        IBlockState iblockstate;
        if (compound.hasKey("growable", 8))
        {
            iblockstate = Block.getBlockFromName(compound.getString("growable")).getStateFromMeta(compound.getShort("growableData") & 65535);
        }
        else
        {
            iblockstate = Block.getBlockById(compound.getShort("growable")).getStateFromMeta(compound.getShort("growableData") & 65535);
        }

        if (iblockstate == null || iblockstate.getBlock() == null || iblockstate.getMaterial() == Material.AIR)
        {
            iblockstate = null;
        }
        this.setSheared(compound.getBoolean("sheared"));
        this.setGrowableBlock(iblockstate);

    }

    protected void entityInit()
    {
        super.entityInit();
        ItemStack itemstack = new ItemStack(Blocks.STONE);
        this.dataManager.register(GROWABLE_BLOCK, Optional.<IBlockState>fromNullable(Block.getBlockFromItem(itemstack.getItem()).getDefaultState()));
    }
    @Nullable
    public IBlockState getGrowableBlock()
    {
        return (IBlockState)((Optional)this.dataManager.get(GROWABLE_BLOCK)).orNull();
    }

    public void setGrowableBlock(@Nullable IBlockState state)
    {
        this.dataManager.set(GROWABLE_BLOCK, Optional.fromNullable(state));
    }
  /*  public void setSheared(boolean sheared)
    {
        byte b0 = ((Byte)this.dataManager.get(DYE_COLOR)).byteValue();
        Random random = this.entityEverythingSheep.getRNG();
        World world = this.entityEverythingSheep.worldObj;
        int i = MathHelper.floor_double(this.entityEverythingSheep.posX - 1.0D + random.nextDouble() * 2.0D);
        int j = MathHelper.floor_double(this.entityEverythingSheep.posY + random.nextDouble() * 2.0D);
        int k = MathHelper.floor_double(this.entityEverythingSheep.posZ - 1.0D + random.nextDouble() * 2.0D);
        BlockPos blockpos = new BlockPos(i, j, k);
        IBlockState iblockstate = world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        RayTraceResult raytraceresult = world.rayTraceBlocks(new Vec3d((double)((float)MathHelper.floor_double(this.enderman.posX) + 0.5F), (double)((float)j + 0.5F), (double)((float)MathHelper.floor_double(this.enderman.posZ) + 0.5F)), new Vec3d((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F)), false, true, false);


        if (sheared)
        {

            this.entityEverythingSheep.setGrowableBlock(null); //, BlockStone.getBlockFromName(Blocks.STONE.getLocalizedName()).getMetaFromState());
        }
        else
        {
            //this.dataManager.set(DYE_COLOR, Byte.valueOf((byte)(b0 & -17)));
            this.entityEverythingSheep.setGrowableBlock();
        }
    }*/

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1D, Items.WHEAT, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(0, new AITransformToBlock(this));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return this.getGrowableBlock() != null && !this.isChild();
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        if (this.getSheared())
        {
            return LootTableList.ENTITIES_SHEEP;
        } else {
            return this.LOOT;
        }
    }

    public boolean getSheared()
    {
        return (((Boolean)this.dataManager.get(SHEARED)).booleanValue());
    }
    public void setSheared(boolean sheared)
    {
        if (sheared)
        {
            this.dataManager.set(SHEARED, Boolean.valueOf(true));
        }
        else
        {
            this.dataManager.set(SHEARED, Boolean.valueOf(false));
        }
    }


    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        this.setSheared(true);
        int i = 1 + this.rand.nextInt(3);
        AITransformToBlock aiTransformToBlock = new AITransformToBlock(entityEverythingSheep);
        java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        for (int j = 0; j < i; ++j)
            ret.add(new ItemStack(Item.getItemFromBlock(aiTransformToBlock.block), 1, this.getGrowableBlock().getBlock().getMetaFromState(aiTransformToBlock.iblockstate)));

        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }

    static class AITransformToBlock extends EntityAIBase {

        private final EntityEverythingSheep entityEverythingSheep;
        Block block;
        IBlockState iblockstate;
        World world;
        private static final DataParameter<Optional<IBlockState>> GROWABLE_BLOCK= EntityDataManager.<Optional<IBlockState>>createKey(EntityEverythingSheep.class, DataSerializers.OPTIONAL_BLOCK_STATE);
        public AITransformToBlock(EntityEverythingSheep entityEverythingSheep1)
        {
            this.entityEverythingSheep= entityEverythingSheep1;


        }
        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            if (this.entityEverythingSheep.getRNG().nextInt(this.entityEverythingSheep.isChild() ? 50 : 1000) != 0)
            {
                return false;
            }
            else
            {
                BlockPos blockpos = new BlockPos(this.entityEverythingSheep.posX, this.entityEverythingSheep.posY, this.entityEverythingSheep.posZ);
                return true; //this.world.getBlockState(blockpos.down()).getBlock() != null;
            }
        }

        public void updateTask(boolean sheared)
        {
            Random random = this.entityEverythingSheep.getRNG();
            world = this.entityEverythingSheep.worldObj;
            int i = MathHelper.floor_double(this.entityEverythingSheep.posX - 1.0D + random.nextDouble() * 2.0D);
            int j = MathHelper.floor_double(this.entityEverythingSheep.posY + random.nextDouble() * 2.0D);
            int k = MathHelper.floor_double(this.entityEverythingSheep.posZ - 1.0D + random.nextDouble() * 2.0D);
            BlockPos blockpos = new BlockPos(i, j, k);
            iblockstate = world.getBlockState(blockpos);
            block = iblockstate.getBlock();

            RayTraceResult raytraceresult = world.rayTraceBlocks(new Vec3d((double)((float)MathHelper.floor_double(this.entityEverythingSheep.posX) + 0.5F), (double)((float)j + 0.5F), (double)((float)MathHelper.floor_double(this.entityEverythingSheep.posZ) + 0.5F)), new Vec3d((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F)), false, true, false);
            boolean flag = raytraceresult != null && raytraceresult.getBlockPos().equals(blockpos);
            if (flag) {

                this.entityEverythingSheep.setGrowableBlock(iblockstate);
                world.setBlockToAir(blockpos);
                //, BlockStone.getBlockFromName(Blocks.STONE.getLocalizedName()).getMetaFromState());
            }
        }
    }

}
