package me.EggieCode.GamersNET.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import me.EggieCode.GamersNET.Models.User;

public class UserController {
	private static UserController UserCon = null;
	private Map<String, User> Users = new HashMap<String, User>();

	private UserController() {

	}

	public User getUser(String username, boolean PlayerJoinEvent) {
		if (!Users.containsKey(username)) {
			User user = new User(username, PlayerJoinEvent);
			Users.put(username, user);
			return user;
		} else
			return Users.get(username);
	}

	public User getUser(Player player, boolean PlayerJoinEvent) {
		if (!Users.containsKey(player.getName())) {
			User user = new User(player.getName(), PlayerJoinEvent);
			Users.put(player.getName(), user);
			return user;
		} else
			return Users.get(player.getName());
	}

	public Map<String, User> getAllUsers() {
		return Users;
	}

	public static UserController getInstance() {
		if (UserCon == null) {
			return UserCon = new UserController();

		} else
			return UserCon;
	}
	public static void Destory() {
		UserCon = null;
	}
}
