package com.laundry.model;

import java.util.Objects;

public class Pelanggan {
    private final String idPelanggan;
    private String nama;
    private String noWhatsapp;

    public Pelanggan(String idPelanggan, String nama, String noWhatsapp) {
        this.idPelanggan = Objects.requireNonNull(idPelanggan);
        setNama(nama);
        setNoWhatsapp(noWhatsapp);
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public String getNoWhatsapp() {
        return noWhatsapp;
    }

    public void setNama(String nama) {
        if (nama == null || nama.isBlank())
            throw new IllegalArgumentException("Nama pelanggan wajib diisi.");
        this.nama = nama.trim();
    }

    public void setNoWhatsapp(String noWhatsapp) {
        if (noWhatsapp == null || noWhatsapp.isBlank())
            throw new IllegalArgumentException("Nomor WhatsApp wajib diisi.");
        this.noWhatsapp = noWhatsapp.trim();
    }
}
