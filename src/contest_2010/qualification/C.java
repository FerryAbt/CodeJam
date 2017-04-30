package contest_2010.qualification;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import abtric.utility.Solution;

/**
 * Theme Park
 * 
 * @author Ferry Abt
 *
 */
public class C extends Solution {

     private static final String stringPath = "C-test";
    // private static final String stringPath = "C-small-practice";
//    private static final String stringPath = "C-large-practice";

    final static BigInteger ZERO = new BigInteger("0");

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

                final String[] RkN = m_inputFile.get(i * 2 + 1).split(" ");
                final String[] g_raw = m_inputFile.get(i * 2 + 2).split(" ");

                final int R = Integer.parseInt(RkN[0]);
                final BigInteger k = new BigInteger(RkN[1]);
                final int N = Integer.parseInt(RkN[2]);
                final BigInteger[] g = new BigInteger[N];
                int[] lastTimeFirstInLine = new int[N];
                BigInteger totalNumberOfCustomers = ZERO;
                for (int j = 0; j < N; j++) {
                    g[j] = new BigInteger(g_raw[j]);
                    totalNumberOfCustomers = totalNumberOfCustomers.add(g[j]);
                    lastTimeFirstInLine[j] = -1;
                }
                BigInteger[] moneyEarnedWithRideX = new BigInteger[R];

                BigInteger solution = ZERO;

                int firstInLine = 0;
                for (int ride = 0; ride < R; ride++) {
                    if (lastTimeFirstInLine[firstInLine] >= 0) {
                        BigInteger moneyThroughRepitition = ZERO;
                        if (ride - lastTimeFirstInLine[firstInLine] < R - ride) {
                            for (int j = lastTimeFirstInLine[firstInLine]; j < ride; j++) {
                                moneyThroughRepitition = moneyThroughRepitition.add(moneyEarnedWithRideX[j]);
                            }
                        }
                        int length = ride - lastTimeFirstInLine[firstInLine];
                        while (ride + length < R) {
                            solution = solution.add(moneyThroughRepitition);
                            ride += length;
                        }
                    }
                    lastTimeFirstInLine[firstInLine] = ride;
                    BigInteger numberOfPassengers = ZERO;
                    while (numberOfPassengers.add(g[firstInLine]).compareTo(k) <= 0
                            && numberOfPassengers.compareTo(totalNumberOfCustomers) < 0) {
                        numberOfPassengers = numberOfPassengers.add(g[firstInLine]);
                        firstInLine = (firstInLine + 1) % N;
                    }
                    solution = solution.add(numberOfPassengers);
                    moneyEarnedWithRideX[ride] = numberOfPassengers;
                }

                finish(i, startTime, solution.toString());
            }
        }
    }
}
