package mod.id107.flexfov;

import org.lwjgl.opengl.Display;

import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FlexFovEventHandler {

	public FlexFovEventHandler() {
		Shader.createShaderProgram(Projection.getProjection());
	}
	
	@SubscribeEvent
	public void onRenderTickStart(TickEvent.RenderTickEvent e) {
		if (Projection.active) {
			Minecraft mc = Minecraft.getMinecraft();
			if (!mc.skipRenderWorld && mc.world != null) {
				if (e.phase == TickEvent.Phase.START) {
					BufferManager.setupFrame();
					Projection.getProjection().renderWorld(e.renderTickTime);
				} else {
					Projection.getProjection().saveRenderPass();
					Projection.getProjection().runShader();
				}
			}
		}
	}
	
	@SubscribeEvent //TODO change event
	public void cameraSetup(EntityViewRenderEvent.CameraSetup e) {
		if (Projection.active) {
			e.getPitch(); //TODO
		}
	}
	
	@SubscribeEvent
	public void getFOVModifier(EntityViewRenderEvent.FOVModifier e) {
		if (Projection.active) {
			e.setFOV(Projection.getProjection().getPassFOV(e.getFOV()));
		}
	}
}
