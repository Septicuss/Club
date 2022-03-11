package club.plasmo.player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;

import club.plasmo.player.events.ClubPlayerCreateEvent;

public class ClubPlayerManager {

	/* Path to the player data folder, where json files are stored */
	private static final String PATH = "data/players";

	private Gson gson;
	private File pluginDataFolder;
	private ConcurrentHashMap<String, ClubPlayer> clubPlayerCache;

	public ClubPlayerManager(final Gson gson, final File pluginDataFolder) {
		this.gson = gson;
		this.pluginDataFolder = pluginDataFolder;
		this.clubPlayerCache = new ConcurrentHashMap<>();
	}

	// Public Getters

	public void save(ClubPlayer clubPlayer) {
		final File playerFile = getPlayerFile(clubPlayer.getName());
		final String json = gson.toJson(clubPlayer);

		try {
			if (!playerFile.exists()) {
				playerFile.getParentFile().mkdirs();
				playerFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		try {
			Files.writeString(playerFile.toPath(), json, StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ClubPlayer get(String name) {
		if (isCached(name)) {
			return getCached(name);
		}

		return loadOrCreateNew(name);
	}

	// Caching

	private void runCacheGarbageCollector() {

	}

	private boolean isCached(String name) {
		return clubPlayerCache.containsKey(name);
	}

	private ClubPlayer getCached(String name) {
		return clubPlayerCache.get(name);
	}

	private void addToCache(ClubPlayer clubPlayer) {
		clubPlayerCache.put(clubPlayer.getName(), clubPlayer);
	}

	private void removeFromCache(String name) {
		clubPlayerCache.remove(name);
	}

	// Loading

	private ClubPlayer loadOrCreateNew(String name) {
		final File playerFile = getPlayerFile(name);

		if (!playerFile.exists()) {
			return createNew(name);
		}

		try {
			final String json = Files.readString(playerFile.toPath());

			if (json == null || json.isBlank() || json.isEmpty()) {
				return createNew(name);
			}

			return gson.fromJson(json, ClubPlayer.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ClubPlayer createNew(String name) {
		ClubPlayer clubPlayer = new ClubPlayer(name, new ClubPlayerData());
		ClubPlayerCreateEvent createEvent = new ClubPlayerCreateEvent(name, clubPlayer);

		createEvent.callEvent();

		addToCache(clubPlayer);
		save(clubPlayer);

		return clubPlayer;
	}

	private File getPlayerFile(String name) {
		final File playerFile = new File(pluginDataFolder, PATH + "/" + name + ".json");
		return playerFile;
	}

}
