package mod.id107.flexfov.projection;

import mod.id107.flexfov.Reader;

public class Hammer extends Projection {

	@Override
	public String getName() {
		return "Hammer";
	}
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/hammer.fs");
	}
}
