package eu.artofcoding.romannumbers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InputParser {

    private static final Pattern questionPattern = Pattern.compile("how (much is|much|many Credits is) (.*) \\?");

    private IntergalacticDefiner intergalacticDefiner;

    public InputParser() {
        intergalacticDefiner = new IntergalacticDefiner();
    }

    private static boolean isQuestion(final String line) {
        final Matcher matcher = questionPattern.matcher(line);
        return matcher.matches();
    }

    private void parseDefinition(final String definition) {
        final String[] defineXForY = definition.split(Constants.IS);
        final boolean definitionIsComplete = defineXForY.length == 2;
        if (definitionIsComplete) {
            final String[] leftTokens = defineXForY[0].split(Constants.SPACE);
            final String[] rightTokens = defineXForY[1].split(Constants.SPACE);
            final boolean isSimpleDefinition = leftTokens.length == 1 && rightTokens.length == 1;
            final boolean isComplexDefinition = leftTokens.length > 1 && rightTokens.length > 1;
            if (isSimpleDefinition) {
                intergalacticDefiner.simpleDefine(leftTokens[0], rightTokens[0]);
            } else if (isComplexDefinition) {
                intergalacticDefiner.complexDefine(leftTokens, rightTokens);
            }
        } else {
            System.out.printf("Cannot parse definition: %s%n", definition);
        }
    }

    private void answerQuestion(final String line) {
        final Matcher matcher = questionPattern.matcher(line);
        if (matcher.matches()) {
            final String subject = matcher.group(2);
            final boolean canAnswerQuestion = intergalacticDefiner.allTokensKnown(subject.split(Constants.SPACE));
            if (canAnswerQuestion) {
                final String[] tokens = subject.split(Constants.SPACE);
                final double answer = intergalacticDefiner.creditsToArabic(tokens);
                System.out.printf("%s is %.0f%n", subject, answer);
            } else {
                System.out.printf("I have no idea what you are talking about%n");
            }
        }
    }

    public static void processInput(final Path filepath) throws IOException {
        final InputParser inputParser = new InputParser();
        final List<String> lines = Files.readAllLines(filepath);
        for (final String line : lines) {
            if (isQuestion(line)) {
                inputParser.answerQuestion(line);
            } else {
                inputParser.parseDefinition(line);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("usage: java -jar <file.jar> <intergalactic-input.txt>");
        } else {
            final String filepath = args[0];
            final Path inputFilepath = Paths.get(filepath);
            if (Files.exists(inputFilepath)) {
                processInput(inputFilepath);
            } else {
                System.out.printf("Sorry, cannot find or access file %s%n", filepath);
            }
        }
    }

}
