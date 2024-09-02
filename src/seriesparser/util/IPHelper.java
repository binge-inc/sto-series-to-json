package seriesparser.util;

import seriesparser.Main;

public class IPHelper {
    /**
     * Tests if a String is a valid IPv4 address.
     *
     * @param ipCandidate any String, preferably an IP address.
     * @return true if IP is in format 123.123.123.123, else false.
     */
    public static boolean checkValidIP(final String ipCandidate) {
        // ToDo
        return true;
    }

    /**
     * Returns the argument in args[0] if args[] contains at least 1 element and if it is a valid IPv4 address.
     * Else returns the default ip specified in "Main.DEFAULT_IP".
     *
     * @param args the String array that contains all program arguments.
     * @return An IPv4 address.
     */
    public static String getIP(final String[] args) {
        if (args.length != 0) {
            String uncheckedIP = args[0];
            if (IPHelper.checkValidIP(uncheckedIP)) {
                return uncheckedIP;
            }
        }
        return Main.DEFAULT_IP;
    }
}
