package abtric.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class Solution {

	protected class Case {
		public ArrayList<String> lines;
		public int index;

		public Case(int index) {
			this.index = index;
			lines = new ArrayList<>();
		}

		public void addLine(String line) {
			lines.add(line);
		}

	}

	protected int m_numOfProblems;
	protected List<Case> m_cases;

	private String[] m_results;
	private InputType inputType;

	public void solve(InputType inputType) {
		this.inputType = inputType;
		parseInput(read());
		m_results = new String[m_numOfProblems];
		solveInternal();
		write();
	}

	protected void parseInput(List<String> input) {
		m_numOfProblems = Integer.parseInt(input.get(0));
		m_cases = new ArrayList<>();
		for (int i = 0; i < m_numOfProblems; i++) {
			Case c = new Case(i);
			c.addLine(input.get(i + 1));
			m_cases.add(c);
		}
	}

	protected abstract String solveCase(Case c);

	private String getFileName() {
		String[] packageName = getClass().getName().split("\\.");
		return packageName[0].replaceFirst("contest_", "") + File.separatorChar + packageName[1] + File.separatorChar
				+ packageName[2] + "-" + inputType.fileName;
	}

	private void solveInternal() {
		m_cases.parallelStream().forEach(c -> m_results[c.index] = solveCase(c));
//		m_cases.stream().forEach(c -> m_results[c.index] = solveCase(c));
	}

	private void write() {
		try {
			Path path = FileSystems.getDefault().getPath("out", getFileName() + ".out");
			System.out.println("writing to " + path);
			FileWriter writer = new FileWriter(path.toString());
			for (int i = 0; i < m_results.length; i++) {
				writer.write("Case #" + (i + 1) + ": " + m_results[i] + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<String> read() {
		List<String> rawInput = null;
		try {
			rawInput = Files.readAllLines(FileSystems.getDefault().getPath("in", getFileName() + ".in"));
		} catch (IOException e) {
			System.err.println("Error reading input file \"" + getFileName() + "\": " + e.getMessage());
			System.exit(0);
		}
		return rawInput;
	}

	public enum InputType {
		TEST("test"), SMALL_PRACTICE("small-practice"), LARGE_PRACTICE("large-practice");

		String fileName;

		InputType(String fileName) {
			this.fileName = fileName;
		}
	}

}
