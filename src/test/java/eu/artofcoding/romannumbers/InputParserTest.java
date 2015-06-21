package eu.artofcoding.romannumbers;

import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputParserTest {

    @Test
    public void testProcessInput() throws Exception {
        final URL inputURL = InputParserTest.class.getResource("/input.txt");
        final Path inputFilepath = Paths.get(inputURL.toURI());
        if (Files.exists(inputFilepath)) {
            InputParser.processInput(inputFilepath);
        } else {
            System.out.printf("Sorry, cannot find or access file %s%n", inputFilepath);
        }
    }

}
