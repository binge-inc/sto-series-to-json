package seriesparser.util;

public class IPHelper {
    private static final int IP_BLOCKS = 4;
    private static final int IP_MIN_VALUE = 0;
    private static final int IP_MAX_VALUE = 255;

    /**
     * Tests if a String is a valid IPv4 address.
     * The only valid IPv4 format is "w.x.y.z" with all values between 0 and 255 (e.g. "123.123.123.123").
     * Ports may not be included (e.g. trying "123.123.123.123:8080" will give false).
     *
     * @param ipCandidate any String, preferably an IP address.
     * @return true if IP is valid, else false.
     */
    public static boolean checkValidIP(final String ipCandidate) {
        String[] parts = ipCandidate.split("\\.");
        if (parts.length != IP_BLOCKS) return false;
        int[] ints = new int[parts.length];
        for (int i = 0; i < ints.length; i++) {
            try {
                ints[i] = Integer.parseInt(parts[i]);
            } catch (final NumberFormatException e) {
                return false;
            }
            if (ints[i] < IP_MIN_VALUE || ints[i] > IP_MAX_VALUE) return false;
        }
        return true;
    }
}
