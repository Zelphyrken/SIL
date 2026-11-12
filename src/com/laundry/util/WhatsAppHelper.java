package com.laundry.util;

import com.laundry.model.Pesanan;
import com.laundry.model.StatusPesanan;
import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WhatsAppHelper implements Notifiable {
    public static String formatNomorInternasional(String nomor) {
        if (nomor == null || nomor.isBlank()) throw new IllegalArgumentException("Nomor WhatsApp wajib diisi.");
        String bersih = nomor.trim();
        String digit = bersih.startsWith("+") ? bersih.substring(1) : bersih;
        if (!digit.matches("\\d{9,15}") || !(digit.startsWith("08") || digit.startsWith("628"))) {
            throw new IllegalArgumentException("Nomor WhatsApp harus berformat 08..., 628..., atau +628... (9–15 digit).");
        }
        if (bersih.startsWith("+") && !digit.startsWith("628")) {
            throw new IllegalArgumentException("Awalan + hanya berlaku untuk +628...");
        }
        String hasil = digit.startsWith("0") ? "62" + digit.substring(1) : digit;
        if (hasil.length() > 15) throw new IllegalArgumentException("Nomor WhatsApp terlalu panjang.");
        return hasil;
    }

    public String buatPesanNotifikasi(Pesanan pesanan) {
        pastikanSelesai(pesanan);
        return "Halo Bapak/Ibu " + pesanan.getPelanggan().getNama()
                + ", cucian Anda dengan nomor pesanan " + pesanan.getIdPesanan()
                + " telah selesai dan siap diambil. Layanan: " + pesanan.getLayanan().getNamaLayanan()
                + ", Berat: " + FormatUtil.berat(pesanan.getBeratKg())
                + ", Total Biaya: " + FormatUtil.rupiah(pesanan.getTotalBiaya())
                + ". Terima kasih telah menggunakan layanan laundry kami.";
    }

    public String buatTautan(Pesanan pesanan) {
        String pesan = buatPesanNotifikasi(pesanan);
        return "https://wa.me/" + formatNomorInternasional(pesanan.getPelanggan().getNoWhatsapp())
                + "?text=" + URLEncoder.encode(pesan, StandardCharsets.UTF_8);
    }

    @Override
    public void kirimNotifikasi(Pesanan pesanan) throws Exception {
        URI alamat = URI.create(buatTautan(pesanan));
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            throw new UnsupportedOperationException("Browser tidak tersedia di komputer ini.");
        }
        Desktop.getDesktop().browse(alamat);
    }

    private void pastikanSelesai(Pesanan pesanan) {
        if (pesanan == null || pesanan.getStatus() != StatusPesanan.SELESAI) {
            throw new IllegalStateException("Notifikasi hanya tersedia untuk pesanan yang selesai.");
        }
    }
}
