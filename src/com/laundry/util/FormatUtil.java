package com.laundry.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class FormatUtil {
    private FormatUtil() { }

    public static String rupiah(double nilai) {
        NumberFormat format = NumberFormat.getIntegerInstance(Locale.forLanguageTag("id-ID"));
        return "Rp" + format.format(Math.round(nilai));
    }

    public static String berat(double nilai) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"));
        format.setMaximumFractionDigits(10);
        return format.format(nilai) + " kg";
    }
}
