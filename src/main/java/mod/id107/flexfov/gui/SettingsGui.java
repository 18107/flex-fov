package mod.id107.flexfov.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class SettingsGui extends GuiScreen {

	private final GuiScreen parentGuiScreen;
	public static final String screenTitle = "Flex FOV Settings";
	
	private static Settings currentGui = new StandardGui();
	
	public SettingsGui(GuiScreen guiScreenIn) {
		parentGuiScreen = guiScreenIn;
	}
	
	@Override
	public void updateScreen() {
		currentGui.updateScreen();
	}
	
	@Override
	public void initGui() {
		GuiButton button = new GuiButton(18100, width / 2 - 190, height / 6 - 12, 120, 20, "Off");
		if (currentGui instanceof StandardGui) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18101, width / 2 - 60, height / 6 - 12, 120, 20, "Simple");
		if (currentGui instanceof SimpleGui) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18102, width / 2 + 70, height / 6 - 12, 120, 20, "Advanced");
		if (currentGui instanceof AdvancedGui) button.enabled = false;
		buttonList.add(button);
		
		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done")));
		
		currentGui.initGui(buttonList, width, height, fontRenderer);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			switch (button.id) {
			case 18100: //Standard
				currentGui = new StandardGui();
				mc.displayGuiScreen(new SettingsGui(parentGuiScreen));
				break;
			case 18101: //Simple
				currentGui = new SimpleGui();
				mc.displayGuiScreen(new SettingsGui(parentGuiScreen));
				break;
			case 18102: //Advanced
				currentGui = new AdvancedGui();
				mc.displayGuiScreen(new SettingsGui(parentGuiScreen));
				break;
			case 200: //Done
				mc.displayGuiScreen(parentGuiScreen);
				break;
			default:
				currentGui.actionPerformed(button, parentGuiScreen);
				break;
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		currentGui.keyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		currentGui.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, 15, 0xFFFFFF);
		currentGui.drawScreen();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
