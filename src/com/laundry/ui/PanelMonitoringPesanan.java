package com.laundry.ui;

import com.laundry.model.Pesanan;
import com.laundry.model.StatusPesanan;
import com.laundry.service.LaundryManager;
import com.laundry.util.FormatUtil;
import com.laundry.util.Notifiable;
import com.laundry.util.WhatsAppHelper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

/** Memisahkan pesanan menurut status dan menyediakan tindakan untuk tiap tahap. */
public class PanelMonitoringPesanan extends JPanel {
    private final LaundryManager manager;
    private final WhatsAppHelper whatsApp = new WhatsAppHelper();
    private final JLabel kapasitas = UiStyle.label("0 / 4", 23, Font.BOLD, UiStyle.BLUE_DARK);
    private final JLabel jumlahMenunggu = badge("0 Menunggu", UiStyle.AMBER_PALE, UiStyle.AMBER);
    private final JLabel jumlahDiproses = badge("0 Diproses", UiStyle.BLUE_PALE, UiStyle.BLUE_DARK);
    private final JLabel jumlahSelesai = badge("0 Siap Diambil", UiStyle.GREEN_PALE, UiStyle.GREEN);
    private final DefaultTableModel modelMenunggu = model("Id Pesanan", "Nama Pelanggan", "Layanan", "Berat", "Total Biaya", "Masuk");
    private final DefaultTableModel modelDiproses = model("Id Pesanan", "Nama Pelanggan", "Layanan", "Berat", "Total Biaya", "Masuk");
    private final DefaultTableModel modelSelesai = model("Id Pesanan", "Nama Pelanggan", "WhatsApp", "Layanan", "Total Biaya", "Masuk");
    private final JTable tabelDiproses = UiStyle.table(modelDiproses);
    private final JTable tabelSelesai = UiStyle.table(modelSelesai);

    /** Ketiga tabel memakai manager yang sama dengan form penerimaan pesanan. */
    public PanelMonitoringPesanan(LaundryManager manager) {
        super(new BorderLayout(0, 18));
        this.manager = manager;
        setBackground(UiStyle.BACKGROUND);
        setBorder(new EmptyBorder(26, 28, 22, 28));

        JPanel kepala = new JPanel(new BorderLayout(16, 0));
        kepala.setOpaque(false);
        JPanel judul = new JPanel();
        judul.setOpaque(false);
        judul.setLayout(new BoxLayout(judul, BoxLayout.Y_AXIS));
        judul.add(UiStyle.label("Antrean & Proses Cucian", 25, Font.BOLD, UiStyle.TEXT));
        judul.add(Box.createVerticalStrut(5));
        judul.add(UiStyle.label("Pantau dan Proses Pesanan Sesuai Tahap Pengerjaan.", 13, Font.PLAIN, UiStyle.MUTED));
        kepala.add(judul, BorderLayout.CENTER);
        JPanel kapasitasCard = UiStyle.card();
        kapasitasCard.setLayout(new BoxLayout(kapasitasCard, BoxLayout.Y_AXIS));
        kapasitasCard.setBorder(new EmptyBorder(9, 15, 9, 15));
        kapasitasCard.setPreferredSize(new Dimension(176, 64));
        kapasitasCard.add(UiStyle.label("Mesin Aktif", 11, Font.BOLD, UiStyle.MUTED));
        kapasitasCard.add(Box.createVerticalStrut(2));
        kapasitasCard.add(kapasitas);
        kepala.add(kapasitasCard, BorderLayout.EAST);
        add(kepala, BorderLayout.NORTH);

        // Tindakan awal selalu mengambil kepala antrean; tabel menunggu tidak perlu dipilih.
        JPanel kelompok = new JPanel();
        kelompok.setBackground(UiStyle.BACKGROUND);
        kelompok.setLayout(new BoxLayout(kelompok, BoxLayout.Y_AXIS));
        JButton proses = UiStyle.button("Proses Antrean Terdepan", true);
        proses.addActionListener(e -> jalankan(() -> {
            Pesanan p = manager.prosesAntrianBerikutnya();
            info("Pesanan " + p.getIdPesanan() + " Mulai Diproses.");
        }));
        kelompok.add(bagian("Antrean Menunggu", jumlahMenunggu, UiStyle.table(modelMenunggu), proses));
        kelompok.add(Box.createVerticalStrut(12));

        // Penyelesaian dan pengambilan memakai baris terpilih karena urutannya bebas.
        JButton selesai = UiStyle.button("Tandai Selesai", true);
        selesai.addActionListener(e -> jalankan(() -> {
            Pesanan p = manager.tandaiSelesai(idTerpilih(tabelDiproses));
            info("Pesanan " + p.getIdPesanan() + " Siap Diambil.");
        }));
        kelompok.add(bagian("Sedang Diproses", jumlahDiproses, tabelDiproses, selesai));
        kelompok.add(Box.createVerticalStrut(12));

        JButton notifikasi = UiStyle.button("Siapkan/Buka Notifikasi WhatsApp", false);
        notifikasi.addActionListener(e -> bukaWhatsapp());
        JButton diambil = UiStyle.button("Konfirmasi Sudah Diambil", true);
        diambil.addActionListener(e -> jalankan(() -> {
            Pesanan p = manager.konfirmasiDiambil(idTerpilih(tabelSelesai));
            info("Pengambilan Pesanan " + p.getIdPesanan() + " Tercatat.");
        }));
        kelompok.add(bagian("Siap Diambil", jumlahSelesai, tabelSelesai, notifikasi, diambil));

        JScrollPane halaman = UiStyle.scroll(kelompok);
        halaman.getViewport().setBackground(UiStyle.BACKGROUND);
        add(halaman, BorderLayout.CENTER);
        perbaruiTabel();
    }

