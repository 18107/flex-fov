package mod.id107.flexfov.projection;

import org.lwjgl.opengl.GL20;

import mod.id107.flexfov.Reader;
import mod.id107.flexfov.Shader;
import net.minecraft.entity.Entity;

public class Equirectangular extends Projection {
	
	public static boolean drawCircle = false;
	public static boolean stabilizePitch = false;
	public static boolean stabilizeYaw = false;
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/equirectangular.fs");
	}
	
	@Override
	public void runShader(float partialTicks) {
		GL20.glUseProgram(Shader.getShaderProgram());
		
		int circleUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "drawCircle");
		GL20.glUniform1i(circleUniform, drawCircle ? 1 : 0);
		
		Entity entity = mc.getRenderViewEntity();
		float pitch = 0;
		float yaw = 0;
		if (stabilizePitch) {
			pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
		}
		if (stabilizeYaw) {
			yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
		}
		if (mc.gameSettings.thirdPersonView == 2) {
			pitch = -pitch;
		}
		
		int angleUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "rotation");
		GL20.glUniform2f(angleUniform, yaw, pitch);
		
		super.runShader(partialTicks);
	}
}
