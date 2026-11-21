package appGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

    private EmployeeManager manager;
    private DepartmentManager departmentManager;

    JPanel leftPanel =  new JPanel();
    JPanel rightPanel =  new JPanel(new GridBagLayout());
    JButton loginButton = new JButton("Login");
    JButton exitButton = new JButton("Exit");
    JTextField username = new JTextField("Enter Username");
    JPasswordField password = new JPasswordField("Password");
    JLabel welcomeLabel = new JLabel("Welcome Back!");
    JLabel welcomeLabel2 = new JLabel("Sign in to your workspace");
    JLabel usernameLabel = new JLabel("Username:");
    JLabel passwordLabel = new JLabel("Password:");

    public LoginFrame(EmployeeManager manager, DepartmentManager departmentManager) {
        this.manager = manager;
        this.departmentManager = departmentManager;
        this.setTitle("Login Frame");
        setSize(920, 570);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1, 2));

        leftPanel.setBackground(NAVY);
        leftPanel.setLayout(new GridBagLayout());

        rightPanel.setBackground(Color.WHITE);


        welcomeLabel.setForeground(NAVY);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 31));
        welcomeLabel.setBorder(new EmptyBorder(0, 50, 0, 0));
        welcomeLabel2.setForeground(BLUE);
        welcomeLabel2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        welcomeLabel2.setBorder(new EmptyBorder(0, 80, 0, 0));

        username.setFont(new Font("SansSerif", Font.PLAIN, 14));
        username.setForeground(Color.GRAY);
        username.setBackground(new Color(0xF8F3F5));
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PALE, 1),
                new EmptyBorder(11, 12, 11, 12)));
        username.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        password.setFont(new Font("SansSerif", Font.PLAIN, 14));
        password.setForeground(Color.GRAY);
        password.setBackground(new Color(0xF8F3F5));
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PALE, 1),
                new EmptyBorder(11, 12, 11, 12)));
        password.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        loginButton.setBackground(ROSE);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setSize(new Dimension(300, 45));
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        loginButton.addActionListener(e -> login());

        exitButton.setBackground(NAVY);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        exitButton.setSize(new Dimension(300, 45));
        exitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        exitButton.addActionListener(e -> System.exit(0));

        JPanel rightText = new JPanel();
        rightText.setOpaque(false);
        rightText.setLayout(new BoxLayout(rightText, BoxLayout.Y_AXIS));
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
        rightText.setBorder(new EmptyBorder(25, 35, 25, 35));


        JLabel title = new JLabel("<html>Employee<br>Management<br>System</html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 38));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitle = new JLabel("<html>Admins:<br>" + "Heba Kholief<br>" + "Menna Kholief<br>" + "Mariam Reda<br>" + "Aya Ahmed</html");
        subtitle.setForeground(PINK);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setAlignmentX(Component.RIGHT_ALIGNMENT);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel leftText = new JPanel();
        leftText.setOpaque(false);
        leftText.setLayout(new BoxLayout(leftText, BoxLayout.Y_AXIS));
        leftText.add(title);
        leftText.add(Box.createVerticalStrut(27));
        leftText.add(subtitle);
        leftText.add(Box.createVerticalStrut(34));

        rightPanel.add(rightText);
        leftPanel.add(leftText);
        add(leftPanel);
        add(rightPanel);


    }

    private void login() {
        String name = username.getText().trim();
        String pass = new String(password.getPassword());

        if ((name.equals("admin") && pass.equals("admin123")) ||
                (name.equals("Heba Kholief") && pass.equals("10061")) ||
                (name.equals("Menna Kholief") && pass.equals("10040")) ||
                (name.equals("Mariam Reda") && pass.equals("10032")) ||
                (name.equals("Aya Ahmed") && pass.equals("10493"))) {
            MainFrame frame = new MainFrame(manager, departmentManager);
            frame.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Incorrect username or password. Please try again.",
                    "Login failed", JOptionPane.ERROR_MESSAGE);
            password.setText("");
        }
    }
}
