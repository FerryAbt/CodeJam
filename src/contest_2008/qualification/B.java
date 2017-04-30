package contest_2008.qualification;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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

    private static final String stringPath = "B-test";
    // private static final String stringPath = "B-small-practice";
    // private static final String stringPath = "B-large-pratice";

    public static void main(String[] args) {
        try {
            // preprocessing to get constant number of lines per problem
            List<String> rawInputFile = Files.readAllLines(FileSystems.getDefault().getPath("in", stringPath + ".in"));
            List<String> inputFile = new ArrayList<>();
            final int T = Integer.parseInt(rawInputFile.get(0));
            inputFile.add(rawInputFile.remove(0));
            for (int i = 0; i < T; i++) {
                inputFile.add(rawInputFile.remove(0));
                final String[] NANB = rawInputFile.remove(0).split(" ");
                final int NA = Integer.parseInt(NANB[0]);
                final int NB = Integer.parseInt(NANB[1]);
                inputFile.add(NA + ";" + NB);
                String NAs = NA > 0 ? rawInputFile.remove(0) : "";
                for (int j = 1; j < NA; j++) {
                    NAs += ";" + rawInputFile.remove(0);
                }
                inputFile.add(NAs);
                String NBs = NB > 0 ? rawInputFile.remove(0) : "";
                for (int j = 1; j < NB; j++) {
                    NBs += ";" + rawInputFile.remove(0);
                }
                inputFile.add(NBs);
            }
            String[] result = new B(inputFile).solve();
            write("out" + File.separatorChar + stringPath + ".out", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private B(List<String> inputFile) throws IOException {
        super(inputFile);
    }

    protected Runnable getNewRunnable(final List<Integer> i) {
        return new SolveRunnable(i);
    }

    private class SolveRunnable implements Runnable {
        private final List<Integer> I;

        private SolveRunnable(final List<Integer> i) {
            I = i;
        }

        public void run() {
            for (Integer i : I) {
                final long startTime = System.currentTimeMillis();

                final int turnoverTime = Integer.parseInt(m_inputFile.get(i * 4 + 1));
                final String[] noTrips_raw = m_inputFile.get(i * 4 + 2).split(";");
                final String[] ABs_raw = m_inputFile.get(i * 4 + 3).split(";");
                final String[] BAs_raw = m_inputFile.get(i * 4 + 4).split(";");

                List<String> departuresA = new ArrayList<>();
                Map<String, List<String>> arrivalsA = new HashMap<>();
                List<String> departuresB = new ArrayList<>();
                Map<String, List<String>> arrivalsB = new HashMap<>();

                for (int j = 0; j < Integer.parseInt(noTrips_raw[0]); j++) {
                    String[] ABs = ABs_raw[j].split(" ");
                    departuresA.add(ABs[0]);
                    if (!arrivalsA.containsKey(ABs[0])) {
                        arrivalsA.put(ABs[0], new ArrayList<>());
                    }
                    arrivalsA.get(ABs[0]).add(ABs[1]);
                }
                for (List<String> arrTimes : arrivalsA.values()) {
                    Collections.sort(arrTimes);
                }
                Collections.sort(departuresA);
                for (int j = 0; j < Integer.parseInt(noTrips_raw[1]); j++) {
                    String[] BAs = BAs_raw[j].split(" ");
                    departuresB.add(BAs[0]);
                    if (!arrivalsB.containsKey(BAs[0])) {
                        arrivalsB.put(BAs[0], new ArrayList<>());
                    }
                    arrivalsB.get(BAs[0]).add(BAs[1]);
                }
                for (List<String> arrTimes : arrivalsB.values()) {
                    Collections.sort(arrTimes);
                }
                Collections.sort(departuresB);

                int solution1 = 0;
                int solution2 = 0;

                while (!departuresA.isEmpty() && !departuresB.isEmpty()) {
                    int nextTrip = 0;
                    if (departuresA.get(0).compareTo(departuresB.get(0)) <= 0) {
                        solution1++;
                        nextTrip = 2;
                    } else {
                        solution2++;
                        nextTrip = 1;
                    }
                    String[] departure = (nextTrip == 2 ? departuresA : departuresB).remove(0).split(":");
                    String[] arrival = (nextTrip == 2 ? arrivalsA : arrivalsB).get(departure[0] + ":" + departure[1])
                            .remove(0).split(":");
                    arrival[0] = String.format("%02d", (int) (Integer.parseInt(arrival[0])
                            + Math.floor((Integer.parseInt(arrival[1]) + turnoverTime) / 60.0)));
                    arrival[1] = String.format("%02d", (int) ((Integer.parseInt(arrival[1]) + turnoverTime) % 60.0));
                    while (nextTrip > 0) {
                        departure = findNextTrip(nextTrip == 2 ? departuresB : departuresA, arrival);
                        if (departure == null) {
                            nextTrip = 0;
                        } else {
                            if (nextTrip == 2) {
                                nextTrip = 1;
                            } else {
                                nextTrip = 2;
                            }
                            arrival = (nextTrip == 2 ? arrivalsA : arrivalsB).get(departure[0] + ":" + departure[1])
                                    .remove(0).split(":");
                            arrival[0] = String.format("%02d", (int) (Integer.parseInt(arrival[0])
                                    + Math.floor((Integer.parseInt(arrival[1]) + turnoverTime) / 60.0)));
                            arrival[1] = String.format("%02d",
                                    (int) ((Integer.parseInt(arrival[1]) + turnoverTime) % 60.0));
                        }
                    }
                }

                solution1 += departuresA.size();
                solution2 += departuresB.size();

                finish(i, startTime, solution1 + " " + solution2);
            }
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
}
