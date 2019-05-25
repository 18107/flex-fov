package mod.id107.flexfov.gui.advanced;

import mod.id107.flexfov.projection.Equirectangular;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiScreen;

public class EquirectangularGui extends AdvancedGui {

	public EquirectangularGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Equirectangular());
	}
}
