package mod.id107.flexfov.gui;

import java.util.List;

import mod.id107.flexfov.projection.Projection;
import mod.id107.flexfov.projection.Rectlinear;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class StandardGui implements Settings {

	public StandardGui() {
		Projection.setProjection(new Rectlinear());
	}
	
	@Override
	public void initGui(List<GuiButton> buttonList, int width, int height, FontRenderer fontRendererobj) {
		
	}

	@Override
	public void updateScreen() {
		
	}
	
	@Override
	public void actionPerformed(GuiButton guiButton, GuiScreen parentScreen) {
		
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
