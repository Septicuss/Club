package club.plasmo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import club.plasmo.Club;
import club.plasmo.chat.ColorUtilities;
import club.plasmo.player.ClubPlayer;
import club.plasmo.player.ClubPlayerData;

public class AuthorizationCommands implements CommandExecutor {

	public AuthorizationCommands(Club plugin) {
		plugin.getCommand("register").setExecutor(this);
		plugin.getCommand("login").setExecutor(this);
	}

	public void register(Player sender, String password, String repeatedPassword) {
		ClubPlayer player = Club.get().getClubPlayerManager().get(sender.getName());
		ClubPlayerData playerData = player.getData();

		if (!(playerData.get("password") == null)) {
			sender.sendMessage(ColorUtilities.mainColorPalette.getFirstColor() + "Вы уже зарегистрированы");
			return;
		}
		if (!password.equals(repeatedPassword)) {
			sender.sendMessage(ColorUtilities.mainColorPalette.getFirstColor() + "Пароли не совпадают!");
			return;
		}

		playerData.set("password", password.hashCode());
		playerData.set("lastIP", sender.getAddress().toString().substring(0, sender.getAddress().toString().length()-6));
		Club.get().getClubPlayerManager().save(player);

		sender.sendMessage(ColorUtilities.mainColorPalette.getFirstColor() + "Вы успешно зарегистрировались");

		sender.teleport(Club.get().getServer().getWorld("club").getSpawnLocation());

	}

	public void login(Player sender, String password) {
		ClubPlayer player = Club.get().getClubPlayerManager().get(sender.getName());
		ClubPlayerData playerData = player.getData();

		if (playerData.get("password") == null) {
			sender.sendMessage(ColorUtilities.mainColorPalette.getFirstColor() + "Вы ещё не зарегистрированы");
			return;
		}
		
		if (password.hashCode() != (Double)playerData.get("password")) {
			sender.sendMessage(ColorUtilities.mainColorPalette.getFirstColor() + "Пароль неверный!");
			return;
		}

		playerData.set("lastIP", sender.getAddress().toString().substring(0, sender.getAddress().toString().length()-6));
		Club.get().getClubPlayerManager().save(player);

		sender.teleport(Club.get().getServer().getWorld("club").getSpawnLocation());

	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {

		if (!needToLogin((Player) sender)) {
			sender.sendMessage(ColorUtilities.mainColorPalette.getFirstColor() + "Зачем?");
			return true;
		}
		if ((command.getName().equals("register") || command.getName().equals("reg"))) {
			if (args.length < 2) {
				sender.sendMessage(ColorUtilities.mainColorPalette.getFirstColor() + "Повторите пароль");
				return true;
			}
			register((Player) sender, args[0], args[1]);
		} else if (command.getName().equals("login") || command.getName().equals("l")) {
			login((Player) sender, args[0]);
		}
		return true;
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
