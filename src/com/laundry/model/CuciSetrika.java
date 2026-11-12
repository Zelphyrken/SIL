package com.laundry.model;

/** Paket cuci setrika dengan tarif tetap Rp7.000 per kilogram. */
public class CuciSetrika extends Layanan {
    public CuciSetrika() { super("Cuci Setrika", 7000); }

    /** Implementasi rumus biaya untuk paket cuci setrika. */
    @Override
    public double hitungTotalBiaya(double beratKg) { return beratKg * hargaPerKg; }
}
