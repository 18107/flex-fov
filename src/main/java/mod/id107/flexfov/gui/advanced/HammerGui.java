package mod.id107.flexfov.gui.advanced;

import mod.id107.flexfov.projection.Hammer;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiScreen;

public class HammerGui extends AdvancedGui {

	public HammerGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Hammer());
	}
}
