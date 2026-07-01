package rabinkarp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

class RKTest {
    private static final char[] ALPHABET = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '2', '4', '7'
    };

    @Test
    void producesCourseExampleHashes() {
        RK example = new RK(4);
        int[] expected = {
                97, 38, 254, 306, 214, 430, 337, 153, 73,
                368, 92, 224, 306, 214, 429, 306, 214
        };
        String target = "aabaacaadaabaabaa";

        for (int i = 0; i < target.length(); i++) {
            assertEquals(expected[i], example.nextCh(target.charAt(i)));
        }
    }

    @Test
    void matchesDirectHashForRandomWindows() {
        Random random = new Random(247);

        for (int length = 1; length <= 100; length++) {
            RK rollingHash = new RK(length);
            String query = randomString(random, length);
            String target = randomString(random, length) + query + randomString(random, length);
            int queryHash = computeHash(query);

            for (int i = 0; i < target.length(); i++) {
                int hash = rollingHash.nextCh(target.charAt(i));
                if (i == (2 * length) - 1) {
                    assertEquals(queryHash, hash);
                }
            }
        }
    }

    @Test
    void singleCharacterWindowReturnsCharacterValue() {
        RK rollingHash = new RK(1);

        assertEquals('a', rollingHash.nextCh('a'));
        assertEquals('Z', rollingHash.nextCh('Z'));
        assertEquals('7', rollingHash.nextCh('7'));
    }

    @Test
    void rejectsInvalidWindowSizes() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new RK(0));

        assertTrue(thrown.getMessage().contains("at least 1"));
    }

    @Test
    void exposesConfiguredWindowSizeThroughInterface() {
        RollingHasher rollingHash = new RK(5);

        assertEquals(5, rollingHash.windowSize());
    }

    private static String randomString(Random random, int length) {
        StringBuilder answer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            answer.append(ALPHABET[random.nextInt(ALPHABET.length)]);
        }
        return answer.toString();
    }

    private static int computeHash(String value) {
        int answer = 0;
        for (int i = 0; i < value.length(); i++) {
            answer = (answer * RK.BASE + value.charAt(i)) % RK.MODULUS;
        }
        return answer;
    }
}
