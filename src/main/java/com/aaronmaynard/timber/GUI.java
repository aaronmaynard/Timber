package com.aaronmaynard.timber;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * 
 * A graphical interface for the Timber Plugin
 * 
 * @author Aaron Maynard
 *
 */
public class GUI implements Listener {

	public static Inventory timberGUI = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Timber Plugin");

	public GUI() {
		
	}
	
	/**
	 * Opens a custom GUI
	 * 
	 * @param player whoever opens the graphical interface
	 * @return returns true if the interface was successfully opened
	 */
	public static boolean openGUI(Player player) {
		
		/*
		 * Plugin Info
		 */
		ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
		ItemMeta bookMeta = book.getItemMeta();
		bookMeta.setDisplayName(ChatColor.YELLOW + "Plugin Info");
		ArrayList<String> bookLore = new ArrayList<String>();
		bookLore.add(ChatColor.WHITE + "Author: The_Illusi0nist");
		bookLore.add(ChatColor.WHITE + "Version: " + Bukkit.getServer().getPluginManager().getPlugin("Timber").getDescription().getVersion());
		bookMeta.setLore(bookLore);
		book.setItemMeta(bookMeta);
		
		/*
		 * Close GUI
		 */
		ItemStack lamp = new ItemStack(Material.REDSTONE_LAMP);
		ItemMeta lampMeta = lamp.getItemMeta();
		lampMeta.setDisplayName(ChatColor.YELLOW + "Close GUI");
		lamp.setItemMeta(lampMeta);
		
		/*
		 * Toggle onSneak
		 */
		boolean bootsBool = TListener.getOnSneak();
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.setDisplayName(ChatColor.YELLOW + "onSneak");
		ArrayList<String> bootsLore = new ArrayList<String>();
		bootsLore.add(ChatColor.WHITE + "Can only fell while crouching");
		bootsLore.add(ChatColor.WHITE + "Set to: " + (bootsBool ? ChatColor.GREEN + "true" : ChatColor.RED + "false"));
		bootsMeta.setLore(bootsLore);
		bootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		boots.setItemMeta(bootsMeta);
		
		/*
		 * Toggle axeOnly
		 */
		boolean axeBool = TListener.getAxeOnly();
		ItemStack axe = new ItemStack(Material.IRON_AXE);
		ItemMeta axeMeta = axe.getItemMeta();
		axeMeta.setDisplayName(ChatColor.YELLOW + "axeOnly");
		ArrayList<String> axeLore = new ArrayList<String>();
		axeLore.add(ChatColor.WHITE + "Only Axes will work");
		axeLore.add(ChatColor.WHITE + "Set to: " + (axeBool ? ChatColor.GREEN + "true" : ChatColor.RED + "false"));
		axeMeta.setLore(axeLore);
		axeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		axe.setItemMeta(axeMeta);
		
		/*
		 * Toggle thickTrees
		 */
		boolean logBool = TListener.getThickTrees();
		ItemStack log = new ItemStack(Material.OAK_LOG);
		ItemMeta logMeta = log.getItemMeta();
		logMeta.setDisplayName(ChatColor.YELLOW + "thickTrees");
		ArrayList<String> logLore = new ArrayList<String>();
		logLore.add(ChatColor.WHITE + "Thick trees can be felled");
		logLore.add(ChatColor.WHITE + "Set to: " + (logBool ? ChatColor.GREEN + "true" : ChatColor.RED + "false"));
		logMeta.setLore(logLore);
		log.setItemMeta(logMeta);
		
		/*
		 * Toggle trunkOnly
		 */
		boolean grassBool = TListener.getTrunkOnly();
		ItemStack grass = new ItemStack(Material.GRASS_BLOCK);
		ItemMeta grassMeta = grass.getItemMeta();
		grassMeta.setDisplayName(ChatColor.YELLOW + "trunkOnly");
		ArrayList<String> grassLore = new ArrayList<String>();
		grassLore.add(ChatColor.WHITE + "Trees can only be felled from the trunk");
		grassLore.add(ChatColor.WHITE + "Set to: " + (grassBool ? ChatColor.GREEN + "true" : ChatColor.RED + "false"));
		grassMeta.setLore(grassLore);
		grass.setItemMeta(grassMeta);
		
		/*
		 * Toggle messages
		 */
		boolean signBool = TListener.getMessages();
		ItemStack sign = new ItemStack(Material.SIGN);
		ItemMeta signMeta = sign.getItemMeta();
		signMeta.setDisplayName(ChatColor.YELLOW + "messages");
		ArrayList<String> signLore = new ArrayList<String>();
		signLore.add(ChatColor.WHITE + "Send notification messages to player");
		signLore.add(ChatColor.WHITE + "Set to: " + (signBool ? ChatColor.GREEN + "true" : ChatColor.RED + "false"));
		signMeta.setLore(signLore);
		sign.setItemMeta(signMeta);
		
		/*
		 * Toggle allowCreative
		 */
		boolean shelfBool = TListener.getMessages();
		ItemStack shelf = new ItemStack(Material.BOOKSHELF);
		ItemMeta shelfMeta = shelf.getItemMeta();
		shelfMeta.setDisplayName(ChatColor.YELLOW + "allowCreative");
		ArrayList<String> shelfLore = new ArrayList<String>();
		shelfLore.add(ChatColor.WHITE + "Allow players in creative to fell");
		shelfLore.add(ChatColor.WHITE + "Set to: " + (shelfBool ? ChatColor.GREEN + "true" : ChatColor.RED + "false"));
		shelfMeta.setLore(shelfLore);
		shelf.setItemMeta(shelfMeta);
		
		/*
		 * AIR
		 */
		ItemStack air = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		
		/*
		 * Timber GUI
		 */
		timberGUI.setItem(0, new ItemStack(book));
		timberGUI.setItem(1, new ItemStack(air));
		timberGUI.setItem(2, new ItemStack(air));
		timberGUI.setItem(3, new ItemStack(air));
		timberGUI.setItem(4, new ItemStack(air));
		timberGUI.setItem(5, new ItemStack(air));
		timberGUI.setItem(6, new ItemStack(air));
		timberGUI.setItem(7, new ItemStack(air));
		timberGUI.setItem(8, new ItemStack(air));

		timberGUI.setItem(9, new ItemStack(air));
		timberGUI.setItem(10, new ItemStack(air));
		timberGUI.setItem(11, new ItemStack(boots));
		timberGUI.setItem(12, new ItemStack(axe));
		timberGUI.setItem(13, new ItemStack(log));
		timberGUI.setItem(14, new ItemStack(grass));
		timberGUI.setItem(15, new ItemStack(sign));
		timberGUI.setItem(16, new ItemStack(shelf));
		timberGUI.setItem(17, new ItemStack(air));

		timberGUI.setItem(18, new ItemStack(lamp));
		timberGUI.setItem(19, new ItemStack(air));
		timberGUI.setItem(20, new ItemStack(air));
		timberGUI.setItem(21, new ItemStack(air));
		timberGUI.setItem(22, new ItemStack(air));
		timberGUI.setItem(23, new ItemStack(air));
		timberGUI.setItem(24, new ItemStack(air));
		timberGUI.setItem(25, new ItemStack(air));
		timberGUI.setItem(26, new ItemStack(air));

		
		player.openInventory(timberGUI);

		return true;
	}

}
