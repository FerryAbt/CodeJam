package contest_2010.qualification;

import java.math.BigInteger;
import java.util.List;

import abtric.utility.Solution;

/**
 * Theme Park
 * 
 * @author Ferry Abt
 *
 */
public class C extends Solution {

	final static BigInteger ZERO = new BigInteger("0");

	@Override
	protected void parseInput(List<String> input) {
		defaultInput(input);
	}

	@Override
	protected String solveCaseNo(int i) {
		final String[] RkN = m_inputFile.get(i * 2 + 1).split(" ");
		final String[] g_raw = m_inputFile.get(i * 2 + 2).split(" ");

		final int R = Integer.parseInt(RkN[0]);
		final BigInteger k = new BigInteger(RkN[1]);
		final int N = Integer.parseInt(RkN[2]);
		final BigInteger[] g = new BigInteger[N];
		int[] lastTimeFirstInLine = new int[N];
		BigInteger totalNumberOfCustomers = ZERO;
		for (int j = 0; j < N; j++) {
			g[j] = new BigInteger(g_raw[j]);
			totalNumberOfCustomers = totalNumberOfCustomers.add(g[j]);
			lastTimeFirstInLine[j] = -1;
		}
		BigInteger[] moneyEarnedWithRideX = new BigInteger[R];

		BigInteger solution = ZERO;

		int firstInLine = 0;
		for (int ride = 0; ride < R; ride++) {
			if (lastTimeFirstInLine[firstInLine] >= 0) {
				BigInteger moneyThroughRepitition = ZERO;
				if (ride - lastTimeFirstInLine[firstInLine] < R - ride) {
					for (int j = lastTimeFirstInLine[firstInLine]; j < ride; j++) {
						moneyThroughRepitition = moneyThroughRepitition.add(moneyEarnedWithRideX[j]);
					}
				}
				int length = ride - lastTimeFirstInLine[firstInLine];
				while (ride + length < R) {
					solution = solution.add(moneyThroughRepitition);
					ride += length;
				}
			}
			lastTimeFirstInLine[firstInLine] = ride;
			BigInteger numberOfPassengers = ZERO;
			while (numberOfPassengers.add(g[firstInLine]).compareTo(k) <= 0
					&& numberOfPassengers.compareTo(totalNumberOfCustomers) < 0) {
				numberOfPassengers = numberOfPassengers.add(g[firstInLine]);
				firstInLine = (firstInLine + 1) % N;
			}
			solution = solution.add(numberOfPassengers);
			moneyEarnedWithRideX[ride] = numberOfPassengers;
		}
		return solution.toString();
	}
}
