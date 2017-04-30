package contest_2008.qualification;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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

    private static final String stringPath = "A-test";
    // private static final String stringPath = "A-small-practice";
    // private static final String stringPath = "A-large-pratice";

    public static void main(String[] args) {
        try {
            // preprocessing to get constant number of lines per problem
            List<String> rawInputFile = Files.readAllLines(FileSystems.getDefault().getPath("in", stringPath + ".in"));
            List<String> inputFile = new ArrayList<>();
            final int T = Integer.parseInt(rawInputFile.get(0));
            inputFile.add(rawInputFile.remove(0));
            for (int i = 0; i < T; i++) {
                inputFile.add(rawInputFile.remove(0));
                final int Q = Integer.parseInt(rawInputFile.remove(0));
                String searches = Q > 0 ? rawInputFile.remove(0) : "";
                for (int j = 1; j < Q; j++) {
                    searches += ";" + rawInputFile.remove(0);
                }
                inputFile.add(searches);
            }
            String[] result = new A(inputFile).solve();
            write("out" + File.separatorChar + stringPath + ".out", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private A(List<String> inputFile) throws IOException {
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

                final int S = Integer.parseInt(m_inputFile.get(i * 2 + 1));
                final String[] Q = m_inputFile.get(i * 2 + 2).split(";");

                int solution = 0;
                Set<String> searchedMachines = new HashSet<>();
                for (int j = 0; j < Q.length; j++) {
                    searchedMachines.add(Q[j]);
                    if (searchedMachines.size() == S) {
                        searchedMachines.clear();
                        searchedMachines.add(Q[j]);
                        solution++;
                    }
                }

                finish(i, startTime, Integer.toString(solution));
            }
        }
    }
}
