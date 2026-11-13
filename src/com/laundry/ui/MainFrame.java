package com.laundry.ui;

import com.laundry.service.LaundryManager;
import javax.swing.*;

/** Kedua tab memakai satu manager agar perubahan pesanan langsung terlihat. */
public class MainFrame extends JFrame {
    private final LaundryManager manager = new LaundryManager();

    public MainFrame() {
        super("Sistem Informasi Laundry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JTabbedPane tab = new JTabbedPane();
        PanelMonitoringPesanan panelMonitoring = new PanelMonitoringPesanan(manager);
        PanelInputPesanan panelInput = new PanelInputPesanan(manager, () -> {
            panelMonitoring.perbaruiTabel();
            tab.setSelectedIndex(1);
        });
        tab.addTab("Input Pesanan", panelInput);
        tab.addTab("Monitoring Pesanan", panelMonitoring);
        tab.addChangeListener(e -> {
            if (tab.getSelectedIndex() == 1) panelMonitoring.perbaruiTabel();
        });
        tab.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                if (tab.indexAtLocation(e.getX(), e.getY()) == 1) panelMonitoring.perbaruiTabel();
            }
        });
        add(tab);
    }
}
