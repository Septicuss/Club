package club.plasmo.mechanics;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Location;

import club.plasmo.Club;
import club.plasmo.SimpleLocation;
import club.plasmo.mechanics.kalik.Kalik;
import club.plasmo.mechanics.kalik.KalikListener;

public class MechanicsManager {
	
	private HashMap<SimpleLocation, Kalik> kaliks;
	
	public MechanicsManager(Club plugin) {
		
		kaliks = new HashMap<>();
		
		plugin.getServer().getPluginManager().registerEvents(new KalikListener(), plugin);
		
	}
	
	public void createKalik(Location location) {
		Kalik kalik = new Kalik(location);
		kaliks.put(SimpleLocation.getSimpleLocation(location), kalik);
	}
	
	public Kalik getKalik(Location location) {
		for(Entry<SimpleLocation, Kalik> entry: kaliks.entrySet()) {
			Kalik kalik = entry.getValue();
			if(kalik.getLocation().equals(SimpleLocation.getSimpleLocation(location)))
				return kalik;
		}
		return null;
	}

}
