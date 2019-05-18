package mod.id107.flexfov;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = FlexFov.MOD_ID, name = FlexFov.MOD_NAME, version = FlexFov.MOD_VERSION, useMetadata = true)
public class FlexFov {

	public static final String MOD_ID = "flexfov";
	public static final String MOD_NAME = "Flex FOV";
    public static final String MOD_VERSION = "1.0.0";
    
    public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ':';
    
    @Mod.Instance
    public static FlexFov instance;// TODO verify needed
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new FlexFovEventHandler());
    	Log.info(ForgeVersion.mcVersion);
    }
}
