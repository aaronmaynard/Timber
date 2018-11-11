package com.aaronmaynard.timber;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Timber extends JavaPlugin {

	private static boolean enabled = true;

	/**
	 * Called when the plugin is enabled
	 * 
	 * @author Aaron Maynard
	 */
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new TListener(this), this);

		getConfig().options().copyDefaults(true);
		saveConfig();
		TListener.setAxeOnly(getConfig().getBoolean("axeOnly"));
		TListener.setMessages(getConfig().getBoolean("messages"));
		TListener.setAllowCreative(getConfig().getBoolean("allowCreative"));
		TListener.setOnSneak(getConfig().getBoolean("onSneak"));
		TListener.setThickTrees(getConfig().getBoolean("thickTrees"));
		TListener.setTrunkOnly(getConfig().getBoolean("trunkOnly"));
		TListener.setOnActivation(getConfig().getString("onActivation"));
		TListener.setOnDeactivation(getConfig().getString("onDeactivation"));

	}

	/**
	 * Executes the given command, returning its success
	 * 
	 * @param sender Source of the command
	 * @param cmd    Command which was executed
	 * @param label  Alias of the command which was used
	 * @param args   Passed command arguments
	 */
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
				TListener.setAxeOnly(getConfig().getBoolean("axeOnly"));
				TListener.setMessages(getConfig().getBoolean("messages"));
				TListener.setAllowCreative(getConfig().getBoolean("allowCreative"));
				TListener.setOnSneak(getConfig().getBoolean("onSneak"));
				TListener.setThickTrees(getConfig().getBoolean("thickTrees"));
				TListener.setTrunkOnly(getConfig().getBoolean("trunkOnly"));
				TListener.setOnActivation(getConfig().getString("onActivation"));
				TListener.setOnDeactivation(getConfig().getString("onDeactivation"));
				sender.sendMessage(ChatColor.GOLD + "The Timber configuration has been reloaded" + ChatColor.RESET);
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("help")
					|| args.length == 1 && args[0].equalsIgnoreCase("?")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage(
							ChatColor.GREEN + "====================================================" + ChatColor.RESET);
					sender.sendMessage(ChatColor.AQUA + "Timber Commands - /timber <command> [flag]" + ChatColor.RESET);
					sender.sendMessage(
							ChatColor.DARK_RED + "toggle" + ChatColor.RESET + " : Toggles the plugin on/off");
					sender.sendMessage(ChatColor.DARK_RED + "onsneak" + ChatColor.RESET
							+ " : Players must sneak in order to fell - " + ChatColor.AQUA + "[true|false]"
							+ ChatColor.RESET);
					sender.sendMessage(ChatColor.DARK_RED + "axeonly" + ChatColor.RESET + " : Only axes work - "
							+ ChatColor.AQUA + "[true|false]" + ChatColor.RESET);
					sender.sendMessage(ChatColor.DARK_RED + "thicktrees" + ChatColor.RESET
							+ " : Thicc trees can be felled - " + ChatColor.AQUA + "[true/false]" + ChatColor.RESET);
					sender.sendMessage(ChatColor.DARK_RED + "trunkonly" + ChatColor.RESET
							+ " : Only chopping the trunk will fell - " + ChatColor.AQUA + "[true|false]"
							+ ChatColor.RESET);
					sender.sendMessage(ChatColor.GREEN + "Type /help 2 for more commands" + ChatColor.RESET);
					sender.sendMessage(
							ChatColor.GREEN + "====================================================" + ChatColor.RESET);

				}
			}

			if ((args.length == 2 && args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("2"))
					|| (args.length == 2 && args[0].equalsIgnoreCase("?") && args[1].equalsIgnoreCase("2"))) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage(
							ChatColor.GREEN + "====================================================" + ChatColor.RESET);
					sender.sendMessage(ChatColor.AQUA + "Timber Commands - /timber <command> [flag]" + ChatColor.RESET);
					sender.sendMessage(ChatColor.DARK_RED + "allowcreative" + ChatColor.RESET
							+ " : Players in creative can fell - " + ChatColor.AQUA + "[true|false]" + ChatColor.RESET);
					sender.sendMessage(ChatColor.DARK_RED + "messages" + ChatColor.RESET
							+ " : onSneak & axeOnly must be set true - " + ChatColor.AQUA + "[true|false]"
							+ ChatColor.RESET);
					sender.sendMessage(ChatColor.DARK_RED + "onactivation" + ChatColor.RESET
							+ " : message displayed when ready to fell - " + ChatColor.AQUA + "[String]"
							+ ChatColor.RESET);
					sender.sendMessage(ChatColor.DARK_RED + "ondeactivation" + ChatColor.RESET
							+ " : message displayed when done felling - " + ChatColor.AQUA + "[String]"
							+ ChatColor.RESET);
					sender.sendMessage(
							ChatColor.GREEN + "====================================================" + ChatColor.RESET);

				}
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
					sender.sendMessage("axeOnly is currently set to "
							+ (getConfig().getBoolean("axeOnly") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage("thickTrees is currently set to "
							+ (getConfig().getBoolean("thickTrees") ? ChatColor.GREEN + "true"
									: ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage("trunkOnly is currently set to "
							+ (getConfig().getBoolean("trunkOnly") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage("messages is currently set to "
							+ (getConfig().getBoolean("messages") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage("allowCreative is currently set to "
							+ (getConfig().getBoolean("allowCreative") ? ChatColor.GREEN + "true"
									: ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
					sender.sendMessage(
							ChatColor.GOLD + "===========================================" + ChatColor.RESET);

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

			if (args.length == 1 && args[0].equalsIgnoreCase("allowCreative")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					sender.sendMessage("allowCreative is currently set to "
							+ (getConfig().getBoolean("allowCreative") ? ChatColor.GREEN + "true"
									: ChatColor.RED + "false")
							+ ChatColor.RESET + ".");
				}
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("allowCreative")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					setAllowCreative(sender, args[1]);
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
							+ (getConfig().getBoolean("thickTrees") ? ChatColor.GREEN + "true"
									: ChatColor.RED + "false")
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

			if (args.length >= 2 && args[0].equalsIgnoreCase("onactivation")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					String message = "";
					for(int i = 1; i < args.length; i++) {
						message = message + args[i] + " ";
					}
					setOnActivation(sender, message);
				}
			}

			if (args.length >= 2 && args[0].equalsIgnoreCase("ondeactivation")) {
				if (!(sender instanceof Player) || sender.hasPermission("timber.toggle")) {
					String message = "";
					for(int i = 1; i < args.length; i++) {
						message = message + args[i] + " ";
					}
					setOnDeactivation(sender, message);
				}
			}

		}

		return false;
	}

	/**
	 * Sets the flag onSneak to a boolean value
	 * 
	 * @param sender  the entity that sent the command
	 * @param setting the boolean value that toggles the flag
	 */
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

	/**
	 * Sets the flag allowCreative to a boolean value
	 * 
	 * @param sender  the entity that sent the command
	 * @param setting the boolean value that toggles the flag
	 */
	public void setAllowCreative(CommandSender sender, String setting) {

		if (setting.equalsIgnoreCase("true") || setting.equalsIgnoreCase("1")) {

			TListener.setAllowCreative(true);
		} else if (setting.equalsIgnoreCase("false") || setting.equalsIgnoreCase("0")) {

			TListener.setAllowCreative(false);
		} else {
			sender.sendMessage(ChatColor.RED + "This needs to be a boolean value!");
			return;
		}

		getConfig().set("allowCreative", TListener.getAllowCreative());
		sender.sendMessage("allowCreative was set to "
				+ (getConfig().getBoolean("allowCreative") ? ChatColor.GREEN + "true" : ChatColor.RED + "false")
				+ ChatColor.RESET + ".");
		saveConfig();
		reloadConfig();
	}

	/**
	 * Sets the flag axeOnly to a boolean value
	 * 
	 * @param sender  the entity that sent the command
	 * @param setting the boolean value that toggles the flag
	 */
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

	/**
	 * Sets the flag trunkOnly to a boolean value
	 * 
	 * @param sender  the entity that sent the command
	 * @param setting the boolean value that toggles the flag
	 */
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

	/**
	 * Sets the flag thickTrees to a boolean value
	 * 
	 * @param sender  the entity that sent the command
	 * @param setting the boolean value that toggles the flag
	 */
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

	/**
	 * Sets the flag messages to a boolean value
	 * 
	 * @param sender  the entity that sent the command
	 * @param setting the boolean value that toggles the flag
	 */
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

	/**
	 * Sets the onActivation message to a given string
	 * 
	 * @param sender  the entity that sent the command
	 * @param setting the string value for onActivation
	 */
	public void setOnActivation(CommandSender sender, String setting) {

		TListener.setOnActivation(setting);

		getConfig().set("onActivation", TListener.getOnActivation());
		sender.sendMessage("onActivation was set to " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("onActivation")));
		saveConfig();
		reloadConfig();
	}

	/**
	 * Sets the onDeactivation message to a given string
	 * 
	 * @param sender  the entity that sent the command
	 * @param setting the string value for onDeactivation
	 */
	public void setOnDeactivation(CommandSender sender, String setting) {

		TListener.setOnDeactivation(setting);

		getConfig().set("onDeactivation", TListener.getOnDeactivation());
		sender.sendMessage("onDeactivation was set to " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("onDeactivation")));
		saveConfig();
		reloadConfig();
	}

}