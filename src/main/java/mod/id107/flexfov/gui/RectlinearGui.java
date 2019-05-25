package mod.id107.flexfov.gui;

import mod.id107.flexfov.projection.Projection;
import mod.id107.flexfov.projection.Rectlinear;
import net.minecraft.client.gui.GuiScreen;

public class RectlinearGui extends SettingsGui {

	public RectlinearGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Rectlinear());
	}
}
