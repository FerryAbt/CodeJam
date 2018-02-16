package contest_2010.qualification;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import abtric.utility.Solution;

/**
 * Theme Park
 * 
 * @author Ferry Abt
 *
 */
public class C extends Solution {

	@Override
	protected void parseInput(List<String> input) {
		m_cases = new ArrayList<>();
		m_numOfProblems = Integer.parseInt(input.remove(0));
		for (int i = 0; i < m_numOfProblems; i++) {
			Case c = new Case(i);
			c.addLine(input.remove(0));
			c.addLine(input.remove(0));
			m_cases.add(c);
		}
	}

	@Override
	protected String solveCase(Case c) {
		final String[] RkN = c.lines.get(0).split(" ");
		final String[] g_raw = c.lines.get(1).split(" ");
		final int R = Integer.parseInt(RkN[0]);
		final BigInteger k = new BigInteger(RkN[1]);
		final int N = Integer.parseInt(RkN[2]);
		final BigInteger[] g = new BigInteger[N];
		int[] lastTimeFirstInLine = new int[N];
		BigInteger totalNumberOfCustomers = BigInteger.ZERO;
		for (int j = 0; j < N; j++) {
			g[j] = new BigInteger(g_raw[j]);
			totalNumberOfCustomers = totalNumberOfCustomers.add(g[j]);
			lastTimeFirstInLine[j] = -1;
		}
		BigInteger[] moneyEarnedWithRideX = new BigInteger[R];
		BigInteger solution = BigInteger.ZERO;
		int firstInLine = 0;
		for (int ride = 0; ride < R; ride++) {
			if (lastTimeFirstInLine[firstInLine] >= 0) {
				BigInteger moneyThroughRepitition = BigInteger.ZERO;
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
			BigInteger numberOfPassengers = BigInteger.ZERO;
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
