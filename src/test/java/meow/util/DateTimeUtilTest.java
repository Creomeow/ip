package meow.util;

import meow.exception.MeowException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeUtilTest {

    @Test
    void parseDate_validIso_success() throws Exception {
        LocalDate d = DateTimeUtil.parseDate("2019-10-15");
        assertEquals(LocalDate.of(2019, 10, 15), d);
    }

    @Test
    void parseDate_leapDay_success() throws Exception {
        LocalDate d = DateTimeUtil.parseDate("2020-02-29");
        assertEquals(LocalDate.of(2020, 2, 29), d);
    }

    @Test
    void parseDate_wrongFormat_throwsMeowException() {
        MeowException e = assertThrows(MeowException.class, () -> DateTimeUtil.parseDate("15-10-2019"));
        assertEquals("Please use yyyy-mm-dd (e.g., 2019-10-15).", e.getMessage());
    }

    @Test
    void parseDate_invalidDate_throwsMeowException() {
        MeowException e = assertThrows(MeowException.class, () -> DateTimeUtil.parseDate("2019-02-29"));
        assertEquals("Please use yyyy-mm-dd (e.g., 2019-10-15).", e.getMessage());
    }

    @Test
    void parseDate_invalidMonth_throwsMeowException() {
        MeowException e = assertThrows(MeowException.class, () -> DateTimeUtil.parseDate("2019-13-01"));
        assertEquals("Please use yyyy-mm-dd (e.g., 2019-10-15).", e.getMessage());
    }

    @Test
    void formatDate_formatsCorrectly_success() {
        String s = DateTimeUtil.formatDate(LocalDate.of(2019, 10, 15));
        assertEquals("Oct 15 2019", s);
    }

    @Test
    void formatDate_dayWithLeadingZero_success() {
        String s = DateTimeUtil.formatDate(LocalDate.of(2019, 10, 5));
        assertEquals("Oct 05 2019", s);
    }
}
