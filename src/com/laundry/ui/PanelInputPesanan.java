package com.laundry.ui;

import com.laundry.model.CuciKering;
import com.laundry.model.CuciSetrika;
import com.laundry.model.Layanan;
import com.laundry.model.Pesanan;
import com.laundry.service.LaundryManager;
import com.laundry.util.FormatUtil;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.EmptyBorder;

public class PanelInputPesanan extends JPanel {
    private final LaundryManager manager;
    private final Runnable setelahSimpan;
    private final JTextField nama = new JTextField(24);
    private final JTextField noWhatsapp = new JTextField(24);
    private final JComboBox<Layanan> layanan = new JComboBox<>(new Layanan[] { new CuciKering(), new CuciSetrika() });
    private final JTextField berat = new JTextField(24);
    private final JLabel estimasi = new JLabel("Estimasi Total: Rp0");

    public PanelInputPesanan(LaundryManager manager, Runnable setelahSimpan) {
        super(new BorderLayout());
        this.manager = manager;
        this.setelahSimpan = setelahSimpan;
        setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel judul = new JLabel("PENERIMAAN PESANAN LAUNDRY");
        judul.setFont(judul.getFont().deriveFont(Font.BOLD, 20f));
        add(judul, BorderLayout.NORTH);

        JPanel formulir = new JPanel(new GridBagLayout());
        formulir.setBorder(new EmptyBorder(20, 0, 0, 0));
        tambahBaris(formulir, 0, "Nama Pelanggan", nama);
        tambahBaris(formulir, 1, "Nomor WhatsApp", noWhatsapp);
        tambahBaris(formulir, 2, "Paket Layanan", layanan);
        tambahBaris(formulir, 3, "Berat Cucian (kg)", berat);
        estimasi.setFont(estimasi.getFont().deriveFont(Font.BOLD, 17f));
        GridBagConstraints c = batas(4, 1);
        formulir.add(estimasi, c);
        JButton simpan = new JButton("Terima & Masukkan Antrean");
        c = batas(5, 1);
        c.insets = new Insets(20, 8, 8, 8);
        formulir.add(simpan, c);
        add(formulir, BorderLayout.CENTER);

        layanan.addActionListener(e -> perbaruiEstimasi());
        berat.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { perbaruiEstimasi(); }
            public void removeUpdate(DocumentEvent e) { perbaruiEstimasi(); }
            public void changedUpdate(DocumentEvent e) { perbaruiEstimasi(); }
        });
        simpan.addActionListener(e -> simpanPesanan());
    }

    private void tambahBaris(JPanel panel, int baris, String label, java.awt.Component input) {
        panel.add(new JLabel(label + ":"), batas(baris, 0));
        panel.add(input, batas(baris, 1));
    }

    private GridBagConstraints batas(int baris, int kolom) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = kolom;
        c.gridy = baris;
        c.anchor = GridBagConstraints.WEST;
        c.fill = kolom == 1 ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE;
        c.insets = new Insets(8, 8, 8, 8);
        return c;
    }

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

    private void perbaruiEstimasi() {
        try {
            Layanan pilihan = (Layanan) layanan.getSelectedItem();
            double biaya = pilihan.hitungTotalBiaya(bacaBerat());
            estimasi.setText("Estimasi Total: " + (Double.isFinite(biaya) && biaya <= Long.MAX_VALUE
                    ? FormatUtil.rupiah(biaya) : "Rp0"));
        } catch (IllegalArgumentException ex) {
            estimasi.setText("Estimasi Total: Rp0");
        }
    }

    private void simpanPesanan() {
        try {
            Pesanan pesanan = manager.tambahPesanan(nama.getText(), noWhatsapp.getText(),
                    (Layanan) layanan.getSelectedItem(), bacaBerat());
            nama.setText("");
            noWhatsapp.setText("");
            berat.setText("");
            layanan.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this, "Pesanan " + pesanan.getIdPesanan() + " berhasil masuk antrean.",
                    "Pesanan Tercatat", JOptionPane.INFORMATION_MESSAGE);
            setelahSimpan.run();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
        }
    }
}
