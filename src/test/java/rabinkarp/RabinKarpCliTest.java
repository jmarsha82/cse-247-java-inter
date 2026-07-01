package rabinkarp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class RabinKarpCliTest {
    @Test
    void parsesWindowSize() {
        assertEquals(4, RabinKarpCli.parseWindowSize("4"));
    }

    @Test
    void rejectsNonNumericWindowSize() {
        assertThrows(IllegalArgumentException.class, () -> RabinKarpCli.parseWindowSize("abc"));
    }

    @Test
    void printsHashesForTargetString() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream error = new ByteArrayOutputStream();

        int exitCode = RabinKarpCli.run(
                new String[] {"4", "aaba"},
                new PrintStream(output, true, StandardCharsets.UTF_8),
                new PrintStream(error, true, StandardCharsets.UTF_8));

        assertEquals(0, exitCode);
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("3: a -> 306"));
        assertEquals("", error.toString(StandardCharsets.UTF_8));
    }

    @Test
    void returnsErrorForBadArguments() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream error = new ByteArrayOutputStream();

        int exitCode = RabinKarpCli.run(
                new String[] {"4"},
                new PrintStream(output, true, StandardCharsets.UTF_8),
                new PrintStream(error, true, StandardCharsets.UTF_8));

        assertEquals(1, exitCode);
        assertEquals("", output.toString(StandardCharsets.UTF_8));
        assertTrue(error.toString(StandardCharsets.UTF_8).contains("Usage:"));
    }

    @Test
    void returnsErrorForNonNumericWindowSize() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream error = new ByteArrayOutputStream();

        int exitCode = RabinKarpCli.run(
                new String[] {"abc", "target"},
                new PrintStream(output, true, StandardCharsets.UTF_8),
                new PrintStream(error, true, StandardCharsets.UTF_8));

        assertEquals(1, exitCode);
        assertEquals("", output.toString(StandardCharsets.UTF_8));
        assertTrue(error.toString(StandardCharsets.UTF_8).contains("integer"));
    }
}
