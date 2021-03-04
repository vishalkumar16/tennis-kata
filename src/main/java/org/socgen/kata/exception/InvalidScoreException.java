package org.socgen.kata.exception;

import org.socgen.kata.util.KataConstants;

public class InvalidScoreException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3710515414856878801L;

	@Override
	public String getMessage() {
		return KataConstants.INVALID_GAME_SCORE;
	}

}
