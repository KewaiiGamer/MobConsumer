package kewaiigamer.everythinggrowablemob;


import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        //ModBlocks.init();
        ModEntities.init();
    }

    public void init(FMLInitializationEvent e) {
        //NetworkRegistry.INSTANCE.registerGuiHandler(Instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
