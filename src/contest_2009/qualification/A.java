package contest_2009.qualification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abtric.utility.Solution;

/**
 * Alien Language
 * 
 * @author Ferry Abt
 *
 */
public class A extends Solution {

	private static HashMap<Character, Node> dictionary;

	private static class Node {
		HashMap<Character, Node> children = new HashMap<>();

		private Node(String s) {
			children.put(s.charAt(0), s.length() > 1 ? new Node(s.substring(1)) : null);
		}

		private void addWord(String s) {
			if (s.length() > 1) {
				if (children.containsKey(s.charAt(0))) {
					children.get(s.charAt(0)).addWord(s.substring(1));
				} else {
					children.put(s.charAt(0), new Node(s.substring(1)));
				}
			} else if (!children.containsKey(s.charAt(0))) {
				children.put(s.charAt(0), null);
			}
		}

		private int matches(String s) {
			char first = s.charAt(0);
			if (s.length() <= 1) {
				if (children.containsKey(first)) {
					return 1;
				}
				return 0;
			}
			if (first == '(') {
				ArrayList<Character> next = new ArrayList<>();
				int i = 0;
				while ((first = s.charAt(++i)) != ')') {
					next.add(first);
				}
				int numOfMatches = 0;
				for (int j = 0; j < next.size(); j++) {
					if (children.containsKey(next.get(j))) {
						numOfMatches += i < s.length() - 1 ? children.get(next.get(j)).matches(s.substring(i + 1)) : 1;
					}
				}
				return numOfMatches;
			}
			if (children.containsKey(first)) {
				return children.get(first).matches(s.substring(1));
			}
			return 0;
		}
	}

	@Override
	protected void parseInput(List<String> rawInputFile) {
		m_inputFile = new ArrayList<>();
		final String[] LDN = rawInputFile.remove(0).split(" ");
		final int D = Integer.parseInt(LDN[1]);
		m_numOfProblems = Integer.parseInt(LDN[2]);
		m_inputFile.add(Integer.toString(m_numOfProblems));
		String S = rawInputFile.remove(0);
		dictionary = new HashMap<>();
		dictionary.put(S.charAt(0), new Node(S.substring(1)));
		for (int i = 1; i < D; i++) {
			S = rawInputFile.remove(0);
			if (dictionary.containsKey(S.charAt(0))) {
				dictionary.get(S.charAt(0)).addWord(S.substring(1));
			} else {
				dictionary.put(S.charAt(0), new Node(S.substring(1)));
			}
		}
		m_inputFile.addAll(rawInputFile);
	}

	@Override
	protected String solveCaseNo(int i) {
		final String S = m_inputFile.get(1 + i);
		char first = S.charAt(0);
		int solution = 0;
		if (first == '(') {
			ArrayList<Character> next = new ArrayList<>();
			int j = 0;
			while ((first = S.charAt(++j)) != ')') {
				next.add(first);
			}
			for (int k = 0; k < next.size(); k++) {
				if (dictionary.containsKey(next.get(k))) {
					solution += dictionary.get(next.get(k)).matches(S.substring(j + 1));
				}
			}
		} else if (dictionary.containsKey(S.charAt(0))) {
			solution = dictionary.get(S.charAt(0)).matches(S.substring(1));
		}
		return Integer.toString(solution);
	}
}
