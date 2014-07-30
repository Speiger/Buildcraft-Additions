package buildcraftAdditions.core;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.api.IEurekaBlock;
import buildcraftAdditions.api.IEurekaItem;
import buildcraftAdditions.config.ConfigurationHandeler;
import buildcraftAdditions.utils.Eureka;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EventListener  {

    public static class FML {

        @SubscribeEvent
        public void playerLogin (PlayerLoggedInEvent event){
            //version check stuff
            if (VersionCheck.newerVersionAvailable && event != null){
                event.player.addChatComponentMessage(new ChatComponentText("There is a newer version of Buildcraft Additions available (" + VersionCheck.newerVersionNumber + ") Please consider updating"));
                if (!ConfigurationHandeler.shouldPrintChangelog)
                    return;
                event.player.addChatComponentMessage(new ChatComponentText("Changelog: "));
                for (int t = 0; t < VersionCheck.numLines; t++){

                    event.player.addChatComponentMessage(new ChatComponentText("- " + VersionCheck.changelog[t]));
                }
            }
            //initialize player knowledge if needed
            Eureka.init(event.player);
	        //give engineering diary
	        if (!event.player.getEntityData().hasKey("bookRecieved")){
		        for (int slot = 0; slot < event.player.inventory.getSizeInventory(); slot++){
			        if (event.player.inventory.getStackInSlot(slot) == null){
				        event.player.inventory.setInventorySlotContents(slot, new ItemStack(BuildcraftAdditions.engineeringDiary));
				        event.player.getEntityData().setBoolean("bookRecieved", true);
				        return;
			        }

		        }
	        }

        }

        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
            if (event.modID.equals("bcadditions"))
                ConfigurationHandeler.readConfig();
        }
    }

    public static class Forge{
        @SubscribeEvent
        public void onPlyerUsesBlock(PlayerInteractEvent event) {
            if (event != null) {
                Block block = event.world.getBlock(event.x, event.y, event.z);
                if (block instanceof  IEurekaBlock)
                Eureka.eurekaBlockEvent(event.world, (IEurekaBlock) block, event.x, event.y, event.z, event.entityPlayer);
            }
        }

        @SubscribeEvent
        public void onItemUse(PlayerUseItemEvent event){
            if (event== null)
                return;
            Item item = event.item.getItem();
            if (item instanceof IEurekaItem){
                IEurekaItem eurekaItem = (IEurekaItem) item;
                if (!eurekaItem.isAllowed(event.entityPlayer)) {
                    event.setCanceled(true);
                    if (event.entityPlayer != null)
                        event.entityPlayer.addChatComponentMessage(new ChatComponentText(eurekaItem.getMessage()));
                }
            }
        }
    }
}
