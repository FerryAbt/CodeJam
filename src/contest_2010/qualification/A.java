package contest_2010.qualification;

import java.io.File;
import java.io.IOException;
import java.util.List;

import abtric.utility.Solution;

/**
 * Snapper Chain
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
            String[] result = new A(stringPath).solve();
            write("out" + File.separatorChar + stringPath + ".out", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private A(String stringPath) throws IOException {
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
            for (Integer i : I) {
                final long startTime = System.currentTimeMillis();

                final String[] S = m_inputFile.get(1 + i).split(" ");
                final int N = Integer.parseInt(S[0]);

                final String result = Integer.toBinaryString(Integer.parseInt(S[1]));

                if (result.length() >= N) {
                    m_results[i] = "ON";
                    for (int j = result.length() - N; j < result.length(); j++) {
                        if (result.charAt(j) == '0') {
                            m_results[i] = "OFF";
                            break;
                        }
                    }
                } else {
                    m_results[i] = "OFF";
                }
                final long duration = System.currentTimeMillis() - startTime;
                synchronized (m_lock) {
                    m_done++;
                    System.out.println(String.format("%03d/%03d (%dms)", m_done, m_numOfProblems, duration));
                }
            }
        }
    }

}