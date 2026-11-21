package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public final class Theme {
    private Theme() {}

    public static final Color BG = new Color(246, 247, 251);
    public static final Color WHITE = Color.WHITE;
    public static final Color SIDEBAR = new Color(255, 255, 255);
    public static final Color PINK = new Color(218, 70, 145);
    public static final Color PURPLE = new Color(119, 79, 183);
    public static final Color CYAN = new Color(70, 184, 211);
    public static final Color ORANGE = new Color(245, 174, 54);
    public static final Color NAVY = new Color(47, 57, 77);
    public static final Color TEXT = new Color(48, 55, 72);
    public static final Color MUTED = new Color(143, 149, 163);
    public static final Color BORDER = new Color(232, 234, 241);
    public static final Font TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font BODY = new Font("Segoe UI", Font.PLAIN, 13);

    public static void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
    }

    public static void styleTextField(JTextField field) {
        field.setFont(BODY);
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(9, 10, 9, 10)));
    }

    public static JPanel card() {
        JPanel panel = new JPanel();
        panel.setBackground(WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(16, 16, 16, 16)));
        return panel;
    }

    public static JLabel pageTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE);
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel muted(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY);
        label.setForeground(MUTED);
        return label;
    }
}
