package contest_2010.qualification;

import java.util.List;

import abtric.utility.Solution;

/**
 * Snapper Chain
 * 
 * @author Ferry Abt
 *
 */
public class A extends Solution {

	@Override
	protected void parseInput(List<String> input) {
		defaultInput(input);
	}

	@Override
	protected String solveCaseNo(int i) {
		final String[] S = m_inputFile.get(1 + i).split(" ");
		final int N = Integer.parseInt(S[0]);

		final String result = Integer.toBinaryString(Integer.parseInt(S[1]));

		String solution = "";
		if (result.length() >= N) {
			solution = "ON";
			for (int j = result.length() - N; j < result.length(); j++) {
				if (result.charAt(j) == '0') {
					solution = "OFF";
					break;
				}
			}
		} else {
			solution = "OFF";
		}
		return solution;
	}

}
