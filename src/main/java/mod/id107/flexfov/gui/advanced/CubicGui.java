package mod.id107.flexfov.gui.advanced;

import mod.id107.flexfov.projection.Cubic;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiScreen;

public class CubicGui extends AdvancedGui {

	public CubicGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Cubic());
	}
}
