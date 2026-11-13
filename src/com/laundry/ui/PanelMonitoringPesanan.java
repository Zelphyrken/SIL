package com.laundry.ui;

import com.laundry.model.Pesanan;
import com.laundry.model.StatusPesanan;
import com.laundry.service.LaundryManager;
import com.laundry.util.FormatUtil;
import com.laundry.util.Notifiable;
import com.laundry.util.WhatsAppHelper;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/** Menampilkan antrean, pekerjaan aktif, dan pesanan siap diambil. */
public class PanelMonitoringPesanan extends JPanel {
    private final LaundryManager manager;
    private final WhatsAppHelper whatsApp = new WhatsAppHelper();
    private final JLabel ringkasan = new JLabel();
    private final DefaultTableModel modelMenunggu = model("ID", "Pelanggan", "Layanan", "Berat", "Total Biaya", "Waktu Masuk");
    private final DefaultTableModel modelDiproses = model("ID", "Pelanggan", "Layanan", "Berat", "Total Biaya", "Status");
    private final DefaultTableModel modelSelesai = model("ID", "Pelanggan", "No. WhatsApp", "Layanan", "Total Biaya", "Status");
    private final JTable tabelDiproses = new JTable(modelDiproses);
    private final JTable tabelSelesai = new JTable(modelSelesai);

    public PanelMonitoringPesanan(LaundryManager manager) {
        super(new BorderLayout());
        this.manager = manager;
        add(ringkasan, BorderLayout.NORTH);

        JPanel daftar = new JPanel(new GridLayout(3, 1, 8, 8));
        JButton proses = new JButton("Proses Antrean Terdepan");
        proses.addActionListener(e -> jalankan(() -> manager.prosesAntrianBerikutnya()));
        daftar.add(bagian("Antrean Menunggu", new JTable(modelMenunggu), proses));

        JButton selesai = new JButton("Tandai Selesai");
        selesai.addActionListener(e -> jalankan(() -> manager.tandaiSelesai(idTerpilih(tabelDiproses))));
        daftar.add(bagian("Sedang Diproses", tabelDiproses, selesai));

        JButton notifikasi = new JButton("Siapkan / Buka WhatsApp");
        notifikasi.addActionListener(e -> bukaWhatsapp());
        JButton diambil = new JButton("Konfirmasi Sudah Diambil");
        diambil.addActionListener(e -> jalankan(() -> manager.konfirmasiDiambil(idTerpilih(tabelSelesai))));
        daftar.add(bagian("Siap Diambil", tabelSelesai, notifikasi, diambil));
        add(daftar, BorderLayout.CENTER);
        perbaruiTabel();
    }

    private static DefaultTableModel model(String... kolom) {
        return new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int baris, int kolom) { return false; }
        };
    }

    private JPanel bagian(String judul, JTable tabel, JButton... tombol) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(judul));
        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);
        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (JButton item : tombol) aksi.add(item);
        panel.add(aksi, BorderLayout.SOUTH);
        return panel;
    }

    public void perbaruiTabel() {
        modelMenunggu.setRowCount(0);
        modelDiproses.setRowCount(0);
        modelSelesai.setRowCount(0);
        var menunggu = manager.getDaftarAntrianFifo();
        var diproses = manager.getPesananByStatus(StatusPesanan.DIPROSES);
        var selesai = manager.getPesananByStatus(StatusPesanan.SELESAI);
        for (Pesanan p : menunggu) {
            modelMenunggu.addRow(new Object[] { p.getIdPesanan(), p.getPelanggan().getNama(),
                    p.getLayanan().getNamaLayanan(), FormatUtil.berat(p.getBeratKg()),
                    FormatUtil.rupiah(p.getTotalBiaya()), p.getWaktuMasukTeks() });
        }
        for (Pesanan p : diproses) {
            modelDiproses.addRow(new Object[] { p.getIdPesanan(), p.getPelanggan().getNama(),
                    p.getLayanan().getNamaLayanan(), FormatUtil.berat(p.getBeratKg()),
                    FormatUtil.rupiah(p.getTotalBiaya()), p.getStatus() });
        }
        for (Pesanan p : selesai) {
            modelSelesai.addRow(new Object[] { p.getIdPesanan(), p.getPelanggan().getNama(),
                    p.getPelanggan().getNoWhatsapp(), p.getLayanan().getNamaLayanan(),
                    FormatUtil.rupiah(p.getTotalBiaya()), p.getStatus() });
        }
        ringkasan.setText("Menunggu: " + menunggu.size() + "   |   Diproses: "
                + diproses.size() + " / " + manager.getKapasitasMesin()
                + "   |   Siap diambil: " + selesai.size());
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
            Pesanan pesanan = manager.getPesananByStatus(StatusPesanan.SELESAI).stream()
                    .filter(p -> p.getIdPesanan().equals(id)).findFirst()
                    .orElseThrow(() -> new IllegalStateException("Pesanan tidak lagi siap diambil."));
            Notifiable pengirim = whatsApp;
            try {
                pengirim.kirimNotifikasi(pesanan);
                JOptionPane.showMessageDialog(this, "WhatsApp dibuka. Periksa pesan lalu kirim secara manual.",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JTextArea cadangan = new JTextArea(whatsApp.buatTautan(pesanan) + "\n\n"
                        + whatsApp.buatPesanNotifikasi(pesanan), 8, 55);
                cadangan.setEditable(false);
                cadangan.setLineWrap(true);
                cadangan.setWrapStyleWord(true);
                JOptionPane.showMessageDialog(this, new JScrollPane(cadangan),
                        "Salin Tautan atau Pesan WhatsApp", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Pilih Pesanan", JOptionPane.WARNING_MESSAGE);
        }
    }
}
