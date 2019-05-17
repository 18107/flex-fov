package mod.id107.flexfov.projection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import mod.id107.flexfov.BufferManager;
import mod.id107.flexfov.Log;
import mod.id107.flexfov.Reader;
import mod.id107.flexfov.Shader;
import net.minecraft.client.Minecraft;

public class Equirectangular extends Projection {

	@Override
	public String getName() {
		return "Equirectangular";
	}
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/passthrough.fs"); //FIXME
	}
	
	@Override
	public void renderWorld(float partialTicks) {
		for (renderPass = 0; renderPass < 0; renderPass++) { //FIXME 5, not 0
			Minecraft mc = Minecraft.getMinecraft();
			mc.mcProfiler.endStartSection("gameRenderer");
			mc.entityRenderer.updateCameraAndRender(partialTicks, System.nanoTime()); //FIXME partialTicksPaused
			saveRenderPass();
		}
	}
	
	@Override
	public void runShader() {
		GL20.glUseProgram(Shader.getShaderProgram());
		
		//TODO uniform variables
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glOrtho(-1, 1, -1, 1, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, BufferManager.framebufferTextures[0]);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(-1, -1);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(1, -1);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(1, 1);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(-1, 1);
		}
		GL11.glEnd();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		
		GL20.glUseProgram(0);
	}
}
