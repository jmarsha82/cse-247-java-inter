package rabinkarp;

import java.util.Arrays;

/**
 * Rabin-Karp rolling hash for a fixed-width character window.
 *
 * <p>The hash uses base 31 and modulus 511, matching the original course
 * assignment test data. Until the window fills, missing characters contribute
 * zero to the hash.</p>
 */
public final class RK implements RollingHasher {
    static final int BASE = 31;
    static final int MODULUS = 511;

    private final char[] window;
    private final int baseToWindowPower;
    private int pointer;
    private int hash;

    /**
     * Creates a rolling hash with the requested window size.
     *
     * @param windowSize number of characters in the rolling window
     */
    public RK(int windowSize) {
        if (windowSize < 1) {
            throw new IllegalArgumentException("Window size must be at least 1");
        }

        this.window = new char[windowSize];
        this.baseToWindowPower = powMod(BASE, windowSize);
        Arrays.fill(this.window, '\0');
    }

    @Override
    public int nextCh(char nextCharacter) {
        if (window.length == 1) {
            return nextCharacter;
        }

        char outgoing = window[pointer];
        window[pointer] = nextCharacter;
        hash = floorMod(
                hash * BASE
                        - baseToWindowPower * charValue(outgoing)
                        + charValue(nextCharacter),
                MODULUS);
        pointer = (pointer + 1) % window.length;
        return hash;
    }

    @Override
    public int windowSize() {
        return window.length;
    }

    private static int powMod(int base, int exponent) {
        int answer = 1;
        for (int i = 0; i < exponent; i++) {
            answer = (answer * base) % MODULUS;
        }
        return answer;
    }

    private static int charValue(char character) {
        return character;
    }

    private static int floorMod(int value, int modulus) {
        int remainder = value % modulus;
        return remainder < 0 ? remainder + modulus : remainder;
    }
}
