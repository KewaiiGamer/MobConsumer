package kewaiigamer.everythinggrowablemob;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Created by kewaii on 06-01-2017.
 */
public class EntityAIEatBlock extends EntityAIBase {
    private EntityEverythingSheep entityEverythingSheep;
    private World world;
    int field_151502_a;

    public EntityAIEatBlock(EntityEverythingSheep entityEverythingSheep1) {
        this.entityEverythingSheep = entityEverythingSheep1;
        this.world = entityEverythingSheep1.worldObj;
        func_75248_a(7);
    }

    public boolean func_75250_a()
    {
        if (this.thisEntity.func_70681_au().nextInt(this.thisEntity.func_70631_g_() ? 50 : 1000) != 0) {
            return false;
        }
        int x = MathHelper.func_76128_c(this.thisEntity.field_70165_t);
        int y = MathHelper.func_76128_c(this.thisEntity.field_70163_u);
        int z = MathHelper.func_76128_c(this.thisEntity.field_70161_v);
        Block block = this.world.func_147439_a(x, y - 1, z);
        Block block2 = this.world.func_147439_a(x, y, z);
        int blockMeta = this.thisEntity.field_70170_p.func_72805_g(x, y - 1, z);
        int block2Meta = this.thisEntity.field_70170_p.func_72805_g(x, y, z);
        if (canBlockBeEaten(block2, block2Meta)) {
            return true;
        }
        if (canBlockBeEaten(block, blockMeta)) {
            return true;
        }
        return false;
    }

    public void func_75249_e() {
        this.field_151502_a = 0;
    }

    public boolean () {
        int x = MathHelper.floor_double(this.entityEverythingSheep.posX);
        int y = MathHelper.floor_double(this.entityEverythingSheep.posY);
        int z = MathHelper.floor_double(this.entityEverythingSheep.posZ);
        return (this.field_151502_a > 0) && ((this.thisEntity.stones.contains(this.world.func_147439_a(x, y - 1, z))) || (this.thisEntity.stones.contains(this.world.func_147439_a(x, y, z))));
    }

