package com.laundry.ui;

import com.laundry.service.LaundryManager;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
    private final LaundryManager manager = new LaundryManager();

    public MainFrame() {
        super("Sistem Informasi Laundry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 720);
        setMinimumSize(new java.awt.Dimension(760, 560));
        setLocationRelativeTo(null);

        JTabbedPane tab = new JTabbedPane();
        PanelMonitoringPesanan monitoring = new PanelMonitoringPesanan(manager);
        PanelInputPesanan input = new PanelInputPesanan(manager, () -> {
            monitoring.perbaruiTabel();
            tab.setSelectedIndex(1);
        });
        tab.addTab("Terima Pesanan Baru", input);
        tab.addTab("Antrean & Proses Cucian", monitoring);
        tab.addChangeListener(e -> {
            if (tab.getSelectedIndex() == 1) monitoring.perbaruiTabel();
        });

        JPanel content = new JPanel(new BorderLayout());
        content.add(tab, BorderLayout.CENTER);
        JLabel catatan = new JLabel("Data hanya tersimpan selama aplikasi berjalan. Notifikasi WhatsApp dikirim manual oleh kasir.");
        catatan.setBorder(new EmptyBorder(6, 12, 8, 12));
        content.add(catatan, BorderLayout.SOUTH);
        setContentPane(content);
    }
}
