package com.kitchen.aline.alinekitchenapp.core.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class RupiahFormatter {
    public static String format(Integer rupiah) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return currencyFormat.format(rupiah);
    }
}
