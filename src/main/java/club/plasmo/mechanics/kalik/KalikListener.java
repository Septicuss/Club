package club.plasmo.mechanics.kalik;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import club.plasmo.Club;
import club.plasmo.color.ColorUtilities;

public class KalikListener implements Listener {

	@EventHandler
	public void onKalikCharge(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		
		if (event.getItem() == null)
			return;
		
		if (!(event.getItem().getType() == Material.CHARCOAL))
			return;
		
		if (Club.get().getMechanicsManager().getKalik(event.getClickedBlock().getLocation()) == null)
			return;
		
		event.setCancelled(true);
		Kalik kalik = Club.get().getMechanicsManager().getKalik(event.getClickedBlock().getLocation());
		if (kalik.getCoalAmount() == kalik.COAL_MAXIMUM) {
			event.getPlayer().sendMessage(ColorUtilities.mainColorPalette.getFirstColor() + "Калик забит углём");
			return;
		}
		kalik.addCoal();
		event.getItem().setAmount(event.getItem().getAmount() - 1);
	}

	@EventHandler
	public void onKalikUse(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if (!(event.getItem() == null))
			return;
		if (Club.get().getMechanicsManager().getKalik(event.getClickedBlock().getLocation()) == null)
			return;

		event.setCancelled(true);
		Kalik kalik = Club.get().getMechanicsManager().getKalik(event.getClickedBlock().getLocation());
		Player player = event.getPlayer();
		if (kalik.getCoalAmount() == 0) {
			player.sendMessage(ColorUtilities.mainColorPalette.getFirstColor() + "В калике закончились угли");
			return;
		}
		kalik.removeCoal();
		List<Entity> entities = player.getNearbyEntities(40, 40, 40);
		entities.add(player);
		for (Entity entity : entities) {
			if (!(entity instanceof Player))
				continue;	
			Player playyer = (Player)entity;
			playyer.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, player.getLocation().add(0, 1, 0), 5);
			((Player) entity).sendMessage(ColorUtilities.mainColorPalette.getFirstColor() + player.getName()
					+ ColorUtilities.mainColorPalette.getSecondColor() + " смачно напыхтел");
		}
	}
	
	

}
