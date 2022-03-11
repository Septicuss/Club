package club.plasmo;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MainListener implements Listener {

	public MainListener(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer().teleport(Club.get().getServer().getWorld("world").getSpawnLocation().add(0.5, 0, 0.5));
	}
}
