package org.socgen.kata.model;

import org.socgen.kata.exception.InvalidScoreException;
import org.socgen.kata.util.KataConstants;

/**
 * Player is the main model which would be used in the Match object. Each player
 * would be defined by his name and id and this object would score the game
 * scores, set scores and tie break scores for the player. Game scores could
 * only be from the defined set of values described in constant file.
 * 
 * @author Vishal Kumar
 * @version 1.0
 * @since 2021-03-02
 */
public class Player {

	private String name;
	private Integer gameScoreIndex;
	private Integer setScore;
	private Integer tieBreakScore;
	private Integer id;

	/**
	 * 
	 * Parameterized constructor for creating a Player object
	 */
	public Player(String name, Integer id) {
		this.id = id;
		this.name = name;
		this.gameScoreIndex = 0;
		this.setScore = 0;
		this.tieBreakScore = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGameScore() {
		return KataConstants.GAME_SCORES.get(getGameScoreIndex());
	}

	public Integer getGameScoreIndex() {
		return gameScoreIndex;
	}

	public void setGameScoreIndex(Integer gameScoreIndex) {
		this.gameScoreIndex = gameScoreIndex;
	}

	public Integer getSetScore() {
		return setScore;
	}

	public void setSetScore(Integer setScore) {
		this.setScore = setScore;
	}

	public Integer getTieBreakScore() {
		return tieBreakScore;
	}

	public void setTieBreakScore(Integer tieBreakScore) {
		this.tieBreakScore = tieBreakScore;
	}

	public void setGameScore(String gameScore) throws InvalidScoreException {
		Integer index = KataConstants.GAME_SCORES.indexOf(gameScore);
		if (index == -1) {
			throw new InvalidScoreException();
		}
		this.gameScoreIndex = index;

	}

	public Integer getId() {
		return id;
	}

}
