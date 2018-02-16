package contest_2008.qualification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import abtric.utility.Solution;

/**
 * Saving the Universe
 * 
 * @author Ferry Abt
 *
 */
public class A extends Solution {

	@Override
	protected void parseInput(List<String> rawInputFile) {
		m_numOfProblems = Integer.parseInt(rawInputFile.remove(0)); // N
		m_cases = new ArrayList<>();
		for (int i = 0; i < m_numOfProblems; i++) {
			Case c = new Case(i);
			String S_String = rawInputFile.remove(0);
			int S = Integer.parseInt(S_String);
			c.addLine(S_String);
			// with this solution the exact search engines are irrelevant. Drop them instead
			// of adding them to the case.
			rawInputFile.subList(0, S).clear();
			/*
			 * for (int j = 0; j < S; j++) { c.addLine(rawInputFile.remove(0)); }
			 */
			String Q_String = rawInputFile.remove(0);
			int Q = Integer.parseInt(Q_String);
			c.addLine(Q_String);
			for (int j = 0; j < Q; j++) {
				c.addLine(rawInputFile.remove(0));
			}
			m_cases.add(c);
		}
	}

	@Override
	protected String solveCaseNo(int i) {
		Case c = m_cases.get(i);
		int S = Integer.parseInt(c.lines.get(0));
		// Exact search engines are irrelevant
		/*
		 * String[] searchEngines = new String[S]; for (int j = 0; j < S; j++) {
		 * searchEngines[j] = c.lines.get(j + 1); }
		 */
		final int Q = Integer.parseInt(c.lines.get(S + 1));
		final String[] queries = new String[Q];
		for (int j = 0; j < Q; j++) {
			queries[j] = c.lines.get(S + j + 2);
		}

		//
		int solution = 0;
		Set<String> searchedMachines = new HashSet<>();
		for (int j = 0; j < Q; j++) {
			if (searchedMachines.add(queries[j]) && searchedMachines.size() == S) {
				searchedMachines.clear();
				searchedMachines.add(queries[j]);
				solution++;
			}
		}
		return Integer.toString(solution);
	}
}
