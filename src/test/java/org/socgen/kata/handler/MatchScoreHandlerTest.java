package org.socgen.kata.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.socgen.kata.exception.InvalidScoreException;
import org.socgen.kata.handlers.MatchScoreHandler;
import org.socgen.kata.model.Match;
import org.socgen.kata.model.Player;

/**
 * The MatchScoreHandlerTest class tests the point addition logic created in the
 * MatchScoreHandler class against predefined inputs.
 * 
 * @author Vishal Kumar
 * @version 1.0
 * @since 2021-03-02
 */
public class MatchScoreHandlerTest {

	Player p1 = new Player("Vishal", 1);
	Player p2 = new Player("Bharat", 2);
	Match match = null;

	@BeforeEach
	void setUpMatchObject() {
		match = new Match(p1, p2);
	}

	@Test
	public void testGameStartScoreEquals0() {
		assertEquals("0", match.getPlayer1().getGameScore());
		assertEquals("0", match.getPlayer1().getGameScore());
	}

	@Test
	public void testGameScoreOnPointScoredBySinglePlayer() {
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		assertEquals("15", match.getPlayer1().getGameScore());
		assertEquals("0", match.getPlayer2().getGameScore());
	}

	@Test
	public void testGameScoreOnMultiplePointsScoredBySinglePlayer() {
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		assertEquals("30", match.getPlayer1().getGameScore());
		assertEquals("0", match.getPlayer2().getGameScore());
	}

	@Test
	public void testGameScoreOnMultiplePointsScoredAlternatively() {

		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);

