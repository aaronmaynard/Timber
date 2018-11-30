package com.aaronmaynard.timber;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * Handles the actions for the graphical interface
 * 
 * @author Aaron Maynard
 *
 */
public class GUIHandler implements Listener {

	/**
	 * Attatches to the instance of the plugin
	 * 
	 * @param instance Timber plugin
	 */
	public GUIHandler(Timber instance) {
	}

	/**
	 * Constructor for getters and setters
	 */
	public GUIHandler() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Fires when the player opens the inventory
	 * 
	 * @param event click on inventory
	 */
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getInventory();
		ItemStack clicked = event.getCurrentItem();
		Material clickedType = clicked.getType();

		if (inventory == null) {
			return;
		}

		if (inventory.getName().equals("Timber Plugin") && event.getSlotType() != SlotType.OUTSIDE) {

			event.setCancelled(true);

			if (clicked == null || !clicked.hasItemMeta()) {
				return;
			}

			/*
			 * Plugin Info Do nothing... this is just info.
			 */
			if (clickedType == Material.WRITABLE_BOOK) {
				player.closeInventory();
				GUI.openGUI(player);
			}

			/*
			 * Close GUI Just close the GUI
			 */
			if (clickedType == Material.REDSTONE_LAMP) {
				player.closeInventory();
			}

			/*
			 * Toggle onSneak change boolean
			 */
			if (clickedType == Material.LEATHER_BOOTS) {
				player.closeInventory();
				if (TListener.getOnSneak()) {
					TListener.setOnSneak(false);
				} else {
					TListener.setOnSneak(true);
				}
				GUI.openGUI(player);
			}

			/*
			 * Toggle axeOnly
			 */
			if (clickedType == Material.IRON_AXE) {
				player.closeInventory();
				if (TListener.getAxeOnly()) {
					TListener.setAxeOnly(false);
				} else {
					TListener.setAxeOnly(true);
				}
				GUI.openGUI(player);
			}

			/*
			 * Toggle thickTrees
			 */
			if (clickedType == Material.IRON_AXE) {
				player.closeInventory();
				if (TListener.getThickTrees()) {
					TListener.setThickTrees(false);
				} else {
					TListener.setThickTrees(true);
				}
				GUI.openGUI(player);
			}

			/*
			 * Toggle trunkOnly
			 */
			if (clickedType == Material.IRON_AXE) {
				player.closeInventory();
				if (TListener.getTrunkOnly()) {
					TListener.setTrunkOnly(false);
				} else {
					TListener.setTrunkOnly(true);
				}
				GUI.openGUI(player);
			}

			/*
			 * Toggle messages
			 */
			if (clickedType == Material.IRON_AXE) {
				player.closeInventory();
				if (TListener.getMessages()) {
					TListener.setMessages(false);
				} else {
					TListener.setMessages(true);
				}
				GUI.openGUI(player);
			}

			/*
			 * Toggle allowCreative
			 */
			if (clickedType == Material.IRON_AXE) {
				player.closeInventory();
				if (TListener.getAllowCreative()) {
					TListener.setAllowCreative(false);
				} else {
					TListener.setAllowCreative(true);
				}
				GUI.openGUI(player);
			}

		}

	}

}
