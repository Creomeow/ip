package meow.util;

import meow.exception.MeowException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static final DateTimeFormatter INPUT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter OUTPUT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public static LocalDate parseDate(String raw) throws MeowException {
        try {
            return LocalDate.parse(raw.trim(), INPUT);
        } catch (DateTimeParseException e) {
            throw new MeowException("meow.Meow! Please use yyyy-mm-dd (e.g., 2019-10-15).");
        }
    }

    public static String formatDate(LocalDate date) {
        return date.format(OUTPUT);
    }
}
