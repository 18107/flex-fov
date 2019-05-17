package mod.id107.flexfov.projection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
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
		return Reader.read("flexfov:shaders/equirectangular.fs"); //FIXME
	}
	
	@Override
	public void renderWorld(float partialTicks) {
		for (renderPass = 0; renderPass < 5; renderPass++) { //FIXME 5, not 0
			Minecraft mc = Minecraft.getMinecraft();
			mc.mcProfiler.endStartSection("gameRenderer");
			mc.entityRenderer.updateCameraAndRender(partialTicks, System.nanoTime()); //FIXME partialTicksPaused
			saveRenderPass();
		}
	}
	
	@Override
	public void onCameraSetup() {
		switch (renderPass) {
		case 0: //left
			GL11.glRotatef(-90, 0, 1, 0);
			break;
		case 1: //right
			GL11.glRotatef(90, 0, 1, 0);
			break;
		case 2: //top
			GL11.glRotatef(-90, 1, 0, 0);
			break;
		case 3: //bottom
			GL11.glRotatef(90, 1, 0, 0);
			break;
		case 4: //back
			GL11.glRotatef(180, 0, 1, 0);
			break;
		//case 5 front
		}
	}
	
	@Override
	public void runShader() {
		GL20.glUseProgram(Shader.getShaderProgram());
		
		//TODO uniform variables
		int aaUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "antialiasing");
		GL20.glUniform1i(aaUniform, 1);
		int pixelOffsetUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "pixelOffset[0]");
		GL20.glUniform2f(pixelOffsetUniform, 0, 0);
		
		int texFrontUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "texFront");
		GL20.glUniform1i(texFrontUniform, 5);
		int texBackUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "texBack");
		GL20.glUniform1i(texBackUniform, 4);
		int texLeftUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "texLeft");
		GL20.glUniform1i(texLeftUniform, 0);
		int texRightUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "texRight");
		GL20.glUniform1i(texRightUniform, 1);
		int texTopUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "texTop");
		GL20.glUniform1i(texTopUniform, 2);
		int texBottomUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "texBottom");
		GL20.glUniform1i(texBottomUniform, 3);
		
		int fovUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "fovx");
		GL20.glUniform1f(fovUniform, 360f);
		
		int backgroundUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "backgroundColor");
		GL20.glUniform4f(backgroundUniform, 0, 0, 0, 1);
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glOrtho(-1, 1, -1, 1, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		
		for (int i = 0; i < BufferManager.framebufferTextures.length; i++) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0+i);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, BufferManager.framebufferTextures[i]);
		}
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, BufferManager.framebufferTextures[0]);
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
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		for (int i = BufferManager.framebufferTextures.length-1; i >= 0; i--) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0+i);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		
		GL20.glUseProgram(0);
	}
}
