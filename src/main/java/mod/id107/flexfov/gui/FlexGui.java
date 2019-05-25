package mod.id107.flexfov.gui;

import java.io.IOException;

import mod.id107.flexfov.projection.Flex;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class FlexGui extends SettingsGui {
	
	private Slider FOVSlider;
	private GuiTextField FOVTextField;
	private GuiTextField ZoomTextField;

	public FlexGui(GuiScreen guiScreenIn) {
		super(guiScreenIn);
		Projection.setProjection(new Flex());
	}
	
	@Override
	public void updateScreen() {
		FOVTextField.updateCursorCounter();
		ZoomTextField.updateCursorCounter();
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		FOVSlider = new Slider(new Responder(), 18103, width / 2 -180, height / 6 + 24, 360, 20, "FOV", 0f, 360f, Projection.fov, 1f, null);
		buttonList.add(FOVSlider);
		FOVTextField = new GuiTextField(18121, fontRenderer, width / 2 - 155, height / 6 + 72, 150, 20);
		FOVTextField.setText(String.format("%s", Projection.getProjection().getFOV()));
		ZoomTextField = new GuiTextField(18122, fontRenderer, width / 2 + 5, height / 6 + 72, 150, 20);
		ZoomTextField.setText(String.format("%s", Projection.getProjection().getZoom()));
	}
	
	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		if (FOVTextField.isFocused()) {
			FOVTextField.textboxKeyTyped(typedChar, keyCode);
			try {
				Projection.getProjection().fov = Float.parseFloat(FOVTextField.getText());
			} catch (NumberFormatException e) {
				//This space intentionally left blank
			}
		} else if (ZoomTextField.isFocused()) {
			ZoomTextField.textboxKeyTyped(typedChar, keyCode);
			try {
				Projection.getProjection().zoom = Float.parseFloat(ZoomTextField.getText());
			} catch (NumberFormatException e) {
				//This space intentionally left blank
			}
		}
		
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		FOVTextField.mouseClicked(mouseX, mouseY, mouseButton);
		ZoomTextField.mouseClicked(mouseX, mouseY, mouseButton);
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		FOVTextField.drawTextBox();
		ZoomTextField.drawTextBox();
	}
}
