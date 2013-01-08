package me.EggieCode.GamersNET.Tools;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class RandomSpawn {
	public static Location RandomSpawn(double xmin, double xmax,double zmin, double zmax) {
		
		double x = nextIntInRange(xmin,xmax);
		double z = nextIntInRange(zmin,xmax);
		World world = Bukkit.getServer().getWorlds().get(0);
		
		Block SpawnBlock =  world.getHighestBlockAt((int)x, (int)z);
		Block BeloveSB = world.getBlockAt((int)x,SpawnBlock.getY(), (int)z);
		return new Location(world, x,SpawnBlock.getY() + 2, z);
		
		
	}

	private static double nextIntInRange(double min, double max) {

			return Math.random() * (max - min) + min;
	}
}
