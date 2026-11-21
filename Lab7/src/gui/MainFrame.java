package gui;

import model.Department;
import model.DepartmentName;
import model.Employee;
import manager.DepartmentManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel content = new JPanel(cardLayout);

    /*
     * TEMPORARY GUI-side collection:
     * The current uploaded project does not yet contain manager.EmployeeManager or
     * fileHandling.FileHandler. This list keeps added employees visible only while the app
     * is running. Replace it with manager.EmployeeManager when that class is ready.
     */
    private final ArrayList<Employee> employees = new ArrayList<>();

    private final DepartmentManager departmentManager = new DepartmentManager();
    private final Map<DepartmentName, Department> departments =
            new EnumMap<>(DepartmentName.class);

    private final EmployeesPanel employeesPanel;
    private final AddEmployeePanel addEmployeePanel;
    private final DepartmentPanel departmentPanel;
    private final JLabel pageHeading = new JLabel("Dashboard");

    public MainFrame() {
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 780);
        setMinimumSize(new Dimension(1050, 680));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG);

        initializeDepartments();

        employeesPanel = new EmployeesPanel(employees);
        addEmployeePanel = new AddEmployeePanel(this::addEmployee, departments);
        departmentPanel = new DepartmentPanel(departments);

        content.setBackground(Theme.BG);
        content.add(createDashboard(), "dashboard");
        content.add(addEmployeePanel, "add");
        content.add(employeesPanel, "employees");
        content.add(departmentPanel, "departments");

        setLayout(new BorderLayout());
        add(createSidebar(), BorderLayout.WEST);
        add(createMainArea(), BorderLayout.CENTER);

        showPage("dashboard", "Dashboard");
    }

    private void initializeDepartments() {
        int id = 1;
        for (DepartmentName name : DepartmentName.values()) {
            Department department = new Department(id++, name);
            departmentManager.addDepartment(department);
            departments.put(name, department);
        }
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Theme.SIDEBAR);
        sidebar.setPreferredSize(new Dimension(215, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BORDER));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel brand = new JLabel("  ✦  EMS");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 25));
        brand.setForeground(Theme.PINK);
        brand.setBorder(new EmptyBorder(24, 14, 30, 10));
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(brand);

        addNavButton(sidebar, "Dashboard", "dashboard", Theme.PINK);
        addNavButton(sidebar, "Add model.Employee", "add", Theme.PURPLE);
        addNavButton(sidebar, "Employees", "employees", Theme.CYAN);
        addNavButton(sidebar, "Departments", "departments", Theme.ORANGE);

        sidebar.add(Box.createVerticalGlue());

        JButton logout = new JButton("↪   Logout");
        styleNavButton(logout);
        logout.setForeground(Theme.PINK);
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        sidebar.add(logout);
        sidebar.add(Box.createVerticalStrut(20));

        return sidebar;
    }

    private void addNavButton(JPanel sidebar, String text, String page, Color accent) {
        JButton button = new JButton("   " + text);
        styleNavButton(button);
        button.addActionListener(e -> showPage(page, text));
        sidebar.add(button);
    }

    private void styleNavButton(JButton button) {
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Theme.TEXT);
        button.setBackground(Theme.WHITE);
        button.setBorder(new EmptyBorder(14, 22, 14, 10));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private JPanel createMainArea() {
        JPanel main = new JPanel(new BorderLayout(0, 18));
        main.setBackground(Theme.BG);
        main.setBorder(new EmptyBorder(22, 26, 24, 26));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        pageHeading.setFont(new Font("Segoe UI", Font.BOLD, 23));
        pageHeading.setForeground(Theme.NAVY);
        JLabel user = Theme.muted("Administrator  •  model.Employee Management System");
        top.add(pageHeading, BorderLayout.WEST);
        top.add(user, BorderLayout.EAST);

        main.add(top, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);
        return main;
    }

    private JPanel createDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout(0, 20));
        dashboard.setOpaque(false);

        JPanel intro = new JPanel();
        intro.setOpaque(false);
        intro.setLayout(new BoxLayout(intro, BoxLayout.Y_AXIS));
        JLabel welcome = new JLabel("Welcome back, Admin!");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 21));
        welcome.setForeground(Theme.TEXT);
        intro.add(welcome);
        intro.add(Box.createVerticalStrut(5));
        intro.add(Theme.muted("Here is an overview of your employee system."));
        dashboard.add(intro, BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(1, 3, 16, 0));
        stats.setOpaque(false);
        stats.add(statCard("TOTAL EMPLOYEES", "0", "Registered employees", Theme.PINK));
        stats.add(statCard("DEPARTMENTS", String.valueOf(departments.size()),
                "Available departments", Theme.PURPLE));
        stats.add(statCard("EMPLOYEE TYPES", "2", "Full-Time and Part-Time", Theme.CYAN));

        JPanel center = new JPanel(new BorderLayout(0, 18));
        center.setOpaque(false);
        center.add(stats, BorderLayout.NORTH);

        JPanel quick = Theme.card();
        quick.setLayout(new BoxLayout(quick, BoxLayout.Y_AXIS));
        JLabel quickTitle = new JLabel("Quick actions");
        quickTitle.setFont(Theme.SUBTITLE);
        quickTitle.setForeground(Theme.TEXT);
        quick.add(quickTitle);
        quick.add(Box.createVerticalStrut(15));

        JPanel actions = new JPanel(new GridLayout(1, 3, 12, 0));
        actions.setOpaque(false);
        actions.add(actionButton("＋  Add model.Employee", Theme.PINK,
                () -> showPage("add", "Add model.Employee")));
        actions.add(actionButton("▤  View Employees", Theme.PURPLE,
                () -> showPage("employees", "Employees")));
        actions.add(actionButton("▦  Departments", Theme.CYAN,
                () -> showPage("departments", "Departments")));
        quick.add(actions);
        center.add(quick, BorderLayout.CENTER);

        JLabel note = Theme.muted("Note: employee records are currently kept in memory until manager.EmployeeManager and file handling are integrated.");
        note.setBorder(new EmptyBorder(4, 2, 0, 2));
        center.add(note, BorderLayout.SOUTH);

        dashboard.add(center, BorderLayout.CENTER);
        return dashboard;
    }

    private JPanel statCard(String title, String value, String description, Color accent) {
        JPanel card = Theme.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 11));
        t.setForeground(Theme.MUTED);
        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 31));
        v.setForeground(accent);
        JLabel d = Theme.muted(description);
        card.add(t);
        card.add(Box.createVerticalStrut(12));
        card.add(v);
        card.add(Box.createVerticalStrut(5));
        card.add(d);
        return card;
    }

    private JButton actionButton(String text, Color color, Runnable action) {
        JButton button = new JButton(text);
        Theme.styleButton(button, color);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void addEmployee(Employee employee) {
        employees.add(employee);
        employeesPanel.refreshTable();
        showPage("employees", "Employees");
        JOptionPane.showMessageDialog(this,
                "model.Employee added successfully!\nmodel.Employee ID: " + employee.getId(),
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showPage(String page, String title) {
        pageHeading.setText(title);
        cardLayout.show(content, page);
        if ("employees".equals(page)) {
            employeesPanel.refreshTable();
        } else if ("departments".equals(page)) {
            departmentPanel.refreshTable();
        }
        content.revalidate();
        content.repaint();
    }
}
