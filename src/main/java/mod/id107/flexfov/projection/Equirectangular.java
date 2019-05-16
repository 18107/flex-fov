package mod.id107.flexfov.projection;

import mod.id107.flexfov.Log;
import mod.id107.flexfov.Reader;
import net.minecraft.client.Minecraft;

public class Equirectangular extends Projection {

	@Override
	public String getName() {
		return "Equirectangular";
	}
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/equirectangular.fs");
	}
	
	@Override
	public void renderWorld(float partialTicks) {
		for (renderPass = 0; renderPass < 5; renderPass++) {
			Minecraft mc = Minecraft.getMinecraft();
			mc.mcProfiler.endStartSection("gameRenderer");
			mc.entityRenderer.updateCameraAndRender(partialTicks, System.nanoTime()); //FIXME partialTicksPaused
			saveRenderPass();
		}
	}
	
	@Override
	public float getPassFOV(float fovIn) {
		return 90f;
	}
}
