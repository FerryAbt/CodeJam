package contest_2009.qualification;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abtric.utility.Solution;

/**
 * Alien Language
 * 
 * @author Ferry Abt
 *
 */
public class A extends Solution {

    private static final String stringPath = "A-test";
    // private static final String stringPath = "A-small-practice";
    // private static final String stringPath = "A-large-pratice";

    private static HashMap<Character, Node> dictionary;

    public static void main(String[] args) {
        try {
            // preprocessing to get constant number of lines per problem
            List<String> rawInputFile = Files.readAllLines(FileSystems.getDefault().getPath("in", stringPath + ".in"));
            List<String> inputFile = new ArrayList<>();
            final String[] LDN = rawInputFile.remove(0).split(" ");
            final int D = Integer.parseInt(LDN[1]);
            final int N = Integer.parseInt(LDN[2]);
            inputFile.add(Integer.toString(N));
            String S = rawInputFile.remove(0);
            dictionary = new HashMap<>();
            dictionary.put(S.charAt(0), new Node(S.substring(1)));
            for (int i = 1; i < D; i++) {
                S = rawInputFile.remove(0);
                if (dictionary.containsKey(S.charAt(0))) {
                    dictionary.get(S.charAt(0)).addWord(S.substring(1));
                } else {
                    dictionary.put(S.charAt(0), new Node(S.substring(1)));
                }
            }
            inputFile.addAll(rawInputFile);

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

                final String S = m_inputFile.get(1 + i);
                char first = S.charAt(0);
                int solution = 0;
                if (first == '(') {
                    ArrayList<Character> next = new ArrayList<>();
                    int j = 0;
                    while ((first = S.charAt(++j)) != ')') {
                        next.add(first);
                    }
                    for (int k = 0; k < next.size(); k++) {
                        if (dictionary.containsKey(next.get(k))) {
                            solution += dictionary.get(next.get(k)).matches(S.substring(j + 1));
                        }
                    }
                } else if (dictionary.containsKey(S.charAt(0))) {
                    solution = dictionary.get(S.charAt(0)).matches(S.substring(1));
                }

                finish(i, startTime, Integer.toString(solution));
            }
        }
    }

    private static class Node {
        HashMap<Character, Node> children = new HashMap<>();

        private Node(String s) {
            if (s.length() > 1) {
                children.put(s.charAt(0), new Node(s.substring(1)));
            } else {
                children.put(s.charAt(0), null);
            }
        }

        private void addWord(String s) {
            if (s.length() > 1) {
                if (children.containsKey(s.charAt(0))) {
                    children.get(s.charAt(0)).addWord(s.substring(1));
                } else {
                    children.put(s.charAt(0), new Node(s.substring(1)));
                }
            } else if (!children.containsKey(s.charAt(0))) {
                children.put(s.charAt(0), null);
            }
        }

        private int matches(String s) {
            char first = s.charAt(0);
            if (s.length() > 1) {
                if (first == '(') {
                    ArrayList<Character> next = new ArrayList<>();
                    int i = 0;
                    while ((first = s.charAt(++i)) != ')') {
                        next.add(first);
                    }
                    int numOfMatches = 0;
                    if (i < s.length() - 1) {
                        for (int j = 0; j < next.size(); j++) {
                            if (children.containsKey(next.get(j))) {
                                numOfMatches += children.get(next.get(j)).matches(s.substring(i + 1));
                            }
                        }
                    } else {
                        for (int j = 0; j < next.size(); j++) {
                            if (children.containsKey(next.get(j))) {
                                numOfMatches++;
                            }
                        }
                    }
                    return numOfMatches;
                }
                if (children.containsKey(first)) {
                    return children.get(first).matches(s.substring(1));
                }
                return 0;
            }
            if (children.containsKey(first)) {
                return 1;
            }
            return 0;
        }
    }
}
