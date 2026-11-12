package com.laundry.util;

import java.text.NumberFormat;
import java.util.Locale;

/** Format tampilan angka Indonesia yang dipakai oleh tabel dan pesan pelanggan. */
public final class FormatUtil {
    private FormatUtil() { }

    /** Membulatkan biaya ke rupiah terdekat tanpa angka desimal. */
    public static String rupiah(double nilai) {
        NumberFormat format = NumberFormat.getIntegerInstance(Locale.forLanguageTag("id-ID"));
        return "Rp" + format.format(Math.round(nilai));
    }

    /** Menampilkan pecahan berat dengan koma desimal sesuai locale Indonesia. */
    public static String berat(double nilai) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"));
        format.setMaximumFractionDigits(10);
        return format.format(nilai) + " kg";
    }
}
