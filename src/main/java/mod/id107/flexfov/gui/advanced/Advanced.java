package mod.id107.flexfov.gui.advanced;

import java.util.List;

import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public interface Advanced {

	public void initGui(List<GuiButton> buttonList, int width, int height);
	
	public void actionPerformed(GuiButton guiButton, GuiScreen parentScreen);
	
	public Projection getProjection();
}
