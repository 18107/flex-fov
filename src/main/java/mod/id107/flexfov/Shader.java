package mod.id107.flexfov;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import mod.id107.flexfov.projection.Projection;

public class Shader {

	private static int shaderProgram;
	private static int vertexShader;
	private static int fragmentShader;
	
	public static void createShaderProgram(Projection projection) {
		if (shaderProgram != 0) return;
		shaderProgram = GL20.glCreateProgram();
		vertexShader = createShader(projection.getVertexShader(), GL20.GL_VERTEX_SHADER);
		fragmentShader = createShader(projection.getFragmentShader(), GL20.GL_FRAGMENT_SHADER);
		GL20.glAttachShader(shaderProgram, vertexShader);
		GL20.glAttachShader(shaderProgram, fragmentShader);
		GL20.glBindAttribLocation(shaderProgram, 0, "vertex");
		GL30.glBindFragDataLocation(shaderProgram, 0, "color");
		GL20.glLinkProgram(shaderProgram);
		int linked = GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS);
		String programLog = GL20.glGetProgramInfoLog(shaderProgram, GL20.glGetProgrami(shaderProgram, GL20.GL_INFO_LOG_LENGTH));
		if (programLog.trim().length() > 0) {
			System.err.println(programLog);
		}
		if (linked == 0) {
			throw new AssertionError("Could not link program");
		}
	}
	
	private static int createShader(String source, int type) {
		int shader = GL20.glCreateShader(type);
		GL20.glShaderSource(shader, source);
		GL20.glCompileShader(shader);
		int compiled = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
		String shaderLog = GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH));
		if (shaderLog.trim().length() > 0) {
			System.err.println(shaderLog);
		}
		if (compiled == 0) {
			throw new AssertionError("Could not compile shader");
		}
		return shader;
	}
	
	public static void deleteShaderProgram() {
		if (shaderProgram == 0) return;
		GL20.glDetachShader(shaderProgram, vertexShader);
		GL20.glDetachShader(shaderProgram, fragmentShader);
		GL20.glDeleteShader(vertexShader);
		vertexShader = 0;
		GL20.glDeleteShader(fragmentShader);
		fragmentShader = 0;
		GL20.glDeleteProgram(shaderProgram);
		shaderProgram = 0;
	}
	
	public static int getShaderProgram() {
		return shaderProgram;
	}
}
