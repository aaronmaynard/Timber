package com.aaronmaynard.timber;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Timber extends JavaPlugin {

	private static boolean enabled = true;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new TListener(this), this);

		getConfig().options().copyDefaults(true);
		saveConfig();
		TListener.setAxeOnly(getConfig().getBoolean("axeOnly"));
		TListener.setMessages(getConfig().getBoolean("messages"));
		TListener.setNoCreative(getConfig().getBoolean("noCreative"));
		TListener.setOnSneak(getConfig().getBoolean("onSneak"));
		TListener.setThickTrees(getConfig().getBoolean("thickTrees"));
		TListener.setTrunkOnly(getConfig().getBoolean("trunkOnly"));

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("timber")) {

			if (args.length == 0) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.check")) {

					sender.sendMessage("Timber plugin is currently "
							+ (enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled") + ChatColor.RESET
							+ ".");
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

					sender.sendMessage("Timber plugin is now "
							+ (enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled") + ChatColor.RESET
							+ ".");
					return true;
				}
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

				reloadConfig();
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("rules")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage(ChatColor.GOLD + "These are the current rules of Timber: " + ChatColor.RESET);
					sender.sendMessage("Timber plugin is currently "
							+ (enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled") + ChatColor.RESET
							+ ".");
					sender.sendMessage("onSneak is currently set to "
							+ (getConfig().getBoolean("onSneak") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage("noCreative is currently set to "
							+ (getConfig().getBoolean("noCreative") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage("axeOnly is currently set to "
							+ (getConfig().getBoolean("axeOnly") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage("trunkOnly is currently set to "
							+ (getConfig().getBoolean("trunkOnly") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage("thickTrees is currently set to "
							+ (getConfig().getBoolean("thickTrees") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage("messages is currently set to "
							+ (getConfig().getBoolean("messages") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage(ChatColor.GOLD + "===========================================" + ChatColor.RESET);
					
				}
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("onsneak")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage("onSneak is currently set to "
							+ (getConfig().getBoolean("onSneak") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
				}
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("onsneak")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					setOnSneak(sender, args[1]);
				}
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("noCreative")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage("noCreative is currently set to "
							+ (getConfig().getBoolean("noCreative") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
				}
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("noCreative")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					setNoCreative(sender, args[1]);
				}
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("axeOnly")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage("axeOnly is currently set to "
							+ (getConfig().getBoolean("axeOnly") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
				}
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("axeOnly")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					setAxeOnly(sender, args[1]);
				}
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("trunkOnly")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage("trunkOnly is currently set to "
							+ (getConfig().getBoolean("trunkOnly") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
				}
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("trunkOnly")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					setTrunkOnly(sender, args[1]);
				}
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("thickTrees")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage("thickTrees is currently set to "
							+ (getConfig().getBoolean("thickTrees") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
				}
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("thickTrees")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					setThickTrees(sender, args[1]);
				}
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("messages")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage("messages is currently set to "
							+ (getConfig().getBoolean("messages") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
				}
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("messages")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					setMessages(sender, args[1]);
				}
			}

		}

		return false;
	}

	public void setOnSneak(CommandSender sender, String setting) {

		if (setting.equalsIgnoreCase("true") || setting.equalsIgnoreCase("1")) {

			TListener.setOnSneak(true);
		} else if (setting.equalsIgnoreCase("false") || setting.equalsIgnoreCase("0")) {

			TListener.setOnSneak(false);
		} else {
			sender.sendMessage(ChatColor.RED + "This needs to be a boolean value!");
			return;
		}

		getConfig().set("onSneak", TListener.getOnSneak());
		sender.sendMessage("onSneak was set to "
				+ (getConfig().getBoolean("onSneak") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
				+ ChatColor.RESET + ".");
		saveConfig();
		reloadConfig();
	}

	public void setNoCreative(CommandSender sender, String setting) {

		if (setting.equalsIgnoreCase("true") || setting.equalsIgnoreCase("1")) {

			TListener.setNoCreative(true);
		} else if (setting.equalsIgnoreCase("false") || setting.equalsIgnoreCase("0")) {

			TListener.setNoCreative(false);
		} else {
			sender.sendMessage(ChatColor.RED + "This needs to be a boolean value!");
			return;
		}

		getConfig().set("noCreative", TListener.getNoCreative());
		sender.sendMessage("noCreative was set to "
				+ (getConfig().getBoolean("noCreative") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
				+ ChatColor.RESET + ".");
		saveConfig();
		reloadConfig();
	}

	public void setAxeOnly(CommandSender sender, String setting) {

		if (setting.equalsIgnoreCase("true") || setting.equalsIgnoreCase("1")) {

			TListener.setAxeOnly(true);
		} else if (setting.equalsIgnoreCase("false") || setting.equalsIgnoreCase("0")) {

			TListener.setAxeOnly(false);
		} else {
			sender.sendMessage(ChatColor.RED + "This needs to be a boolean value!");
			return;
		}

		getConfig().set("axeOnly", TListener.getAxeOnly());
		sender.sendMessage("axeOnly was set to "
				+ (getConfig().getBoolean("axeOnly") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
				+ ChatColor.RESET + ".");
		saveConfig();
		reloadConfig();
	}

	public void setTrunkOnly(CommandSender sender, String setting) {

		if (setting.equalsIgnoreCase("true") || setting.equalsIgnoreCase("1")) {

			TListener.setTrunkOnly(true);
		} else if (setting.equalsIgnoreCase("false") || setting.equalsIgnoreCase("0")) {

			TListener.setTrunkOnly(false);
		} else {
			sender.sendMessage(ChatColor.RED + "This needs to be a boolean value!");
			return;
		}

		getConfig().set("trunkOnly", TListener.getTrunkOnly());
		sender.sendMessage("trunkOnly was set to "
				+ (getConfig().getBoolean("trunkOnly") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
				+ ChatColor.RESET + ".");
		saveConfig();
		reloadConfig();
	}

	public void setThickTrees(CommandSender sender, String setting) {

		if (setting.equalsIgnoreCase("true") || setting.equalsIgnoreCase("1")) {

			TListener.setThickTrees(true);
		} else if (setting.equalsIgnoreCase("false") || setting.equalsIgnoreCase("0")) {

			TListener.setThickTrees(false);
		} else {
			sender.sendMessage(ChatColor.RED + "This needs to be a boolean value!");
			return;
		}

		getConfig().set("thickTrees", TListener.getThickTrees());
		sender.sendMessage("thickTrees was set to "
				+ (getConfig().getBoolean("thickTrees") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
				+ ChatColor.RESET + ".");
		saveConfig();
		reloadConfig();
	}

	public void setMessages(CommandSender sender, String setting) {

		if (setting.equalsIgnoreCase("true") || setting.equalsIgnoreCase("1")) {

			TListener.setMessages(true);
		} else if (setting.equalsIgnoreCase("false") || setting.equalsIgnoreCase("0")) {

			TListener.setMessages(false);
		} else {
			sender.sendMessage(ChatColor.RED + "This needs to be a boolean value!");
			return;
		}

		getConfig().set("messages", TListener.getMessages());
		sender.sendMessage("messages was set to "
				+ (getConfig().getBoolean("messages") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
				+ ChatColor.RESET + ".");
		saveConfig();
		reloadConfig();
	}

}