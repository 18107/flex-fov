package mod.id107.flexfov;

import mod.id107.flexfov.projection.Projection;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = FlexFov.MOD_ID, name = FlexFov.MOD_NAME, version = FlexFov.MOD_VERSION, useMetadata = true, acceptableRemoteVersions = "*")
public class FlexFov {

	public static final String MOD_ID = "flexfov";
	public static final String MOD_NAME = "Flex FOV";
    public static final String MOD_VERSION = "1.0.3";
    
    public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ':';
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new FlexFovEventHandler());
    	Log.info(ForgeVersion.mcVersion);
    	ClientRegistry.registerKeyBinding(Projection.KEY_ZOOM);
    }
}
