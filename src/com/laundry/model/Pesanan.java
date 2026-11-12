package com.laundry.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/** Data satu pesanan beserta biaya, waktu masuk, dan tahap pengerjaannya. */
public class Pesanan {
    private static final DateTimeFormatter FORMAT_WAKTU = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
    private final String idPesanan;
    private final Pelanggan pelanggan;
    private final Layanan layanan;
    private final double beratKg;
    private final double totalBiaya;
    private StatusPesanan status;
    private final LocalDateTime waktuMasuk;

    /** Biaya dihitung dari objek layanan dan status awal selalu BELUM_MULAI. */
    public Pesanan(String idPesanan, Pelanggan pelanggan, Layanan layanan, double beratKg) {
        if (idPesanan == null || idPesanan.isBlank()) throw new IllegalArgumentException("ID pesanan wajib diisi.");
        if (!Double.isFinite(beratKg) || beratKg <= 0) throw new IllegalArgumentException("Berat harus lebih dari 0 kg.");
        this.idPesanan = idPesanan;
        this.pelanggan = Objects.requireNonNull(pelanggan);
        this.layanan = Objects.requireNonNull(layanan);
        this.beratKg = beratKg;
        // Perhitungan memakai implementasi layanan yang dipilih melalui polymorphism.
        this.totalBiaya = layanan.hitungTotalBiaya(beratKg);
        if (!Double.isFinite(totalBiaya) || totalBiaya > Long.MAX_VALUE || totalBiaya < 0) {
            throw new IllegalArgumentException("Total biaya di luar batas yang dapat dihitung.");
        }
        this.status = StatusPesanan.BELUM_MULAI;
        this.waktuMasuk = LocalDateTime.now();
    }

    public String getIdPesanan() { return idPesanan; }
    public Pelanggan getPelanggan() { return pelanggan; }
    public Layanan getLayanan() { return layanan; }
    public double getBeratKg() { return beratKg; }
    public double getTotalBiaya() { return totalBiaya; }
    public StatusPesanan getStatus() { return status; }
    public LocalDateTime getWaktuMasuk() { return waktuMasuk; }
    public String getWaktuMasukTeks() { return waktuMasuk.format(FORMAT_WAKTU); }

    /** Memajukan tepat satu tahap; pesanan yang sudah diambil tidak bisa diubah lagi. */
    public void lanjutkanStatus() {
        switch (status) {
            case BELUM_MULAI -> status = StatusPesanan.DIPROSES;
            case DIPROSES -> status = StatusPesanan.SELESAI;
            case SELESAI -> status = StatusPesanan.DIAMBIL;
            case DIAMBIL -> throw new IllegalStateException("Pesanan sudah diambil.");
        }
    }
}
