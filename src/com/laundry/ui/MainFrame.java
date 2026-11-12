package com.laundry.ui;

import com.laundry.service.LaundryManager;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/** Jendela utama yang membagikan satu LaundryManager kepada kedua halaman. */
public class MainFrame extends JFrame {
    private final LaundryManager manager = new LaundryManager();

    /** Menyusun navigasi samping dan area halaman tanpa membuat manager baru per halaman. */
    public MainFrame() {
        super("Sistem Informasi Laundry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 740);
        setMinimumSize(new Dimension(860, 620));
        setLocationRelativeTo(null);

        // CardLayout menampilkan satu halaman; kedua panel tetap memakai data manager yang sama.
        CardLayout layout = new CardLayout();
        JPanel halaman = new JPanel(layout);
        halaman.setBackground(UiStyle.BACKGROUND);
        PanelMonitoringPesanan monitoring = new PanelMonitoringPesanan(manager);
        PanelInputPesanan input = new PanelInputPesanan(manager, () -> {
            monitoring.perbaruiTabel();
            layout.show(halaman, "monitoring");
        });
        halaman.add(input, "input");
        halaman.add(monitoring, "monitoring");

        JPanel navigasi = new JPanel();
        navigasi.setLayout(new BoxLayout(navigasi, BoxLayout.Y_AXIS));
        navigasi.setBackground(UiStyle.WHITE);
        navigasi.setBorder(new EmptyBorder(26, 18, 20, 18));
        navigasi.setPreferredSize(new Dimension(220, 0));

        JLabel identitas = UiStyle.label("Laundry", 26, Font.BOLD, UiStyle.BLUE_DARK);
        identitas.setAlignmentX(LEFT_ALIGNMENT);
        navigasi.add(identitas);
        JLabel keterangan = UiStyle.label("Sistem Informasi Laundry", 12, Font.PLAIN, UiStyle.MUTED);
        keterangan.setAlignmentX(LEFT_ALIGNMENT);
        navigasi.add(Box.createVerticalStrut(4));
        navigasi.add(keterangan);
        navigasi.add(Box.createVerticalStrut(42));
        JLabel menu = UiStyle.label("Menu Utama", 11, Font.BOLD, UiStyle.MUTED);
        menu.setAlignmentX(LEFT_ALIGNMENT);
        navigasi.add(menu);
        navigasi.add(Box.createVerticalStrut(12));

        JButton tabInput = UiStyle.subtleButton("Terima Pesanan", UiStyle.BLUE_PALE,
                UiStyle.BLUE_DARK, new Color(0xDBEAFE));
        JButton tabMonitoring = UiStyle.subtleButton("Antrean & Proses", UiStyle.WHITE,
                UiStyle.TEXT, UiStyle.BACKGROUND);
        for (JButton tombol : new JButton[] { tabInput, tabMonitoring }) {
            tombol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            tombol.setPreferredSize(new Dimension(180, 42));
            tombol.setHorizontalAlignment(JButton.LEFT);
            tombol.setAlignmentX(LEFT_ALIGNMENT);
        }
        navigasi.add(tabInput);
        navigasi.add(Box.createVerticalStrut(6));
        navigasi.add(tabMonitoring);
        navigasi.add(Box.createVerticalGlue());
        JLabel lokal = UiStyle.label("Aplikasi Lokal • Data Sementara", 11, Font.PLAIN, UiStyle.MUTED);
        lokal.setAlignmentX(LEFT_ALIGNMENT);
        navigasi.add(lokal);

        // Setiap perpindahan halaman juga mengubah penanda menu yang sedang aktif.
        Runnable pilihInput = () -> {
            layout.show(halaman, "input");
            tabInput.setBackground(UiStyle.BLUE_PALE);
            tabInput.setForeground(UiStyle.BLUE_DARK);
            tabMonitoring.setBackground(UiStyle.WHITE);
            tabMonitoring.setForeground(UiStyle.TEXT);
        };
        // Data tabel diambil ulang saat halaman monitoring dibuka.
        Runnable pilihMonitoring = () -> {
            monitoring.perbaruiTabel();
            layout.show(halaman, "monitoring");
            tabMonitoring.setBackground(UiStyle.BLUE_PALE);
            tabMonitoring.setForeground(UiStyle.BLUE_DARK);
            tabInput.setBackground(UiStyle.WHITE);
            tabInput.setForeground(UiStyle.TEXT);
        };
        tabInput.addActionListener(e -> pilihInput.run());
        tabMonitoring.addActionListener(e -> pilihMonitoring.run());

        // Simpan pesanan berpindah ke halaman monitoring melalui callback panel input.
        input.setSetelahSimpan(pilihMonitoring);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UiStyle.BACKGROUND);
        root.add(navigasi, BorderLayout.WEST);
        root.add(halaman, BorderLayout.CENTER);
        setContentPane(root);
    }
}
