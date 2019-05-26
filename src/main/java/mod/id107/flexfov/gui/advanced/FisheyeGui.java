package mod.id107.flexfov.gui.advanced;

import mod.id107.flexfov.gui.Slider;
import mod.id107.flexfov.gui.SettingsGui.Responder;
import mod.id107.flexfov.projection.Fisheye;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class FisheyeGui extends AdvancedGui {

	private Slider FOVSlider;
	
	public FisheyeGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Fisheye());
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		FOVSlider = new Slider(new Responder(), 18103, width / 2 -180, height / 6 + 36, 360, 20, "FOV", 0f, 360f, Projection.fov, 1f, null);
		buttonList.add(FOVSlider);
		buttonList.add(new GuiButton(18160, width / 2 - 155, height / 6 + 84, 150, 20, "Background Color: " + (Projection.skyBackground ? "Sky" : "Black")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		
		switch (button.id) {
		case 18160: //Background Color
			Projection.skyBackground = !Projection.skyBackground;
			button.displayString = "Background Color: " + (Projection.skyBackground ? "Sky" : "Black");
			break;
		}
	}
}
