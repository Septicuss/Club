package club.plasmo;

import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import club.plasmo.chat.ChatListener;
import club.plasmo.commands.AdminCommands;
import club.plasmo.commands.AuthorizationCommands;
import club.plasmo.mechanics.MechanicsManager;
import club.plasmo.player.ClubPlayerManager;

public class Club extends JavaPlugin {

	private static Club instance;

	private Gson gson;
	private ClubPlayerManager clubPlayerManager;
	private MechanicsManager mechanicsManager;

	public void onLoad() {
		instance = this;
	}

	public void onEnable() {

		instance = this;

		new ChatListener(instance);
		new ClubListener(instance);

		new AuthorizationCommands(instance);
		new AdminCommands(instance);
		
		this.mechanicsManager = new MechanicsManager(instance);
		
		this.gson = new GsonBuilder().setLenient().create();
		this.clubPlayerManager = new ClubPlayerManager(this.gson, this.getDataFolder());

		if (this.getServer().getWorld("club") == null) {
			WorldCreator creator = new WorldCreator("club");
			creator.generator(new BukkitGenerator());

			this.getServer().createWorld(creator);
		}
	}

	public void onDisable() {
		this.clubPlayerManager.saveCached();
	}

	public static Club get() {
		return instance;
	}

	public ClubPlayerManager getClubPlayerManager() {
		return clubPlayerManager;
	}

	public MechanicsManager getMechanicsManager() {
		return mechanicsManager;
	}

}
