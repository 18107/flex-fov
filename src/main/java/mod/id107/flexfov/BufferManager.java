package mod.id107.flexfov;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.shader.Framebuffer;

public class BufferManager {

	public static Framebuffer framebuffer;
	private static int[] framebufferTextures = new int[6];
	
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
}
