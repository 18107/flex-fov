package mod.id107.flexfov;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
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
	
	@SubscribeEvent
	public void drawScreen(GuiScreenEvent.DrawScreenEvent.Pre e) {
		if (Projection.active) {
			Projection.getProjection().onDrawGui(e);
		}
	}
	
	@SubscribeEvent
	public void cameraSetup(EntityViewRenderEvent.CameraSetup e) {
		if (Projection.active) {
			GL11.glTranslatef(0, 0, -0.05f);
			Projection.getProjection().onCameraSetup();
		}
	}
	
	@SubscribeEvent
	public void getFOVModifier(EntityViewRenderEvent.FOVModifier e) {
		if (Projection.active) {
			e.setFOV(Projection.getProjection().getPassFOV(e.getFOV()));
		}
	}
}
