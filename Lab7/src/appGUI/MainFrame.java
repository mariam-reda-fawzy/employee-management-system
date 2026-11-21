package appGUI;

import manager.DepartmentManager;
import manager.EmployeeManager;
import model.Employee;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    static final Color NAVY = new Color(0x1B2A44);
    static final Color BLUE = new Color(0x3E5C7A);
    static final Color PALE = new Color(0xE7CED6);
    static final Color PINK = new Color(0xF7B7C1);
    static final Color ROSE = new Color(0xC95B6B);
    static final Color WHITE = new Color(0xFFFCFD);
    static final Color LIGHT = new Color(0xF8F3F5);
    static final Color BG = new Color(0xF7F3F5);

    private final EmployeeManager employeeManager;
    private DepartmentManager departmentManager;
    private EmployeesPanel employeesPanel;
    private DepartmentPanel departmentPanel;

    private JLabel heading = new JLabel("Dashboard");
    private CardLayout cardLayout = new CardLayout();
    private JPanel pages = new JPanel(cardLayout);

    public MainFrame(EmployeeManager manager, DepartmentManager departments) {
        this.employeeManager = manager;
        this.departmentManager = departments;
        this.setTitle("Dashboard Frame");
        this.setSize(1240, 790);
        this.setMinimumSize(new Dimension(1000, 650));
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        JPanel contentArea = new JPanel(new BorderLayout(0, 18));
        contentArea.setBackground(LoginFrame.LIGHT);
        contentArea.setBorder(new EmptyBorder(25, 28, 25, 28));

        heading.setFont(new Font("Serif", Font.BOLD, 28));
        heading.setForeground(LoginFrame.NAVY);
        contentArea.add(heading, BorderLayout.NORTH);

        departmentManager.setEmployeeManager(employeeManager);
        employeesPanel = new EmployeesPanel(employeeManager, departmentManager.getAllDepartments());
        departmentPanel = new DepartmentPanel(departmentManager);

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

        this.setVisible(true);


    }

    private JPanel Sidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(225, 0));
        sidebar.setBorder(new EmptyBorder(28, 18, 22, 18));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("<html>Employee<br>Management<br>System</html>");
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

        sidebar.add(sideButton("Dashboard", "dashboard"));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(sideButton("Employees", "employees"));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(sideButton("Departments", "departments"));

        sidebar.add(Box.createVerticalGlue());

        JButton logout = new JButton("↪  Log out");
        logout.setAlignmentX(Component.LEFT_ALIGNMENT);
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        logout.setForeground(new Color(0xF4DDE2));
        logout.setBackground(new Color(0x293D5A));
        logout.setFont(new Font("SansSerif", Font.BOLD, 12));
        logout.setBorderPainted(false);
        logout.setFocusPainted(false);
        //logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logout.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?",
                    "Log out", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                new LoginFrame(employeeManager,departmentManager).setVisible(true);
                dispose();
            }
        });
        sidebar.add(logout);
        return sidebar;
    }

    private JButton sideButton(String text, String page) {
        JButton button = new JButton("✦" + "    " + text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setPreferredSize(new Dimension(185, 45));
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        if(text.equals("Dashboard")){
            button.setForeground(NAVY);
            button.setBackground(PINK);
        }
        else{
            button.setForeground(BG);
            button.setBackground(NAVY);
        }
        button.setBorder(new EmptyBorder(0, 14, 0, 8));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        //button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> showPage(page));
        return button;
    }

    private JPanel createDashboard() {
        JPanel panel = new JPanel(new BorderLayout(0, 18));
        panel.setBackground(LoginFrame.LIGHT);
        panel.setOpaque(false);

        JPanel welcome = new JPanel();
        welcome.setBackground(Color.WHITE);
        welcome.setBorder(new EmptyBorder(28, 30, 28, 30));
        welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Welcome to your workspace!");
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(LoginFrame.NAVY);
        JLabel subtitle = new JLabel("Manage your employees and departments easily.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(LoginFrame.BLUE);
        welcome.add(title);
        welcome.add(Box.createVerticalStrut(10));
        welcome.add(subtitle);


        JPanel stats = new JPanel(new GridLayout(1, 2, 18, 0));
        stats.setOpaque(false);
        JLabel employeeCountLabel = new JLabel(String.valueOf(Employee.getEmployeeCount()));
        JLabel departmentCountLabel = new JLabel("5");
        stats.add(statCard("TOTAL EMPLOYEES", employeeCountLabel, "People in your organization", BLUE));
        stats.add(statCard("DEPARTMENTS", departmentCountLabel, "Teams across the company", ROSE));

        JPanel actions = new JPanel(new GridLayout(1, 2, 18, 0));
        actions.setOpaque(false);;
        actions.add(dashboardCard("Employees", "Add, view, search, update and delete employee records.",
                "Open Employees", () -> showPage("employees")));
        actions.add(dashboardCard("Departments", "View and manage the company departments.",
                "Manage Departments", () -> showPage("departments")));

        JPanel lowerSection =  new JPanel(new BorderLayout(0,18));
        lowerSection.setOpaque(false);

        //lowerSection.setBackground(LoginFrame.LIGHT);
        lowerSection.add(stats, BorderLayout.NORTH);
        lowerSection.add(actions, BorderLayout.CENTER);


        panel.add(welcome, BorderLayout.NORTH);
        panel.add(lowerSection, BorderLayout.CENTER);
        return panel;
    }

    private JPanel dashboardCard(String title, String description, String buttonText, Runnable action) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LoginFrame.PALE),
                new EmptyBorder(24, 22, 24, 22)));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(title);
        name.setFont(new Font("Serif", Font.BOLD, 22));
        name.setForeground(LoginFrame.NAVY);
        JLabel desc = new JLabel("<html><div style='width:240px'>" + description + "</div></html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        desc.setForeground(LoginFrame.BLUE);
        JButton button = new JButton(buttonText);
        //LoginFrame.styleButton(button, LoginFrame.ROSE);
        button.addActionListener(e -> action.run());

        card.add(name);
        card.add(Box.createVerticalStrut(12));
        card.add(desc);
        card.add(Box.createVerticalGlue());
        card.add(Box.createVerticalStrut(18));
        card.add(button);
        return card;
    }

    private JPanel statCard(String heading, JLabel value, String note, Color accent) {
        JPanel card = new JPanel();
        card.setBackground(WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(PALE, 1, true), new EmptyBorder(18, 20, 18, 20)));
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
        if (page.equals("dashboard")) heading.setText("Dashboard");
        if (page.equals("employees")) {
            heading.setText("Employee Management");
            //employeesPanel.refreshTable();
        }
        if (page.equals("departments")) {
            heading.setText("Department Management");
            //departmentPanel.refreshTable();
        }
        cardLayout.show(pages, page);
    }
}
