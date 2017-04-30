package contest_2010.qualification;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import abtric.utility.Solution;

/**
 * Fair Warning
 * 
 * @author Ferry Abt
 *
 */
public class B extends Solution {

    private static final String stringPath = "B-test";
    // private static final String stringPath = "B-small-practice";
    // private static final String stringPath = "B-large-practice";

    final static BigInteger ZERO = new BigInteger("0");
    final static BigInteger ONE = new BigInteger("1");

    public static void main(String[] args) {
        try {
            String[] result = new B(stringPath).solve();
            write("out" + File.separatorChar + stringPath + ".out", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private B(String stringPath) throws IOException {
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
                BigInteger[] t = new BigInteger[N];
                for (int j = 0; j < N; j++) {
                    t[j] = new BigInteger(S[1 + j]);
                }

                BigInteger solution = ZERO;
                BigInteger T = t[0].subtract(t[1]);
                for (int j = 0; j < N - 1; j++) {
                    for (int k = 1; k < N; k++) {
                        T = T.gcd(t[j].subtract(t[k]));
                    }
                }

                if (t[0].divide(T).multiply(T).compareTo(t[0]) == 0) {
                    solution = ZERO;
                } else {
                    solution = t[0].divide(T).add(ONE).multiply(T).subtract(t[0]);
                }
                boolean done = false;
                while (!done) {
                    done = true;
                    for (int k = 0; k < N; k++) {
                        if (t[k].add(solution).mod(T).compareTo(ZERO) != 0) {
                            done = false;
                            break;
                        }
                    }
                    solution = solution.add(T);
                }
                solution = solution.subtract(T);

                finish(i, startTime, solution.toString());
            }
        }
    }

}
