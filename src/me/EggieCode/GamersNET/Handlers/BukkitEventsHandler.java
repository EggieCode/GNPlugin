package me.EggieCode.GamersNET.Handlers;

import java.text.SimpleDateFormat;

import me.EggieCode.GamersNET.GNPlugin;
import me.EggieCode.GamersNET.Controllers.UserController;
import me.EggieCode.GamersNET.Handlers.Commands.CMDRandomSpawn;
import me.EggieCode.GamersNET.Models.User;

import org.bukkit.event.*;
import org.bukkit.event.player.*;


public class BukkitEventsHandler implements Listener{
	private GNPlugin plugin;
	
	public BukkitEventsHandler(GNPlugin plugin) {
		this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
    @EventHandler
	public void Join(PlayerJoinEvent event) {
    	UserController.getInstance().getUser(event.getPlayer(), true);
    }
}
