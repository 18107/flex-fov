package mod.id107.flexfov.projection;

import mod.id107.flexfov.Reader;

public class Equirectangular extends Projection {
	
	@Override
	public String getFragmentShader() {
		return Reader.read("flexfov:shaders/equirectangular.fs");
	}
}
