package seriesparser.util;

public class StringFunctions {
    /**
     * Pads a number (technically any String) with leading zeros until it has as many digits as specified.
     * If the number already has the same amount of or more digits than requiredDigitsAmount, inputNumber is returned as a String.
     *
     * @param inputNumber any number, ideally with a digit amount less than requiredDigitsAmount.
     * @param requiredDigitsAmount the minimum length of the resulting string.
     * @return a String representation of the number padded with up to requiredDigitsAmount leading zeros.
     */
    public static String leftPadZero(final int inputNumber, final int requiredDigitsAmount) {
        StringBuilder output = new StringBuilder("" + inputNumber);
        while (output.length() < requiredDigitsAmount) {
            output.insert(0, "0");
        }
        return output.toString();
    }

    /**
     * Replaces HTML entities (escaped & and ') with their unescaped character.
     * s.to is inconsistent with its titles. Some series already have unescaped & and ' in their names, some do not.
     *
     * @param input any String.
     * @return String with unescaped characters.
     */
    public static String sanitize(final String input) {
        String output = input.replace("&amp;", "&");
        output = output.replace("&#039;", "'");
        /*
        output = output.replace("&apos;", "'");
        output = output.replace("&quot;", "\"");
        output = output.replace("&lt;", "<");
        output = output.replace("&gt;", ">");
        output = output.replace("&nbsp;", "\u00A0\n");
        output = output.replace("&dollar;", "$");
        output = output.replace("&euro;", "€");
        output = output.replace("&pound;", "£");
        output = output.replace("&yen;", "¥");
        output = output.replace("&plus;", "+");
        output = output.replace("&minus;", "-");
        output = output.replace("&times;", "×");
        output = output.replace("&divide;", "÷");
        output = output.replace("&equals;", "=");
        output = output.replace("&excl;", "!");
        output = output.replace("&quest;", "?");
        output = output.replace("&commat;", "@");
        output = output.replace("&num;", "#");
        output = output.replace("&percnt;", "%");
        output = output.replace("&copy;", "©");
        output = output.replace("&reg;", "®");
        output = output.replace("&trade;", "™");
        output = output.replace("&sect;", "§");
        output = output.replace("&para;", "¶");
        */
        return output;
    }
}
