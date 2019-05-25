package mod.id107.flexfov.gui;

import mod.id107.flexfov.gui.advanced.AdvancedGui;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public abstract class SettingsGui extends GuiScreen {

	public static final String screenTitle = "Flex FOV Settings";
	protected final GuiScreen parentGuiScreen;
	private static int gui = 0;
	
	public SettingsGui(GuiScreen guiScreenIn) {
		parentGuiScreen = guiScreenIn;
	}
	
	public static SettingsGui getGui(GuiScreen guiScreenIn) {
		switch (gui) {
		default: //Rectlinear
			return new RectlinearGui(guiScreenIn);
		case 1: //Flex
			return new FlexGui(guiScreenIn);
		case 2: //Advanced
			return AdvancedGui.getGui(guiScreenIn);
		}
	}
	
	@Override
	public void initGui() {
		GuiButton button = new GuiButton(18100, width / 2 - 190, height / 6 - 12, 120, 20, "Off");
		if (this instanceof RectlinearGui) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18101, width / 2 - 60, height / 6 - 12, 120, 20, "Simple");
		if (this instanceof FlexGui) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18102, width / 2 + 70, height / 6 - 12, 120, 20, "Advanced");
		if (this instanceof AdvancedGui) button.enabled = false;
		buttonList.add(button);
		
		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 18100: //Off
			gui = 0;
			mc.displayGuiScreen(new RectlinearGui(parentGuiScreen));
			break;
		case 18101: //Simple
			gui = 1;
			mc.displayGuiScreen(new FlexGui(parentGuiScreen));
			break;
		case 18102: //Advanced
			gui = 2;
			mc.displayGuiScreen(AdvancedGui.getGui(parentGuiScreen));
			break;
		case 200: //Done
			mc.displayGuiScreen(parentGuiScreen);
			break;
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, 15, 0xFFFFFF);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	public class Responder implements GuiResponder {
		@Override
		public void setEntryValue(int id, boolean value) {
			
		}
		
		@Override
		public void setEntryValue(int id, float value) {
			//FOV
			if (id == 18103) {
				Projection.fov = value;
			}
		}
		
		@Override
		public void setEntryValue(int id, String value) {
			
		}
	}
}
