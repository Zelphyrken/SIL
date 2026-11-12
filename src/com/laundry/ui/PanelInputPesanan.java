package com.laundry.ui;

import com.laundry.model.CuciKering;
import com.laundry.model.CuciSetrika;
import com.laundry.model.Layanan;
import com.laundry.model.Pesanan;
import com.laundry.service.LaundryManager;
import com.laundry.util.FormatUtil;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.EmptyBorder;

/** Form penerimaan pesanan dan estimasi biaya sebelum data disimpan. */
public class PanelInputPesanan extends JPanel {
    private final LaundryManager manager;
    private Runnable setelahSimpan;
    private final JTextField nama = new JTextField(24);
    private final JTextField noWhatsapp = new JTextField(24);
    private final JComboBox<Layanan> layanan = new JComboBox<>(new Layanan[] { new CuciKering(), new CuciSetrika() });
    private final JTextField berat = new JTextField(24);
    private final JLabel estimasi = UiStyle.label("Rp0", 24, Font.BOLD, UiStyle.BLUE_DARK);

    /** Callback memberi tahu MainFrame agar membuka monitoring setelah penyimpanan berhasil. */
    public PanelInputPesanan(LaundryManager manager, Runnable setelahSimpan) {
        super(new BorderLayout());
        this.manager = manager;
        this.setelahSimpan = setelahSimpan;
        setBackground(UiStyle.BACKGROUND);
        setBorder(new EmptyBorder(28, 30, 24, 30));

        JPanel kepala = new JPanel();
        kepala.setOpaque(false);
        kepala.setLayout(new BoxLayout(kepala, BoxLayout.Y_AXIS));
        kepala.add(UiStyle.label("Terima Pesanan Baru", 25, Font.BOLD, UiStyle.TEXT));
        kepala.add(Box.createVerticalStrut(5));
        kepala.add(UiStyle.label("Catat Pelanggan dan Detail Cucian dalam Satu Langkah.", 13, Font.PLAIN, UiStyle.MUTED));
        kepala.add(Box.createVerticalStrut(22));
        add(kepala, BorderLayout.NORTH);

        // Form dipusatkan dalam card agar lebar field tetap nyaman pada jendela besar.
        JPanel kartu = UiStyle.card();
        kartu.setLayout(new GridBagLayout());
        kartu.setBorder(new EmptyBorder(24, 28, 26, 28));
        kartu.setPreferredSize(new Dimension(550, 508));
        kartu.setMaximumSize(new Dimension(550, 508));
        UiStyle.field(nama);
        UiStyle.field(noWhatsapp);
        UiStyle.field(berat);
        UiStyle.combo(layanan);
        baris(kartu, 0, "Nama Pelanggan", nama);
        baris(kartu, 1, "Nomor WhatsApp", noWhatsapp);
        baris(kartu, 2, "Jenis Layanan Laundry", layanan);
        baris(kartu, 3, "Berat Cucian (kg)", berat);

        JPanel ringkasan = new JPanel(new BorderLayout());
        ringkasan.setBackground(UiStyle.BLUE_PALE);
        ringkasan.setBorder(new EmptyBorder(12, 16, 12, 16));
        JPanel teksRingkasan = new JPanel();
        teksRingkasan.setOpaque(false);
        teksRingkasan.setLayout(new BoxLayout(teksRingkasan, BoxLayout.Y_AXIS));
        teksRingkasan.add(UiStyle.label("Estimasi Total Biaya", 11, Font.BOLD, UiStyle.MUTED));
        teksRingkasan.add(Box.createVerticalStrut(3));
        teksRingkasan.add(estimasi);
        ringkasan.add(teksRingkasan, BorderLayout.CENTER);
        GridBagConstraints c = posisi(4, 10);
        c.insets = new Insets(13, 0, 15, 0);
        kartu.add(ringkasan, c);

        JButton simpan = UiStyle.button("Terima & Masukkan Antrean", true);
        simpan.setPreferredSize(new Dimension(240, 42));
        c = posisi(5, 10);
        kartu.add(simpan, c);

        JPanel pembungkus = new JPanel(new GridBagLayout());
        pembungkus.setOpaque(false);
        GridBagConstraints outer = new GridBagConstraints();
        outer.gridx = 0;
        outer.gridy = 0;
        outer.weightx = 1;
        outer.weighty = 1;
        outer.anchor = GridBagConstraints.NORTH;
        outer.fill = GridBagConstraints.NONE;
        outer.anchor = GridBagConstraints.NORTHWEST;
        pembungkus.add(kartu, outer);
        JScrollPane scroll = UiStyle.scroll(pembungkus);
        scroll.getViewport().setBackground(UiStyle.BACKGROUND);
        add(scroll, BorderLayout.CENTER);

        JLabel catatan = UiStyle.label("Data Pesanan Hanya Tersimpan Selama Aplikasi Berjalan.", 12, Font.PLAIN, UiStyle.MUTED);
        catatan.setBorder(new EmptyBorder(14, 0, 0, 0));
        add(catatan, BorderLayout.SOUTH);

        // Estimasi langsung berubah saat layanan atau berat diganti, tanpa menyimpan pesanan.
        layanan.addActionListener(e -> perbaruiEstimasi());
        berat.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { perbaruiEstimasi(); }
            public void removeUpdate(DocumentEvent e) { perbaruiEstimasi(); }
            public void changedUpdate(DocumentEvent e) { perbaruiEstimasi(); }
        });
        simpan.addActionListener(e -> simpanPesanan());
    }

    /** Memasang aksi navigasi akhir setelah semua tombol MainFrame selesai dibuat. */
    public void setSetelahSimpan(Runnable setelahSimpan) { this.setelahSimpan = setelahSimpan; }

    /** Menempatkan label di atas field pada satu baris formulir. */
    private void baris(JPanel panel, int baris, String label, java.awt.Component field) {
        GridBagConstraints c = posisi(baris, 10);
        c.insets = new Insets(baris == 0 ? 0 : 10, 0, 5, 0);
        panel.add(UiStyle.label(label, 13, Font.BOLD, UiStyle.TEXT), c);
        c = posisi(baris, 11);
        c.insets = new Insets(0, 0, 0, 0);
        panel.add(field, c);
    }

    /** Menjaga susunan label dan field konsisten tanpa koordinat absolut. */
    private GridBagConstraints posisi(int baris, int kolom) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = baris * 2 + (kolom == 11 ? 1 : 0);
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        return c;
    }

    /** Menerima koma atau titik desimal, lalu menolak angka kosong, salah format, dan nonpositif. */
    private double bacaBerat() {
        String teks = berat.getText().trim();
        if (teks.isBlank()) throw new IllegalArgumentException("Berat cucian wajib diisi.");
        if (!teks.matches("[0-9]+([,.][0-9]+)?")) {
            throw new IllegalArgumentException("Berat harus berupa angka, misalnya 2,5.");
        }
        try {
            double nilai = Double.parseDouble(teks.replace(',', '.'));
            if (!Double.isFinite(nilai) || nilai <= 0) throw new IllegalArgumentException("Berat harus lebih dari 0 kg.");
            return nilai;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Berat harus berupa angka, misalnya 2,5.");
        }
    }

    /** Input yang belum valid hanya menampilkan Rp0; peringatan muncul saat tombol simpan ditekan. */
    private void perbaruiEstimasi() {
        try {
            Layanan pilihan = (Layanan) layanan.getSelectedItem();
            double biaya = pilihan.hitungTotalBiaya(bacaBerat());
            estimasi.setText(Double.isFinite(biaya) && biaya <= Long.MAX_VALUE
                    ? FormatUtil.rupiah(biaya) : "Rp0");
        } catch (IllegalArgumentException ex) {
            estimasi.setText("Rp0");
        }
    }

    /** Menyerahkan validasi akhir ke manager, lalu membersihkan form jika berhasil. */
    private void simpanPesanan() {
        try {
            Pesanan pesanan = manager.tambahPesanan(nama.getText(), noWhatsapp.getText(),
                    (Layanan) layanan.getSelectedItem(), bacaBerat());
            nama.setText("");
            noWhatsapp.setText("");
            berat.setText("");
            layanan.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this, "Pesanan " + pesanan.getIdPesanan() + " Berhasil Masuk Antrean.",
                    "Pesanan Tercatat", JOptionPane.INFORMATION_MESSAGE);
            setelahSimpan.run();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
        }
    }
}
