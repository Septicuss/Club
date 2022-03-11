package club.plasmo.chat;

public class ColorPalette {

	private String[] hexColors;

	public ColorPalette(String... hexColors) {
		this.hexColors = hexColors;
	}

	public String[] getHexColors() {
		return hexColors;
	}

	public String getFirstColor() {
		return getColor(1);
	}

	public String getSecondColor() {
		return getColor(2);
	}

	public String getColor(int index) {
		return ColorUtilities.color(hexColors[index - 1]);
	}

}
