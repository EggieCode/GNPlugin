package me.EggieCode.GamersNET.Handlers.Commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import me.EggieCode.GamersNET.GNPlugin;
import me.EggieCode.GamersNET.Controllers.UserController;
import me.EggieCode.GamersNET.Models.User;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDHome implements CommandExecutor {
	private GNPlugin plugin;

	public CMDHome(GNPlugin plugin) {
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
		User user = UserController.getInstance().getUser(player, false);
		if (cmd.getName().equalsIgnoreCase("home")) {

			switch (args.length) {
			case 0:
				TeleportPlayerToHome(player, "default");
				return true;
			case 1:
				switch (args[0].toLowerCase()) {
				case "help":
					break;
				case "set":
					user.SetHome(player.getLocation(), "default");
					player.sendMessage("Jou home is succesvol geplaats");
					return true;
				case "list":

					if (user.GetAllHomes().size() == 0) {
						player.sendMessage(new String[] {
								"Je hebt nog geen home's neergezet",
								"Doe /home help voor meer info" });
						break;

					}
					player.sendMessage("Jou Home's:");
					for (String key : user.GetAllHomes().keySet()) {
						player.sendMessage(key + " | "
								+ user.GetAllHomes().get(key).toString());
					}
					return true;

				default:
					TeleportPlayerToHome(player, args[0]);
					return true;
				}
				break;
			case 2:

				switch (args[0].toLowerCase()) {
				case "del":
					if (user.RemoveHome(args[1])) {
						player.sendMessage(args[1]
								+ "home is succes vol verwijdered");
					} else
						player.sendMessage(new String[] {
								args[1] + " home bestaat niet!",
								"/home list voor al jou home's" });
					return true;
				case "set":
					user.SetHome(player.getLocation(), args[1]);
					player.sendMessage("Jou home is succesvol geplaats");
					return true;
				}

			}

		} else if (cmd.getName().equalsIgnoreCase("sethome")) {

		}

		player.sendMessage(new String[] { "--- Home Help ---",
				"/home > Teleport je naar je default home",
				"/home [naam] > Telepoteer je naar een home met een naam",
				"/home set > Zet je default home neer waar je staat",
				"/home set [naam] > Zet je home neer met een naam",
				"/home del [naam] > Verwijder een home",
				"/home list > Weergeeft al jou home's met cor." });
		return true;
	}

	private void TeleportPlayerToHome(Player ply, String name) {

		try {
			User user = UserController.getInstance().getUser(ply, false);
			Location loc = user.GetHomeByName(name);
			ply.sendMessage("Je bent succes voor teleporteed naar je home!");
			ply.teleport(loc);
		} catch (Exception e) {
			if (e.getMessage() == "Home not exist") {
				ply.sendMessage(new String[] {
						"Je hebt je home nog niet gezet!",
						"Doe /home help voor meer info" });
			} else
				plugin.getLogger().log(Level.WARNING, e.getMessage());
		}
	}

}
