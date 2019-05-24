package mod.id107.flexfov.gui;

import java.util.List;

import mod.id107.flexfov.gui.advanced.Advanced;
import mod.id107.flexfov.gui.advanced.EquirectangularGui;
import mod.id107.flexfov.gui.advanced.HammerGui;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class AdvancedGui implements Settings {
	
	private static Advanced gui = new EquirectangularGui();
	
	public AdvancedGui() {
		Projection.setProjection(gui.getProjection());
	}

	@Override
	public void initGui(List<GuiButton> buttonList, int width, int height, FontRenderer fontRenderer) {
		GuiButton button = new GuiButton(18130, width / 2 - 212, height / 6 + 24, 84, 20, "Cubic");
		button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18131, width / 2 - 127, height / 6 + 24, 84, 20, "Hammer");
		if (gui instanceof HammerGui) button.enabled = false;
		buttonList.add(button);
		
		button = new GuiButton(18134, width / 2 + 128, height / 6 + 24, 84, 20, "Equirectangular");
		if (gui instanceof EquirectangularGui) button.enabled = false;
		buttonList.add(button);
		
		gui.initGui(buttonList, width, height);
	}

	@Override
	public void actionPerformed(GuiButton guiButton, GuiScreen parentScreen) {
		switch (guiButton.id) {
		case 18131: //Hammer
			gui = new HammerGui();
			break;
		case 18134: //Equirectangular
			gui = new EquirectangularGui();
			break;
		default:
			gui.actionPerformed(guiButton, parentScreen);
			return;
		}
		Projection.setProjection(gui.getProjection());
		Minecraft.getMinecraft().displayGuiScreen(new SettingsGui(parentScreen));
	}

	@Override
	public void updateScreen() {
		
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		
	}

	@Override
	public void drawScreen() {
		
	}

}
