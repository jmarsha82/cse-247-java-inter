package rabinkarp;

import java.io.PrintStream;

/**
 * Small command-line interface for exploring the rolling hash output.
 */
public final class RabinKarpCli {
    private RabinKarpCli() {
    }

    /**
     * Prints one hash value per character in the provided target string.
     *
     * @param args expected as: window-size target-string
     */
    public static void main(String[] args) {
        System.exit(run(args, System.out, System.err));
    }

    static int run(String[] args, PrintStream out, PrintStream err) {
        if (args.length != 2) {
            err.println("Usage: java rabinkarp.RabinKarpCli <window-size> <target-string>");
            return 1;
        }

        int windowSize;
        try {
            windowSize = parseWindowSize(args[0]);
        } catch (IllegalArgumentException ex) {
            err.println(ex.getMessage());
            return 1;
        }

        RK rollingHash = new RK(windowSize);
        String target = args[1];
        for (int i = 0; i < target.length(); i++) {
            int hash = rollingHash.nextCh(target.charAt(i));
            out.printf("%d: %c -> %d%n", i, target.charAt(i), hash);
        }
        return 0;
    }

    static int parseWindowSize(String rawWindowSize) {
        try {
            return Integer.parseInt(rawWindowSize);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Window size must be an integer", ex);
        }
    }
}
