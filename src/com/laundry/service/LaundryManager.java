package com.laundry.service;

import com.laundry.model.Layanan;
import com.laundry.model.Pelanggan;
import com.laundry.model.Pesanan;
import com.laundry.model.StatusPesanan;
import com.laundry.util.WhatsAppHelper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LaundryManager {
    private static final int KAPASITAS_MESIN = 4;
    private final Queue<Pesanan> antrianFifo = new LinkedList<>();
    private final List<Pesanan> daftarSemuaPesanan = new ArrayList<>();
    private int counterOrder;

    public Pesanan tambahPesanan(String nama, String noWhatsapp, Layanan layanan, double beratKg) {
        if (nama == null || nama.isBlank()) throw new IllegalArgumentException("Nama pelanggan wajib diisi.");
        String nomor = WhatsAppHelper.formatNomorInternasional(noWhatsapp);
        if (layanan == null) throw new IllegalArgumentException("Layanan wajib dipilih.");
        if (!Double.isFinite(beratKg) || beratKg <= 0) {
            throw new IllegalArgumentException("Berat harus berupa angka lebih dari 0 kg.");
        }
        int nomorUrut = counterOrder + 1;
        Pelanggan pelanggan = new Pelanggan(String.format("PLG-%03d", nomorUrut), nama, nomor);
        Pesanan pesanan = new Pesanan(generateIdPesanan(nomorUrut), pelanggan, layanan, beratKg);
        antrianFifo.add(pesanan);
        daftarSemuaPesanan.add(pesanan);
        counterOrder = nomorUrut;
        return pesanan;
    }

    private String generateIdPesanan(int nomorUrut) {
        return String.format("ORD-%03d", nomorUrut);
    }

    public Pesanan prosesAntrianBerikutnya() {
        if (antrianFifo.isEmpty()) throw new IllegalStateException("Antrean menunggu kosong.");
        if (getJumlahSedangDiproses() >= KAPASITAS_MESIN) {
            throw new IllegalStateException("Kapasitas mesin penuh (4 / 4). Selesaikan pesanan terlebih dahulu.");
        }
        Pesanan berikutnya = antrianFifo.peek();
        if (berikutnya.getStatus() != StatusPesanan.BELUM_MULAI) {
            throw new IllegalStateException("Status antrean tidak sesuai. Pesanan tidak dapat diproses.");
        }
        berikutnya.lanjutkanStatus();
        antrianFifo.remove();
        return berikutnya;
    }

    public Pesanan tandaiSelesai(String idPesanan) {
        Pesanan pesanan = cariPesanan(idPesanan);
        if (pesanan.getStatus() != StatusPesanan.DIPROSES) {
            throw new IllegalStateException("Hanya pesanan yang sedang diproses dapat ditandai selesai.");
        }
        pesanan.lanjutkanStatus();
        return pesanan;
    }

    public Pesanan konfirmasiDiambil(String idPesanan) {
        Pesanan pesanan = cariPesanan(idPesanan);
        if (pesanan.getStatus() != StatusPesanan.SELESAI) {
            throw new IllegalStateException("Hanya pesanan selesai yang dapat dikonfirmasi diambil.");
        }
        pesanan.lanjutkanStatus();
        return pesanan;
    }

    public List<Pesanan> getPesananByStatus(StatusPesanan status) {
        if (status == null) throw new IllegalArgumentException("Status wajib dipilih.");
        List<Pesanan> hasil = new ArrayList<>();
        for (Pesanan pesanan : daftarSemuaPesanan) {
            if (pesanan.getStatus() == status) hasil.add(pesanan);
        }
        return List.copyOf(hasil);
    }

    public List<Pesanan> getDaftarAntrianFifo() { return List.copyOf(antrianFifo); }
    public List<Pesanan> getDaftarSemuaPesanan() { return List.copyOf(daftarSemuaPesanan); }
    public int getJumlahSedangDiproses() { return getPesananByStatus(StatusPesanan.DIPROSES).size(); }
    public int getKapasitasMesin() { return KAPASITAS_MESIN; }

    private Pesanan cariPesanan(String idPesanan) {
        for (Pesanan pesanan : daftarSemuaPesanan) {
            if (pesanan.getIdPesanan().equals(idPesanan)) return pesanan;
        }
        throw new IllegalArgumentException("Pesanan tidak ditemukan: " + idPesanan);
    }
}
