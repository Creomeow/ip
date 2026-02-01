package meow.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import meow.exception.MeowException;

/**
 * Utility class for parsing and formatting dates in the MeowBot application.
 * Handles conversion between string representations and LocalDate objects.
 */

public class DateTimeUtil {
    private static final DateTimeFormatter INPUT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter OUTPUT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Parses a date string in yyyy-mm-dd format into a LocalDate object.
     *
     * @param input the date string to parse in yyyy-mm-dd format
     * @return the parsed LocalDate
     * @throws MeowException if the input is not in the correct format
     */

    public static LocalDate parseDate(String input) throws MeowException {
        try {
            return LocalDate.parse(input, INPUT);
        } catch (DateTimeParseException e) {
            throw new MeowException("Please use yyyy-mm-dd (e.g., 2019-10-15).");
        }
    }

    /**
     * Formats a LocalDate object into a human-readable string format.
     *
     * @param date the LocalDate to format
     * @return the formatted date string in "MMM dd yyyy" format (e.g., "Oct 15 2019")
     */
    
    public static String formatDate(LocalDate date) {
        return date.format(OUTPUT);
    }
}
