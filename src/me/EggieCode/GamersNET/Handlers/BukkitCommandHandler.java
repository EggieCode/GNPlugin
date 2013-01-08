package me.EggieCode.GamersNET.Handlers;

import me.EggieCode.GamersNET.GNPlugin;
import me.EggieCode.GamersNET.Handlers.Commands.CMDHome;
import me.EggieCode.GamersNET.Handlers.Commands.CMDRandomSpawn;

public class BukkitCommandHandler {
	GNPlugin plugin;
	public BukkitCommandHandler(GNPlugin plugin) {
		this.plugin = plugin;
        plugin.getCommand("RandomSpawn").setExecutor(new CMDRandomSpawn(plugin));
        plugin.getCommand("sethome").setExecutor(new CMDHome(plugin));
        plugin.getCommand("home").setExecutor(new CMDHome(plugin));
	}
	
	
}
