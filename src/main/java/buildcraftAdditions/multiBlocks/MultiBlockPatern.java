package buildcraftAdditions.multiBlocks;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.blocks.multiBlocks.MulitBlockBase;
import buildcraftAdditions.utils.Location;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockPatern {
	public ForgeDirection directions[];
	public char identifier;

	public MultiBlockPatern(ForgeDirection directions[], char identifier) {
			this.directions = directions;
			this.identifier = identifier;
	}

	public void checkPatern(World world, int x, int y, int z) {
		Location location = new Location(world, x, y, z);
		for (ForgeDirection direction: directions) {
			location.move(direction);
			if (!(location.getBlock() instanceof MulitBlockBase))
				return;
			MulitBlockBase block = (MulitBlockBase) location.getBlock();
			if (!(block.identifier == identifier) || location.getMeatadata() != 0 )
				return;
		}
		location = new Location(world, x, y, z);
		for (ForgeDirection direction: directions) {
			location.move(direction);
			location.setMetadata(1);
			IMultiBlockTile slave = (IMultiBlockTile) location.getTileEntity();
			slave.formMultiblock(x, y, z);
		}
		addMaster(world, x, y, z);
	}

	public void destroyMultiblock(World world, int x, int y, int z) {
		Location location = new Location(world, x, y, z);
		for (ForgeDirection direction: directions) {
			location.move(direction);
			if (location.getTileEntity() instanceof IMultiBlockTile)
				((IMultiBlockTile) location.getTileEntity()).invalidateBlock();
		}
	}

	public ArrayList<Location> getLocations(World world, int masterX, int masterY, int masterZ) {
		ArrayList<Location> list = new ArrayList<Location>(directions.length);
		Location location = new Location(world, masterX, masterY, masterZ);
		for (ForgeDirection direction: directions) {
			location.move(direction);
			list.add(location.copy());
		}
		return list;


	}

	public void addMaster (World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof IMultiBlockTile) {
			IMultiBlockTile master = (IMultiBlockTile) entity;
			master.makeMaster();
			master.sync();
		}
	}
}
