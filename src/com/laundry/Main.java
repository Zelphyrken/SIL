package com.laundry;

import com.laundry.ui.MainFrame;
import javax.swing.SwingUtilities;

/** Titik masuk aplikasi desktop. */
public class Main {
    /** Swing dijalankan di Event Dispatch Thread agar pembaruan komponen aman. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
