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
		protected int index;

		public Case(int index) {
			this.index = index;
			lines = new ArrayList<>();
		}

		public void addLine(String line) {
			lines.add(line);
		}

	}

	protected int m_numOfProblems;
	protected List<String> m_inputFile;
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

	protected abstract void parseInput(List<String> input);

	protected abstract String solveCaseNo(int i);

	private String getFileName() {
		String[] packageName = getClass().getName().split("\\.");
		return packageName[0].replaceFirst("contest_", "") + File.separatorChar + packageName[1] + File.separatorChar
				+ packageName[2] + "-" + inputType.fileName;
	}

	private void solveInternal() {
		for (int i = 0; i < m_numOfProblems; i++) {
			m_results[i] = solveCaseNo(i);
		}
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

	protected void defaultInput(List<String> input) {
		m_inputFile = input;
		m_numOfProblems = Integer.parseInt(m_inputFile.get(0));
	}

	public enum InputType {
		TEST("test"), SMALL_PRACTICE("small-practice"), LARGE_PRACTICE("large-practice");

		String fileName;

		InputType(String fileName) {
			this.fileName = fileName;
		}
	}

}
