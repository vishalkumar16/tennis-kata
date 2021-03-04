package org.socgen.kata.handlers;

import org.socgen.kata.model.Match;
import org.socgen.kata.model.Player;

/**
 * MatchScoreHandler is the main class handling the point system logic in the
 * system. The method addPoint would be exposed to the other classes to update
 * the state of the match and points.
 * 
 * @author Vishal Kumar
 * @version 1.0
 * @since 2021-03-02
 */
public class MatchScoreHandler {

	/**
	 * addPoint is the main method exposed for adding the points for a particular
	 * match. The method takes in the winner of the point, the loser and match.
	 * Using the match state, paths for whether TieBreakPoint or GameScore point
	 * systems would be used are defined in the method.
	 * 
	 * @param winner
	 * @param loser
	 * @param match
	 */
	public static void addPoint(Player winner, Player loser, Match match) {
		if (match.isTieBreak()) {
			addTieBreakPoint(winner, loser, match);
		} else {
			updateGameScore(winner, loser, match);
		}

	}

	/**
	 * updateGameScore updates the game scores for the players who has won the
	 * match. The set scores are also updated when the player has won the game and
	 * match object is loaded with the winner in the variable lastGameWinner.
	 * 
	 * @param winner
	 * @param loser
	 * @param match
	 */
	private static void updateGameScore(Player winner, Player loser, Match match) {
		if ((winner.getGameScore() == "40" && !match.isDeuce()) || (winner.getGameScore() == "ADV")) {
			updateGameWin(winner, loser, match);
		} else if (match.isDeuce()) {
			handleDeuce(winner, loser);
		} else if (loser.getGameScore() == "40" && winner.getGameScore() == "30") {
			winner.setGameScoreIndex(loser.getGameScoreIndex());
			match.setDeuce(true);
		} else {
			winner.setGameScoreIndex(winner.getGameScoreIndex() + 1);
		}
	}

	/**
	 * This method updates the point scoring mechanism as per the tennis deuce
	 * standards. When both the players are at 40, advantage is given to the players
	 * while if the player with advantage loses he is brought back to the initial
	 * score of 40 and deuce is maintained.
	 * 
	 * @param winner
	 * @param loser
	 */
	private static void handleDeuce(Player winner, Player loser) {
		if (winner.getGameScore().equals(loser.getGameScore())) {
			winner.setGameScoreIndex(winner.getGameScoreIndex() + 1);
		} else {
			loser.setGameScoreIndex(loser.getGameScoreIndex() - 1);
		}
	}

	/**
	 * UpdateGamewin updates the set scores when a player wins the game. After a
	 * player has won a game, the game scores are reset such that new game can start
	 * with fresh scores. If any player reaches 6 games maintaining a difference of
	 * 2, he is declared a winner. If the scores are at 6-5 and the player with 6
	 * games win, the player is declared the winner and the score becomes 7 -5.
	 * 
	 * @param winner
	 * @param loser
	 * @param match
	 */
	private static void updateGameWin(Player winner, Player loser, Match match) {
		winner.setGameScoreIndex(0);
		loser.setGameScoreIndex(0);
		winner.setSetScore(winner.getSetScore() + 1);
		match.setLastGameWinner(winner);
		match.setDeuce(false);
		if (winner.getSetScore() == 6 && loser.getSetScore() == 6) {
			match.setTieBreak(true);
		} else if ((winner.getSetScore() - loser.getSetScore()) >= 2 && winner.getSetScore() >= 6) {
			updateMatchWinner(winner, match);
		}
	}

	/**
	 * addTieBreakPoint updates the set scores when player scores points in a tie
	 * break. The first to 6 points with a difference of 2 wins the game and match
	 * with a score of 7 - 6. However, if no player achieves a difference of 2 on
	 * reaching 6, the game continues till a player reaches a 2 point difference at
	 * which it is stopped.
	 * 
	 * @param winner
	 * @param loser
	 * @param match
	 */

	private static void addTieBreakPoint(Player winner, Player loser, Match match) {
		if ((winner.getTieBreakScore() - loser.getTieBreakScore()) >= 1 && winner.getTieBreakScore() >= 5) {
			winner.setSetScore(7);
			updateMatchWinner(winner, match);
		} else {
			winner.setTieBreakScore(winner.getTieBreakScore() + 1);
		}
	}

	/**
	 * The method updates the match winner in the match object and sets the status
	 * of the match complete.
	 * 
	 * @param winner
	 * @param match
	 */
	private static void updateMatchWinner(Player winner, Match match) {
		winner.setTieBreakScore(winner.getTieBreakScore() + 1);
		match.setMatchWinner(winner);
		match.setMatchComplete(true);
	}

}
