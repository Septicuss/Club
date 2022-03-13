package club.plasmo.mechanics.kalik;

import org.bukkit.Location;

import club.plasmo.SimpleLocation;

public class Kalik {
	
	public final int COAL_MAXIMUM = 3;

	private SimpleLocation location;
	private int coal;

	public Kalik(Location location) {
		this.location = SimpleLocation.getSimpleLocation(location);
		coal = 0;
	}
	
	public int getCoalAmount() {
		return coal;
	}

	public void addCoal() {
		if (coal == COAL_MAXIMUM) 
			return;
		coal += 1;
	}

	public void removeCoal() {
		if (coal == 0) 
			return;
		coal -= 1;
	}

	public SimpleLocation getLocation() {
		return location;
	}

}
