package kewaiigamer.everythinggrowablemob;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ModEntities {

    public static void init() {
        int id = 1;
        EntityRegistry.registerModEntity(EntityEverythingSheep.class, "EverythingSheep", id++, EverythingGrowableMob.Instance, 64, 3, true, 0x996600, 0x00ff00);
        EntityRegistry.addSpawn(EntityEverythingSheep.class, 100, 3, 5, EnumCreatureType.MONSTER, Biomes.PLAINS, Biomes.ICE_PLAINS);
    //    LootTableList.register(EntityEverythingSheep.LOOT);
    }
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityEverythingSheep.class, RenderEverythingSheep.FACTORY);
    }
}
