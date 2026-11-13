package com.laundry.ui;

import com.laundry.model.CuciKering;
import com.laundry.model.CuciSetrika;
import com.laundry.model.Layanan;
import com.laundry.model.Pesanan;
import com.laundry.service.LaundryManager;
import com.laundry.util.FormatUtil;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/** Form penerimaan pesanan dengan estimasi biaya saat input berubah. */
public class PanelInputPesanan extends JPanel {
    private final LaundryManager manager;
    private Runnable setelahSimpan;
    private final JTextField nama = new JTextField();
    private final JTextField noWhatsapp = new JTextField();
    private final JComboBox<Layanan> layanan = new JComboBox<>(new Layanan[] { new CuciKering(), new CuciSetrika() });
    private final JTextField berat = new JTextField();
    private final JLabel estimasi = new JLabel(FormatUtil.rupiah(0));

    public PanelInputPesanan(LaundryManager manager, Runnable setelahSimpan) {
        super(new BorderLayout());
        this.manager = manager;
        this.setelahSimpan = setelahSimpan;

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Pesanan Baru"));
        form.add(new JLabel("Nama Pelanggan"));
        form.add(nama);
        form.add(new JLabel("Nomor WhatsApp"));
        form.add(noWhatsapp);
        form.add(new JLabel("Jenis Layanan"));
        form.add(layanan);
        form.add(new JLabel("Berat Cucian (Kg)"));
        form.add(berat);
        form.add(new JLabel("Estimasi Biaya"));
        form.add(estimasi);
        add(form, BorderLayout.CENTER);

        JButton simpan = new JButton("Terima dan Masukkan Antrean");
        simpan.addActionListener(e -> simpanPesanan());
        add(simpan, BorderLayout.SOUTH);

        layanan.addActionListener(e -> perbaruiEstimasi());
        berat.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { perbaruiEstimasi(); }
            public void removeUpdate(DocumentEvent e) { perbaruiEstimasi(); }
            public void changedUpdate(DocumentEvent e) { perbaruiEstimasi(); }
        });
    }

    public void setSetelahSimpan(Runnable setelahSimpan) { this.setelahSimpan = setelahSimpan; }

    /** Menerima koma atau titik desimal dan menolak berat nonpositif. */
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
        double biaya = 0;
        try {
            Layanan pilihan = (Layanan) layanan.getSelectedItem();
            if (pilihan != null) biaya = pilihan.hitungTotalBiaya(bacaBerat());
            if (!Double.isFinite(biaya) || biaya > Long.MAX_VALUE) biaya = 0;
        } catch (IllegalArgumentException ex) {
            biaya = 0;
        }
        estimasi.setText(FormatUtil.rupiah(biaya));
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
            if (setelahSimpan != null) setelahSimpan.run();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
        }
    }
}
