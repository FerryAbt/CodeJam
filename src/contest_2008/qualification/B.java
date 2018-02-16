package contest_2008.qualification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		m_inputFile = new ArrayList<>();
		m_numOfProblems = Integer.parseInt(rawInputFile.get(0));
		m_inputFile.add(rawInputFile.remove(0));
		for (int i = 0; i < m_numOfProblems; i++) {
			final String turnoverTime = rawInputFile.remove(0);
			final String[] NANB = rawInputFile.remove(0).split(" ");
			final int NA = Integer.parseInt(NANB[0]);
			final int NB = Integer.parseInt(NANB[1]);
			String NAs = NA > 0 ? rawInputFile.remove(0) : "";
			for (int j = 1; j < NA; j++) {
				NAs += ";" + rawInputFile.remove(0);
			}
			String NBs = NB > 0 ? rawInputFile.remove(0) : "";
			for (int j = 1; j < NB; j++) {
				NBs += ";" + rawInputFile.remove(0);
			}

			m_inputFile.add(turnoverTime);
			m_inputFile.add(Integer.toString(NA));
			m_inputFile.add(Integer.toString(NB));
			m_inputFile.add(NAs);
			m_inputFile.add(NBs);
		}
	}

	@Override
	protected String solveCaseNo(int i) {
		final int turnoverTime = Integer.parseInt(m_inputFile.get(i * 5 + 1));
		final int NA = Integer.parseInt(m_inputFile.get(i * 5 + 2));
		final int NB = Integer.parseInt(m_inputFile.get(i * 5 + 3));
		final String[] ABs_raw = m_inputFile.get(i * 5 + 4).split(";");
		final String[] BAs_raw = m_inputFile.get(i * 5 + 5).split(";");

		List<String> departuresA = new ArrayList<>();
		Map<String, List<String>> arrivalsA = new HashMap<>();
		List<String> departuresB = new ArrayList<>();
		Map<String, List<String>> arrivalsB = new HashMap<>();

		for (int j = 0; j < NA; j++) {
			String departure = ABs_raw[j].split(" ")[0];
			String arrival = ABs_raw[j].split(" ")[1];
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
		for (int j = 0; j < NB; j++) {
			String departure = BAs_raw[j].split(" ")[0];
			String arrival = BAs_raw[j].split(" ")[1];
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
			arrival[0] = String.format("%02d", (int) (Integer.parseInt(arrival[0])
					+ Math.floor((Integer.parseInt(arrival[1]) + turnoverTime) / 60.0)));
			arrival[1] = String.format("%02d", (int) ((Integer.parseInt(arrival[1]) + turnoverTime) % 60.0));
			while (true) {
				if ((departure = findNextTrip(nextTripFromB ? departuresB : departuresA, arrival)) == null) {
					break;
				} else {
					nextTripFromB = !nextTripFromB;
					arrival = (nextTripFromB ? arrivalsA : arrivalsB).get(departure[0] + ":" + departure[1]).remove(0)
							.split(":");
					arrival[0] = String.format("%02d", (int) (Integer.parseInt(arrival[0])
							+ Math.floor((Integer.parseInt(arrival[1]) + turnoverTime) / 60.0)));
					arrival[1] = String.format("%02d", (int) ((Integer.parseInt(arrival[1]) + turnoverTime) % 60.0));
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
