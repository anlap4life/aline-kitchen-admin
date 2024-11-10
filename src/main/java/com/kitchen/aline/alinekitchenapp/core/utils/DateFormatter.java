package com.kitchen.aline.alinekitchenapp.core.utils;

import com.kitchen.aline.alinekitchenapp.domain.Procurement;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatter {
    public static String format(Date date) {
        if (date == null) return "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (date instanceof java.sql.Date) {
            date = new Date(date.getTime());
        }

        Instant instant =  date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        return formatter.format(zonedDateTime);
    }
}
