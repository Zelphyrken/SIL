package com.laundry.model;

public class CuciSetrika extends Layanan {
    public CuciSetrika() { super("Cuci Setrika", 7000); }

    @Override
    public double hitungTotalBiaya(double beratKg) { return beratKg * hargaPerKg; }
}
