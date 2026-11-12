package com.laundry;

import com.laundry.ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) { /* Tampilan standar Swing tetap dapat digunakan. */ }
            new MainFrame().setVisible(true);
        });
    }
}
