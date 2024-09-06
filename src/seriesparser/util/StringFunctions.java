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
     * Also turn German quotes to general quotes.
     *
     * @param input any String.
     * @return String with unescaped characters.
     */
    public static String htmlEntitiesToASCII(final String input) {
        String output = input.replace("&amp;", "&");
        output = output.replace("&#039;", "'");
        output = output.replace("&quot;", "\"");
        output = output.replace("„", "\"");
        output = output.replace("“", "\"");
        output = output.replace("«", "\"");
        output = output.replace("»", "\"");
        output = output.replace("<br />\n<br />", " ");
        output = output.replace("<br>", "");
        output = output.replace("<br />", "");
        output = output.replace("<br/>", "");
        output = output.replace("</br>", "");
        output = output.replace("\n", "");
        return output;
    }

    /**
     * Cuts the String from the first occurrence of the sequence fromPattern (not including itself) to the first occurrence of toPattern AFTER fromPattern (not including).
     * If any argument is null the value null is returned.
     * If fromPattern or toPattern are empty null is returned.
     * If the sequence fromPattern is not found in the String an empty String is returned.
     * If the sequence toPattern is not found after the sequence fromPattern in the String an empty String is returned.
     *
     * @param input any String
     * @param fromPattern the pattern to start the new string after.
     * @param toPattern the pattern to end the new string at.
     * @return the part of input between fromPattern and toPattern.
     */
    public static String cutFromTo(final String input, final String fromPattern, final String toPattern) {
        if (input == null) {
            System.err.println("StringFunctions.cutFromTo(String, String, String): input may not be null.");
            return null;
        }
        if (fromPattern == null || toPattern == null || fromPattern.isEmpty() || toPattern.isEmpty()) {
            System.err.println("StringFunctions.cutFromTo(String, String, String): fromPattern and toPattern may not be null.");
            return null;
        }
        String relevantFromStart;
        if (input.contains(fromPattern)) {
            relevantFromStart = input.substring(input.indexOf(fromPattern) + fromPattern.length());
            if (relevantFromStart.contains(toPattern)) {
                return relevantFromStart.substring(0, relevantFromStart.indexOf(toPattern));
            } else {
                System.err.println("input did not contain sequence toPattern after sequence fromPattern. Returning empty String.");
                return "";
            }
        } else {
            System.err.println("input did not contain sequence fromPattern. Returning empty String.");
            return "";
        }
    }
}
