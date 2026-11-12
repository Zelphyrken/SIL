package com.laundry.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/** Gaya Swing bersama agar warna, tipografi, dan komponen konsisten di kedua halaman. */
final class UiStyle {
    // Palet terpusat: perubahan warna di sini berlaku untuk seluruh antarmuka.
    static final Color BACKGROUND = new Color(0xF5F6F8);
    static final Color WHITE = Color.WHITE;
    static final Color TEXT = new Color(0x1F2937);
    static final Color MUTED = new Color(0x6B7280);
    static final Color BLUE = new Color(0x3B82F6);
    static final Color BLUE_DARK = new Color(0x2563EB);
    static final Color BLUE_PALE = new Color(0xEFF6FF);
    static final Color LINE = new Color(0xE5E7EB);
    static final Color GREEN = new Color(0x15803D);
    static final Color GREEN_PALE = new Color(0xF0FDF4);
    static final Color AMBER = new Color(0xB45309);
    static final Color AMBER_PALE = new Color(0xFFFBEB);

    private UiStyle() { }

    /** Memakai Segoe UI bila tersedia; Java memilih font pengganti pada sistem lain. */
    static Font font(int size, int weight) {
        return new Font("Segoe UI", weight, size);
    }

    static JLabel label(String text, int size, int weight, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font(size, weight));
        label.setForeground(color);
        return label;
    }

    /** Panel putih bersudut membulat untuk mengelompokkan informasi. */
    static JPanel card() {
        JPanel panel = new RoundedPanel(18, WHITE, LINE);
        panel.setOpaque(false);
        return panel;
    }

    /** Tombol utama berwarna biru, sedangkan tindakan pendukung berwarna putih. */
    static JButton button(String text, boolean primary) {
        return new RoundedButton(text, primary ? BLUE : WHITE,
                primary ? WHITE : TEXT, primary ? BLUE_DARK : BLUE_PALE);
    }

    static JButton subtleButton(String text, Color background, Color foreground, Color hover) {
        return new RoundedButton(text, background, foreground, hover);
    }

    /** Memberi ukuran, padding, dan garis fokus yang sama pada seluruh input teks. */
    static void field(JTextField field) {
        field.setFont(font(14, Font.PLAIN));
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setCaretColor(BLUE_DARK);
        field.setPreferredSize(new Dimension(340, 42));
        field.setBorder(new RoundedBorder(field));
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { field.repaint(); }
            @Override public void focusLost(FocusEvent e) { field.repaint(); }
        });
    }

    /** Menyamakan ukuran dan garis fokus pilihan layanan dengan input teks. */
    static void combo(JComboBox<?> combo) {
        combo.setFont(font(14, Font.PLAIN));
        combo.setForeground(TEXT);
        combo.setBackground(WHITE);
        combo.setPreferredSize(new Dimension(340, 42));
        combo.setBorder(new RoundedBorder(combo));
        combo.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { combo.repaint(); }
            @Override public void focusLost(FocusEvent e) { combo.repaint(); }
        });
    }

    /** Menyiapkan tabel dengan header jelas, baris lega, seleksi lembut, dan empty state. */
    static JTable table(TableModel model) {
        JTable table = new JTable(model) {
            // Pesan kosong dilukis di area data tanpa menghilangkan header tabel.
            @Override protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                if (getRowCount() == 0) {
                    Graphics2D g = (Graphics2D) graphics.create();
                    g.setColor(MUTED);
                    g.setFont(font(13, Font.PLAIN));
                    String message = "Belum Ada Pesanan pada Bagian Ini";
                    int x = Math.max(14, (getWidth() - g.getFontMetrics().stringWidth(message)) / 2);
                    int y = Math.max(25, (getHeight() + g.getFontMetrics().getAscent()) / 2);
                    g.drawString(message, x, y);
                    g.dispose();
                }
            }
        };
        table.setFont(font(13, Font.PLAIN));
        table.setForeground(TEXT);
        table.setBackground(WHITE);
        table.setSelectionBackground(BLUE_PALE);
        table.setSelectionForeground(BLUE_DARK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(34);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(0xEEF0F3));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 36));
        header.setBackground(new Color(0xF8FAFC));
        header.setForeground(MUTED);
        header.setFont(font(12, Font.BOLD));
        header.setBorder(new EmptyBorder(0, 0, 1, 0));
        DefaultTableCellRenderer headRenderer = new DefaultTableCellRenderer();
        headRenderer.setBackground(new Color(0xF8FAFC));
        headRenderer.setForeground(MUTED);
        headRenderer.setFont(font(12, Font.BOLD));
        headRenderer.setBorder(new EmptyBorder(0, 12, 0, 8));
        header.setDefaultRenderer(headRenderer);

        // Renderer mengubah warna seleksi dan menegaskan ID tanpa mengubah isi data.
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean selected, boolean focused, int row, int column) {
                super.getTableCellRendererComponent(t, value, selected, focused, row, column);
                setForeground(selected ? BLUE_DARK : TEXT);
                setBackground(selected ? BLUE_PALE : WHITE);
                setBorder(new EmptyBorder(0, 12, 0, 8));
                setFont(font(13, column == 0 ? Font.BOLD : Font.PLAIN));
                return this;
            }
        };
        table.setDefaultRenderer(Object.class, cellRenderer);
        return table;
    }

    /** Membuat area gulir tanpa border bawaan yang bertabrakan dengan card. */
    static JScrollPane scroll(JComponent component) {
        JScrollPane pane = new JScrollPane(component);
        pane.setBorder(null);
        pane.getViewport().setBackground(WHITE);
        pane.getVerticalScrollBar().setUnitIncrement(16);
        return pane;
    }

    /** Melukis latar dan garis card dengan anti aliasing agar sudut halus. */
    private static final class RoundedPanel extends JPanel {
        private final int radius;
        private final Color fill;
        private final Color line;

        RoundedPanel(int radius, Color fill, Color line) {
            this.radius = radius;
            this.fill = fill;
            this.line = line;
        }

        /** Menggambar card sebelum anak komponen dilukis oleh Swing. */
        @Override protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(fill);
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g.setColor(line);
            g.setStroke(new BasicStroke(1f));
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    /** Tombol kustom dengan kondisi normal, hover, dan pressed. */
    private static final class RoundedButton extends JButton {
        private final Color hover;
        private boolean hovered;

        RoundedButton(String text, Color normal, Color foreground, Color hover) {
            super(text);
            this.hover = hover;
            setBackground(normal);
            setForeground(foreground);
            setFont(font(13, Font.BOLD));
            setBorder(new EmptyBorder(0, 16, 0, 16));
            int buttonWidth = Math.max(100, getFontMetrics(getFont()).stringWidth(text) + 32);
            setPreferredSize(new Dimension(buttonWidth, 36));
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                @Override public void mouseExited(MouseEvent e) { hovered = false; repaint(); }
            });
        }

        /** Warna dasar dibaca dari komponen agar penanda menu aktif bisa berubah. */
        @Override protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color base = getBackground();
            Color hoverColor = base.equals(BLUE_PALE) ? new Color(0xDBEAFE) : hover;
            g.setColor(getModel().isPressed() ? hoverColor.darker() : hovered ? hoverColor : base);
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            if (base.equals(WHITE)) {
                g.setColor(LINE);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
            }
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    /** Border input yang berubah biru saat komponen mendapat fokus. */
    private static final class RoundedBorder extends AbstractBorder {
        private final JComponent component;

        RoundedBorder(JComponent component) { this.component = component; }

        @Override public Insets getBorderInsets(Component c) { return new Insets(8, 12, 8, 12); }

        /** Menggambar garis halus tanpa mengubah mekanisme input Swing. */
        @Override public void paintBorder(Component c, Graphics graphics, int x, int y, int width, int height) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(component.isFocusOwner() ? BLUE : LINE);
            g.setStroke(new BasicStroke(component.isFocusOwner() ? 1.5f : 1f));
            g.drawRoundRect(x, y, width - 1, height - 1, 10, 10);
            g.dispose();
        }
    }
}
