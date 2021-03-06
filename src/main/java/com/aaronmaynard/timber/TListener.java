package com.aaronmaynard.timber;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.PlayerInventory;

/**
 * 
 * Listens for activity that relates to the plugin
 * 
 * @author Aaron Maynard
 *
 */
public class TListener implements Listener {

	private Timber plugin;
	// defaults
	private static boolean onSneak, axeOnly, allowCreative, trunkOnly, thickTrees, messages;
	private static String onActivation, onDeactivation;

	/**
	 * Attatches to the instance of the plugin
	 * 
	 * @param instance Timber plugin
	 */
	public TListener(Timber instance) {
		plugin = instance;
	}

	/**
	 * Constructor for getters and setters
	 */
	public TListener() {
		// TODO Auto-generated constructor stub
	}

	static int dx[] = { 0, 0, -1, 1 }, dz[] = { -1, 1, 0, 0 };
	static Material[] axes = { Material.DIAMOND_AXE, Material.GOLDEN_AXE, Material.IRON_AXE, Material.STONE_AXE,
			Material.WOODEN_AXE, Material.LEGACY_DIAMOND_AXE, Material.LEGACY_GOLD_AXE, Material.LEGACY_IRON_AXE,
			Material.LEGACY_STONE_AXE, Material.LEGACY_WOOD_AXE };
	static Material[] logs = { Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG, Material.JUNGLE_LOG,
			Material.LEGACY_LOG, Material.LEGACY_LOG_2, Material.OAK_LOG, Material.SPRUCE_LOG,
			Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_DARK_OAK_LOG,
			Material.STRIPPED_JUNGLE_LOG, Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG };
	static Material[] leaves = { Material.ACACIA_LEAVES, Material.BIRCH_LEAVES, Material.DARK_OAK_LEAVES,
			Material.JUNGLE_LEAVES, Material.LEGACY_LEAVES, Material.LEGACY_LEAVES_2, Material.OAK_LEAVES,
			Material.SPRUCE_LEAVES };

	/**
	 * Recursively finds the log blocks to fell
	 * 
	 * @param w the current world
	 * @param x variable position in the current world
	 * @param y variable position in the current world
	 * @param z variable position in the current world
	 */
	private void fell(World w, int x, int y, int z) {

		if (!w.getBlockAt(x, y, z).breakNaturally()) {
			plugin.getLogger().warning("Could not break wood block at (" + x + ", " + y + ", " + z + ")!");
			return;
		}
		// Checks blocks on current y plane
		for (int j = 0; j < logs.length; j++) {
			for (int i = 0; i < dx.length; i++) {
				if (w.getBlockAt(x + dx[i], y, z + dz[i]).getType() == logs[j]) {
					fell(w, x + dx[i], y, z + dz[i]);
				}
			}

			// Checks block above
			if (w.getBlockAt(x, y + 1, z).getType() == logs[j]) {
				fell(w, x, y + 1, z);
			}
			// Checks block below
			if (w.getBlockAt(x, y - 1, z).getType() == logs[j]) {
				fell(w, x, y - 1, z);
			}
		}
	}

	/**
	 * registers an event when a player breaks a block
	 * 
	 * @param e block break event
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();

		if (!player.hasPermission("timber.fell")) {
			return; // must have perms
		}

		if (!allowCreative) {

			if (player.getGameMode() == GameMode.CREATIVE) {
				return; // must not be in creative
			}
		}

		if (onSneak) {

			if (!player.isSneaking()) {
				return; // must be sneaking
			}
		}

		Block block = e.getBlock();
		Location loc = block.getLocation();
		World w = loc.getWorld();

		/*
		 * Log Checker
		 */
		boolean okay = false;
		for (int j = 0; j < logs.length; j++) {
			if (block.getType() == logs[j]) {
				okay = true;
			}
		}
		if (!okay) {
			return; // Must be some type of log
		}

		/*
		 * Axe Checker
		 */
		okay = false;
		if (axeOnly) {
			PlayerInventory inv = player.getInventory();
			for (int i = 0; !okay && i < axes.length; i++) {
				okay |= inv.getItemInMainHand().getType().equals(axes[i]);
			}
			if (!okay) {
				return; // must destroy with axe
			}
		}

		/*
		 * Thick Trees
		 */
		if (!thickTrees) {

			for (int j = 0; j < logs.length; j++) {
				for (int i = 0; i < dx.length; i++) {
					if (w.getBlockAt(loc.getBlockX() + dx[i], loc.getBlockY(), loc.getBlockZ() + dz[i])
							.getType() == logs[j]) {
						return; // disable cutting of thick trees
					}
				}
			}
		}

