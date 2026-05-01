import java.util.*;

public static int computePolkadotScore(String asciiArt) {
    String[] lines = asciiArt.split("\n");
    Set<Character> excluded = new HashSet<>(Arrays.asList('\'', ',', '-'));

    int eyeLineIdx = -1;
    List<int[]> eyePairs = new ArrayList<>();
    for (int i = 0; i < lines.length; i++) {
        List<int[]> pairs = new ArrayList<>();
        for (int x = 0; x < lines[i].length() - 1; x++) {
            if (lines[i].charAt(x) == '(' && lines[i].charAt(x + 1) == ')') {
                pairs.add(new int[]{x, x + 1});
            }
        }
        if (pairs.size() == 2) {
            eyeLineIdx = i;
            eyePairs = pairs;
            break;
        }
    }

    int pupilCharCount = 0;
    for (int[] pair : eyePairs) {
        pupilCharCount += (pair[1] - pair[0] + 1);
    }

    int lipsStartX = -1;
    int lipsEndX = -1;
    int bestLength = 0;
    int searchTo = Math.min(lines.length, eyeLineIdx + 5);
    for (int i = eyeLineIdx + 1; i < searchTo; i++) {
        String line = lines[i];
        int x = 0;
        while (x < line.length()) {
            char c = line.charAt(x);
            if (c != ' ' && !excluded.contains(c)) {
                int start = x;
                while (x < line.length() && line.charAt(x) != ' ' && !excluded.contains(line.charAt(x))) {
                    x++;
                }
                int end = x - 1;
                int length = end - start + 1;
                if (length > bestLength) {
                    bestLength = length;
                    lipsStartX = start;
                    lipsEndX = end;
                }
            } else {
                x++;
            }
        }
    }

    int inRange = 0;
    int outRange = 0;
    for (String line : lines) {
        for (int x = 0; x < line.length(); x++) {
            if (line.charAt(x) == 'O') {
                if (x >= lipsStartX && x <= lipsEndX) inRange++;
                else outRange++;
            }
        }
    }

    return outRange + inRange * pupilCharCount;
}