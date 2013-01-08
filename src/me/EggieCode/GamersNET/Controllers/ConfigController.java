package me.EggieCode.GamersNET.Controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ConfigController {
	private final JavaPlugin plugin;
	private final String dataFolder;
	private final InputStream defConfigStream;

	protected FileConfiguration config = null;
	private File customConfigFile = null;

	protected ConfigController(JavaPlugin plugin, String FileName) {
		this.plugin = plugin;
		dataFolder = plugin.getDataFolder().toString();
		defConfigStream = plugin.getResource(FileName);

		OpenConfig();
		SaveConfig();
	}

	private void OpenConfig() {
		if (customConfigFile == null) {
			customConfigFile = new File(dataFolder, "config.yml");
		}
		boolean fileExist = customConfigFile.exists();
		if (!fileExist) {
			this.plugin.saveResource("config.yml", false);
		}
		config = YamlConfiguration.loadConfiguration(customConfigFile);

		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			config.setDefaults(defConfig);
		}
		if (!fileExist) {
			config.set("version", plugin.getDescription().getVersion());
		}
		config.set("version", null);
		LoadDefaultConfig();
		config.set("version", plugin.getDescription().getVersion());
		
	}

	protected abstract void LoadDefaultConfig();

	public void SaveConfig() {

		try {
			config.save(customConfigFile);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.ALL, ex.getMessage());
			plugin.getLogger().log(Level.ALL, "Shuting down!");
			plugin.getPluginLoader().disablePlugin(plugin);
		}

	}

	public void ReloadConfig() {
		OpenConfig();
	}

	public String getVersion() {
		return config.getString("version");
	}

}