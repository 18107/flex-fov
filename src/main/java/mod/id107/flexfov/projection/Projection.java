package mod.id107.flexfov.projection;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import mod.id107.flexfov.BufferManager;
import mod.id107.flexfov.Reader;
import mod.id107.flexfov.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.client.event.GuiScreenEvent;

public abstract class Projection {

	//TODO reorganize
	protected Minecraft mc = Minecraft.getMinecraft();
	public static boolean active = true;
	protected static int renderPass;
	public static boolean fullscreenGui = true;
	public static float fov = 360f;
	protected static boolean skyBackground = true;
	protected FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	protected static boolean hideGui = false;
	protected static boolean pauseOnLostFocus;
	protected static GuiScreen currentScreen;
	
	private static Projection currentProjection = new Flex();
	
	public static Projection getProjection() {
		return currentProjection;
	}
	
	public static void setProjection(Projection projection) {
		currentProjection = projection;
		Shader.deleteShaderProgram();
		Shader.createShaderProgram(projection);
	}
	
	public abstract String getName();
	
	public String getVertexShader() {
		return Reader.read("flexfov:shaders/quad.vs");
	}
	public abstract String getFragmentShader();
	
	public void renderWorld(float partialTicks) {
		//Render only 1 gui
		hideGui = mc.gameSettings.hideGUI;
		pauseOnLostFocus = mc.gameSettings.pauseOnLostFocus;
		currentScreen = mc.currentScreen;

		mc.gameSettings.pauseOnLostFocus = false;
		mc.currentScreen = null;

		for (renderPass = 0; renderPass < 5; renderPass++) {
			mc.mcProfiler.endStartSection("gameRenderer");
			mc.entityRenderer.updateCameraAndRender(partialTicks, System.nanoTime());
			mc.gameSettings.hideGUI = hideGui;
			saveRenderPass();
		}

		if (!fullscreenGui) {
			mc.gameSettings.pauseOnLostFocus = pauseOnLostFocus;
			mc.currentScreen = currentScreen;
		}
	}
	
	public void onCameraSetup() {
		//fix multiple guis
		//this needs to be called after EntityRenderer.isDrawBlockOutline()
		if (fullscreenGui || renderPass != 5) {
			Minecraft.getMinecraft().gameSettings.hideGUI = true;
		}
		
		//fix screen tearing
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, matrixBuffer);
		GL11.glLoadIdentity();

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

		//fix screen tearing
		GL11.glMultMatrix(matrixBuffer);

		//update chunk culling
		mc.renderGlobal.displayListEntitiesDirty = true;
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
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		int lightmap = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
		GL20.glUseProgram(Shader.getShaderProgram());

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

		int fovxUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "fovx");
		GL20.glUniform1f(fovxUniform, getProjection().getFinalFOV());
		int fovyUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "fovy");
		GL20.glUniform1f(fovyUniform, getProjection().getFinalFOV()*Display.getHeight()/Display.getWidth());

		int backgroundUniform = GL20.glGetUniformLocation(Shader.getShaderProgram(), "backgroundColor");
		float backgroundColor[] = getBackgroundColor();
		if (backgroundColor != null) {
			GL20.glUniform4f(backgroundUniform, backgroundColor[0], backgroundColor[1], backgroundColor[2], 1);
		} else {
			GL20.glUniform4f(backgroundUniform, 0, 0, 0, 1);
		}

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
		for (int i = BufferManager.framebufferTextures.length-1; i >= 0; i--) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0+i);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();

		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, lightmap);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		GL20.glUseProgram(0);
	}
	
	public void drawOverlay(float partialTicks) {
		if (fullscreenGui) {
			mc.gameSettings.hideGUI = hideGui;
			mc.gameSettings.pauseOnLostFocus = pauseOnLostFocus;
			mc.currentScreen = currentScreen;
			
			if (!hideGui) {
				if (mc.entityRenderer.renderHand) {
					GlStateManager.enableDepth();
					mc.entityRenderer.renderHand(partialTicks, 2);
				}
				final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
				int i1 = scaledresolution.getScaledWidth();
				int j1 = scaledresolution.getScaledHeight();
				GlStateManager.alphaFunc(516, 0.1F);
				mc.entityRenderer.setupOverlayRendering();
				mc.entityRenderer.renderItemActivation(i1, j1, partialTicks);
				this.mc.ingameGUI.renderGameOverlay(partialTicks);

				if (mc.currentScreen != null) {
					GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);

					final int k1 = Mouse.getX() * i1 / mc.displayWidth;
					final int l1 = j1 - Mouse.getY() * j1 / mc.displayHeight - 1;
					net.minecraftforge.client.ForgeHooksClient.drawScreen(mc.currentScreen, k1, l1, mc.getTickLength());
				}
			}
		}
	}
	
	public float[] getBackgroundColor() {
		return null;
	}
	
	public float getPassFOV(float fovIn) {
		return BufferManager.getFOV();
	}
	
	public float getFinalFOV() {
		return fov;
	}
	
	public static int getRenderPass() {
		return renderPass;
	}
}
