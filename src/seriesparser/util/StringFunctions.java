package seriesparser.util;

public class StringFunctions {
    public static String leftPadZero(final int inputNumber, final int seriesAmountDigits) {
        StringBuilder output = new StringBuilder("" + inputNumber);
        while (output.length() < seriesAmountDigits) {
            output.insert(0, "0");
        }
        return output.toString();
    }
}
