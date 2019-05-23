package mod.id107.flexfov;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import mod.id107.flexfov.gui.SettingsGui;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FlexFovEventHandler {

	public FlexFovEventHandler() {
		Shader.createShaderProgram(Projection.getProjection());
	}
	
	@SubscribeEvent
	public void initGui(InitGuiEvent.Post e) {
		if (e.getGui() instanceof GuiOptions) {
			e.getButtonList().add(new GuiButton(18107, e.getGui().width / 2 - 155,
					e.getGui().height / 6 + 12, 150, 20, SettingsGui.screenTitle));
		}
	}
	
	@SubscribeEvent
	public void actionPerformed(ActionPerformedEvent.Pre e) {
		if (e.getGui() instanceof GuiOptions && e.getButton().id == 18107) {
			e.getGui().mc.gameSettings.saveOptions();
			e.getGui().mc.displayGuiScreen(new SettingsGui(e.getGui()));
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH) //render before other overlays
	public void onRenderTickStart(TickEvent.RenderTickEvent e) {
		if (Projection.active) {
			Minecraft mc = Minecraft.getMinecraft();
			if (!mc.skipRenderWorld && mc.world != null) {
				Projection projection = Projection.getProjection();
				if (e.phase == TickEvent.Phase.START) {
					BufferManager.setupFrame();
					projection.renderWorld(mc.isGamePaused() ? mc.renderPartialTicksPaused : e.renderTickTime);
				} else {
					projection.saveRenderPass();
					projection.runShader();
					projection.drawOverlay(mc.isGamePaused() ? mc.renderPartialTicksPaused : e.renderTickTime);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void cameraSetup(EntityViewRenderEvent.CameraSetup e) {
		if (Projection.active) {
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
