package eu.artofcoding.romannumbers;

import java.util.HashMap;
import java.util.Map;

public final class RomanNumber {

    private RomanNumber() {
        throw new AssertionError();
    }

    private static final Map<String, Integer> subtractMap = new HashMap<String, Integer>() {
        {
            put("IV", 4);
            put("IX", 9);
            put("XL", 40);
            put("XC", 90);
            put("CD", 400);
            put("CM", 900);
        }
    };

    private static final Map<String, Integer> romanMap = new HashMap<String, Integer>() {
        {
            put("I", 1);
            put("V", 5);
            put("X", 10);
            put("L", 50);
            put("C", 100);
            put("D", 500);
            put("M", 1000);
        }
    };

    public static long romanToArabic(final String romanNumber) {
        String tmp = romanNumber;
        long result = 0l;
        for (final String subtractKey : subtractMap.keySet()) {
            if (tmp.contains(subtractKey)) {
                tmp = tmp.replace(subtractKey, Constants.EMPTY_STRING);
                result += subtractMap.get(subtractKey);
            }
        }
        if (tmp.length() > 0) {
            for (final String romanDigit : tmp.split("")) {
                if (tmp.contains(romanDigit)) {
                    result += romanMap.get(romanDigit);
                }
            }
        }
        return result;
    }

}
