package com.laundry.model;

/** Dasar bersama untuk paket layanan; setiap turunan menentukan perhitungan biayanya. */
public abstract class Layanan {
    protected final String namaLayanan;
    protected final double hargaPerKg;

    protected Layanan(String namaLayanan, double hargaPerKg) {
        this.namaLayanan = namaLayanan;
        this.hargaPerKg = hargaPerKg;
    }

    public String getNamaLayanan() { return namaLayanan; }
    public double getHargaPerKg() { return hargaPerKg; }
    /** Polimorfisme: pemanggil cukup memakai tipe Layanan tanpa mengetahui paket konkret. */
    public abstract double hitungTotalBiaya(double beratKg);

    /** Nama layanan dipakai sebagai teks pilihan pada JComboBox. */
    @Override
    public String toString() { return namaLayanan; }
}
