package contest_2011.qualification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import abtric.utility.Solution;

/**
 * Candy Splitting
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
			c.addLine(input.remove(0));// N
			c.addLine(input.remove(0));// C_i
			m_cases.add(c);
		}
	}

	@Override
	protected String solveCase(Case c) {
		// int N = Integer.parseInt(c.lines.get(0));
		List<Integer> C_i = Arrays.asList(c.lines.get(1).split(" ")).stream().map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());

		if (!testPossibility(C_i)) {
			return "NO";
		}
		Collections.sort(C_i);
		int max = getMaximum(C_i, new ArrayList<>(), 0);
		String solution = "NO";
		if (max > 0) {
			solution = Integer.toString(max);
		}
		return solution;
	}

	/**
	 * simple test if it's possible at all. If there is an uneven number of candies
	 * with a 1 at a certain position in the binary representation, there is no way
	 * to split them according to the conditions
	 * 
	 * @param input
	 * @return
	 */
	private boolean testPossibility(List<Integer> input) {
		List<String> list = input.stream().map(i -> Integer.toBinaryString(i)).collect(Collectors.toList());
		int iteration = 0;
		while (!list.isEmpty()) {
			iteration++;
			final int iteration2 = iteration;
			if (list.stream().map(i -> Character.getNumericValue(i.charAt(i.length() - iteration2)))
					.collect(Collectors.summingInt(i -> i)) % 2 != 0) {
				return false;
			}
			list = list.stream().filter(i -> i.length() > iteration2).collect(Collectors.toList());
		}
		return true;
	}

	/**
	 * Recursively tries to find a Patrick-even split with Sean getting at least
	 * {@code currentMax} candy by moving candies from Sean to Patrick
	 * 
	 * @param sean's
	 *            pile
	 * @param patrick's
	 *            pile
	 * @param currentMax
	 *            Sean's pile in the best Patrick-even split that's been found
	 * @return Sean's pile in the best Patrick-even split
	 */
	private int getMaximum(List<Integer> sean, ArrayList<Integer> patrick, int currentMax) {
		int compare = compare(sean, patrick);
		if (compare > 0) {
			return compare;
		}
		if (sean.size() == 1) {
			return 0;
		}
		if (sean.stream().collect(Collectors.summingInt(i -> i)) <= currentMax) {
			return 0;
		}
		int max = currentMax;
		for (int i = 0; i < sean.size(); i++) {
			if (patrick.size() > 0 && sean.get(i) < patrick.get(patrick.size() - 1)) {
				continue;
			}
			ArrayList<Integer> newSean = new ArrayList<>();
			newSean.addAll(sean);
			Integer move = newSean.remove(i);
			ArrayList<Integer> newPatrick = new ArrayList<>();
			newPatrick.addAll(patrick);
			newPatrick.add(move);
			int newMax = getMaximum(newSean, newPatrick, max);
			if (newMax > max) {
				max = newMax;
			}
		}
		return max;
	}

	/**
	 * Checks whether the piles are Patrick-even
	 * 
	 * @param sean's
	 *            pile
	 * @param patrick's
	 *            pile
	 * @return Sean's pile or 0 if not Patrick-even
	 */
	private int compare(List<Integer> sean, ArrayList<Integer> patrick) {
		if (patrick.size() == 0) {
			return 0;
		}
		String sumPatrick = "";
		String sumSean = "";
		List<String> listPatrick = patrick.stream().map(i -> Integer.toBinaryString(i)).collect(Collectors.toList());
		List<String> listSean = sean.stream().map(i -> Integer.toBinaryString(i)).collect(Collectors.toList());
		int iteration = 0;
		// Compare digits after summation from lowest to highest. Any difference means
		// difference in the total
		while (!listPatrick.isEmpty() && !listSean.isEmpty()) {
			iteration++;
			final int iteration2 = iteration;
			sumPatrick = listPatrick.stream().map(i -> Character.getNumericValue(i.charAt(i.length() - iteration2)))
					.collect(Collectors.summingInt(i -> i)) % 2 + sumPatrick;
			listPatrick = listPatrick.stream().filter(i -> i.length() > iteration2).collect(Collectors.toList());
			sumSean = listSean.stream().map(i -> Character.getNumericValue(i.charAt(i.length() - iteration2)))
					.collect(Collectors.summingInt(i -> i)) % 2 + sumSean;
			listSean = listSean.stream().filter(i -> i.length() > iteration2).collect(Collectors.toList());
			if (!sumPatrick.equals(sumSean)) {
				return 0;
			}
		}
		// If Patrick has larger numbers than Sean, they have to cancel out in the
		// higher digits
		while (!listPatrick.isEmpty()) {
			iteration++;
			final int iteration2 = iteration;
			if (listPatrick.stream().map(i -> Character.getNumericValue(i.charAt(i.length() - iteration2)))
					.collect(Collectors.summingInt(i -> i)) % 2 != 0) {
				return 0;
			}
			listPatrick = listPatrick.stream().filter(i -> i.length() > iteration2).collect(Collectors.toList());
		}
		// Same for Sean
		while (!listSean.isEmpty()) {
			iteration++;
			final int iteration2 = iteration;
			if (listSean.stream().map(i -> Character.getNumericValue(i.charAt(i.length() - iteration2)))
					.collect(Collectors.summingInt(i -> i)) % 2 != 0) {
				return 0;
			}
			listSean = listSean.stream().filter(i -> i.length() > iteration2).collect(Collectors.toList());
		}
		// Patrick-even piles -> return Sean's pile
		if (Integer.parseInt(sumSean, 2) == Integer.parseInt(sumPatrick, 2)) {
			return sean.stream().collect(Collectors.summingInt(i -> i));
		}
		return 0;
	}

}
