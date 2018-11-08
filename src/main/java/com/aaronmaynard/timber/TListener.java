package com.aaronmaynard.timber;

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


public class TListener implements Listener {
	
	private Timber plugin;
	// defaults
	private static boolean onSneak, axeOnly, noCreative, trunkOnly, thickTrees, messages;

	public TListener(Timber instance) {
		plugin = instance;
	}

	public TListener() {
		// TODO Auto-generated constructor stub
	}

	static int dx[] = { 0, 0, -1, 1 }, dz[] = { -1, 1, 0, 0 };
	static Material[] axes = { Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE };
	//static Material[] logs = { Material.LOG, Material.LOG_2};
	//static Material[] leaves = { Material.LEAVES, Material.LEAVES_2};

	private void chop(World w, int x, int y, int z) {
		if (!w.getBlockAt(x, y, z).breakNaturally()) {
			plugin.getLogger().warning("Could not break wood block at (" + x + ", " + y + ", " + z + ")!");
			return;
		}
		// Checks blocks on current y plane
		//for (int j = 0; j < logs.length; j++) {
			for (int i = 0; i < dx.length; i++) {
				if (w.getBlockAt(x + dx[i], y, z + dz[i]).getType() == Material.LOG) {
					chop(w, x + dx[i], y, z + dz[i]);
				}
			}
		
			
			// Checks block above
			if (w.getBlockAt(x, y + 1, z).getType() == Material.LOG) {
				chop(w, x, y + 1, z);
			}
			// Checks block below
			if (w.getBlockAt(x, y - 1, z).getType() == Material.LOG) {
				chop(w, x, y - 1, z);
			}
		//}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();

		if (!player.hasPermission("timber.fell")) {
			return; // must have perms
		}

		if (noCreative) {
		
			if (player.getGameMode() == GameMode.CREATIVE) {
				return; // must not be in creative
			}
		}	
		
		if (onSneak) {
		
			if (!player.isSneaking()) {
				return; // must be sneaking
			}
		}
		
		
		boolean okay = false;
		Block block = e.getBlock();
		Location loc = block.getLocation();
		World w = loc.getWorld();
		
		
		//for (int j = 0; j < logs.length; j++) {
			if (block.getType() != Material.LOG) {
				return; // must be wood
			}
			
		//}
		
		
		
		if (axeOnly) {
		
			PlayerInventory inv = player.getInventory();
			for (int i = 0; !okay && i < axes.length; i++) {
				okay |= inv.getItemInMainHand().getType().equals(axes[i]);
			}
			if (!okay) {
				return; // must destroy with axe
			}
		}
		
		
		
		if (!thickTrees) {
		
			//for (int j = 0; j < logs.length; j++) {
				for (int i = 0; i < dx.length; i++) {
					if (w.getBlockAt(loc.getBlockX() + dx[i], loc.getBlockY(), loc.getBlockZ() + dz[i]).getType() == Material.LOG) {
						return; // disable cutting of thick trees
					}
				}
			//}
		}
		
		
		// leaves check
		okay = false;
		for (int dy = 1; !okay && dy < 9; dy++) {
			//for (int k = 0; k < leaves.length; k++) {
				for (int i = 0; !okay && i < dx.length; i++) {
					okay |= w.getBlockAt(loc.getBlockX() + dx[i], loc.getBlockY() + dy, loc.getBlockZ() + dz[i]).getType() == Material.LEAVES; // must have leaves somewhere nearby
				}
			//}
		}
		if (!okay)
			return;

		
		if (trunkOnly) {
			if (w.getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getType() != Material.DIRT) {
				return; // A log was detected below
			}
		} else if (!trunkOnly) {
			
			// It doesnt matter that a log is below
		}
		// All checks passed
		chop(w, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
		if(onSneak && axeOnly && messages) {
			Player player = event.getPlayer();
		      if(player.isSneaking()) {
		    	  for (int i = 0; i < axes.length; i++) {
		    		  PlayerInventory inv = player.getInventory();
		    		  if(inv.getItemInMainHand().getType().equals(axes[i])){
		    			  player.sendMessage("You relax just a bit");
		    		  }
							
		    	  }
		    	  
		      } else {
		    	  for (int i = 0; i < axes.length; i++) {
		    		  PlayerInventory inv = player.getInventory();
		    		  if(inv.getItemInMainHand().getType().equals(axes[i])){
		    			  player.sendMessage("You prepare for a mighty timber");
		    		  }
							
		    	  }
		      }
		}
	      
	}
	
	public static void setOnSneak(boolean setting) {
		onSneak = setting;
	}
	
	public static boolean getOnSneak() {
		return onSneak;
	}
	
	public static void setNoCreative(boolean setting) {
		noCreative = setting;
	}
	
	public static boolean getNoCreative() {
		return noCreative;
	}
	
	public static void setAxeOnly(boolean setting) {
		axeOnly = setting;
	}
	
	public static boolean getAxeOnly() {
		return axeOnly;
	}
	
	public static void setTrunkOnly(boolean setting) {
		trunkOnly = setting;
	}
	
	public static boolean getTrunkOnly() {
		return trunkOnly;
	}
	
	public static void setThickTrees(boolean setting) {
		thickTrees = setting;
	}
	
	public static boolean getThickTrees() {
		return thickTrees;
	}
	
	public static void setMessages(boolean setting) {
		messages = setting;
	}
	
	public static boolean getMessages() {
		return messages;
	}
}