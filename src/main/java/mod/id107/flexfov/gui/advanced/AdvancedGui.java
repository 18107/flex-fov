package mod.id107.flexfov.gui.advanced;

import mod.id107.flexfov.gui.SettingsGui;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public abstract class AdvancedGui extends SettingsGui {

	private static int gui = 4;
	
	public AdvancedGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
	}
	
	public static AdvancedGui getGui(GuiScreen guiScreenIn) {
		switch(gui) {
		case 0:
			return new CubicGui(guiScreenIn);
		case 1:
			return new HammerGui(guiScreenIn);
		case 2:
			return new FisheyeGui(guiScreenIn);
		case 3:
			return new PaniniGui(guiScreenIn);
		default:
			return new EquirectangularGui(guiScreenIn);
		}
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		GuiButton button = new GuiButton(18130, width / 2 - 212, height / 6 + 12, 84, 20, "Cubic");
		if (this instanceof CubicGui) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18131, width / 2 - 127, height / 6 + 12, 84, 20, "Hammer");
		if (this instanceof HammerGui) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18132, width / 2 - 42, height / 6 + 12, 84, 20, "Fisheye");
		if (this instanceof FisheyeGui) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18133, width / 2 + 43, height / 6 + 12, 84, 20, "Panini");
		if (this instanceof PaniniGui) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18134, width / 2 + 128, height / 6 + 12, 84, 20, "Equirectangular");
		if (this instanceof EquirectangularGui) button.enabled = false;
		buttonList.add(button);
		
		buttonList.add(new GuiButton(18135, width / 2 - 155, height / 6 + 60, 150, 20, "Fullscreen Gui: " + (Projection.fullscreenGui ? "ON" : "OFF")));
		String aa = Projection.antialiasing == 1 ? "OFF" : Projection.antialiasing == 4 ? "LOW" : "HIGH";
		buttonList.add(new GuiButton(18136, width / 2 + 5, height / 6 + 60, 150, 20, "Antialiasing: " + aa));
		
		buttonList.add(new GuiButton(18138, width / 2 + 5, height / 6 + 84, 150, 20, "Show Hand: " + (Projection.renderHand ? "ON" : "OFF")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		
		switch (button.id) {
		case 18130: //Cubic
			gui = 0;
			mc.displayGuiScreen(new CubicGui(parentGuiScreen));
			break;
		case 18131: //Hammer
			gui = 1;
			mc.displayGuiScreen(new HammerGui(parentGuiScreen));
			break;
		case 18132: //Fisheye
			gui = 2;
			mc.displayGuiScreen(new FisheyeGui(parentGuiScreen));
			break;
		case 18133: //Panini
			gui = 3;
			mc.displayGuiScreen(new PaniniGui(parentGuiScreen));
			break;
		case 18134: //Equirectangular
			gui = 4;
			mc.displayGuiScreen(new EquirectangularGui(parentGuiScreen));
			break;
			
		case 18135: //Fullscreen gui
			Projection.fullscreenGui = !Projection.fullscreenGui;
			button.displayString = "Fullscreen Gui: " + (Projection.fullscreenGui ? "ON" : "OFF");
			break;
		case 18136: //Antialiasing
			switch (Projection.antialiasing) {
			case 1:
				Projection.antialiasing = 4;
				break;
			case 4:
				Projection.antialiasing = 16;
				break;
			default:
			case 16:
				Projection.antialiasing = 1;
				break;
			}
			String aa = Projection.antialiasing == 1 ? "OFF" : Projection.antialiasing == 4 ? "LOW" : "HIGH";
			button.displayString = "Antialiasing: " + aa;
			break;
		case 18138: //Show Hand
			Projection.renderHand = !Projection.renderHand;
			button.displayString = "Show Hand: " + (Projection.renderHand ? "ON" : "OFF");
			break;
		}
	}
}
