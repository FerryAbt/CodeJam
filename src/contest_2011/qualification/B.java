package contest_2011.qualification;

import abtric.utility.Solution;

/**
 * Magicka
 * 
 * @author Ferry Abt
 *
 */
public class B extends Solution {

	@Override
	protected String solveCase(Case c) {
		String[] split = c.lines.get(0).split(" ");
		int C = Integer.parseInt(split[0]);
		char[][] combinations = new char[26][26];
		for (int i = 0; i < C; i++) {
			combinations[split[i + 1].charAt(0) - 65][split[i + 1].charAt(1) - 65] = split[i + 1].charAt(2);
			combinations[split[i + 1].charAt(1) - 65][split[i + 1].charAt(0) - 65] = split[i + 1].charAt(2);
		}
		int D = Integer.parseInt(split[C + 1]);
		boolean[][] opposed = new boolean[26][26];
		for (int i = 0; i < D; i++) {
			opposed[split[C + i + 2].charAt(0) - 65][split[C + i + 2].charAt(1) - 65] = true;
			opposed[split[C + i + 2].charAt(1) - 65][split[C + i + 2].charAt(0) - 65] = true;
		}
		// int N = Integer.parseInt(split[C + D + 2]);
		char[] elements = split[C + D + 3].toCharArray();
		int endOfList = 1;
		while (endOfList < elements.length && elements[endOfList] > 0) {
			if (endOfList < 1) {
				endOfList = 1;
				continue;
			}
			if (combinations[elements[endOfList - 1] - 65][elements[endOfList] - 65] > 0) {
				elements[endOfList - 1] = combinations[elements[endOfList - 1] - 65][elements[endOfList] - 65];
				for (int i = endOfList; i < elements.length; i++) {
					elements[i] = i + 1 < elements.length ? elements[i + 1] : 0;
				}
				endOfList--;
				continue;
			}
			boolean clear = false;
			for (int i = 0; i < endOfList; i++) {
				if (clear) {
					break;
				}
				for (int j = i + 1; j <= endOfList; j++) {
					if (opposed[elements[i] - 65][elements[j] - 65]) {
						clear = true;
						break;
					}
				}
			}
			if (clear) {
				for (int i = 0; i < elements.length; i++) {
					elements[i] = endOfList + i + 1 < elements.length ? elements[endOfList + i + 1] : 0;
				}
				endOfList = 1;
				continue;
			}
			endOfList++;
		}
		String solution = "[";
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] > 0) {
				solution += elements[i] + (i < elements.length - 1 ? ", " : "");
			} else {
				if (solution.length() > 2) {
					solution = solution.substring(0, solution.length() - 2);
				}
				break;
			}
		}
		solution += "]";
		return solution;
	}
}
