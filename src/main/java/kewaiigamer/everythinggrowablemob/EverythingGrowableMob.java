package kewaiigamer.everythinggrowablemob;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = EverythingGrowableMob.MODID, name = EverythingGrowableMob.MODNAME, version = EverythingGrowableMob.VERSION, acceptedMinecraftVersions = "[1.10.2]")
public class EverythingGrowableMob
{
    public static final String MODID = "egmob";
    public static final String MODNAME = "Everything Growable Mob";
    public static final String VERSION = "0.1";
    public static final String COMMON_PROXY_CLASS = "kewaiigamer.everythinggrowablemob.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "kewaiigamer.everythinggrowablemob.ClientProxy";

    @Mod.Instance(MODID)
    public static EverythingGrowableMob Instance;

    @SidedProxy(serverSide = COMMON_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS, modId = MODID)
    public static CommonProxy proxy;



    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        EverythingGrowableMob.proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        EverythingGrowableMob.proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        EverythingGrowableMob.proxy.postInit(e);

    }
}
