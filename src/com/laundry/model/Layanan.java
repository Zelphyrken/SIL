package com.laundry.model;

public abstract class Layanan {
    protected final String namaLayanan;
    protected final double hargaPerKg;

    protected Layanan(String namaLayanan, double hargaPerKg) {
        this.namaLayanan = namaLayanan;
        this.hargaPerKg = hargaPerKg;
    }

    public String getNamaLayanan() { return namaLayanan; }
    public double getHargaPerKg() { return hargaPerKg; }
    public abstract double hitungTotalBiaya(double beratKg);

    @Override
    public String toString() { return namaLayanan; }
}
