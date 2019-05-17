package mod.id107.flexfov.projection;

import org.lwjgl.opengl.GL11;

import mod.id107.flexfov.BufferManager;
import mod.id107.flexfov.Reader;
import mod.id107.flexfov.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;

public abstract class Projection {
	
	public static boolean active = true;
	protected static int renderPass;
	
	//Put all projections here
	private static final Projection[] projections = new Projection[] {new Equirectangular(), new Rectlinear()};
	private static int currentProjection = 0;
	
	public static Projection getProjection() {
		return projections[currentProjection];
	}
	
	public static Projection nextProjection() {
		currentProjection++;
		if (currentProjection >= projections.length) {
			currentProjection = 0;
		}
		Shader.deleteShaderProgram();
		Shader.createShaderProgram(projections[currentProjection]);
		return projections[currentProjection];
	}
	
	public abstract String getName();
	
	public String getVertexShader() {
		return Reader.read("flexfov:shaders/quad.vs");
	}
	public abstract String getFragmentShader();
	
	public void renderWorld(float partialTicks) {
		renderPass = 0;
	}
	
	public void onCameraSetup() {
		
	}
	
	public void saveRenderPass() {
		Framebuffer defaultFramebuffer = Minecraft.getMinecraft().getFramebuffer();
		Framebuffer targetFramebuffer = BufferManager.framebuffer;
		targetFramebuffer.bindFramebuffer(false);
		GL11.glViewport(0, 0, targetFramebuffer.framebufferTextureWidth, targetFramebuffer.framebufferTextureHeight);
		OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0,
				GL11.GL_TEXTURE_2D, BufferManager.framebufferTextures[renderPass], 0);
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glOrtho(-1, 1, -1, 1, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().getFramebuffer().framebufferTexture);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(BufferManager.getMinX(), BufferManager.getMinY());
			GL11.glVertex2f(-1, -1);
			GL11.glTexCoord2f(BufferManager.getMaxX(), BufferManager.getMinY());
			GL11.glVertex2f(1, -1);
			GL11.glTexCoord2f(BufferManager.getMaxX(), BufferManager.getMaxY());
			GL11.glVertex2f(1, 1);
			GL11.glTexCoord2f(BufferManager.getMinX(), BufferManager.getMaxY());
			GL11.glVertex2f(-1, 1);
		}
		GL11.glEnd();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		
		defaultFramebuffer.bindFramebuffer(true);
	}
	
	public void runShader() {
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
	}
	
	public float getPassFOV(float fovIn) {
		return BufferManager.getFOV();
	}
	
	public static int getRenderPass() {
		return renderPass;
	}
}
