package club.plasmo.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClubPlayer {

	private String name;
	private ClubPlayerData data;

	public ClubPlayer(final String name, final ClubPlayerData data) {
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public ClubPlayerData getData() {
		return data;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(name);
	}

}
