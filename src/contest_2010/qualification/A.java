package contest_2010.qualification;

import abtric.utility.Solution;

/**
 * Snapper Chain
 * 
 * @author Ferry Abt
 *
 */
public class A extends Solution {

	@Override
	protected String solveCaseNo(int i) {
		final String[] S = m_cases.get(i).lines.get(0).split(" ");
		final int N = Integer.parseInt(S[0]);
		final String result = Integer.toBinaryString(Integer.parseInt(S[1]));
		if (result.length() < N) {
			return "OFF";
		}
		for (int j = result.length() - N; j < result.length(); j++) {
			if (result.charAt(j) == '0') {
				return "OFF";
			}
		}
		return "ON";
	}

}
