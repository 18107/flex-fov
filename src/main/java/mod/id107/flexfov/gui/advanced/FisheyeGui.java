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
		
		GuiButton button = new GuiButton(18160, width / 2 - 190, height / 6 + 36, 76, 20, "Orthographic");
		if (Fisheye.fisheyeType == 0) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18161, width / 2 - 114, height / 6 + 36, 76, 20, "Thoby");
		if (Fisheye.fisheyeType == 1) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18162, width / 2 - 38, height / 6 + 36, 76, 20, "Equisolid");
		if (Fisheye.fisheyeType == 2) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18163, width / 2 + 38, height / 6 + 36, 76, 20, "Equidistant");
		if (Fisheye.fisheyeType == 3) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18164, width / 2 + 114, height / 6 + 36, 76, 20, "Stereographic");
		if (Fisheye.fisheyeType == 4) button.enabled = false;
		buttonList.add(button);
		
		int fovSliderLimit = 360;
		if (Fisheye.fisheyeType == 1) fovSliderLimit = (int)Math.ceil(fovSliderLimit*0.713); //Thoby 256.68 degrees, slider goes up to 257
		if (Fisheye.fisheyeType == 0) fovSliderLimit = 180; //Orthographic
		FOVSlider = new Slider(new Responder(), 18103, width / 2 -180, height / 6 + 138, fovSliderLimit, 20, "FOV", 0f, fovSliderLimit, Math.min(fovSliderLimit, Projection.fov), 1f, null);
		buttonList.add(FOVSlider);
		buttonList.add(new GuiButton(18165, width / 2 - 155, height / 6 + 84, 150, 20, "Background Color: " + (Projection.skyBackground ? "Sky" : "Black")));
		buttonList.add(new GuiButton(18166, width / 2 - 155, height / 6 + 108, 150, 20, "Full Frame: " + (Fisheye.fullFrame ? "ON" : "OFF")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		
		switch (button.id) {
		case 18160:
		case 18161:
		case 18162:
		case 18163:
		case 18164:
			Fisheye.fisheyeType = button.id - 18160;
			mc.displayGuiScreen(new FisheyeGui(parentGuiScreen));
			break;
		case 18165: //Background Color
			Projection.skyBackground = !Projection.skyBackground;
			button.displayString = "Background Color: " + (Projection.skyBackground ? "Sky" : "Black");
			break;
		case 18166: //Full Frame
			Fisheye.fullFrame = !Fisheye.fullFrame;
			button.displayString = "Full Frame: " + (Fisheye.fullFrame ? "ON" : "OFF");
			break;
		}
	}
}
