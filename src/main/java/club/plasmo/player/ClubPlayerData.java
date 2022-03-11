package club.plasmo.player;

import java.util.HashMap;
import java.util.Map;

public class ClubPlayerData {

	private Map<String, Object> dataMap = new HashMap<>();

	public ClubPlayerData() {
		this.dataMap = new HashMap<>();
	}

	public ClubPlayerData(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void set(String key, Object object) {
		this.dataMap.put(key, object);
	}

	public Object get(String key) {
		return this.dataMap.get(key);
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

}
