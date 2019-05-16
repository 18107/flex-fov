package mod.id107.flexfov;

import org.lwjgl.opengl.Display;

import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FlexFovEventHandler {

	@SubscribeEvent
	public void onRenderTickStart(TickEvent.RenderTickEvent e) {
		if (Projection.active) {
			Minecraft mc = Minecraft.getMinecraft();
			if (!mc.skipRenderWorld && mc.world != null) {
				if (e.phase == TickEvent.Phase.START) {
					//if screen resized recreate framebuffer
					if (BufferManager.framebuffer == null ||
							Math.min(Display.getWidth(), Display.getHeight()) !=
							BufferManager.framebuffer.framebufferTextureWidth) {
						BufferManager.deleteFramebuffer();
						BufferManager.createFramebuffer();
					}
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
			Log.info("Render pass " + Projection.getProjection().getRenderPass());
		}
	}
	
	@SubscribeEvent
	public void getFOVModifier(EntityViewRenderEvent.FOVModifier e) {
		if (Projection.active) {
			e.setFOV(Projection.getProjection().getPassFOV(e.getFOV()));
		}
	}
}
