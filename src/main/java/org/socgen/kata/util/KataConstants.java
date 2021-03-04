package org.socgen.kata.util;

import java.util.Arrays;
import java.util.List;

/**
 * The KataConstants is a constant file handling all the constants names and
 * messages being used in the Tennis Kata application .
 * 
 * @author Vishal Kumar
 * @version 1.0
 * @since 2021-03-02
 */
public class KataConstants {

	public static final String INVALID_GAME_SCORE = "The game score being updated is invalid. Please use 0, 15, 30, 40 and ADV(player advantage)";
	public static final List<String> GAME_SCORES = Arrays.asList(new String[] { "0", "15", "30", "40", "ADV" });

}
