package seriesparser.util;

import seriesparser.Main;

public class ParserOptions {
    public static boolean getShowProgress(final String[] args) {
        if (args.length >= 2) {
            return Boolean.parseBoolean(args[2]);
        }
        return Main.DEFAULT_SHOW_PROGRESS;
    }
}
