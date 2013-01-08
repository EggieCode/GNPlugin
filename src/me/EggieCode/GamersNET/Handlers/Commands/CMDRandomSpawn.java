package me.EggieCode.GamersNET.Handlers.Commands;

import javax.swing.DefaultListModel;

import me.EggieCode.GamersNET.GNConfig;
import me.EggieCode.GamersNET.GNPlugin;
import me.EggieCode.GamersNET.Controllers.UserController;
import me.EggieCode.GamersNET.Tools.RandomSpawn;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDRandomSpawn implements CommandExecutor {
	private GNPlugin plugin;

	public CMDRandomSpawn(GNPlugin plugin) {
		this.plugin = plugin;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (player == null) {
			sender.sendMessage("this command can only be run by a player");
			return true;
		}
		Location loc;
		while (true) {
			loc = GetRandomSpawnLoc();
			if (!in_array(new String[] { "9", "10", "11", "12" },
					String.valueOf(loc.getBlock().getTypeId())))
				break;
		}
		player.teleport(loc);
		player.sendMessage("[GN Plugin] Je bent succesvol geteleporteed naar een random locatie");
		return true;
	}

	private Location GetRandomSpawnLoc() {
		return RandomSpawn.RandomSpawn(GNConfig.RandomSpawnArea.get("xmin"),
				GNConfig.RandomSpawnArea.get("xmax"),
				GNConfig.RandomSpawnArea.get("zmin"),
				GNConfig.RandomSpawnArea.get("zmax"));

	}

	private static boolean in_array(String[] strings, String needle) {
		for (int i = 0; i < strings.length; i++) {
			if (strings[i] == needle) {
				return true;
			}
		}
		return false;
	}
}
