package com.pzen.utils;

import com.pzen.evn.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_TIME);

    public static LocalDateTime parseDate(String dateStr) throws DateTimeParseException {
        return parseDate(dateStr, DEFAULT_DATE_FORMATTER);
    }

    public static LocalDateTime parseDate(String dateStr, DateTimeFormatter formatter) throws DateTimeParseException {
        return LocalDateTime.parse(dateStr, formatter);
    }

    public static String formatDate(LocalDateTime date) {
        return formatDate(date, DEFAULT_DATE_FORMATTER);
    }

    public static String formatDate(LocalDateTime date, DateTimeFormatter formatter) {
        return date.format(formatter);
    }

    public static LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }

    public static String getCurrentDateString() {
        return formatDate(getCurrentDate());
    }
}
