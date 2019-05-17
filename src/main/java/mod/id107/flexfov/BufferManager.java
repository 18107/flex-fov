package mod.id107.flexfov;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.shader.Framebuffer;

public class BufferManager {

	public static Framebuffer framebuffer;
	public static int[] framebufferTextures = new int[6];
	
	private static float minX;
	private static float maxX;
	private static float minY;
	private static float maxY;
	private static float fov;
	
	private static int displayWidth;
	private static int displayHeight;
	
	public static void setupFrame() {
		//if screen resized recreate framebuffer
		if (Display.getWidth() != displayWidth || Display.getHeight() != displayHeight) {
			deleteFramebuffer();
			createFramebuffer();
			displayWidth = Display.getWidth();
			displayHeight = Display.getHeight();
		}
	}
	
	public static void createFramebuffer() {
		if (framebuffer != null) return;
		
		int width = Math.min(Display.getWidth(), Display.getHeight());
		framebuffer = new Framebuffer(width, width, false);
		
		for (int i = 0; i < framebufferTextures.length; i++) {
			framebufferTextures[i] = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebufferTextures[i]);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, width,
					0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)null);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebufferTextures[i]); //FIXME remove this line if possible
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		if (Display.getWidth() >= Display.getHeight()) {
			//wide screen
			minY = 0;
			maxY = 1;
			
			float aspectRatio = Display.getHeight()/(float)Display.getWidth();
			
			minX = 0.5f - 0.5f*aspectRatio;
			maxX = 0.5f + 0.5f*aspectRatio;
			
			fov = 90f;
		} else {
			//tall screen //TODO correct FOV
			minX = 0;
			maxX = 1;
			
			float aspectRatio = Display.getWidth()/(float)Display.getHeight();
			
			minY = 0.5f - 0.5f*aspectRatio;
			maxY = 0.5f + 0.5f*aspectRatio;
			
			//Make horizontal fov 90 degrees
			fov = (float) Math.toDegrees(2*Math.atan(Math.tan(Math.toRadians(90f/2))/aspectRatio));
		}
	}
	
	public static void deleteFramebuffer() {
		if (framebuffer == null) return;
		for (int i = 0; i < framebufferTextures.length; i++) {
			GL11.glDeleteTextures(framebufferTextures[i]);
			framebufferTextures[i] = -1;
		}
		framebuffer.deleteFramebuffer();
		framebuffer = null;
	}

	public static float getMinX() {
		return minX;
	}

	public static float getMaxX() {
		return maxX;
	}

	public static float getMinY() {
		return minY;
	}

	public static float getMaxY() {
		return maxY;
	}
	
	public static float getFOV() {
		return fov;
	}
}
