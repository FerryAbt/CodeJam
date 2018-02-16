package contest_2009.qualification;

import java.util.HashMap;
import java.util.List;

import abtric.utility.Solution;

/**
 * Welcome to Code Jam
 * 
 * @author Ferry Abt
 *
 */
public class C extends Solution {

	private static HashMap<String, Long> known = new HashMap<>();

	private final static char[] TEXT = new char[] { 'w', 'e', 'l', 'c', 'o', 'm', 'e', ' ', 't', 'o', ' ', 'c', 'o',
			'd', 'e', ' ', 'j', 'a', 'm' };

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
	protected void parseInput(List<String> input) {
		defaultInput(input);
	}

	@Override
	protected String solveCaseNo(int i) {
		final String S = m_inputFile.get(1 + i);

		long solution = countSubString(S, 0);

		final String solutionStringRaw = String.format("%04d", solution);

		return solutionStringRaw.substring(solutionStringRaw.length() - 4);
	}
}
