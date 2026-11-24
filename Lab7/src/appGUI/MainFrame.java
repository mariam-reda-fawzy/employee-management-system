
package appGUI;

import manager.DepartmentManager;
import manager.EmployeeManager;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    static final Color NAVY = new Color(0x1B2A44);
    static final Color BLUE = new Color(0x3E5C7A);
    static final Color PALE = new Color(0xE7CED6);
    static final Color PINK = new Color(0xF7B7C1);
    static final Color ROSE = new Color(0xC95B6B);
    static final Color WHITE = new Color(0xFFFCFD);
    static final Color LIGHT = new Color(0xF8F3F5);
    static final Color BG = new Color(0xF7F3F5);

    private JLabel employeeCountLabel;
    private JLabel departmentCountLabel;

    private final EmployeeManager employeeManager;
    private DepartmentManager departmentManager;
    private EmployeesPanel employeesPanel;
    private DepartmentPanel departmentPanel;

    private JLabel heading = new JLabel("Dashboard");
    private CardLayout cardLayout = new CardLayout();
    private JPanel pages = new JPanel(cardLayout);

    private JButton dashboardButton;
    private JButton employeesButton;
    private JButton departmentsButton;

    public MainFrame(EmployeeManager manager, DepartmentManager departments) {

        this.employeeManager = manager;
        this.departmentManager = departments;

        setTitle("Dashboard Frame");
        setSize(1240, 790);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });

        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        JPanel contentArea = new JPanel(new BorderLayout(0, 18));
        contentArea.setBackground(LoginFrame.LIGHT);
        contentArea.setBorder(new EmptyBorder(25, 28, 25, 28));

        heading.setFont(new Font("Serif", Font.BOLD, 28));
        heading.setForeground(LoginFrame.NAVY);
        contentArea.add(heading, BorderLayout.NORTH);

        departmentManager.setEmployeeManager(employeeManager);

        employeesPanel = new EmployeesPanel(
                employeeManager,
                departmentManager.getAllDepartments(),
                this::updateDashboardStats
        );

        departmentPanel = new DepartmentPanel(departmentManager);

        departmentPanel.setOnDepartmentsChanged(() -> {

            employeesPanel.setDepartments(
                    departmentManager.getAllDepartments()
            );

            updateDashboardStats();
        });

        pages.setBackground(LoginFrame.LIGHT);
        pages.add(createDashboard(), "dashboard");
        pages.add(employeesPanel, "employees");
        pages.add(departmentPanel, "departments");

        contentArea.add(pages, BorderLayout.CENTER);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(LoginFrame.LIGHT);

        root.add(Sidebar(), BorderLayout.WEST);
        root.add(contentArea, BorderLayout.CENTER);

        setContentPane(root);

        showPage("dashboard");

        setVisible(true);
    }

    private JPanel Sidebar() {

        JPanel sidebar = new JPanel();

        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(225, 0));
        sidebar.setBorder(new EmptyBorder(28, 18, 22, 18));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(
                "<html>Employee<br>Management<br>System</html>"
        );

        title.setForeground(PINK);
        title.setFont(new Font("Serif", Font.BOLD, 27));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(new EmptyBorder(0, 8, 0, 0));

        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(8));

        JLabel subtitle = new JLabel("EMPLOYEE MANAGEMENT");

        subtitle.setForeground(BG);
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 9));
        subtitle.setBorder(new EmptyBorder(7, 9, 35, 0));

        sidebar.add(subtitle);

        dashboardButton = sideButton("Dashboard", "dashboard");
        employeesButton = sideButton("Employees", "employees");
        departmentsButton = sideButton("Departments", "departments");

        sidebar.add(dashboardButton);
        sidebar.add(Box.createVerticalStrut(8));

        sidebar.add(employeesButton);
        sidebar.add(Box.createVerticalStrut(8));

        sidebar.add(departmentsButton);

        sidebar.add(Box.createVerticalGlue());

        JButton exitButton = new JButton("✕  Exit");

        styleBottomButton(exitButton);

        exitButton.addActionListener(e -> confirmExit());

        JButton logout = new JButton("↪  Log out");

        styleBottomButton(logout);

        logout.addActionListener(e -> {

            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Confirm Log out",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (answer == JOptionPane.YES_OPTION) {
                new LoginFrame(employeeManager, departmentManager);
                dispose();
            }
        });

        sidebar.add(exitButton);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(logout);

        return sidebar;
    }

    private void styleBottomButton(JButton button) {

        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setPreferredSize(new Dimension(190, 42));
        button.setForeground(new Color(0xF4DDE2));
        button.setBackground(new Color(0x293D5A));
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void confirmExit() {

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private JButton sideButton(String text, String page) {

        JButton button = new JButton("✦    " + text);

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setPreferredSize(new Dimension(185, 45));
        button.setFont(new Font("SansSerif", Font.BOLD, 12));

        button.setForeground(WHITE);
        button.setBackground(NAVY);

        button.setBorder(new EmptyBorder(0, 14, 0, 8));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> showPage(page));

        return button;
    }


    private JPanel createDashboard() {

        JPanel panel = new JPanel(new BorderLayout(0, 26));
        panel.setBackground(LoginFrame.LIGHT);
        panel.setOpaque(false);

        JPanel welcome = new JPanel();
        welcome.setBackground(Color.WHITE);
        welcome.setBorder(new EmptyBorder(25, 30, 25, 30));
        welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Welcome to your workspace!");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setForeground(LoginFrame.NAVY);

        JLabel subtitle = new JLabel(
                "Manage your employees and departments easily."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(LoginFrame.BLUE);

        welcome.add(title);
        welcome.add(Box.createVerticalStrut(10));
        welcome.add(subtitle);

        JPanel stats = new JPanel(new GridLayout(1, 2, 18, 0));
        stats.setOpaque(false);

        employeeCountLabel = new JLabel(
                String.valueOf(employeeManager.getAllEmployees().size())
        );

        departmentCountLabel = new JLabel(
                String.valueOf(departmentManager.getAllDepartments().size())
        );

        stats.add(statCard(
                "TOTAL EMPLOYEES",
                employeeCountLabel,
                "People in your organization.",
                BLUE
        ));

        stats.add(statCard(
                "DEPARTMENTS",
                departmentCountLabel,
                "Departments across the company.",
                ROSE
        ));

        JPanel actions = new JPanel(new GridLayout(1, 2, 18, 0));
        actions.setOpaque(false);

        actions.add(dashboardCard(
                "Employees",
                "Add, view, search, update and delete employee records.",
                "Open Employees",
                () -> showPage("employees")
        ));

        actions.add(dashboardCard(
                "Departments",
                "View and manage the company departments.",
                "Manage Departments",
                () -> showPage("departments")
        ));

        JPanel lowerSection = new JPanel();
        lowerSection.setOpaque(false);
        lowerSection.setLayout(
                new BoxLayout(lowerSection, BoxLayout.Y_AXIS)
        );

        stats.setPreferredSize(
                new Dimension(Short.MAX_VALUE, 140)
        );
        stats.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 140)
        );

        actions.setPreferredSize(
                new Dimension(Short.MAX_VALUE, 220)
        );
        actions.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 220)
        );

        lowerSection.add(stats);
        lowerSection.add(Box.createVerticalStrut(22));
        lowerSection.add(actions);
        lowerSection.add(Box.createVerticalGlue());

        panel.add(welcome, BorderLayout.NORTH);
        panel.add(lowerSection, BorderLayout.CENTER);

        return panel;
    }



    private JPanel dashboardCard(
            String title,
            String description,
            String buttonText,
            Runnable action) {

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LoginFrame.PALE),
                new EmptyBorder(18, 22, 18, 22)
        ));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(title);
        name.setFont(new Font("Serif", Font.BOLD, 21));
        name.setForeground(LoginFrame.NAVY);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel(
                "<html><div style='width:300px'>"
                        + description
                        + "</div></html>"
        );

        desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        desc.setForeground(LoginFrame.BLUE);
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(210, 40));
        button.setMaximumSize(new Dimension(210, 40));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(e -> action.run());

        content.add(name);
        content.add(Box.createVerticalStrut(12));
        content.add(desc);
        content.add(Box.createVerticalStrut(18));
        content.add(button);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 8, 0, 0);

        card.add(content, gbc);

        return card;
    }



    private JPanel statCard(
            String heading,
            JLabel value,
            String note,
            Color accent) {

        JPanel card = new JPanel();
        card.setBackground(WHITE);

        card.setBorder(new CompoundBorder(
                new LineBorder(PALE, 1, true),
                new EmptyBorder(18, 20, 18, 20)
        ));

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(heading);
        label.setForeground(BLUE);
        label.setFont(new Font("SansSerif", Font.BOLD, 10));

        value.setForeground(NAVY);
        value.setFont(new Font("Serif", Font.BOLD, 28));

        JLabel detail = new JLabel(note);
        detail.setForeground(new Color(0x8995A4));
        detail.setFont(new Font("SansSerif", Font.PLAIN, 10));

        card.add(label);
        card.add(Box.createVerticalStrut(12));
        card.add(value);
        card.add(Box.createVerticalStrut(5));
        card.add(detail);

        return card;
    }

    private void showPage(String page) {

        if (page.equals("dashboard")) {
            heading.setText("Dashboard");
        }

        if (page.equals("employees")) {
            heading.setText("Employee Management");
        }

        if (page.equals("departments")) {
            heading.setText("Department Management");
        }

        dashboardButton.setBackground(
                page.equals("dashboard") ? PINK : NAVY
        );

        employeesButton.setBackground(
                page.equals("employees") ? PINK : NAVY
        );

        departmentsButton.setBackground(
                page.equals("departments") ? PINK : NAVY
        );

        dashboardButton.setForeground(
                page.equals("dashboard") ? NAVY : WHITE
        );

        employeesButton.setForeground(
                page.equals("employees") ? NAVY : WHITE
        );

        departmentsButton.setForeground(
                page.equals("departments") ? NAVY : WHITE
        );

        cardLayout.show(pages, page);
    }

    private void updateDashboardStats() {
        employeeCountLabel.setText(
                String.valueOf(employeeManager.getAllEmployees().size())
        );
        departmentCountLabel.setText(
                String.valueOf(departmentManager.getAllDepartments().size())
        );
        if (departmentPanel != null) {
            departmentPanel.refreshTable();
        }
        revalidate();
        repaint();
    }
}
