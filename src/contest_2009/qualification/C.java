package contest_2009.qualification;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import abtric.utility.Solution;

/**
 * Welcome to Code Jam
 * 
 * @author Ferry Abt
 *
 */
public class C extends Solution {

    private static final String stringPath = "C-test";
    // private static final String stringPath = "C-small-practice";
    // private static final String stringPath = "C-large-pratice";

    private static HashMap<String, Long> known = new HashMap<>();

    private final static char[] TEXT = new char[] { 'w', 'e', 'l', 'c', 'o', 'm', 'e', ' ', 't', 'o', ' ', 'c', 'o',
            'd', 'e', ' ', 'j', 'a', 'm' };

    public static void main(String[] args) {
        try {
            String[] result = new C(stringPath).solve();
            write("out" + File.separatorChar + stringPath + ".out", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private C(String stringPath) throws IOException {
        super(stringPath);
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
            // final int T = Integer.parseInt(m_inputFile.get(0));
            for (Integer i : I) {
                final long startTime = System.currentTimeMillis();

                final String S = m_inputFile.get(1 + i);

                long solution = countSubString(S, 0);

                final String solutionStringRaw = String.format("%04d", solution);

                finish(i, startTime, solutionStringRaw.substring(solutionStringRaw.length() - 4));
            }
        }

        private long countSubString(final String S, final int i) {
            if (known.containsKey(i + S)) {
                return known.get(i + S);
            }
            long solution = 0;
            for (int j = 0; j <= S.length() - (TEXT.length - i); j++) {
                if (S.charAt(j) != TEXT[i]) {
                    continue;
                }
                if (i == TEXT.length - 1) {
                    solution = (solution + 1) % 10000;
                } else {
                    solution += countSubString(S.substring(j + 1), i + 1) % 10000;
                }
            }
            known.put(i + S, solution);
            return solution;
        }
    }
}
