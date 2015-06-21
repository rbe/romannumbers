package eu.artofcoding.romannumbers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class IntergalacticDefiner {

    private final Map<String, String> intergalacticToRomanMap = new HashMap<>();

    private final Map<String, Double> intergalacticCreditsMap = new HashMap<>();

    boolean allTokensKnown(final String... tokens) {
        final Predicate<String> predicate = (String t) -> intergalacticToRomanMap.containsKey(t) || intergalacticCreditsMap.containsKey(t);
        return Arrays.stream(tokens).allMatch(predicate);
    }

    private String[] filterTokens(final String[] tokens, final Predicate<? super String> predicate) {
        final Stream<String> knownIntergalacticTokens = Arrays.stream(tokens).filter(predicate);
        return knownIntergalacticTokens.toArray(String[]::new);
    }

    private String intergalacticToRoman(final String[] knownTokens) {
        final StringBuilder roman = new StringBuilder();
        for (final String k : knownTokens) {
            String romanFromIntergalactic = intergalacticToRomanMap.get(k);
            roman.append(romanFromIntergalactic);
        }
        return roman.toString();
    }

    private long intergalacticToArabic(final String[] knownTokens) {
        final String roman = intergalacticToRoman(knownTokens);
        return RomanNumber.romanToArabic(roman);
    }

    void simpleDefine(final String token, final String value) {
        intergalacticToRomanMap.put(token, value);
    }

    void complexDefine(final String[] leftTokens, final String[] rightToken) {
        // leftTokens=glob glob Silver, rightTokens=34 Credits
        final boolean shouldDefineUnknownToken = !allTokensKnown(leftTokens);
        if (shouldDefineUnknownToken) {
            // knownTokens=glob glob
            final String[] knownTokens = filterTokens(leftTokens, intergalacticToRomanMap::containsKey);
            final long arabic = intergalacticToArabic(knownTokens);
            // unknownTokens=Silver
            final String[] unknownTokens = filterTokens(leftTokens, (t) -> !intergalacticToRomanMap.containsKey(t));
            final String unknownToken = unknownTokens[0];
            // definedCredits=34
            final String definedCredits = rightToken[0];
            final double valueForUnknownToken = Long.parseLong(definedCredits) * 1.0d / arabic;
            intergalacticCreditsMap.put(unknownToken, valueForUnknownToken);
        }
    }

    public double creditsToArabic(final String... token) {
        final StringBuilder romanNumbers = new StringBuilder();
        Double credit = 0.0d;
        // glob prok Gold
        for (final String t : token) {
            final String roman = intergalacticToRomanMap.get(t);
            if (null != roman) { // glob prok
                romanNumbers.append(roman);
            } else { // Gold
                credit += intergalacticCreditsMap.get(t);
            }
        }
        // glob prok -> IV
        final long arabian = RomanNumber.romanToArabic(romanNumbers.toString());
        if (credit > 0.0d) {
            return arabian * credit;
        } else {
            return arabian;
        }
    }

}
