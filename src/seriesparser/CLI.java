package seriesparser;

import seriesparser.util.IPHelper;

public class CLI {
    /**
     * Returns whether the series index json will be recreated or not.
     * The argument should be passed as "update-index=true" or "update-index=false".
     * If update-index is passed but with an invalid value this function will return false.
     * If update-index is not passed the function will return the value of Config.DEFAULT_UPDATE_INDEX.
     *
     * @param args the String array that contains all program arguments.
     * @return whether the series index json will be recreated or not.
     */
    public static boolean getUpdateIndex(final String[] args) {
        String pattern = "update-index=";
        if (hasStringThatStartsWith(args, pattern)) {
            int i = getArgumentThatStartsWith(args, pattern);
            return parseFlexBoolean(args[i].substring(args[i].indexOf(pattern) + pattern.length()));
        }
        return Config.DEFAULT_UPDATE_INDEX;
    }

    /**
     * Returns whether parsing progress should be shown on the command line.
     * The argument should be passed as "show-progress=true" or "show-progress=false".
     * If show-progress is passed but with an invalid value this function will return false.
     * If show-progress is not passed the function will return the value of Config.DEFAULT_SHOW_PROGRESS.
     *
     * @param args the String array that contains all program arguments.
     * @return whether parsing progress should be shown on the command line or not.
     */
    public static boolean getShowProgress(final String[] args) {
        String pattern = "show-progress=";
        if (hasStringThatStartsWith(args, pattern)) {
            int i = getArgumentThatStartsWith(args, pattern);
            return parseFlexBoolean(args[i].substring(args[i].indexOf(pattern) + pattern.length()));
        }
        return Config.DEFAULT_SHOW_PROGRESS;
    }

    /**
     * Returns the IPv4 address to use as the server to crawl.
     * This is either an argument "ip=123.123.123.123" or, if not specified or invalid, the value of Config.DEFAULT_IP
     *
     * @param args the String array that contains all program arguments.
     * @return An IPv4 address.
     */
    public static String getIP(final String[] args) {
        String pattern = "ip=";
        if (hasStringThatStartsWith(args, pattern)) {
            int i = getArgumentThatStartsWith(args, pattern);
            if (IPHelper.checkValidIP(args[i])) {
                return args[i];
            } else {
                System.err.println("IPv4 address \"" + args[i] + "\" is invalid. Using fallback (\"" + Config.DEFAULT_IP + "\").");
            }
        }
        return Config.DEFAULT_IP;
    }

    public static String getSeriesListJsonOutputDirectory(final String[] args) {
        String pattern = "list-output=";
        if (hasStringThatStartsWith(args, pattern)) {
            int i = getArgumentThatStartsWith(args, pattern);
            return args[i].substring(args[i].indexOf(pattern) + pattern.length());
        }
        return Config.DEFAULT_SERIES_LIST_JSON_OUTPUT_DIRECTORY;
    }

    public static String getSeriesJsonOutputDirectory(final String[] args) {
        String pattern = "list-output=";
        if (hasStringThatStartsWith(args, pattern)) {
            int i = getArgumentThatStartsWith(args, pattern);
            return args[i].substring(args[i].indexOf(pattern) + pattern.length());
        }
        return Config.DEFAULT_SERIES_JSON_OUTPUT_DIRECTORY;
    }

    /**
     * Returns true if any String in args[] starts with the String pattern or false if there is no such element.
     *
     * @param args the program arguments created by main(String[]).
     * @param pattern The first characters in the argument.
     * @return true if pattern is found, false if not.
     */
    private static boolean hasStringThatStartsWith(final String[] args, final String pattern) {
        boolean hasString = false;
        for (final String s : args) {
            if (s.startsWith(pattern)) {
                hasString = true;
                break;
            }
        }
        return hasString;
    }

    /**
     * Returns the index in args[] of the first String that starts with the specified String pattern or -1 if there is no such element.
     *
     * @param args the program arguments created by main(String[]).
     * @param pattern The first characters in the argument.
     * @return index in args[index] of found element or -1 if none is found.
     */
    private static int getArgumentThatStartsWith(final String[] args, final String pattern) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith(pattern)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Compares the specified String to a collection of representations for "true" and returns true if it matched one.
     * Returns false otherwise.
     *
     * @param s the String to check.
     * @return true if String is "true", "1", "yes", "y" or "on" or any capitalization of one of these strings. Else false.
     */
    private static boolean parseFlexBoolean(final String s) {
        return ((s != null) && (s.equalsIgnoreCase("true") || s.equals("1") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y") || s.equalsIgnoreCase("on")));
    }

    /**
     * Returns the seriesId from args.
     * The seriesId must be the only argument that does not contain the equals character ("=").
     * If no seriesId is found the function returns null.
     *
     * @param args the program arguments created by main(String[]).
     * @return seriesId if found, else null.
     */
    public static String getSeriesId(final String[] args) {
        for (final String s : args) {
            if (!s.contains("=")) return s;
        }
        return null;
    }
}
