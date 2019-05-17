package mod.id107.flexfov.projection;

import mod.id107.flexfov.Reader;

public class Rectlinear extends Projection {

	@Override
	public String getName() {
		return "Standard";
	}
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/error.fs");
	}
	
	@Override
	public void saveRenderPass() {}
	
	@Override
	public void runShader() {}
	
	@Override
	public float getPassFOV(float fovIn) {
		return fovIn;
	}
}
