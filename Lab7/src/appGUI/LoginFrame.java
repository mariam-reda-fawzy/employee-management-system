
package appGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import manager.*;

public class LoginFrame extends JFrame {

    static final Color NAVY = new Color(0x1B2A44);
    static final Color BLUE = new Color(0x3E5C7A);
    static final Color PALE = new Color(0xE7CED6);
    static final Color PINK = new Color(0xF7B7C1);
    static final Color ROSE = new Color(0xC95B6B);
    static final Color WHITE = new Color(0xFFFCFD);
    static final Color LIGHT = new Color(0xF8F3F5);
    static final Color BG = new Color(0xF7F3F5);

    private static final String USERNAME_PLACEHOLDER = "Enter Username";
    private static final String PASSWORD_PLACEHOLDER = "**********";

    private EmployeeManager manager;
    private DepartmentManager departmentManager;

    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel(new GridBagLayout());

    JButton loginButton = new JButton("Login");
    JButton exitButton = new JButton("Exit");

    JTextField username = new JTextField(USERNAME_PLACEHOLDER);
    JPasswordField password = new JPasswordField();

    JLabel welcomeLabel = new JLabel("Welcome Back!");
    JLabel welcomeLabel2 = new JLabel("Sign in to your workspace");
    JLabel usernameLabel = new JLabel("Username:");
    JLabel passwordLabel = new JLabel("Password:");

    public LoginFrame(EmployeeManager manager,
                      DepartmentManager departmentManager) {

        this.manager = manager;
        this.departmentManager = departmentManager;

        setTitle("Login Frame");
        setSize(920, 570);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });

        leftPanel.setBackground(NAVY);
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setFocusable(true);

        rightPanel.setBackground(Color.WHITE);

        welcomeLabel.setForeground(NAVY);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 31));
        welcomeLabel.setBorder(new EmptyBorder(0, 50, 0, 0));

        welcomeLabel2.setForeground(BLUE);
        welcomeLabel2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        welcomeLabel2.setBorder(new EmptyBorder(0, 80, 0, 0));

        username.setFont(new Font("SansSerif", Font.PLAIN, 14));
        username.setForeground(Color.GRAY);
        username.setBackground(LIGHT);

        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PALE, 1),
                new EmptyBorder(11, 12, 11, 12)));

        username.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 44));

        setupUsernamePlaceholder();

        password.setFont(new Font("SansSerif", Font.PLAIN, 14));
        password.setForeground(Color.GRAY);
        password.setBackground(LIGHT);

        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PALE, 1),
                new EmptyBorder(11, 12, 11, 12)));

        password.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 44));

        setupPasswordPlaceholder();

        loginButton.setBackground(ROSE);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(300, 45));
        loginButton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 45));

        loginButton.addActionListener(e -> login());

        exitButton.setBackground(NAVY);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        exitButton.setPreferredSize(new Dimension(300, 45));
        exitButton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 45));

        exitButton.addActionListener(e -> confirmExit());

        JPanel rightText = new JPanel();

        rightText.setOpaque(false);
        rightText.setLayout(
                new BoxLayout(rightText, BoxLayout.Y_AXIS));

        rightText.add(welcomeLabel);
        rightText.add(Box.createVerticalStrut(8));

        rightText.add(welcomeLabel2);
        rightText.add(Box.createVerticalStrut(34));

        rightText.add(usernameLabel);
        rightText.add(Box.createVerticalStrut(8));

        rightText.add(username);
        rightText.add(Box.createVerticalStrut(19));

        rightText.add(passwordLabel);
        rightText.add(Box.createVerticalStrut(8));

        rightText.add(password);
        rightText.add(Box.createVerticalStrut(25));

        rightText.add(loginButton);
        rightText.add(Box.createVerticalStrut(8));

        rightText.add(exitButton);
        rightText.add(Box.createVerticalStrut(10));

        rightText.setBorder(
                new EmptyBorder(25, 35, 25, 35));

        JLabel title = new JLabel(
                "<html>Employee<br>Management<br>System</html>");

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 38));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel leftText = new JPanel();

        leftText.setOpaque(false);
        leftText.setLayout(
                new BoxLayout(leftText, BoxLayout.Y_AXIS));

        leftText.add(title);
        leftText.add(Box.createVerticalStrut(27));

        rightPanel.add(rightText);
        leftPanel.add(leftText);

        add(leftPanel);
        add(rightPanel);

        getRootPane().setDefaultButton(loginButton);

        setVisible(true);

        SwingUtilities.invokeLater(() ->
                KeyboardFocusManager.getCurrentKeyboardFocusManager()
                        .clearGlobalFocusOwner());
    }

    private void setupUsernamePlaceholder() {

        username.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (username.getText().equals(USERNAME_PLACEHOLDER)) {
                    username.setText("");
                    username.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (username.getText().trim().isEmpty()) {
                    username.setText(USERNAME_PLACEHOLDER);
                    username.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void setupPasswordPlaceholder() {

        password.setEchoChar((char) 0);
        password.setText(PASSWORD_PLACEHOLDER);

        password.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (String.valueOf(password.getPassword())
                        .equals(PASSWORD_PLACEHOLDER)) {
                    password.setText("");
                    password.setEchoChar('•');
                    password.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (password.getPassword().length == 0) {
                    password.setEchoChar((char) 0);
                    password.setText(PASSWORD_PLACEHOLDER);
                    password.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void confirmExit() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void login() {

        String name = username.getText().trim();
        String pass = new String(password.getPassword());

        if (name.isEmpty()
                || name.equals(USERNAME_PLACEHOLDER)
                || pass.isEmpty()
                || pass.equals(PASSWORD_PLACEHOLDER)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both username and password.",
                    "Missing information",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        if ((name.equals("admin") && pass.equals("admin123"))
                || (name.equals("Heba Kholief") && pass.equals("10061"))
                || (name.equals("Menna Kholief") && pass.equals("10040"))
                || (name.equals("mariamreda") && pass.equals("10032"))
                || (name.equals("Aya Ahmed") && pass.equals("10493"))
                || (name.equals("Tarek") && pass.equals("1234"))
                || (name.equals("Kiro") && pass.equals("1234"))) {

            MainFrame frame = new MainFrame(
                    manager, departmentManager);

            frame.setVisible(true);
            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Incorrect username or password. Please try again.",
                    "Login failed",
                    JOptionPane.ERROR_MESSAGE);

            password.setText("");
            password.requestFocusInWindow();
        }
    }
}
