package club.plasmo.player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.gson.Gson;

import club.plasmo.Club;
import club.plasmo.player.events.ClubPlayerCreateEvent;

public class ClubPlayerManager {

	/* Path to the player data folder, where json files are stored */
	private static final String PATH = "data/players";

	/* Defines how often the cache garbage collector is ran (seconds) */
	private static final long GARBAGE_COLLECTOR_DELAY = 180;

	private Gson gson;
	private File pluginDataFolder;
	private ConcurrentHashMap<String, ClubPlayer> clubPlayerCache;

	public ClubPlayerManager(final Gson gson, final File pluginDataFolder) {
		this.gson = gson;
		this.pluginDataFolder = pluginDataFolder;
		this.clubPlayerCache = new ConcurrentHashMap<>();

		runCacheGarbageCollector();
	}

	// Public Getters

	public void save(ClubPlayer clubPlayer) {
		final File playerFile = getPlayerFile(clubPlayer.getName());
		save(playerFile, clubPlayer);
	}

	public ClubPlayer get(String name) {
		return (isCached(name) ? getCached(name) : loadOrCreateNew(name));
	}

	public void saveCached() {
		for (ClubPlayer clubPlayer : clubPlayerCache.values()) {
			save(clubPlayer);
		}
	}

	// Caching

	private void runCacheGarbageCollector() {

		Bukkit.getScheduler().runTaskTimer(Club.get(), () -> {

			for (ClubPlayer clubPlayer : clubPlayerCache.values()) {
				if (!isGarbage(clubPlayer)) {
					continue;
				}

				save(clubPlayer);
				removeFromCache(clubPlayer.getName());
			}

		}, GARBAGE_COLLECTOR_DELAY, GARBAGE_COLLECTOR_DELAY);

	}

	private boolean isGarbage(ClubPlayer cachedClubPlayer) {
		if (cachedClubPlayer == null)
			return true;

		final Player player = cachedClubPlayer.getPlayer();
		final boolean online = player != null && player.isOnline();

		if (online)
			return false;

		final ClubPlayerData playerData = cachedClubPlayer.getData();
		final long lastOnline = (long) playerData.get("lastonline");

		final long timeSinceLogOff = System.currentTimeMillis() - lastOnline;
		return timeSinceLogOff >= (GARBAGE_COLLECTOR_DELAY * 1000);
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

	private void save(File playerFile, ClubPlayer clubPlayer) {
		try {
			final String json = gson.toJson(clubPlayer);

			if (!playerFile.exists()) {
				playerFile.getParentFile().mkdirs();
				playerFile.createNewFile();
			}

			Files.writeString(playerFile.toPath(), json, StandardOpenOption.WRITE);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

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

			ClubPlayer clubPlayer = gson.fromJson(json, ClubPlayer.class);
			addToCache(clubPlayer);

			return clubPlayer;
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
