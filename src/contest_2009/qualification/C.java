package contest_2009.qualification;

import java.util.HashMap;

import abtric.utility.Solution;

/**
 * Welcome to Code Jam
 * 
 * @author Ferry Abt
 *
 */
public class C extends Solution {

	private static final HashMap<String, Long> known = new HashMap<>();

	private static final char[] TEXT = "welcome to code jam".toCharArray();

	private long countSubString(final String S, final int i) {
		if (known.containsKey(i + S)) {
			return known.get(i + S);
		}
		long solution = 0;
		for (int j = 0; j <= S.length() - (TEXT.length - i); j++) {
			if (S.charAt(j) != TEXT[i]) {
				continue;
			}
			if (i == TEXT.length - 1) {
				solution = (solution + 1) % 10000;
			} else {
				solution += countSubString(S.substring(j + 1), i + 1) % 10000;
			}
		}
		known.put(i + S, solution);
		return solution;
	}

	@Override
	protected String solveCase(Case c) {
		final String S = c.lines.get(0);
		long solution = countSubString(S, 0);
		final String solutionStringRaw = String.format("%04d", solution);
		return solutionStringRaw.substring(solutionStringRaw.length() - 4);
	}
}
