package com.laundry.ui;

import com.laundry.model.Pesanan;
import com.laundry.model.StatusPesanan;
import com.laundry.service.LaundryManager;
import com.laundry.util.FormatUtil;
import com.laundry.util.Notifiable;
import com.laundry.util.WhatsAppHelper;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

public class PanelMonitoringPesanan extends JPanel {
    private final LaundryManager manager;
    private final WhatsAppHelper whatsApp = new WhatsAppHelper();
    private final JLabel kapasitas = new JLabel();
    private final DefaultTableModel modelMenunggu = model("ID", "Nama", "Layanan", "Berat", "Biaya", "Masuk");
    private final DefaultTableModel modelDiproses = model("ID", "Nama", "Layanan", "Berat", "Biaya", "Masuk");
    private final DefaultTableModel modelSelesai = model("ID", "Nama", "WhatsApp", "Layanan", "Biaya", "Masuk");
    private final JTable tabelDiproses = tabel(modelDiproses);
    private final JTable tabelSelesai = tabel(modelSelesai);

    public PanelMonitoringPesanan(LaundryManager manager) {
        super(new BorderLayout(0, 12));
        this.manager = manager;
        setBorder(new EmptyBorder(18, 18, 18, 18));
        kapasitas.setFont(kapasitas.getFont().deriveFont(Font.BOLD, 17f));
        add(kapasitas, BorderLayout.NORTH);

        JPanel kelompok = new JPanel(new GridLayout(3, 1, 0, 12));
        JButton proses = new JButton("Proses Antrean Terdepan (FIFO)");
        proses.addActionListener(e -> jalankan(() -> {
            Pesanan p = manager.prosesAntrianBerikutnya();
            info("Pesanan " + p.getIdPesanan() + " mulai diproses.");
        }));
        kelompok.add(bagian("Antrean Menunggu", tabel(modelMenunggu), proses));

        JButton selesai = new JButton("Tandai Selesai");
        selesai.addActionListener(e -> jalankan(() -> {
            Pesanan p = manager.tandaiSelesai(idTerpilih(tabelDiproses));
            info("Pesanan " + p.getIdPesanan() + " siap diambil.");
        }));
        kelompok.add(bagian("Sedang Diproses", tabelDiproses, selesai));

        JButton notifikasi = new JButton("Siapkan/Buka Notifikasi WhatsApp");
        notifikasi.addActionListener(e -> bukaWhatsapp());
        JButton diambil = new JButton("Konfirmasi Sudah Diambil");
        diambil.addActionListener(e -> jalankan(() -> {
            Pesanan p = manager.konfirmasiDiambil(idTerpilih(tabelSelesai));
            info("Pengambilan pesanan " + p.getIdPesanan() + " tercatat.");
        }));
        kelompok.add(bagian("Siap Diambil", tabelSelesai, notifikasi, diambil));
        add(kelompok, BorderLayout.CENTER);
        perbaruiTabel();
    }

    private static DefaultTableModel model(String... kolom) {
        return new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int baris, int kolom) { return false; }
        };
    }

    private static JTable tabel(DefaultTableModel model) {
        JTable hasil = new JTable(model);
        hasil.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hasil.setRowHeight(24);
        hasil.getTableHeader().setReorderingAllowed(false);
        return hasil;
    }

    private JPanel bagian(String judul, JTable tabel, JButton... tombol) {
        JPanel bagian = new JPanel(new BorderLayout(0, 5));
        JLabel label = new JLabel(judul);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
        bagian.add(label, BorderLayout.NORTH);
        bagian.add(new JScrollPane(tabel), BorderLayout.CENTER);
        JPanel aksi = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        for (JButton tombolAksi : tombol) {
            aksi.add(tombolAksi);
            if (tombol.length > 1) aksi.add(javax.swing.Box.createHorizontalStrut(8));
        }
        bagian.add(aksi, BorderLayout.SOUTH);
        return bagian;
    }

    public void perbaruiTabel() {
        isi(modelMenunggu, manager.getDaftarAntrianFifo(), false);
        isi(modelDiproses, manager.getPesananByStatus(StatusPesanan.DIPROSES), false);
        isi(modelSelesai, manager.getPesananByStatus(StatusPesanan.SELESAI), true);
        kapasitas.setText("Kapasitas Mesin Aktif: " + manager.getJumlahSedangDiproses()
                + " / " + manager.getKapasitasMesin());
    }

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

    private String idTerpilih(JTable tabel) {
        int baris = tabel.getSelectedRow();
        if (baris < 0) throw new IllegalArgumentException("Pilih satu pesanan pada tabel terlebih dahulu.");
        return tabel.getValueAt(baris, 0).toString();
    }

    private void jalankan(Runnable tindakan) {
        try {
            tindakan.run();
            perbaruiTabel();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Tindakan Tidak Dapat Dilakukan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void bukaWhatsapp() {
        try {
            String id = idTerpilih(tabelSelesai);
            Pesanan p = manager.getPesananByStatus(StatusPesanan.SELESAI).stream()
                    .filter(item -> item.getIdPesanan().equals(id)).findFirst()
                    .orElseThrow(() -> new IllegalStateException("Pesanan tidak lagi siap diambil."));
            Notifiable pengirim = whatsApp;
            try {
                pengirim.kirimNotifikasi(p);
                info("WhatsApp dibuka. Periksa pesan, lalu kirim secara manual.");
            } catch (Exception ex) {
                JPanel cadangan = new JPanel(new BorderLayout(0, 8));
                cadangan.add(new JLabel("Browser gagal dibuka. Salin tautan atau pesan berikut:"), BorderLayout.NORTH);
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
