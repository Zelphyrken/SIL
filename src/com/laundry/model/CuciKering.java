package com.laundry.model;

public class CuciKering extends Layanan {
    public CuciKering() { super("Cuci Kering", 5000); }

    @Override
    public double hitungTotalBiaya(double beratKg) { return beratKg * hargaPerKg; }
}
