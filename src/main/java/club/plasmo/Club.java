package club.plasmo;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

import club.plasmo.chat.ChatListener;
import club.plasmo.player.ClubPlayer;
import club.plasmo.player.ClubPlayerData;
import club.plasmo.player.ClubPlayerManager;

public class Club extends JavaPlugin {

	private static Club instance;

	private Gson gson;
	private ClubPlayerManager clubPlayerManager;

	public void onLoad() {
		instance = this;
	}

	public void onEnable() {

		instance = this;

		new ChatListener(instance);
		new MainListener(instance);

		this.gson = new Gson();
		this.clubPlayerManager = new ClubPlayerManager(this.gson, this.getDataFolder());

		ClubPlayer player = clubPlayerManager.get("Septicuss");
		ClubPlayerData playerData = player.getData();

		playerData.set("test", 500);

		clubPlayerManager.save(player);
	}

	public static Club get() {
		return instance;
	}

	public ClubPlayerManager getClubPlayerManager() {
		return clubPlayerManager;
	}

}
