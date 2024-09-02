package seriesparser.util;

public class StringAnalyzer {
    public static String getSeriesIdFromPath(final String path) {
        String expectedStartPattern = "/serie/stream/";
        String throwawayPattern = "/";
        if (!path.startsWith(expectedStartPattern)) {
            System.err.println("StringAnalyzer.getSeriesIdFromPath(String): Can't handle this path.");
            return null;
        }
        String cutPath = path.substring(path.indexOf(expectedStartPattern) + expectedStartPattern.length());
        if (cutPath.contains(throwawayPattern)) cutPath = cutPath.substring(0, cutPath.indexOf(throwawayPattern));
        return cutPath;
    }

    /**
     * Gets the amount of digits in a number.
     * @param amount any number
     * @return the amount of characters needed to represent the number
     */
    public static int getDigits(final int amount) {
        String amountString = "" + amount;
        return amountString.length();
    }
}
