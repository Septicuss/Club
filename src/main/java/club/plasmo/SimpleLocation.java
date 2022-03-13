package club.plasmo;

import org.bukkit.Location;
import org.bukkit.World;

public class SimpleLocation {
	
	private int x;
	private int y;
	private int z;
	
	private World world;
	
	public SimpleLocation(Location loc) {
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
		
		this.world = loc.getWorld();
	}
	
	public static SimpleLocation getSimpleLocation(Location location) {
		return new SimpleLocation(location);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SimpleLocation)) 
			return false;
		SimpleLocation loc2 = (SimpleLocation)obj;
		if((loc2.getX() == x)&&(loc2.getY() == y)&&(loc2.getZ() == z)&&(loc2.getWorld().getName().equals(world.getName()))) 
			return true;
		return false;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public World getWorld() {
		return world;
	}

}
