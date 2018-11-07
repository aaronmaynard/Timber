package com.aaronmaynard.timber;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Timber extends JavaPlugin {
	
	private static boolean enabled = true;
	TListener config = new TListener();
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new TListener(this), this);
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("timber")) {
			
			if (args.length == 0) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.check")) {
					
					sender.sendMessage("Timber plugin is currently " + (enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled") + ChatColor.RESET + ".");
					return true;
				} else {
					return false;
				}
			}
			
			if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
				
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					
					enabled = !enabled;
					
					if (enabled) {
						
						getServer().getPluginManager().registerEvents(new TListener(this), this);
					} else {
						
						HandlerList.unregisterAll(this);
					}
					
					sender.sendMessage("Timber plugin is now " + (enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled") + ChatColor.RESET + ".");
					return true;
				}
			}
			
			if (args.length == 1 && args[0].equalsIgnoreCase("onsneak")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage("onSneak is currently set to " + (getConfig().getBoolean("onSneak") ? ChatColor.GREEN + "true" : ChatColor.RED + "false") + ChatColor.RESET + ".");
				}
			}
			
			if (args.length == 2 && args[0].equalsIgnoreCase("onsneak")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					setOnSneak(sender, args[1]);
				}
			}
			
			
		}
		
		return false;
	}
	
	public void setOnSneak(CommandSender sender, String setting) {
				
		if (setting.equalsIgnoreCase("true") || setting.equalsIgnoreCase("1")) {
			
			config.setOnSneak(true);
		} else if (setting.equalsIgnoreCase("false") || setting.equalsIgnoreCase("0")) {

			config.setOnSneak(false);
		} else {
			sender.sendMessage(ChatColor.RED + "This needs to be a boolean value!");
			return;
		}
		
		getConfig().set("onSneak", config.getOnSneak());
		sender.sendMessage("onSneak was set to " + (getConfig().getBoolean("onSneak") ? ChatColor.GREEN + "true" : ChatColor.RED + "false") + ChatColor.RESET + ".");
		saveConfig();
		reloadConfig();
	}

}
