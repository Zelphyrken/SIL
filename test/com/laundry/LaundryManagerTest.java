package com.laundry;

import com.laundry.model.CuciKering;
import com.laundry.model.CuciSetrika;
import com.laundry.model.Pesanan;
import com.laundry.model.StatusPesanan;
import com.laundry.service.LaundryManager;
import com.laundry.util.FormatUtil;
import com.laundry.util.WhatsAppHelper;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/** Pemeriksaan alur utama tanpa library pengujian tambahan dan tanpa membuka GUI. */
public class LaundryManagerTest {
    private static int diperiksa;

    /** Menjalankan skenario sesuai aturan biaya, antrean, status, dan WhatsApp. */
    public static void main(String[] args) {
        LaundryManager manager = new LaundryManager();
        gagal(manager::prosesAntrianBerikutnya);
        cek(new CuciKering().hitungTotalBiaya(2) == 10000, "Tarif cuci kering");
        cek(new CuciSetrika().hitungTotalBiaya(2.5) == 17500, "Tarif cuci setrika");
        cek("Rp17.500".equals(FormatUtil.rupiah(17500)), "Format rupiah");
        cek("Rp2".equals(FormatUtil.rupiah(1.5)), "Pembulatan rupiah");

        // Input gagal tidak boleh menghasilkan pesanan atau memakai nomor urut ID.
        gagal(() -> manager.tambahPesanan(" ", "08123456789", new CuciKering(), 1));
        gagal(() -> manager.tambahPesanan("Andi", "123", new CuciKering(), 1));
        gagal(() -> manager.tambahPesanan("Andi", " ", new CuciKering(), 1));
        gagal(() -> manager.tambahPesanan("Andi", "08123456789", new CuciKering(), 0));
        gagal(() -> manager.tambahPesanan("Andi", "08123456789", new CuciKering(), -1));
        gagal(() -> manager.tambahPesanan("Andi", "08123456789", new CuciKering(), Double.NaN));
        gagal(() -> manager.tambahPesanan("Andi", "08123456789", null, 1));
        cek(manager.getDaftarSemuaPesanan().isEmpty(), "Input gagal tidak dicatat");

        // Lima pesanan membuktikan urutan FIFO dan batas empat pesanan aktif.
        Pesanan[] pesanan = new Pesanan[5];
        for (int i = 0; i < 5; i++) {
            pesanan[i] = manager.tambahPesanan("Pelanggan " + i, "08123456789", new CuciSetrika(), 2.5);
            cek(pesanan[i].getIdPesanan().equals(String.format("ORD-%03d", i + 1)), "ID urut");
            cek(pesanan[i].getStatus() == StatusPesanan.BELUM_MULAI, "Status awal");
        }
        cek(manager.getDaftarAntrianFifo().get(0) == pesanan[0], "Antrean sesuai urutan masuk");
        for (int i = 0; i < 4; i++) cek(manager.prosesAntrianBerikutnya() == pesanan[i], "Mulai FIFO");
        cek(manager.getJumlahSedangDiproses() == 4, "Kapasitas empat");
        gagal(manager::prosesAntrianBerikutnya);
        cek(pesanan[4].getStatus() == StatusPesanan.BELUM_MULAI, "Pesanan kelima menunggu");
        cek(manager.getDaftarAntrianFifo().size() == 1, "Antrean tidak hilang saat penuh");

        // Pesanan ketiga boleh selesai lebih dulu dan membuka satu slot kapasitas.
        gagal(() -> manager.tandaiSelesai(pesanan[4].getIdPesanan()));
        gagal(() -> manager.konfirmasiDiambil(pesanan[0].getIdPesanan()));
        manager.tandaiSelesai(pesanan[2].getIdPesanan());
        cek(pesanan[2].getStatus() == StatusPesanan.SELESAI, "Selesai tanpa FIFO");
        cek(manager.getJumlahSedangDiproses() == 3, "Kapasitas kembali tersedia");
        cek(manager.prosesAntrianBerikutnya() == pesanan[4], "Antrean berikutnya dimulai");
        cek(manager.getPesananByStatus(StatusPesanan.SELESAI).size() == 1, "Daftar siap diambil");

        // Tautan harus memakai nomor internasional dan memuat rincian pesanan selesai.
        WhatsAppHelper whatsapp = new WhatsAppHelper();
        cek(WhatsAppHelper.formatNomorInternasional("+628123456789").equals("628123456789"), "Nomor +62");
        cek(WhatsAppHelper.formatNomorInternasional("08123456789").equals("628123456789"), "Nomor 08");
        gagal(() -> WhatsAppHelper.formatNomorInternasional("+08123456789"));
        gagal(() -> whatsapp.buatTautan(pesanan[0]));
        String tautan = whatsapp.buatTautan(pesanan[2]);
        cek(tautan.startsWith("https://wa.me/628123456789?text="), "Alamat WhatsApp");
        String pesan = URLDecoder.decode(tautan.substring(tautan.indexOf("?text=") + 6), StandardCharsets.UTF_8);
        cek(pesan.contains("ORD-003") && pesan.contains("Cuci Setrika")
                && pesan.contains("2,5 kg") && pesan.contains("Rp17.500")
                && pesan.contains("siap diambil"), "Isi notifikasi lengkap");

        // Pesanan yang telah diambil keluar dari daftar aktif tetapi tetap di riwayat sesi.
        manager.konfirmasiDiambil(pesanan[2].getIdPesanan());
        cek(pesanan[2].getStatus() == StatusPesanan.DIAMBIL, "Status diambil");
        cek(manager.getPesananByStatus(StatusPesanan.SELESAI).isEmpty(), "Tidak lagi siap diambil");
        cek(manager.getDaftarSemuaPesanan().size() == 5, "Riwayat tetap tersedia");
        gagal(() -> manager.konfirmasiDiambil(pesanan[2].getIdPesanan()));
        gagal(pesanan[2]::lanjutkanStatus);

        // Memastikan format ID tetap unik saat jumlah pesanan melewati dua digit.
        for (int i = 0; i < 95; i++) manager.tambahPesanan("Uji " + i, "628123456789", new CuciKering(), 1);
        cek(manager.getDaftarSemuaPesanan().get(99).getIdPesanan().equals("ORD-100"), "100 pesanan unik");
        System.out.println("LULUS: " + diperiksa + " pemeriksaan logika pesanan.");
    }

    /** Menandai pemeriksaan berhasil atau menghentikan tes dengan nama skenario. */
    private static void cek(boolean kondisi, String nama) {
        diperiksa++;
        if (!kondisi) throw new AssertionError(nama);
    }

    /** Memastikan operasi tidak valid ditolak dengan pengecualian yang diharapkan. */
    private static void gagal(Runnable tindakan) {
        diperiksa++;
        try {
            tindakan.run();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return;
        }
        throw new AssertionError("Operasi seharusnya ditolak.");
    }
}
