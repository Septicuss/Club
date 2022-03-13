package club.plasmo;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import club.plasmo.color.ColorPalette;
import club.plasmo.color.ColorUtilities;
import club.plasmo.player.ClubPlayer;
import club.plasmo.player.ClubPlayerData;

public class ClubListener implements Listener {

	private static final ColorPalette PALETTE = ColorUtilities.mainColorPalette;
	
	private Club club;

	public ClubListener(Club club) {
		this.club = club;
		this.club.getServer().getPluginManager().registerEvents(this, club);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {

		club.getClubPlayerManager().get(e.getPlayer().getName()); // Cache player

		e.setJoinMessage(ColorUtilities.color(PALETTE.getFirstColor() + e.getPlayer().getName()
				+ PALETTE.getColor(4) + " зашёл в клуб."));
		e.getPlayer().setGameMode(GameMode.ADVENTURE);
		e.getPlayer().setFoodLevel(20);
		setupTab(e.getPlayer());

		if (!needToLogin(e.getPlayer())) {
			e.getPlayer().teleport(club.getServer().getWorld("club").getSpawnLocation());
		} else {
			e.getPlayer().sendMessage(PALETTE.getSecondColor() + "Зарегайся или залогинься");
			e.getPlayer().teleport(new Location(club.getServer().getWorld("club"), 14, 79, 88));
		}

	}

	/*-
	 * Used to handle ClubPlayer garbage collection 
	 * (Removing old entries from cache)
	 */
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		final long lastOnline = System.currentTimeMillis();
		final ClubPlayer clubPlayer = getClubPlayer(event.getPlayer().getName());

		final ClubPlayerData data = clubPlayer.getData();
		data.set("lastonline", lastOnline);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		event.setCancelled(true);
	}

	private void setupTab(Player player) {

		player.setPlayerListName(ColorUtilities.color(player.getName()));

		player.setPlayerListHeader(
				ColorUtilities.color(PALETTE.getFirstColor() + "\n   Plasmo Club   \n"));
		player.setPlayerListFooter("\n");

	}

	private boolean needToLogin(Player player) {
		ClubPlayer cPlayer = club.getClubPlayerManager().get(player.getName());

		String IP = player.getAddress().toString().substring(0, player.getAddress().toString().length() - 6);
		if (cPlayer.getData().get("password") == null) {
			return true;
		} else if (!(((String) cPlayer.getData().get("lastIP")).equals(IP))) {
			return true;
		}
		return false;
	}

	private ClubPlayer getClubPlayer(final String name) {
		return this.club.getClubPlayerManager().get(name);
	}
}
