package me.EggieCode.GamersNET;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.Location;

import me.EggieCode.GamersNET.Controllers.MySQLController;

public class GNDatabase extends MySQLController {
	private GNPlugin plugin;

	public GNDatabase(GNPlugin plugin) {

		super(GNConfig.DatabaseConfig.get("hostname"), GNConfig.DatabaseConfig
				.get("port"), GNConfig.DatabaseConfig.get("database"),
				GNConfig.DatabaseConfig.get("username"),
				GNConfig.DatabaseConfig.get("password"));
		try {
			connection = open();
			plugin.getLogger().log(Level.INFO, "Open connection!");

		} catch (ClassNotFoundException | SQLException e) {
			plugin.getLogger().log(Level.WARNING, "!-----------------------!");
			plugin.getLogger().log(Level.WARNING, "Config the plugin!");
			plugin.getLogger().log(Level.WARNING, e.getMessage());
			plugin.getLogger().log(Level.WARNING, "!-----------------------!");
			plugin.getLogger().log(Level.WARNING, "Shuting down!");
			plugin.getPluginLoader().disablePlugin(plugin);

		}

	}

	public void Disconnect() {
		try {
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet GetUser(String username) {
		try {
			ResultSet res = query("SELECT * FROM `user` WHERE `username` = '"
					+ username + "'");
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean CreateUser(String username) {
		return this.insert("user", new String[] { "username", "createDate" },
				new String[] { "'" + username + "'", "now()" });
	}

	public void SetLastLoginUser(int id) {
		try {
			this.query("UPDATE user SET lastLogin = now() WHERE ID = " + id
					+ "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ResultSet GetHomesByUserID(int ID_user) {
		try {
			return query("SELECT * FROM `homes` WHERE `ID_user` = " + ID_user);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean AddHomeByUserID(int ID_user, Location loc, String name) {
		return insert(
				"homes",
				new String[] { "ID_user", "x", "y", "z", "pitch", "yaw",
						"world", "name" },
				new String[] { String.valueOf(ID_user),
						"'" + String.valueOf(loc.getX()) + "'",
						"'" + String.valueOf(loc.getY()) + "'",
						"'" + String.valueOf(loc.getZ()) + "'",
						"'" + String.valueOf(loc.getPitch()) + "'",
						"'" + String.valueOf(loc.getYaw()) + "'",
						"'" + loc.getWorld().getName() + "'", "'" + name + "'" });

	}

	public void RemoveHomeByUserID(int id, String name) {

		try {
			this.query("DELETE FROM homes WHERE ID_user = " + id
					+ " AND name = '" + name + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void UpdateHomeByUserID(int id, Location loc, String name) {
		try {
			String query = String.format(QueryFormats.Update_TabelHome,
					loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(),
					loc.getYaw(), id, name);
			this.query(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private final static class QueryFormats {
		public final static String Update_TabelHome = "UPDATE homes SET x='&f' y='&f' z='&f' pitch='&f' yaw='&f' WHERE ID_user = '&i' AND name = '&s'";

	}
}
