package club.plasmo;

import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

import club.plasmo.chat.ChatListener;
import club.plasmo.commands.AuthorizationCommands;
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
		
		new AuthorizationCommands(instance);

		this.gson = new Gson();
		this.clubPlayerManager = new ClubPlayerManager(this.gson, this.getDataFolder());
		
		if (this.getServer().getWorld("club") == null) {
			WorldCreator creator = new WorldCreator("club");
			creator.generator(new BukkitGenerator());

			this.getServer().createWorld(creator);
		}
	}

	public static Club get() {
		return instance;
	}

	public ClubPlayerManager getClubPlayerManager() {
		return clubPlayerManager;
	}

}
