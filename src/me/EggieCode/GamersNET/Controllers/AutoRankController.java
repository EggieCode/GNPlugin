package me.EggieCode.GamersNET.Controllers;

import org.bukkit.plugin.java.JavaPlugin;

public class AutoRankController extends ConfigController{
	
	protected AutoRankController(JavaPlugin plugin) {
		super(plugin, "autorank.yml");
	}

	@Override
	protected void LoadDefaultConfig() {
		
	}

}
