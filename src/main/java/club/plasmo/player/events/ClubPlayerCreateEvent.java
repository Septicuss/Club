package club.plasmo.player.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import club.plasmo.player.ClubPlayer;

/**
 * Event that is called, whether a new player creates their account. Can be used
 * to, for example add a start kit or some starting data. Get and modify the
 * given ClubPlayer
 */
public class ClubPlayerCreateEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	private final String playerName;
	private final ClubPlayer clubPlayer;

	public ClubPlayerCreateEvent(final String playerName, final ClubPlayer clubPlayer) {
		this.playerName = playerName;
		this.clubPlayer = clubPlayer;
	}

	public String getPlayerName() {
		return playerName;
	}

	public ClubPlayer getClubPlayer() {
		return clubPlayer;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

}
