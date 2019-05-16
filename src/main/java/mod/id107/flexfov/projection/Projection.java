package mod.id107.flexfov.projection;

import mod.id107.flexfov.Reader;

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
	
	public void saveRenderPass() {
		//TODO init framebuffer
		//TODO blit colorbuffer?
	}
	
	public void runShader() {
		//TODO
	}
	
	public float getPassFOV(float fovIn) {
		return fovIn;
	}
	
	public static int getRenderPass() {
		return renderPass;
	}
}