		/*
		 * Leaves Checker
		 */
		okay = false;
		for (int dy = 1; !okay && dy < 9; dy++) {
			for (int k = 0; k < leaves.length; k++) {
				for (int i = 0; !okay && i < dx.length; i++) {
					okay |= (w.getBlockAt(loc.getBlockX() + dx[i], loc.getBlockY() + dy, loc.getBlockZ() + dz[i])
							.getType() == leaves[k]); // must have leaves somewhere nearby
				}
			}
		}
		if (!okay)
			return;

		/*
		 * Trunk Checker
		 */
		if (trunkOnly) {
			if (w.getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getType() != Material.DIRT) {
				return; // A log was detected below
			}
		} else if (!trunkOnly) {
			// It doesnt matter that a log is below
		}
		// All checks passed
		fell(w, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	/**
	 * Registers an event when the player activates sneak
	 * 
	 * @param e on sneak event
	 * @throws Exception unable to send hotbar message
	 */
	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e) throws Exception {
		if (onSneak && axeOnly && messages) {
			Player player = e.getPlayer();
			if (player.isSneaking()) {
				for (int i = 0; i < axes.length; i++) {
					PlayerInventory inv = player.getInventory();
					if (inv.getItemInMainHand().getType().equals(axes[i])) {
						HotbarMessager.sendHotBarMessage(player,
								ChatColor.translateAlternateColorCodes('&', onDeactivation));
					}

				}

			} else {
				for (int i = 0; i < axes.length; i++) {
					PlayerInventory inv = player.getInventory();
					if (inv.getItemInMainHand().getType().equals(axes[i])) {
						HotbarMessager.sendHotBarMessage(player,
								ChatColor.translateAlternateColorCodes('&', onActivation));
					}

				}
			}
		}

	}

	/**
	 * onSneak setter function
	 * 
	 * @param setting boolean value to set the flag
	 */
	public static void setOnSneak(boolean setting) {
		onSneak = setting;
	}

	/**
	 * onSneak getter function
	 * 
	 * @return the value of onSneak
	 */
	public static boolean getOnSneak() {
		return onSneak;
	}

	/**
	 * allowCreative setter function
	 * 
	 * @param setting boolean value to set the flag
	 */
	public static void setAllowCreative(boolean setting) {
		allowCreative = setting;
	}

	/**
	 * allowCreative getter function
	 * 
	 * @return the value of allowCreative
	 */
	public static boolean getAllowCreative() {
		return allowCreative;
	}

	/**
	 * axeOnly setter function
	 * 
	 * @param setting boolean value to set the flag
	 */
	public static void setAxeOnly(boolean setting) {
		axeOnly = setting;
	}

	/**
	 * axeOnly getter function
	 * 
	 * @return the value of axeOnly
	 */
	public static boolean getAxeOnly() {
		return axeOnly;
	}

	/**
	 * trunkOnly setter function
	 * 
	 * @param setting boolean value to set the flag
	 */
	public static void setTrunkOnly(boolean setting) {
		trunkOnly = setting;
	}

	/**
	 * trunkOnly getter function
	 * 
	 * @return the value of trunkOnly
	 */
	public static boolean getTrunkOnly() {
		return trunkOnly;
	}

	/**
	 * thickTrees setter function
	 * 
	 * @param setting boolean value to set the flag
	 */
	public static void setThickTrees(boolean setting) {
		thickTrees = setting;
	}

	/**
	 * thickTrees getter function
	 * 
	 * @return the value of thickTrees
	 */
	public static boolean getThickTrees() {
		return thickTrees;
	}

	/**
	 * messages setter function
	 * 
	 * @param setting boolean value to set the flag
	 */
	public static void setMessages(boolean setting) {
		messages = setting;
	}

	/**
	 * messages getter function
	 * 
	 * @return the value of messages
	 */
	public static boolean getMessages() {
		return messages;
	}

	/**
	 * onActivation setter function
	 * 
	 * @param setting the message to be displayed on activation
	 */
	public static void setOnActivation(String setting) {
		onActivation = setting;
	}

	/**
	 * onActivation getter function
	 * 
	 * @return onActivation string value
	 */
	public static String getOnActivation() {
		return onActivation;
	}

	/**
	 * onDeactivation setter function
	 * 
	 * @param setting the message to be displayed on deactivation
	 */
	public static void setOnDeactivation(String setting) {
		onDeactivation = setting;
	}

	/**
	 * onDeactivation getter function
	 * 
	 * @return onDeactivation string value
	 */
	public static String getOnDeactivation() {
		return onDeactivation;
	}
}