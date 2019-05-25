package mod.id107.flexfov.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;

import mod.id107.flexfov.projection.Flex;
import mod.id107.flexfov.projection.Projection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;

public class SimpleGui implements Settings {
	
	private Slider FOVSlider;
	private GuiTextField FOVTextField;
	private GuiTextField ZoomTextField;

	public SimpleGui() {
		Projection.setProjection(new Flex());
	}

	@Override
	public void updateScreen() {
		FOVTextField.updateCursorCounter();
		ZoomTextField.updateCursorCounter();
	}
	
	@Override
	public void initGui(List<GuiButton> buttonList, int width, int height, FontRenderer fontRenderer) {
		FOVSlider = new Slider(new Responder(), 18120, width / 2 -180, height / 6 + 24, 360, 20, "FOV", 0f, 360f, Projection.fov, 1f, null);
		buttonList.add(FOVSlider);
		FOVTextField = new GuiTextField(18121, fontRenderer, width / 2 - 155, height / 6 + 72, 150, 20);
		FOVTextField.setText(String.format("%s", Projection.getProjection().getFOV()));
		ZoomTextField = new GuiTextField(18122, fontRenderer, width / 2 + 5, height / 6 + 72, 150, 20);
		ZoomTextField.setText(String.format("%s", Projection.getProjection().getZoom()));
		buttonList.add(new GuiButton(18124, width / 2 + 5, height / 6 + 96, 150, 20, "Show Hand: " + (Minecraft.getMinecraft().entityRenderer.renderHand ? "ON" : "OFF")));
	}
	
	@Override
	public void actionPerformed(GuiButton guiButton, GuiScreen parentScreen) {
		if (guiButton.id == 18124) {
			Minecraft.getMinecraft().entityRenderer.renderHand = !Minecraft.getMinecraft().entityRenderer.renderHand;
			guiButton.displayString = "Show Hand: " + (Minecraft.getMinecraft().entityRenderer.renderHand ? "ON" : "OFF");
		}
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) { //FIXME
			return;
		}
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
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		FOVTextField.mouseClicked(mouseX, mouseY, mouseButton);
		ZoomTextField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void drawScreen() {
		FOVTextField.drawTextBox();
		ZoomTextField.drawTextBox();
	}
	
	public class Responder implements GuiResponder {
		@Override
		public void setEntryValue(int id, boolean value) {
			
		}
		
		@Override
		public void setEntryValue(int id, float value) {
			//FOV
			if (id == 18120) {
				Projection.fov = value;
			}
		}
		
		@Override
		public void setEntryValue(int id, String value) {
			
		}
	}
}
