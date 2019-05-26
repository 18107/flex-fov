package mod.id107.flexfov.gui.advanced;

import mod.id107.flexfov.gui.Slider;
import mod.id107.flexfov.gui.SettingsGui.Responder;
import mod.id107.flexfov.projection.Panini;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiScreen;

public class PaniniGui extends AdvancedGui {

	private Slider FOVSlider;
	
	public PaniniGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Panini());
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		FOVSlider = new Slider(new Responder(), 18103, width / 2 -180, height / 6 + 36, 360, 20, "FOV", 0f, 360f, Projection.fov, 1f, null);
		buttonList.add(FOVSlider);
	}
}
