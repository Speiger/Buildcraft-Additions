package buildcraftAdditions.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiKEB extends GuiContainer {
	public ResourceLocation texture = new ResourceLocation("bcadditions","textures/gui/KineticEnergyBuffer.png");
	private TileKineticEnergyBufferBase keb;
	private GuiButton north, east, south, west, up, down;


	public GuiKEB(TileKineticEnergyBufferBase keb) {
		super(new ContainerKEB(keb));
		this.keb = keb;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		int percent = (keb.energy * 248) / keb.maxEnergy;
		int temp = percent;
		if (temp > 36)
			temp = 36;
		drawTexturedModalRect(guiLeft + 90, guiTop + 17, 215, 42, temp, 11);
		percent -= 36;
		if (percent <= 0)
			return;
		temp = percent;
		if (temp > 62)
			temp = 62;
		drawTexturedModalRect(guiLeft + 115, guiTop + 28, 241, 53, 11, temp);
		percent -= 62;
		if (percent <= 0)
			return;
		temp = percent;
		if (temp > 61)
			temp = 61;
		drawTexturedModalRect(guiLeft + 115 - temp, guiTop + 79, 241 - temp, 104, temp , 12);
		percent -= 61;
		if (percent <= 0)
			return;
		temp = percent;
		if (percent > 62)
			temp = 62;
		drawTexturedModalRect(guiLeft + 54, guiTop + 79 - temp, 180, 104 - temp, 11, temp);
		percent -=62;
		if (percent <= 0)
			return;
		drawTexturedModalRect(guiLeft + 65, guiTop + 17, 191, 42, percent, 11);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		north = new GuiButton(1, guiLeft + 38, guiTop + 92, 50, 20, getStatus(2));
		east = new GuiButton(1, guiLeft + 38, guiTop + 115, 50, 20, getStatus(5));
		south = new GuiButton(1, guiLeft + 38, guiTop + 138, 50, 20, getStatus(3));
		west = new GuiButton(1, guiLeft + 120, guiTop + 92, 50, 20, getStatus(4));
		up = new GuiButton(1, guiLeft + 120, guiTop + 115, 50, 20, getStatus(1));
		down = new GuiButton(1, guiLeft + 120, guiTop + 138, 50, 20, getStatus(0));
		buttonList.add(north);
		buttonList.add(east);
		buttonList.add(south);
		buttonList.add(west);
		buttonList.add(up);
		buttonList.add(down);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		fontRendererObj.drawString(Utils.localize("tile.blockKEBT" + keb.tier + ".name"), 5, 6, 0x404040);
		fontRendererObj.drawString("North: ", 5, 97, 0x404040);
		fontRendererObj.drawString("East: ", 5, 120, 0x404040);
		fontRendererObj.drawString("South: ", 5, 143, 0x404040);
		fontRendererObj.drawString("West: ", 93, 97, 0x404040);
		fontRendererObj.drawString("Up: ", 93, 120, 0x404040);
		fontRendererObj.drawString("Down: ", 93, 143, 0x404040);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button == north) {
			keb.changeSideMode(2);
			north.displayString = getStatus(2);
			keb.sendConfigurationToSever();
		} else if (button == east) {
			keb.changeSideMode(5);
			east.displayString = getStatus(5);
			keb.sendConfigurationToSever();
		} else if (button == south) {
			keb.changeSideMode(3);
			south.displayString = getStatus(3);
			keb.sendConfigurationToSever();
		} else if (button == west) {
			keb.changeSideMode(4);
			west.displayString = getStatus(4);
			keb.sendConfigurationToSever();
		} else if (button == up) {
			keb.changeSideMode(1);
			up.displayString = getStatus(1);
			keb.sendConfigurationToSever();
		} else if (button == down) {
			keb.changeSideMode(0);
			down.displayString = getStatus(0);
			keb.sendConfigurationToSever();
		}
	}

	private String getStatus(int direction) {
		int status = keb.configuration[direction];
		if (status == 0)
			return "Input";
		else if (status == 1)
			return "Output";
		return "Dissabled";
	}

}
