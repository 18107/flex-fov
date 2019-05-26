package mod.id107.flexfov.projection;

import mod.id107.flexfov.Reader;

public class Flex extends Projection {
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/flex.fs");
	}
	
	@Override
	public float getPassFOV(float fovIn) {
		float fov = getFOV();
		if (KEY_ZOOM.isKeyDown()) {
			fov /= getZoom();
		}
		if (fov <= 90) {
			if (fov == 0) {
				fov = 0.0001f;
			}
			return fov;
		}
		return super.getPassFOV(fovIn);
	}
	
	@Override
	public boolean getFullscreenGui() {
		return true;
	}
	
	@Override
	public int getAntialiasing() {
		return 16;
	}
	
	@Override
	public boolean getRenderHand() {
		return true;
	}
}
