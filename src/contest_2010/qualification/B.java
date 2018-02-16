package contest_2010.qualification;

import java.math.BigInteger;

import abtric.utility.Solution;

/**
 * Fair Warning
 * 
 * @author Ferry Abt
 *
 */
public class B extends Solution {

	@Override
	protected String solveCaseNo(int i) {
		final String[] S = m_cases.get(i).lines.get(0).split(" ");
		final int N = Integer.parseInt(S[0]);
		BigInteger[] t = new BigInteger[N];
		for (int j = 0; j < N; j++) {
			t[j] = new BigInteger(S[1 + j]);
		}

		BigInteger solution = BigInteger.ZERO;
		BigInteger T = t[0].subtract(t[1]);
		for (int j = 0; j < N - 1; j++) {
			for (int k = 1; k < N; k++) {
				T = T.gcd(t[j].subtract(t[k]));
			}
		}

		if (t[0].divide(T).multiply(T).compareTo(t[0]) == 0) {
			solution = BigInteger.ZERO;
		} else {
			solution = t[0].divide(T).add(BigInteger.ONE).multiply(T).subtract(t[0]);
		}
		boolean done = false;
		while (!done) {
			done = true;
			for (int k = 0; k < N; k++) {
				if (t[k].add(solution).mod(T).compareTo(BigInteger.ZERO) != 0) {
					done = false;
					break;
				}
			}
			solution = solution.add(T);
		}
		solution = solution.subtract(T);
		return solution.toString();
	}

}
