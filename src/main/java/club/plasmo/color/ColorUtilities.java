package club.plasmo.color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

public class ColorUtilities {
	
	public final static ColorPalette mainColorPalette = new ColorPalette("#E8871E", "#EDB458", "#D4D4AA", "#BAD4AA", "#EBF5DF");
	private final static char COLOR_CHAR = ChatColor.COLOR_CHAR;

	public static String color(String message) {
		final Pattern hexPattern = Pattern.compile("#" + "([A-Fa-f0-9]{6})");
		Matcher matcher = hexPattern.matcher(message);
		StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
		while (matcher.find()) {
			String group = matcher.group(1);
			matcher.appendReplacement(buffer,
					COLOR_CHAR + "x" + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1) + COLOR_CHAR
							+ group.charAt(2) + COLOR_CHAR + group.charAt(3) + COLOR_CHAR + group.charAt(4) + COLOR_CHAR
							+ group.charAt(5));
		}

		String processedString = ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
		return processedString;
	}
}
