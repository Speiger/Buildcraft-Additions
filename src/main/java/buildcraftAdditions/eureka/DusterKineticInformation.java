package buildcraftAdditions.eureka;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.variables.Variables;
import eureka.client.gui.EurekaChapter;
import eureka.core.EurekaInformation;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DusterKineticInformation extends EurekaInformation {
	@Override
	public String getKey() {
		return Variables.DustT2Key2;
	}

	@Override
	public int getIncrement() {
		return 1;
	}

	@Override
	public int getMaxValue() {
		return 20;
	}

	@Override
	public ItemStack getDisplayStack() {
		return new ItemStack(BuildcraftAdditions.kineticDusterBlock);
	}

	@Override
	public String getCategory() {
		return "BCA";
	}

	@Override
	public Class<? extends EurekaChapter> getGuiClass() {
		return DusterKineticGui.class;
	}
}
