package me.EggieCode.GamersNET;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import me.EggieCode.GamersNET.Controllers.ConfigController;

public class GNConfig extends ConfigController {
	public static Map<String, String> DatabaseConfig = new HashMap<String, String>();
	public static Map<String, Double> RandomSpawnArea = new HashMap<String, Double>();

	GNPlugin plugin;

	public GNConfig(me.EggieCode.GamersNET.GNPlugin plugin) {
		super(plugin,"config.yml");
		this.plugin = plugin;
		
		
	}
/*
	public Map<String, String> getDataBaseInfo() {
		if (_cache_getDataBaseInfo == null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("hostname", config.getString("database.config.hostname"));
			map.put("port", config.getString("database.config.port"));
			map.put("username", config.getString("database.config.username"));
			map.put("password", config.getString("database.config.password"));
			map.put("database", config.getString("database.config.database"));
			_cache_getDataBaseInfo = map;
		}

		return _cache_getDataBaseInfo;

	}

	public Map<String, Double> getRandomSpawnArea() {
		if (_cache_getRandomSpawnArea == null) {
			Map<String, Double> map = new HashMap<String, Double>();
			map.put("xmin", config.getDouble("RandomSpawn.XMin"));
			map.put("xmax", config.getDouble("RandomSpawn.XMax"));
			map.put("zmax", config.getDouble("RandomSpawn.ZMax"));
			map.put("zmin", config.getDouble("RandomSpawn.Zmin"));
			_cache_getRandomSpawnArea = map;
		}

		return _cache_getRandomSpawnArea;

	}
	*/
	@Override
	protected void LoadDefaultConfig() {
		Upgrade();
		
		DatabaseConfig.put("hostname", config.getString("database.config.hostname"));
		DatabaseConfig.put("port", config.getString("database.config.port"));
		DatabaseConfig.put("username", config.getString("database.config.username"));
		DatabaseConfig.put("password", config.getString("database.config.password"));
		DatabaseConfig.put("database", config.getString("database.config.database"));
		

		RandomSpawnArea.put("xmin", config.getDouble("RandomSpawn.XMin"));
		RandomSpawnArea.put("xmax", config.getDouble("RandomSpawn.XMax"));
		RandomSpawnArea.put("zmax", config.getDouble("RandomSpawn.ZMax"));
		RandomSpawnArea.put("zmin", config.getDouble("RandomSpawn.Zmin"));
		
		
	}
	private void Upgrade() 
	{
		if(config.isSet("RandomSpawn")) {
			config.set("RandomSpawn.XMin",-400D);
			config.set("RandomSpawn.XMax",400D);
			config.set("RandomSpawn.ZMin",-400D);
			config.set("RandomSpawn.ZMax",400D);
		}
	
	}

}
