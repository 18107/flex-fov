package mod.id107.flexfov.projection;

import mod.id107.flexfov.Reader;

public class Rectlinear extends Projection {
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/error.fs");
	}
	
	@Override
	public void renderWorld(float partialTicks) {}
	
	@Override
	public void onCameraSetup() {}
	
	@Override
	public void saveRenderPass() {}
	
	@Override
	public void runShader(float PartialTicks) {}
	
	@Override
	public void drawOverlay(float partialTicks) {}
	
	@Override
	public float getPassFOV(float fovIn) {
		return fovIn;
	}
}
