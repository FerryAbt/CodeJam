package contest_2009.qualification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abtric.utility.Solution;

/**
 * Watersheds
 * 
 * @author Ferry Abt
 *
 */
public class B extends Solution {

	private class Label {
		private int label;
		private char actual;

		private Label(int i) {
			label = i;
		}

		@Override
		public String toString() {
			return "" + actual;
		}
	}

	@Override
	protected void parseInput(List<String> rawInputFile) {
		m_cases = new ArrayList<>();
		m_numOfProblems = Integer.parseInt(rawInputFile.remove(0));
		for (int i = 0; i < m_numOfProblems; i++) {
			Case c = new Case(i);
			final String[] HW = rawInputFile.remove(0).split(" ");
			final int H = Integer.parseInt(HW[0]);
			String field = rawInputFile.remove(0);
			for (int j = 1; j < H; j++) {
				field += " " + rawInputFile.remove(0);
			}
			c.addLine(HW[0]);
			c.addLine(HW[1]);
			c.addLine(field);
			m_cases.add(c);
		}
	}

	@Override
	protected String solveCaseNo(int i) {
		Case c = m_cases.get(i);
		final int H = Integer.parseInt(c.lines.get(0));
		final int W = Integer.parseInt(c.lines.get(1));
		final int[][] field = new int[H][W];
		final String[] inputField = c.lines.get(2).split(" ");
		for (int j = 0; j < H; j++) {
			for (int k = 0; k < W; k++) {
				field[j][k] = Integer.parseInt(inputField[j * W + k]);
			}
		}
		Label[][] solution = new Label[H][W];
		int basins = 0;
		for (int j = 0; j < H; j++) {
			for (int k = 0; k < W; k++) {
				if (solution[j][k] != null) {
					continue;
				}
				basins++;
				Label current = new Label(basins);
				int x = j;
				int y = k;
				boolean sink = false;
				while (!sink) {
					solution[x][y] = current;
					int lowestNeighbor = 10001;
					int dir = 0;
					// Check North
					if (x > 0 && field[x - 1][y] < field[x][y]) {
						lowestNeighbor = field[x - 1][y];
						dir = 1;
					}
					// Check West
					if (y > 0 && field[x][y - 1] < field[x][y] && field[x][y - 1] < lowestNeighbor) {
						lowestNeighbor = field[x][y - 1];
						dir = 2;
					}
					// Check East
					if (y < W - 1 && field[x][y + 1] < field[x][y] && field[x][y + 1] < lowestNeighbor) {
						lowestNeighbor = field[x][y + 1];
						dir = 3;
					}
					// Check South
					if (x < H - 1 && field[x + 1][y] < field[x][y] && field[x + 1][y] < lowestNeighbor) {
						lowestNeighbor = field[x + 1][y];
						dir = 4;
					}
					switch (dir) {
					case 1:
						if (solution[x - 1][y] != null) {
							solution[x][y].label = solution[x - 1][y].label;
							sink = true;
						}
						x--;
						break;
					case 2:
						if (solution[x][y - 1] != null) {
							solution[x][y].label = solution[x][y - 1].label;
							sink = true;
						}
						y--;
						break;
					case 3:
						if (solution[x][y + 1] != null) {
							solution[x][y].label = solution[x][y + 1].label;
							sink = true;
						}
						y++;
						break;
					case 4:
						if (solution[x + 1][y] != null) {
							solution[x][y].label = solution[x + 1][y].label;
							sink = true;
						}
						x++;
						break;
					default:
						sink = true;
						break;
					}
				}
			}
		}
		HashMap<Integer, Character> mapping = new HashMap<>();
		char count = 'a';
		for (int j = 0; j < H; j++) {
			for (int k = 0; k < W; k++) {
				if (solution[j][k].actual > 0) {
					continue;
				}
				if (!mapping.containsKey(solution[j][k].label)) {
					mapping.put(solution[j][k].label, count);
					solution[j][k].actual = count;
					count++;
				} else {
					solution[j][k].actual = mapping.get(solution[j][k].label);
				}
			}
		}
		String solutionString = "";
		for (int j = 0; j < H; j++) {
			solutionString += "\n" + solution[j][0];
			for (int k = 1; k < W; k++) {
				solutionString += " " + solution[j][k];
			}
		}
		return solutionString;
	}
}
