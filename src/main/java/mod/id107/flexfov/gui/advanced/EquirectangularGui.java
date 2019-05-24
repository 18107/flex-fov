package mod.id107.flexfov.gui.advanced;

import java.util.List;

import mod.id107.flexfov.projection.Equirectangular;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class EquirectangularGui implements Advanced {

	@Override
	public void initGui(List<GuiButton> buttonList, int width, int height) {
		buttonList.add(new GuiButton(18177, width / 2 + 5, height / 6 + 144, 150, 20,
				"Show Hand: " + (Minecraft.getMinecraft().entityRenderer.renderHand ? "ON" : "OFF")));
	}
	
	@Override
	public void actionPerformed(GuiButton guiButton, GuiScreen parentScreen) {
		switch (guiButton.id) {
		case 18177:
			Minecraft.getMinecraft().entityRenderer.renderHand = !Minecraft.getMinecraft().entityRenderer.renderHand;
			guiButton.displayString = "Show Hand: " + (Minecraft.getMinecraft().entityRenderer.renderHand ? "ON" : "OFF");
			break;
		}
	}
	
	@Override
	public Projection getProjection() {
		return new Equirectangular();
	}
}
