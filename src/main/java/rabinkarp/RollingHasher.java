package rabinkarp;

/**
 * A stateful rolling hash that accepts one character at a time.
 */
public interface RollingHasher {
    /**
     * Adds the next character and returns the hash of the current window.
     *
     * @param nextCharacter next character in the target stream
     * @return hash value for the current rolling window
     */
    int nextCh(char nextCharacter);

    /**
     * Returns the configured rolling window size.
     *
     * @return number of characters included in a full window
     */
    int windowSize();
}