    /** Menolak pengeditan sel agar status hanya berubah melalui tombol tindakan. */
    private static DefaultTableModel model(String... kolom) {
        return new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int baris, int kolom) { return false; }
        };
    }

    /** Label jumlah memakai warna lembut untuk membedakan kelompok status. */
    private static JLabel badge(String text, Color fill, Color foreground) {
        JLabel label = UiStyle.label(text, 11, Font.BOLD, foreground);
        label.setOpaque(true);
        label.setBackground(fill);
        label.setBorder(new EmptyBorder(6, 9, 6, 9));
        return label;
    }

    /** Menyusun judul, tabel, dan tindakan dalam card yang dapat dipakai ulang. */
    private JPanel bagian(String judul, JLabel jumlah, JTable tabel, JButton... tombol) {
        JPanel bagian = UiStyle.card();
        bagian.setLayout(new BorderLayout(0, 8));
        bagian.setBorder(new EmptyBorder(12, 15, 12, 15));
        bagian.setAlignmentX(LEFT_ALIGNMENT);
        bagian.setPreferredSize(new Dimension(760, 220));
        bagian.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        JPanel atas = new JPanel(new BorderLayout());
        atas.setOpaque(false);
        atas.add(UiStyle.label(judul, 15, Font.BOLD, UiStyle.TEXT), BorderLayout.WEST);
        atas.add(jumlah, BorderLayout.EAST);
        bagian.add(atas, BorderLayout.NORTH);
        bagian.add(UiStyle.scroll(tabel), BorderLayout.CENTER);

        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        aksi.setOpaque(false);
        for (JButton tombolAksi : tombol) aksi.add(tombolAksi);
        bagian.add(aksi, BorderLayout.SOUTH);
        return bagian;
    }

    /** Membaca status terbaru dari manager setelah tindakan atau perpindahan halaman. */
    public void perbaruiTabel() {
        List<Pesanan> menunggu = manager.getDaftarAntrianFifo();
        List<Pesanan> diproses = manager.getPesananByStatus(StatusPesanan.DIPROSES);
        List<Pesanan> selesai = manager.getPesananByStatus(StatusPesanan.SELESAI);
        isi(modelMenunggu, menunggu, false);
        isi(modelDiproses, diproses, false);
        isi(modelSelesai, selesai, true);
        kapasitas.setText(manager.getJumlahSedangDiproses() + " / " + manager.getKapasitasMesin());
        jumlahMenunggu.setText(menunggu.size() + " Menunggu");
        jumlahDiproses.setText(diproses.size() + " Diproses");
        jumlahSelesai.setText(selesai.size() + " Siap Diambil");
    }

    /** Kolom ketiga dan keempat berbeda pada tabel siap diambil untuk menampilkan nomor WhatsApp. */
    private void isi(DefaultTableModel model, List<Pesanan> daftar, boolean denganNomor) {
        model.setRowCount(0);
        for (Pesanan p : daftar) {
            model.addRow(new Object[] {
                p.getIdPesanan(), p.getPelanggan().getNama(),
                denganNomor ? p.getPelanggan().getNoWhatsapp() : p.getLayanan().getNamaLayanan(),
                denganNomor ? p.getLayanan().getNamaLayanan() : FormatUtil.berat(p.getBeratKg()),
                FormatUtil.rupiah(p.getTotalBiaya()), p.getWaktuMasukTeks()
            });
        }
    }

    /** Mengambil ID dari baris terpilih untuk mencari pesanan yang tepat di manager. */
    private String idTerpilih(JTable tabel) {
        int baris = tabel.getSelectedRow();
        if (baris < 0) throw new IllegalArgumentException("Pilih satu pesanan pada tabel terlebih dahulu.");
        return tabel.getValueAt(baris, 0).toString();
    }

    /** Menjalankan aksi, memperbarui tabel, atau menampilkan alasan penolakan. */
    private void jalankan(Runnable tindakan) {
        try {
            tindakan.run();
            perbaruiTabel();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Tindakan Tidak Dapat Dilakukan", JOptionPane.WARNING_MESSAGE);
        }
    }

    /** Membuka tautan WhatsApp; jika browser gagal, tautan dan pesan bisa disalin. */
    private void bukaWhatsapp() {
        try {
            String id = idTerpilih(tabelSelesai);
            Pesanan p = manager.getPesananByStatus(StatusPesanan.SELESAI).stream()
                    .filter(item -> item.getIdPesanan().equals(id)).findFirst()
                    .orElseThrow(() -> new IllegalStateException("Pesanan tidak lagi siap diambil."));
            Notifiable pengirim = whatsApp;
            try {
                pengirim.kirimNotifikasi(p);
                info("WhatsApp Dibuka. Periksa Pesan, Lalu Kirim Secara Manual.");
            } catch (Exception ex) {
                JPanel cadangan = new JPanel(new BorderLayout(0, 8));
                cadangan.add(new JLabel("Browser Gagal Dibuka. Salin Tautan atau Pesan Berikut:"), BorderLayout.NORTH);
                JTextField tautan = new JTextField(whatsApp.buatTautan(p));
                JTextArea pesan = new JTextArea(whatsApp.buatPesanNotifikasi(p), 5, 48);
                pesan.setLineWrap(true);
                pesan.setWrapStyleWord(true);
                pesan.setEditable(false);
                JPanel isi = new JPanel(new BorderLayout(0, 8));
                isi.add(tautan, BorderLayout.NORTH);
                isi.add(new JScrollPane(pesan), BorderLayout.CENTER);
                cadangan.add(isi, BorderLayout.CENTER);
                JOptionPane.showMessageDialog(this, cadangan, "Tautan WhatsApp Manual", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Pilih Pesanan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void info(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }
}
