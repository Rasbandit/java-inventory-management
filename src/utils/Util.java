package utils;

/** A collection of helpful functions */
public class Util {

    /** Tests if a string can be parsed to an Integer */
    public static boolean isIntegerParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    /** Tests if a string can be parsed to a Double */
    public static boolean isDoubleParsable(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
}
