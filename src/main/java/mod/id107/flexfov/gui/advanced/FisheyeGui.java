package mod.id107.flexfov.gui.advanced;

import mod.id107.flexfov.projection.Fisheye;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiScreen;

public class FisheyeGui extends AdvancedGui {

	public FisheyeGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Fisheye());
	}
}
