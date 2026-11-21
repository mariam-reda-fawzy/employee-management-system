package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    public LoginFrame() {
        setTitle("Employee Management System - Login");
        setSize(440, 510);
        setMinimumSize(new Dimension(400, 460));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(Theme.BG);

        JPanel card = Theme.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 34, 30, 34));
        card.setPreferredSize(new Dimension(350, 390));

        JLabel logo = new JLabel("EMS");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        logo.setForeground(Theme.PINK);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Welcome back");
        title.setFont(new Font("Segoe UI", Font.BOLD, 23));
        title.setForeground(Theme.NAVY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Sign in to manage your employees");
        subtitle.setFont(Theme.BODY);
        subtitle.setForeground(Theme.MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        Theme.styleTextField(usernameField);
        Theme.styleTextField(passwordField);

        JButton login = new JButton("LOGIN");
        Theme.styleButton(login, Theme.PINK);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JLabel userLabel = fieldLabel("Username");
        JLabel passLabel = fieldLabel("Password");

        card.add(logo);
        card.add(Box.createVerticalStrut(8));
        card.add(title);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(28));
        card.add(userLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(16));
        card.add(passLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(24));
        card.add(login);

        root.add(card);
        setContentPane(root);

        login.addActionListener(e -> login());
        passwordField.addActionListener(e -> login());
    }

    private JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(Theme.TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Demonstration credentials specified in the Lab 7 handout.
        if (username.equals("admin") && password.equals("admin123")) {
            dispose();
            new MainFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Incorrect username or password.",
                    "Login failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