		assertEquals("30", match.getPlayer1().getGameScore());
		assertEquals("30", match.getPlayer2().getGameScore());
	}

	@Test
	public void testGameScoreOnMultiplePointsScoredRandomly() {

		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);

		assertEquals("30", match.getPlayer1().getGameScore());
		assertEquals("40", match.getPlayer2().getGameScore());
	}

	@Test
	public void testInvalidScoreUpdate() throws InvalidScoreException {
		assertThrows(InvalidScoreException.class, () -> match.getPlayer1().setGameScore("20"));
	}

	@Test
	public void testGameWinningPointWithoutDeuce() throws InvalidScoreException {
		match.getPlayer1().setGameScore("40");
		match.getPlayer2().setGameScore("30");
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		assertEquals("Vishal", match.getLastGameWinner().getName());
	}

	@Test
	public void testGameScoreForDeuce() throws InvalidScoreException {
		match.getPlayer1().setGameScore("40");
		match.getPlayer2().setGameScore("30");
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		assertEquals("40", match.getPlayer1().getGameScore());
		assertEquals("40", match.getPlayer2().getGameScore());
		assertTrue(match.isDeuce());
	}

	@Test
	public void testScoreOnePointOnDeuce() throws InvalidScoreException {
		match.getPlayer1().setGameScore("40");
		match.getPlayer2().setGameScore("40");
		match.setDeuce(true);
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		assertEquals("ADV", match.getPlayer2().getGameScore());
		assertEquals("40", match.getPlayer1().getGameScore());
		assertTrue(match.isDeuce());
	}

	@Test
	public void testScoreTwoConsecutivePointOnDeuce() throws InvalidScoreException {
		match.getPlayer1().setGameScore("40");
		match.getPlayer2().setGameScore("40");
		match.setDeuce(true);
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		assertEquals("Bharat", match.getLastGameWinner().getName());
	}

	@Test
	public void testLosingPointOnAdvantageOnDeuce() throws InvalidScoreException {
		match.getPlayer1().setGameScore("40");
		match.getPlayer2().setGameScore("ADV");
		match.setDeuce(true);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		assertEquals("40", match.getPlayer2().getGameScore());
		assertEquals("40", match.getPlayer1().getGameScore());
		assertTrue(match.isDeuce());
	}

	@Test
	public void testWinningPointOnAdvantageOnDeuce() throws InvalidScoreException {
		match.getPlayer1().setGameScore("40");
		match.getPlayer2().setGameScore("ADV");
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		assertEquals("Bharat", match.getLastGameWinner().getName());
	}

	@Test
	public void testMatchStartSetScoreEqualsZero() {
		assertEquals(0, match.getPlayer1().getSetScore());
		assertEquals(0, match.getPlayer1().getSetScore());
	}

	@Test
	public void testGameWinSetScoreWithoutMatchWin() throws InvalidScoreException {
		match.getPlayer1().setSetScore(3);
		match.getPlayer1().setGameScore("15");
		match.getPlayer2().setSetScore(4);
		match.getPlayer2().setGameScore("40");
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		assertEquals(3, match.getPlayer1().getSetScore());
		assertEquals(5, match.getPlayer2().getSetScore());
	}

	@Test
	public void testGameWinScoreSetWithMatchWin() throws InvalidScoreException {
		match.getPlayer1().setSetScore(3);
		match.getPlayer1().setGameScore("15");
		match.getPlayer2().setSetScore(5);
		match.getPlayer2().setGameScore("40");
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		assertEquals(3, match.getPlayer1().getSetScore());
		assertEquals(6, match.getPlayer2().getSetScore());
		assertEquals(match.getPlayer2().getName(), match.getLastGameWinner().getName());
		assertEquals(match.getPlayer2().getName(), match.getMatchWinner().getName());
		assertTrue(match.isMatchComplete());
	}

	@Test
	public void testMatchWinSetScoreAt7WithoutTieBreak() throws InvalidScoreException {
		match.getPlayer1().setSetScore(6);
		match.getPlayer1().setGameScore("40");
		match.getPlayer2().setSetScore(5);
		match.getPlayer2().setGameScore("30");
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		assertEquals(7, match.getPlayer1().getSetScore());
		assertEquals(5, match.getPlayer2().getSetScore());
		assertEquals(match.getPlayer1().getName(), match.getMatchWinner().getName());
		assertTrue(match.isMatchComplete());
	}

	@Test
	public void testTieBreakOnSetScore6() throws InvalidScoreException {
		match.getPlayer1().setSetScore(6);
		match.getPlayer1().setGameScore("30");
		match.getPlayer2().setSetScore(5);
		match.getPlayer2().setGameScore("40");
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		assertEquals(6, match.getPlayer1().getSetScore());
		assertEquals(6, match.getPlayer1().getSetScore());
		assertNull(match.getMatchWinner());
		assertTrue(match.isTieBreak());
		assertFalse(match.isMatchComplete());
	}

	@Test
	public void testTieBreakScoreWinnerAt6() {
		match.setTieBreak(true);
		match.getPlayer1().setSetScore(6);
		match.getPlayer1().setTieBreakScore(5);
		match.getPlayer2().setSetScore(6);
		match.getPlayer2().setTieBreakScore(3);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		assertEquals(6, match.getPlayer1().getTieBreakScore());
		assertEquals(3, match.getPlayer2().getTieBreakScore());
		assertEquals(7, match.getPlayer1().getSetScore());
		assertEquals(6, match.getPlayer2().getSetScore());
		assertEquals(match.getPlayer1().getName(), match.getMatchWinner().getName());
		assertTrue(match.isMatchComplete());
	}

	@Test
	public void testTieBreakScoreOnSinglePointWon() {
		match.setTieBreak(true);
		match.getPlayer1().setSetScore(6);
		match.getPlayer1().setTieBreakScore(2);
		match.getPlayer2().setSetScore(6);
		match.getPlayer2().setTieBreakScore(1);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		assertEquals(3, match.getPlayer1().getTieBreakScore());
		assertEquals(1, match.getPlayer2().getTieBreakScore());
		assertNull(match.getMatchWinner());
		assertTrue(match.isTieBreak());
		assertFalse(match.isMatchComplete());
	}

	@Test
	public void testTieBreakScoreOnMultiplePointWon() {
		match.setTieBreak(true);
		match.getPlayer1().setSetScore(6);
		match.getPlayer1().setTieBreakScore(1);
		match.getPlayer2().setSetScore(6);
		match.getPlayer2().setTieBreakScore(1);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		assertEquals(4, match.getPlayer1().getTieBreakScore());
		assertEquals(1, match.getPlayer2().getTieBreakScore());
		assertNull(match.getMatchWinner());
		assertTrue(match.isTieBreak());
		assertFalse(match.isMatchComplete());
	}

	@Test
	public void testTieBreakScoreAt6With2PtDifference() {
		match.setTieBreak(true);
		match.getPlayer1().setSetScore(6);
		match.getPlayer1().setTieBreakScore(5);
		match.getPlayer2().setSetScore(6);
		match.getPlayer2().setTieBreakScore(3);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		assertEquals(6, match.getPlayer1().getTieBreakScore());
		assertEquals(3, match.getPlayer2().getTieBreakScore());
		assertEquals(7, match.getPlayer1().getSetScore());
		assertEquals(6, match.getPlayer2().getSetScore());
		assertEquals(match.getPlayer1().getName(), match.getMatchWinner().getName());
		assertTrue(match.isMatchComplete());
	}

	@Test
	public void testTieBreakScoreAt6Without2PtDifference() {
		match.setTieBreak(true);
		match.getPlayer1().setSetScore(6);
		match.getPlayer1().setTieBreakScore(5);
		match.getPlayer2().setSetScore(6);
		match.getPlayer2().setTieBreakScore(5);
		MatchScoreHandler.addPoint(match.getPlayer1(), match.getPlayer2(), match);
		assertEquals(6, match.getPlayer1().getTieBreakScore());
		assertEquals(5, match.getPlayer2().getTieBreakScore());
		assertEquals(6, match.getPlayer1().getSetScore());
		assertEquals(6, match.getPlayer2().getSetScore());
		assertNull(match.getMatchWinner());
		assertTrue(match.isTieBreak());
		assertFalse(match.isMatchComplete());
	}

	@Test
	public void testGameSetTieBreakScoreGreaterThan6Without2PtDifference() {
		match.setTieBreak(true);
		match.getPlayer1().setSetScore(6);
		match.getPlayer1().setTieBreakScore(7);
		match.getPlayer2().setSetScore(6);
		match.getPlayer2().setTieBreakScore(6);
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		assertEquals(7, match.getPlayer1().getTieBreakScore());
		assertEquals(7, match.getPlayer2().getTieBreakScore());
		assertNull(match.getMatchWinner());
		assertTrue(match.isTieBreak());
		assertFalse(match.isMatchComplete());
	}

	@Test
	public void testGameSetTieBreakScoreGreaterThan6With2Difference() {
		match.setTieBreak(true);
		match.getPlayer1().setSetScore(6);
		match.getPlayer1().setTieBreakScore(9);
		match.getPlayer2().setSetScore(6);
		match.getPlayer2().setTieBreakScore(10);
		MatchScoreHandler.addPoint(match.getPlayer2(), match.getPlayer1(), match);
		assertEquals(9, match.getPlayer1().getTieBreakScore());
		assertEquals(11, match.getPlayer2().getTieBreakScore());
		assertEquals(6, match.getPlayer1().getSetScore());
		assertEquals(7, match.getPlayer2().getSetScore());
		assertEquals(match.getPlayer2().getName(), match.getMatchWinner().getName());
		assertTrue(match.isMatchComplete());
	}

}
