package com.daclink.fitjourney.Database;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @TypeConverter
    public static LocalDate fromString(String dateString) {
        if (dateString == null) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            // Handle parse error
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(formatter);
    }
}
