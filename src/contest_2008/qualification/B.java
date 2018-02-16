package contest_2008.qualification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import abtric.utility.Solution;

/**
 * Train Timetable
 * 
 * @author Ferry Abt
 *
 */
public class B extends Solution {

	@Override
	protected void parseInput(List<String> rawInputFile) {
		m_numOfProblems = Integer.parseInt(rawInputFile.remove(0)); // N
		m_cases = new ArrayList<>();
		for (int i = 0; i < m_numOfProblems; i++) {
			Case c = new Case(i);
			String T = rawInputFile.remove(0);
			c.addLine(T);
			String[] NANB = rawInputFile.remove(0).split(" ");
			int NA = Integer.parseInt(NANB[0]);
			c.addLine(Integer.toString(NA));
			for (int j = 0; j < NA; j++) {
				c.addLine(rawInputFile.remove(0));
			}
			int NB = Integer.parseInt(NANB[1]);
			c.addLine(Integer.toString(NB));
			for (int j = 0; j < NB; j++) {
				c.addLine(rawInputFile.remove(0));
			}
			m_cases.add(c);
		}
	}

	@Override
	protected String solveCase(Case c) {
		int T = Integer.parseInt(c.lines.get(0));

		int NA = Integer.parseInt(c.lines.get(1));
		ArrayList<String> departuresA = new ArrayList<>();
		HashMap<String, ArrayList<String>> arrivalsA = new HashMap<>();
		for (int j = 0; j < NA; j++) {
			String departure = c.lines.get(j + 2).split(" ")[0];
			String arrival = c.lines.get(j + 2).split(" ")[1];
			departuresA.add(departure);
			if (!arrivalsA.containsKey(departure)) {
				arrivalsA.put(departure, new ArrayList<>());
			}
			arrivalsA.get(departure).add(arrival);
		}
		for (List<String> arrTimes : arrivalsA.values()) {
			Collections.sort(arrTimes);
		}
		Collections.sort(departuresA);

		int NB = Integer.parseInt(c.lines.get(NA + 2));
		ArrayList<String> departuresB = new ArrayList<>();
		HashMap<String, ArrayList<String>> arrivalsB = new HashMap<>();
		for (int j = 0; j < NB; j++) {
			String departure = c.lines.get(NA + j + 3).split(" ")[0];
			String arrival = c.lines.get(NA + j + 3).split(" ")[1];
			departuresB.add(departure);
			if (!arrivalsB.containsKey(departure)) {
				arrivalsB.put(departure, new ArrayList<>());
			}
			arrivalsB.get(departure).add(arrival);
		}
		for (List<String> arrTimes : arrivalsB.values()) {
			Collections.sort(arrTimes);
		}
		Collections.sort(departuresB);

		int solution1 = 0;
		int solution2 = 0;
		while (!departuresA.isEmpty() && !departuresB.isEmpty()) {
			boolean nextTripFromB;
			if (departuresA.get(0).compareTo(departuresB.get(0)) <= 0) {
				solution1++;
				nextTripFromB = true;
			} else {
				solution2++;
				nextTripFromB = false;
			}
			String[] departure = (nextTripFromB ? departuresA : departuresB).remove(0).split(":");
			String[] arrival = (nextTripFromB ? arrivalsA : arrivalsB).get(departure[0] + ":" + departure[1]).remove(0)
					.split(":");
			arrival[0] = String.format("%02d",
					(int) (Integer.parseInt(arrival[0]) + Math.floor((Integer.parseInt(arrival[1]) + T) / 60.0)));
			arrival[1] = String.format("%02d", (int) ((Integer.parseInt(arrival[1]) + T) % 60.0));
			while (true) {
				if ((departure = findNextTrip(nextTripFromB ? departuresB : departuresA, arrival)) == null) {
					break;
				} else {
					nextTripFromB = !nextTripFromB;
					arrival = (nextTripFromB ? arrivalsA : arrivalsB).get(departure[0] + ":" + departure[1]).remove(0)
							.split(":");
					arrival[0] = String.format("%02d", (int) (Integer.parseInt(arrival[0])
							+ Math.floor((Integer.parseInt(arrival[1]) + T) / 60.0)));
					arrival[1] = String.format("%02d", (int) ((Integer.parseInt(arrival[1]) + T) % 60.0));
				}
			}
		}
		solution1 += departuresA.size();
		solution2 += departuresB.size();
		return solution1 + " " + solution2;
	}

	private String[] findNextTrip(List<String> departures, String[] arrival) {
		for (int i = 0; i < departures.size(); i++) {
			if (departures.get(i).compareTo(arrival[0] + ":" + arrival[1]) >= 0) {
				return departures.remove(i).split(":");
			}
		}
		return null;
	}
}
