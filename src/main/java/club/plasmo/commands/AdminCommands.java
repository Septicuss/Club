package club.plasmo.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import club.plasmo.Club;

public class AdminCommands implements CommandExecutor{
	
	public AdminCommands(Club plugin) {
		plugin.getCommand("club").setExecutor(this);
		
	}

	public void createKalik(Player player) {
		if(player.getLocation().add(0, -1, 0).getBlock().getType() == Material.BREWING_STAND) {
			Club.get().getMechanicsManager().createKalik(player.getLocation().add(0, -1, 0));
			player.sendMessage("Калик создан");
		}
	}
	

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if(!sender.isOp())
			return true;
		if(args[0].equals("createKalik"))
			createKalik((Player)sender);
		
		return true;
	}

}
