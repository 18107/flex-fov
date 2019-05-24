package mod.id107.flexfov.projection;

import mod.id107.flexfov.Reader;

public class Panini extends Projection {

	@Override
	public String getName() {
		return "Panini";
	}
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/panini.fs");
	}
}
