package me.EggieCode.GamersNET;

import me.EggieCode.GamersNET.Handlers.BukkitCommandHandler;
import me.EggieCode.GamersNET.Handlers.BukkitEventsHandler;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class GNPlugin extends JavaPlugin {
    private Permission permission = null;
    private Economy economy = null;
    private Chat chat = null;
    
    private BukkitEventsHandler events = null;
    private BukkitCommandHandler commands= null;
    
    private GNConfig config = null;
    private static GNDatabase database = null;
    private static GNPlugin plugin = null;
    
	@Override
	public void onEnable() {
		if (this.setupEconomy())
			getLogger().info("--GamerNET EggieGame loaded Economy");
		if (this.setupPermissions())
			getLogger().info("--GamerNET EggieGame loaded Permissions");
		if (this.setupChat())
			getLogger().info("--GamerNET EggieGame loaded Chat");
		plugin = this;
		config = new GNConfig(this);
		database = new GNDatabase(this);
		events = new BukkitEventsHandler(this);
		commands = new BukkitCommandHandler(this);
		getLogger().info("GamerNET EggieGame pluging enabled! :)");
		
		
	}

	@Override
	public void onDisable() {
		database.Disconnect();
		database = null;
		events = null;
		plugin = null;
		config = null;
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		economy = rsp.getProvider();
		return economy != null;
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager()
				.getRegistration(Chat.class);
		chat = rsp.getProvider();
		return chat != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer()
				.getServicesManager().getRegistration(Permission.class);
		permission = rsp.getProvider();
		return permission != null;
	}
	public static GNDatabase GetGNDatabase() {
		return database;
	
	}
	public static GNPlugin GetGNPlugin() {
		return plugin;
	}
	
	

}