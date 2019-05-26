package mod.id107.flexfov.gui.advanced;

import mod.id107.flexfov.projection.Cubic;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class CubicGui extends AdvancedGui {

	public CubicGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Cubic());
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		buttonList.add(new GuiButton(18140, width / 2 - 155, height / 6 + 84, 150, 20, "Background Color: " + (Projection.skyBackground ? "Sky" : "Black")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		
		switch (button.id) {
		case 18140: //Background Color
			Projection.skyBackground = !Projection.skyBackground;
			button.displayString = "Background Color: " + (Projection.skyBackground ? "Sky" : "Black");
			break;
		}
	}
}
