package com.laundry.model;

/** Paket cuci kering dengan tarif tetap Rp5.000 per kilogram. */
public class CuciKering extends Layanan {
    public CuciKering() { super("Cuci Kering", 5000); }

    /** Implementasi rumus biaya untuk paket cuci kering. */
    @Override
    public double hitungTotalBiaya(double beratKg) { return beratKg * hargaPerKg; }
}
