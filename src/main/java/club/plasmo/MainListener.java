package club.plasmo;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import club.plasmo.chat.ColorUtilities;
import club.plasmo.player.ClubPlayer;

public class MainListener implements Listener {

	public MainListener(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(ColorUtilities.color(ColorUtilities.mainColorPalette.getFirstColor() + e.getPlayer().getName()
				+ ColorUtilities.mainColorPalette.getColor(4) + " зашёл в клуб."));
		e.getPlayer().setGameMode(GameMode.ADVENTURE);
		e.getPlayer().setFoodLevel(20);
		setupTab(e.getPlayer());

		if (!needToLogin(e.getPlayer())) {
			e.getPlayer().sendMessage(
					ColorUtilities.mainColorPalette.getSecondColor() + "Зарегайся или залогинься");
			e.getPlayer().teleport(Club.get().getServer().getWorld("club").getSpawnLocation());
		} else {
			e.getPlayer().teleport(new Location(Club.get().getServer().getWorld("club"), 14, 79, 88));
		}

	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}

	private void setupTab(Player player) {

		player.setPlayerListName(ColorUtilities.color(player.getName()));

		player.setPlayerListHeader(
				ColorUtilities.color(ColorUtilities.mainColorPalette.getFirstColor() + "\n   Plasmo Club   \n"));
		player.setPlayerListFooter("\n");
	}

	public boolean needToLogin(Player player) {
		ClubPlayer cPlayer = Club.get().getClubPlayerManager().get(player.getName());
		
		String IP = player.getAddress().toString().substring(0, player.getAddress().toString().length()-6);
		if (cPlayer.getData().get("password") == null) {
			return true;
		} else if (!(((String)cPlayer.getData().get("lastIP")).equals(IP))) {
			return true;
		}
		return false;
	}
}
