package mod.id107.flexfov.projection;

import mod.id107.flexfov.Reader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;

public class Fisheye extends Projection {
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/fisheye.fs");
	}
	
	@Override
	public float[] getBackgroundColor() {
		if (skyBackground) {
			EntityRenderer er = Minecraft.getMinecraft().entityRenderer;
			return new float[] {er.fogColorRed, er.fogColorGreen, er.fogColorBlue};
		} else {
			return null;
		}
	}
}
