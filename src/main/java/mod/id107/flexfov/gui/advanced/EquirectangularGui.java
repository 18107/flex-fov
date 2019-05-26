package mod.id107.flexfov.gui.advanced;

import mod.id107.flexfov.projection.Equirectangular;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class EquirectangularGui extends AdvancedGui {

	public EquirectangularGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Equirectangular());
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		buttonList.add(new GuiButton(18180, width / 2 - 155, height / 6 + 84, 150, 20, "Show Circle: " + (Equirectangular.drawCircle ? "ON" : "OFF")));
		buttonList.add(new GuiButton(18181, width / 2 - 155, height / 6 + 108, 150, 20, "Stabilize Pitch: " + (Equirectangular.stabilizePitch ? "ON" : "OFF")));
		buttonList.add(new GuiButton(18182, width / 2 + 5, height / 6 + 108, 150, 20, "Stabilize Yaw: " + (Equirectangular.stabilizeYaw ? "ON" : "OFF")));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		
		switch (button.id) {
		case 18180: //Show Circle
			Equirectangular.drawCircle = !Equirectangular.drawCircle;
			button.displayString = "Show Circle: " + (Equirectangular.drawCircle ? "ON" : "OFF");
			break;
		case 18181: //Stabilize Pitch
			Equirectangular.stabilizePitch = !Equirectangular.stabilizePitch;
			button.displayString = "Stabilize Pitch: " + (Equirectangular.stabilizePitch ? "ON" : "OFF");
			break;
		case 18182: //Stabilize Yaw
			Equirectangular.stabilizeYaw = !Equirectangular.stabilizeYaw;
			button.displayString = "Stabilize Yaw: " + (Equirectangular.stabilizeYaw ? "ON" : "OFF");
			break;
		}
	}
}
