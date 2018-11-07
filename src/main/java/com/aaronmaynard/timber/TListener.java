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


public class TListener implements Listener {
	
	private Timber plugin;
	private static boolean onSneak, axeOnly, noCreative, trunkOnly, messages;
	private String onEnable, onDisable;

	public TListener(Timber instance) {
		plugin = instance;
	}

	public TListener() {
		// TODO Auto-generated constructor stub
	}

	static int dx[] = { 0, 0, -1, 1 }, dz[] = { -1, 1, 0, 0 };
	static Material[] ok = { Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE,
			Material.DIAMOND_AXE };

	private void chop(World w, int x, int y, int z) {
		if (!w.getBlockAt(x, y, z).breakNaturally()) {
			plugin.getLogger().warning("Could not break wood block at (" + x + ", " + y + ", " + z + ")!");
			return;
		}
		for (int i = 0; i < dx.length; i++)
			if (w.getBlockAt(x + dx[i], y, z + dz[i]).getType() == Material.LOG)
				chop(w, x + dx[i], y, z + dz[i]);
		if (w.getBlockAt(x, y + 1, z).getType() == Material.LOG)
			chop(w, x, y + 1, z);
		if (w.getBlockAt(x, y - 1, z).getType() == Material.LOG)
			chop(w, x, y - 1, z);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();

		if (player.getGameMode() == GameMode.CREATIVE)
			return; // must not be in creative
		
		if (onSneak) {
		
			if (!player.isSneaking()) {
				return; // must be sneaking
			}
		}

		if (!player.hasPermission("timber.fell"))
			return; // must have perms

		Block block = e.getBlock();
		if (block.getType() != Material.LOG)
			return; // must be wood

		boolean okay = false;
		for (int i = 0; !okay && i < ok.length; i++)
			okay |= player.getItemInHand().getType() == ok[i];
		if (!okay)
			return; // must destroy with axe

		Location loc = block.getLocation();
		World w = loc.getWorld();

		if (w.getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getType() == Material.LOG)
			return; // bottom block only

		for (int i = 0; i < dx.length; i++)
			if (w.getBlockAt(loc.getBlockX() + dx[i], loc.getBlockY(), loc.getBlockZ() + dz[i])
					.getType() == Material.LOG)
				return; // cannot be surrounded by wood

		okay = false;
		for (int dy = 1; !okay && dy < 9; dy++)
			for (int i = 0; !okay && i < dx.length; i++)
				okay |= w.getBlockAt(loc.getBlockX() + dx[i], loc.getBlockY() + dy, loc.getBlockZ() + dz[i])
						.getType() == Material.LEAVES; // must have leaves somewhere nearby
		if (!okay)
			return;

		// All checks passed
		chop(w, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public static void setOnSneak(boolean setting) {
		onSneak = setting;
	}
	
	public static boolean getOnSneak() {
		return onSneak;
	}
}
