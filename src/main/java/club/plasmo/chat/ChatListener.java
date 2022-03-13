package club.plasmo.chat;

import org.apache.logging.log4j.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import club.plasmo.Club;
import club.plasmo.color.ColorUtilities;

public class ChatListener implements Listener {

	public ChatListener(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		event.getPlayer().getWorld().getPlayers()
				.forEach(player -> player.sendMessage(ColorUtilities.mainColorPalette.getSecondColor()
						+ event.getPlayer().getName().concat(": ") + ColorUtilities.mainColorPalette.getColor(5) + event.getMessage()));
		Logger logger = Club.get().getLog4JLogger();
		logger.info(event.getPlayer().getName().concat(": ") + event.getMessage());
	}

}
