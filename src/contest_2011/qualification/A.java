package contest_2011.qualification;

import abtric.utility.Solution;

/**
 * Bot Trust
 * 
 * @author Ferry Abt
 *
 */
public class A extends Solution {

	@Override
	protected String solveCase(Case c) {
		final String[] S = c.lines.get(0).split(" ");
		final int N = Integer.parseInt(S[0]);
		boolean[] orderForBlue = new boolean[N];
		int[] buttonSequence = new int[N];
		for (int j = 0; j < N; j++) {
			orderForBlue[j] = S[j * 2 + 1].equals("B");
			buttonSequence[j] = Integer.parseInt(S[j * 2 + 2]);
		}
		int solution = 0;
		int currentInstruction = 0;
		int orangeLocation = 1;
		int blueLocation = 1;
		int nextInstForOrange = 0;
		while (nextInstForOrange < N && orderForBlue[nextInstForOrange]) {
			nextInstForOrange++;
		}
		if (nextInstForOrange == N) {
			nextInstForOrange = -1;
		}
		int nextInstForBlue = 0;
		while (nextInstForBlue < N && !orderForBlue[nextInstForBlue]) {
			nextInstForBlue++;
		}
		if (nextInstForBlue == N) {
			nextInstForBlue = -1;
		}
		while (currentInstruction < N) {
			boolean pressedButton = false;
			if (!orderForBlue[currentInstruction]) {
				if (buttonSequence[currentInstruction] == orangeLocation) {
					pressedButton = true;
					nextInstForOrange++;
					while (nextInstForOrange < N && orderForBlue[nextInstForOrange]) {
						nextInstForOrange++;
					}
					if (nextInstForOrange == N) {
						nextInstForOrange = -1;
					}
				} else if (buttonSequence[currentInstruction] < orangeLocation) {
					orangeLocation--;
				} else {
					orangeLocation++;
				}
			} else {
				if (nextInstForOrange >= 0 && buttonSequence[nextInstForOrange] < orangeLocation) {
					orangeLocation--;
				} else if (nextInstForOrange >= 0 && buttonSequence[nextInstForOrange] > orangeLocation) {
					orangeLocation++;
				}
			}
			if (orderForBlue[currentInstruction]) {
				if (buttonSequence[currentInstruction] == blueLocation) {
					pressedButton = true;
					nextInstForBlue++;
					while (nextInstForBlue < N && !orderForBlue[nextInstForBlue]) {
						nextInstForBlue++;
					}
					if (nextInstForBlue == N) {
						nextInstForBlue = -1;
					}
				} else if (buttonSequence[currentInstruction] < blueLocation) {
					blueLocation--;
				} else {
					blueLocation++;
				}
			} else {
				if (nextInstForBlue >= 0 && buttonSequence[nextInstForBlue] < blueLocation) {
					blueLocation--;
				} else if (nextInstForBlue >= 0 && buttonSequence[nextInstForBlue] > blueLocation) {
					blueLocation++;
				}
			}
			if (pressedButton) {
				currentInstruction++;
			}
			solution++;
		}
		return Integer.toString(solution);
	}
}
