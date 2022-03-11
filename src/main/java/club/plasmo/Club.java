package club.plasmo;

import org.bukkit.plugin.java.JavaPlugin;

import club.plasmo.chat.ChatListener;

public class Club extends JavaPlugin {
	
	private static Club instance;

	public void onEnable() {
		
		instance = this;
		
		new ChatListener(instance);
		new MainListener(instance);
		

	}
	
	public static Club get() {
		return instance;
	}

}
