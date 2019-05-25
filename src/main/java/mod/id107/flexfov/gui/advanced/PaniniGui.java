package mod.id107.flexfov.gui.advanced;

import mod.id107.flexfov.projection.Panini;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiScreen;

public class PaniniGui extends AdvancedGui {

	public PaniniGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Panini());
	}
}
