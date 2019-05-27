package mod.id107.flexfov.projection;

import org.lwjgl.opengl.GL20;

import mod.id107.flexfov.Reader;
import mod.id107.flexfov.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;

public class Fisheye extends Projection {
	
	public static boolean fullFrame = false;
	public static int fisheyeType = 3;
	
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
	
	@Override
	public void runShader(float partialTicks) {
		GL20.glUseProgram(Shader.getShaderProgram());

		int fullFrameUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "fullFrame");
		GL20.glUniform1i(fullFrameUniform, fullFrame ? 1 : 0);
		int fisheyeTypeUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "fisheyeType");
		GL20.glUniform1i(fisheyeTypeUniform, fisheyeType);
		
		super.runShader(partialTicks);
	}
}
