package org.socgen.kata.model;

/**
 * The Match model would the main match for which the tennis scores are to be
 * calculated. . The model would maintain various match states and values to be
 * used throughout the match.
 * 
 * @author Vishal Kumar
 * @version 1.0
 * @since 2021-03-02
 */
public class Match {

	private Player player1;
	private Player player2;
	private boolean isTieBreak;
	private boolean isDeuce;
	private boolean isMatchComplete;
	private Player lastGameWinner;
	private Player matchWinner;

	public Match(Player p1, Player p2) {
		this.player1 = p1;
		this.player2 = p2;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public boolean isTieBreak() {
		return isTieBreak;
	}

	public void setTieBreak(boolean isTieBreak) {
		this.isTieBreak = isTieBreak;
	}

	public boolean isDeuce() {
		return isDeuce;
	}

	public void setDeuce(boolean isDeuce) {
		this.isDeuce = isDeuce;
	}

	public boolean isMatchComplete() {
		return isMatchComplete;
	}

	public void setMatchComplete(boolean isMatchComplete) {
		this.isMatchComplete = isMatchComplete;
	}

	public Player getLastGameWinner() {
		return lastGameWinner;
	}

	public void setLastGameWinner(Player lastGameWinner) {
		this.lastGameWinner = lastGameWinner;
	}

	public Player getMatchWinner() {
		return matchWinner;
	}

	public void setMatchWinner(Player matchWinner) {
		this.matchWinner = matchWinner;
	}

}
