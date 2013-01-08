package me.EggieCode.GamersNET.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.EggieCode.GamersNET.GNPlugin;

public class User {
	private int id;
	private String username;
	private Timestamp lastLogin;
	private Timestamp createDate;
	private GNPlugin plugin = GNPlugin.GetGNPlugin();
	private Map<String, Location> homes = new HashMap<String, Location>();
	private boolean homesLoaded = false;

	public User(String username, boolean PlayerJoinEvent) {
		this.username = username;
		GetUserFormDB();
		if (PlayerJoinEvent) {
			JoinEvent();
		}
	}

	void GetUserFormDB() {
		ResultSet result;
		try {
			result = GNPlugin.GetGNDatabase().GetUser(username);
			int rowcount = 0;
			if (result.last()) {
				rowcount = result.getRow();
				result.beforeFirst();
			}

			if (rowcount == 1) {
				result.next();
				id = result.getInt("ID");
				lastLogin = result.getTimestamp("lastLogin");
				createDate = result.getTimestamp("createDate");

			} else {
				if (GNPlugin.GetGNDatabase().CreateUser(username)) {
					result = GNPlugin.GetGNDatabase().GetUser(username);
					result.next();
					id = result.getInt("ID");
					lastLogin = result.getTimestamp("lastLogin");
					createDate = result.getTimestamp("createDate");

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void JoinEvent() {
		GNPlugin.GetGNDatabase().SetLastLoginUser(id);
	}

	private void LoadHomes(boolean allways) {
		if (homesLoaded && !allways)
			return;
		homes.clear();
		ResultSet result = GNPlugin.GetGNDatabase().GetHomesByUserID(id);
		homesLoaded = true;
		try {
			while (result.next()) {
				homes.put(result.getString("name"),
						new Location(
								Bukkit.getWorld(result.getString("world")),
								result.getDouble("x"), result.getDouble("y"),
								result.getDouble("z"), result.getFloat("yaw"),
								result.getFloat("pitch")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Map<String, Location> GetAllHomes() {
		return homes;
	}

	public Location GetHomeByName(String Name) throws Exception {
		LoadHomes(false);
		if (homes.containsKey(Name)) {
			return homes.get(Name);
		} else {
			throw new Exception("Home not exist");
		}

	}

	public boolean SetHome(Location loc, String name) {
		LoadHomes(false);
		if (!homes.containsKey(name)) {
			GNPlugin.GetGNDatabase().AddHomeByUserID(id, loc, name);
			LoadHomes(true);
			return true;
		} else {
			GNPlugin.GetGNDatabase().UpdateHomeByUserID(id, loc, name);
			LoadHomes(true);
			return true;
		}

	}

	public boolean RemoveHome(String name) {
		LoadHomes(false);
		if (homes.containsKey(name)) {
			GNPlugin.GetGNDatabase().RemoveHomeByUserID(id, name);
			LoadHomes(true);
			return true;
		} else {
			LoadHomes(true);
			return false;
		}
	}

	public String GetUsername() {
		return username;
	}

	public Date GetLastLoginDate() {
		return lastLogin;
	}

	public Date GetCreateDate() {
		return createDate;
	}

}