    public int func_151499_f() {
        return this.field_151502_a;
    }
    public void func_75246_d()
    {
        this.field_151502_a = Math.max(0, this.field_151502_a - 1);
        if (this.field_151502_a == 4)
        {
            int x = MathHelper.floor_double(this.entityEverythingSheep.posX);
            int y = MathHelper.floor_double(this.entityEverythingSheep.posY);
            int z = MathHelper.floor_double(this.entityEverythingSheep.posZ);

            Block block = this.world.func_147439_a(x, y - 1, z);
            Block block2 = this.world.func_147439_a(x, y, z);
            int blockMeta = this.thisEntity.field_70170_p.func_72805_g(x, y - 1, z);
            int block2Meta = this.thisEntity.field_70170_p.func_72805_g(x, y, z);

            int blockEaten = 0;
            if ((this.thisEntity.stones.contains(block)) || (this.thisEntity.stones.contains(block2))) {
                if (this.world.func_82736_K().func_82766_b("mobGriefing"))
                {
                    if ((block2 == Blocks.field_150348_b) || ((this.thisEntity.stones.contains(block2)) && ((this.thisEntity.getOreBlock() != block2) || ((this.thisEntity.getOreBlock() == block2) && (this.thisEntity.getOreMeta() != block2Meta)))))
                    {
                        blockEaten = 2;
                        this.world.func_72926_e(2001, x, y, z, Block.func_149682_b(block2) + (block2Meta << 12));
                        if (block2.func_149739_a().contains("tile.landmine")) {
                            this.world.func_147475_p(x, y, z);
                        }
                        this.world.func_147465_d(x, y, z, getNewBlock(block2, block2Meta), 0, 2);
                    }
                    else if ((block == Blocks.field_150348_b) || (this.thisEntity.getOreBlock() != block) || ((this.thisEntity.getOreBlock() == block) && (this.thisEntity.getOreMeta() != blockMeta)))
                    {
                        blockEaten = 1;
                        this.world.func_72926_e(2001, x, y, z, Block.func_149682_b(block) + (blockMeta << 12));
                        if (block.func_149739_a().contains("tile.landmine")) {
                            this.world.func_147475_p(x, y - 1, z);
                        }
                        this.world.func_147465_d(x, y - 1, z, getNewBlock(block, blockMeta), 0, 2);
                    }
                    this.thisEntity.eatBlockBonus();
                    int meta;
                    Block theBlock;
                    int meta;
                    if (blockEaten == 1)
                    {
                        Block theBlock = block;
                        int meta;
                        if (blockHasOneMeta(block)) {
                            meta = 0;
                        } else {
                            meta = blockMeta;
                        }
                    }
                    else
                    {
                        theBlock = block2;
                        int meta;
                        if (blockHasOneMeta(block2)) {
                            meta = 0;
                        } else {
                            meta = block2Meta;
                        }
                    }
                    if (theBlock != Blocks.field_150348_b) {
                        for (int l = 0; l < OreRegistryDraw.getBeBlocks().size(); l++)
                        {
                            BlockEntry k = (BlockEntry)OreRegistryDraw.getBeBlocks().get(l);
                            if (k.getBlock().equals(theBlock))
                            {
                                if (isWoodLog(theBlock)) {
                                    meta = setMetaForWood(meta);
                                }
                                this.thisEntity.setOreInteger(l + meta);
                                this.thisEntity.setMetadata((byte)meta);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public Block getNewBlock(Block block, int blockMeta)
    {
        String name = block.func_149739_a().toLowerCase();
        if (name.contains("ore"))
        {
            if ((block.func_149739_a().toLowerCase().contains("nether")) || (name.equals("tile.chromeore")) || ((name.equals("tile.tconstruct.stoneore")) && (blockMeta <= 2))) {
                return Blocks.field_150424_aL;
            }
            if (block.func_149739_a().toLowerCase().contains("end")) {
                return Blocks.field_150377_bs;
            }
            if (block.func_149739_a().toLowerCase().contains("gravel")) {
                return Blocks.field_150351_n;
            }
            return Blocks.field_150348_b;
        }
        if (block == Blocks.field_150417_aV) {
            return Blocks.field_150348_b;
        }
        if (block == Blocks.field_150348_b) {
            return Blocks.field_150347_e;
        }
        if (block == Blocks.field_150390_bg) {
            return Blocks.field_150446_ar;
        }
        if ((block.func_149739_a().toLowerCase().contains("cobble")) || (block == Blocks.field_150347_e) || (block == Blocks.field_150341_Y) || (block == Blocks.field_150446_ar)) {
            return Blocks.field_150351_n;
        }
        if (block == Blocks.field_150349_c) {
            return Blocks.field_150346_d;
        }
        return Blocks.field_150350_a;
    }

    private boolean canBlockBeEaten(Block block, int meta)
    {
        if ((isSlab(block)) && (this.thisEntity.stones.contains(Blocks.field_150333_U)))
        {
            if (!isSlab(this.thisEntity.getOreBlock())) {
                return true;
            }
            if (((BlockEntry)OreRegistryDraw.getBeBlocks().get(OreRegistryDraw.getAllBeBlocks().indexOf(block))).getMeta() != this.thisEntity.getMetadata()) {
                return true;
            }
            return false;
        }
        if ((isWoodLog(block)) && (isWoodLog(this.thisEntity.getOreBlock())) && (this.thisEntity.stones.contains(block)))
        {
            if (this.thisEntity.getOreBlock() != block) {
                return true;
            }
            return this.thisEntity.getOreMeta() != meta % 4;
        }
        if (this.thisEntity.stones.contains(block))
        {
            if ((OreRegistryDraw.getEatBlocks().contains(block)) || (block == Blocks.field_150348_b)) {
                return true;
            }
            if (block != this.thisEntity.getOreBlock()) {
                return true;
            }
            if (meta != this.thisEntity.getMetadata())
            {
                if (blockHasOneMeta(block)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public boolean blockHasOneMeta(Block block)
    {
        return (block.hasTileEntity(0)) || (block.equals(Blocks.field_150407_cf)) || (block.func_149645_b() == 10) || (block.func_149739_a().contains("trapdoor")) || ((block instanceof BlockTrapDoor));
    }

    public int setMetaForWood(int meta)
    {
        if (meta > 3) {
            return meta - meta / 4 * 4;
        }
        return meta;
    }

    private boolean isWoodLog(Block block)
    {
        if ((block.func_149645_b() == 31) && ((block.func_149739_a().contains("log")) || (block.func_149739_a().contains("wood")))) {
            return true;
        }
        return false;
    }

    public static boolean isSlab(Block block)
    {
        if ((block.func_149739_a().toLowerCase().contains("slab")) && (block != Blocks.field_150376_bx) && (block != Blocks.field_150373_bw)) {
            return true;
        }
        return false;
    }
}
}
