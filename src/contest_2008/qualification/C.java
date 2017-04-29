package contest_2008.qualification;

import java.io.File;
import java.io.IOException;
import java.util.List;

import abtric.utility.Solution;

/**
 * Fly Swatter
 * 
 * @author Ferry Abt
 *
 */
public class C extends Solution {

    private static final String stringPath = "C-test";
    // private static final String stringPath = "C-small-practice";
    // private static final String stringPath = "C-large-pratice";

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
            for (Integer i : I) {
                final long startTime = System.currentTimeMillis();

                final String[] input = m_inputFile.get(i + 1).split(" ");
                final double f = Double.parseDouble(input[0]); // radius of fly
                final double R = Double.parseDouble(input[1]); // outer radius
                final double t = Double.parseDouble(input[2]); // ring thickness
                final double r = Double.parseDouble(input[3]); // radius of
                                                               // string
                final double g = Double.parseDouble(input[4]); // gap between
                                                               // strings

                double solution = 1.0;

                // special case: fly too big -> 1.0
                if (g > 2 * f) {
                    solution = 0;
                    final double innerRadius = R - t - f;
                    final double innerRadiusSquared = Math.pow(innerRadius, 2);
                    double x = r + f;
                    while (x < innerRadius) {
                        final double righX = x + g - 2 * f;
                        final double rightXSquared = Math.pow(righX, 2);
                        final double xSquared = Math.pow(x, 2);
                        final double y2 = Math.sqrt(innerRadiusSquared - xSquared);
                        double y = r + f;
                        while (y < innerRadius) {
                            final double rightY = y + g - 2 * f;
                            final double rightYSquared = Math.pow(rightY, 2);
                            if (Math.sqrt(rightXSquared + rightYSquared) < innerRadius) {
                                solution += 4 * Math.pow(g - 2 * f, 2);
                            } else {
                                final double ySquared = Math.pow(y, 2);
                                final double lowerRightCorner = Math.sqrt(rightXSquared + ySquared);
                                final double upperLeftCorner = Math.sqrt(xSquared + rightYSquared);
                                final double x1 = Math.sqrt(innerRadiusSquared - rightYSquared);
                                final double x2 = Math.sqrt(innerRadiusSquared - ySquared);
                                final double y1 = Math.sqrt(innerRadiusSquared - rightXSquared);
                                double triangle1 = 0;
                                double triangle2 = 0;
                                double triangle3 = 0;
                                double circularArea = 0;
                                if (lowerRightCorner < innerRadius && upperLeftCorner < innerRadius) {
                                    triangle1 = ((rightY - y) * (righX - x)) / 2;
                                    triangle2 = ((rightY - y) * (x1 - x)) / 2;
                                    triangle3 = ((y1 - y) * (righX - x1)) / 2;
                                    circularArea = getCircularArea(innerRadius, rightY - y1, righX - x1);
                                    // missing corner
                                } else if (lowerRightCorner < innerRadius) {
                                    // missing top
                                    triangle1 = ((y2 - y) * ((righX) - x)) / 2;
                                    triangle2 = ((y1 - y) * ((righX) - x)) / 2;
                                    circularArea = getCircularArea(innerRadius, y2 - y1, g - 2 * f);
                                } else if (upperLeftCorner < innerRadius) {
                                    // missing side
                                    triangle1 = ((x2 - x) * ((rightY) - y)) / 2;
                                    triangle2 = ((x1 - x) * ((rightY) - y)) / 2;
                                    circularArea = getCircularArea(innerRadius, x2 - x1, g - 2 * f);
                                } else if (Math.sqrt(xSquared + ySquared) < innerRadius) {
                                    // only corner
                                    triangle1 = ((x2 - x) * (y2 - y)) / 2;
                                    circularArea = getCircularArea(innerRadius, x2 - x, y2 - y);
                                }
                                solution += 4 * (triangle1 + triangle2 + triangle3 + circularArea);
                            }
                            y += g + r * 2;
                        }
                        x += g + r * 2;
                    }
                    solution = 1.0 - solution / Math.PI * Math.pow(R, 2);
                }

                m_results[i] = String.format("%06f", solution);
                final long duration = System.currentTimeMillis() - startTime;
                synchronized (m_lock) {
                    m_done++;
                    System.out.println(String.format("%03d/%03d (%dms)", m_done, m_numOfProblems, duration));
                }
            }
        }

        private double getCircularArea(double innerRadius, double x, double y) {
            double phi = Math.asin(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * innerRadius)) * 2;
            return (Math.pow(innerRadius, 2) / 2) * (phi - Math.sin(phi));
        }
    }
}
