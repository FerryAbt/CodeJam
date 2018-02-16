package main;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

import abtric.utility.Solution;
import abtric.utility.Solution.InputType;

public class Main {

	private static HashMap<String, HashMap<String, HashMap<String, Class<? extends Solution>>>> problems = new HashMap<>();
	private static String year = "2011";
	private static String round = "qualification";
	private static String problem = "B";
	private static InputType inputType = InputType.LARGE_PRACTICE;

	static {
		HashMap<String, HashMap<String, Class<? extends Solution>>> year2008 = new HashMap<>();
		HashMap<String, Class<? extends Solution>> quali2008 = new HashMap<>();
		quali2008.put("A", contest_2008.qualification.A.class);
		quali2008.put("B", contest_2008.qualification.B.class);
		quali2008.put("C", contest_2008.qualification.C.class);
		year2008.put("qualification", quali2008);
		problems.put("2008", year2008);
		HashMap<String, HashMap<String, Class<? extends Solution>>> year2009 = new HashMap<>();
		HashMap<String, Class<? extends Solution>> quali2009 = new HashMap<>();
		quali2009.put("A", contest_2009.qualification.A.class);
		quali2009.put("B", contest_2009.qualification.B.class);
		quali2009.put("C", contest_2009.qualification.C.class);
		year2009.put("qualification", quali2009);
		problems.put("2009", year2009);
		HashMap<String, HashMap<String, Class<? extends Solution>>> year2010 = new HashMap<>();
		HashMap<String, Class<? extends Solution>> quali2010 = new HashMap<>();
		quali2010.put("A", contest_2010.qualification.A.class);
		quali2010.put("B", contest_2010.qualification.B.class);
		quali2010.put("C", contest_2010.qualification.C.class);
		year2010.put("qualification", quali2010);
		problems.put("2010", year2010);
		HashMap<String, HashMap<String, Class<? extends Solution>>> year2011 = new HashMap<>();
		HashMap<String, Class<? extends Solution>> quali2011 = new HashMap<>();
		quali2011.put("A", contest_2011.qualification.A.class);
		quali2011.put("B", contest_2011.qualification.B.class);
		year2011.put("qualification", quali2011);
		problems.put("2011", year2011);
	}

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		try (Scanner scanner = new Scanner(System.in)) {
			while (!problems.containsKey(year)) {
				System.out.println("Choose a year ("
						+ problems.keySet().stream().sorted().collect(Collectors.joining(", ")) + "): ");
				year = scanner.nextLine();
			}
			if (problems.get(year).size() == 1) {
				round = problems.get(year).keySet().iterator().next();
				System.out.println("Round: " + round);
			} else {
				while (!problems.get(year).containsKey(round)
						&& !("q".equals(round) && problems.get(year).containsKey("qualification"))) {
					System.out.println("Choose a round ("
							+ problems.get(year).keySet().stream().sorted().collect(Collectors.joining(", ")) + "):");
					round = scanner.nextLine();
				}
			}
			if (problems.get(year).get(round).size() == 1) {
				problem = problems.get(year).get(round).keySet().iterator().next();
				System.out.println("Problem: " + problem);
			} else {
				while (!problems.get(year).get(round).containsKey(problem)) {
					System.out.println("Choose a problem ("
							+ problems.get(year).get(round).keySet().stream().sorted().collect(Collectors.joining(", "))
							+ "):");
					problem = scanner.nextLine();
				}
			}
			Class<? extends Solution> solverToRun = problems.get(year).get(round).get(problem);
			try {
				solverToRun.getMethod("solve", InputType.class).invoke(solverToRun.getConstructor().newInstance(),
						inputType);
			} catch (InvocationTargetException e) {
				e.getCause().printStackTrace();
			}
		}
	}

}
