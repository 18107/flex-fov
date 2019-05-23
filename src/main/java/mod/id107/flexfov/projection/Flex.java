package mod.id107.flexfov.projection;

import mod.id107.flexfov.Reader;

public class Flex extends Projection {

	@Override
	public String getName() {
		return "Flex";
	}
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/flex.fs");
	}
}
