package appGUI;

import manager.EmployeeManager;
import model.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class EmployeesPanel extends JPanel {

    private static final Color NAVY = new Color(0x1B2A44);
    private static final Color BLUE = new Color(0x3E5C7A);
    private static final Color PALE = new Color(0xE7CED6);
    private static final Color ROSE = new Color(0xC95B6B);
    private static final Color WHITE = Color.WHITE;

    private EmployeeManager manager;
    private ArrayList<Department> departments;
    private Runnable dashboardRefresh;

    private JTable employeeTable;
    private DefaultTableModel employeeModel;
    private JTextField searchField;
    private JComboBox<String> searchType;
    private JComboBox<DepartmentName> departmentFilter;

    // Employee count label
    private JLabel employeeCountLabel;

    private boolean updatingDepartmentFilter = false;

    public EmployeesPanel(EmployeeManager manager, ArrayList<Department> departments) {
        this(manager, departments, null);
    }

    public EmployeesPanel(EmployeeManager manager, ArrayList<Department> departments,
                          Runnable dashboardRefresh) {

        this.manager = manager;
        this.departments = departments;
        this.dashboardRefresh = dashboardRefresh;

        setLayout(new BorderLayout(0, 22));
        setOpaque(false);

        createEmployeesPage();
        refreshTable();
    }

    private void createEmployeesPage() {

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = pageTitle(
                "Employee directory",
                "View and manage every employee in your organization."
        );

        header.add(title, BorderLayout.WEST);

        JButton addButton = primaryButton("+  Add employee");
        addButton.addActionListener(e -> showEmployeeDialog(null));

        header.add(addButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.setOpaque(false);

        center.add(createSearchPanel(), BorderLayout.NORTH);

        // Employee count and table section
        JPanel tableSection = new JPanel(new BorderLayout(0, 8));
        tableSection.setOpaque(false);

        employeeCountLabel = new JLabel();
        employeeCountLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        employeeCountLabel.setForeground(NAVY);

        tableSection.add(employeeCountLabel, BorderLayout.NORTH);
        tableSection.add(createTable(), BorderLayout.CENTER);

        center.add(tableSection, BorderLayout.CENTER);
        center.add(createActionsPanel(), BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {

        JPanel tools = new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 0));
        tools.setOpaque(false);

        searchType = new JComboBox<>(new String[]{"All", "ID", "Name"});
        searchType.setPreferredSize(new Dimension(125, 37));
        searchType.setFont(new Font("SansSerif", Font.PLAIN, 12));

        searchField = new JTextField(18);
        searchField.setPreferredSize(new Dimension(220, 37));
        styleInput(searchField);

        departmentFilter = new JComboBox<>();
        departmentFilter.setPreferredSize(new Dimension(150, 37));
        departmentFilter.setFont(new Font("SansSerif", Font.PLAIN, 12));

        refreshDepartmentFilter();

        styleComboBox(searchType);
        styleComboBox(departmentFilter);

        JButton searchButton = secondaryButton("Search");
        searchButton.addActionListener(e -> searchEmployees());

        departmentFilter.addActionListener(e -> {
            if (!updatingDepartmentFilter) {
                searchEmployees();
            }
        });

        JButton resetButton = secondaryButton("Reset");

        resetButton.addActionListener(e -> {
            searchField.setText("");
            searchType.setSelectedIndex(0);
            departmentFilter.setSelectedIndex(-1);
            refreshTable();
        });

        searchField.addActionListener(e -> searchEmployees());

        tools.add(searchType);
        tools.add(searchField);
        tools.add(searchButton);
        tools.add(departmentFilter);
        tools.add(resetButton);

        return tools;
    }

    private JScrollPane createTable() {

        String[] columns = {
                "ID",
                "FULL NAME",
                "AGE",
                "DEPARTMENT",
                "POSITION",
                "TYPE",
                "MONTHLY SALARY"
        };

        employeeModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(employeeModel);
        styleTable(employeeTable);

        JScrollPane scroll = new JScrollPane(employeeTable);
        scroll.setBorder(new LineBorder(PALE, 1, true));
        scroll.getViewport().setBackground(WHITE);

        return scroll;
    }

    private void styleTable(JTable table) {

        table.setRowHeight(39);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setForeground(NAVY);
        table.setBackground(WHITE);
        table.setGridColor(new Color(0xF0E5E9));
        table.setSelectionBackground(new Color(0xF7B7C1));
        table.setSelectionForeground(NAVY);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        table.getTableHeader().setBackground(new Color(0xF4E7EB));
        table.getTableHeader().setForeground(BLUE);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 10));
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));

        table.setFillsViewportHeight(true);
    }

    private JPanel createActionsPanel() {

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 9, 0));
        actions.setOpaque(false);

        JButton editButton = secondaryButton("Edit selected");
        JButton deleteButton = dangerButton("Delete selected");

        editButton.addActionListener(e -> editSelectedEmployee());
        deleteButton.addActionListener(e -> deleteSelectedEmployee());

        actions.add(editButton);
        actions.add(deleteButton);

        return actions;
    }

    private void showEmployeeDialog(Employee existing) {

        boolean editing = existing != null;

        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(this),
                editing ? "Update employee" : "Add employee",
                Dialog.ModalityType.APPLICATION_MODAL
        );

        dialog.setSize(570, 700);
        dialog.setMinimumSize(new Dimension(540, 650));
        dialog.setLocationRelativeTo(this);

        JPanel outer = new JPanel(new BorderLayout(0, 12));
        outer.setBackground(WHITE);
        outer.setBorder(new EmptyBorder(22, 28, 22, 28));

        JLabel heading = new JLabel(
                editing
                        ? "Update employee details"
                        : "Create employee record"
        );

        heading.setForeground(NAVY);
        heading.setFont(new Font("Serif", Font.BOLD, 25));

        JLabel subtitle = new JLabel("Enter the employee information below.");
        subtitle.setForeground(BLUE);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        top.add(heading);
        top.add(Box.createVerticalStrut(5));
        top.add(subtitle);

        outer.add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(0, 2, 12, 11));
        form.setOpaque(false);

        JLabel idValue = new JLabel(
                editing
                        ? String.valueOf(existing.getId())
                        : "Generated automatically"
        );

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();

        DefaultComboBoxModel<DepartmentName> departmentModel =
                new DefaultComboBoxModel<>();

        for (Department department : departments) {
            departmentModel.addElement(department.getDepartmentName());
        }

        JComboBox<DepartmentName> deptBox =
                new JComboBox<>(departmentModel);

        styleComboBox(deptBox);

        JTextField positionField = new JTextField();

        JComboBox<String> typeBox =
                new JComboBox<>(new String[]{"Full-Time", "Part-Time"});

        JTextField firstSalaryField = new JTextField();
        JTextField secondSalaryField = new JTextField();

        addFormField(form, "Employee ID", idValue);
        addFormField(form, "Full name", nameField);
        addFormField(form, "Age", ageField);
        addFormField(form, "Department", deptBox);
        addFormField(form, "Position", positionField);
        addFormField(form, "Employee type", typeBox);

        JLabel firstLabel = new JLabel("Basic monthly salary (EGP)");
        JLabel secondLabel = new JLabel("Monthly bonus (EGP)");

        styleFormLabel(firstLabel);
        styleFormLabel(secondLabel);

        form.add(firstLabel);
        form.add(firstSalaryField);

        form.add(secondLabel);
        form.add(secondSalaryField);

        styleInput(nameField);
        styleInput(ageField);
        styleInput(positionField);
        styleInput(firstSalaryField);
        styleInput(secondSalaryField);

        deptBox.setBackground(WHITE);
        typeBox.setBackground(WHITE);

        idValue.setFont(new Font("SansSerif", Font.BOLD, 12));
        idValue.setForeground(BLUE);

        if (editing) {

            nameField.setText(existing.getFullName());
            ageField.setText(String.valueOf(existing.getAge()));
            positionField.setText(existing.getPosition());

            deptBox.setSelectedItem(existing.getDepartment().getDepartmentName());

            if (existing instanceof FullTimeEmployee) {

                typeBox.setSelectedItem("Full-Time");

                FullTimeEmployee full = (FullTimeEmployee) existing;

                firstSalaryField.setText(
                        String.valueOf(full.getBasicMonthlySalary())
                );

                secondSalaryField.setText(
                        String.valueOf(full.getBonus())
                );

            } else {

                typeBox.setSelectedItem("Part-Time");

                PartTimeEmployee part = (PartTimeEmployee) existing;

                firstSalaryField.setText(
                        String.valueOf(part.getHourlyPaymentRate())
                );

                secondSalaryField.setText(
                        String.valueOf(part.getHoursWorked())
                );
            }
        }

        Runnable updateSalaryLabels = () -> {

            boolean fullTime = typeBox.getSelectedItem().equals("Full-Time");

            firstLabel.setText(
                    fullTime
                            ? "Basic monthly salary (EGP)"
                            : "Hourly rate (EGP)"
            );

            secondLabel.setText(
                    fullTime
                            ? "Monthly bonus (EGP)"
                            : "Hours worked / month"
            );

            firstSalaryField.setToolTipText(
                    fullTime
                            ? "Basic monthly salary"
                            : "Hourly payment rate"
            );

            secondSalaryField.setToolTipText(
                    fullTime
                            ? "Monthly bonus"
                            : "Hours worked per month"
            );
        };

        typeBox.addActionListener(e -> updateSalaryLabels.run());
        updateSalaryLabels.run();

        JScrollPane formScroll = new JScrollPane(form);
        formScroll.setBorder(null);
        formScroll.getViewport().setBackground(WHITE);

        outer.add(formScroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);

        JButton cancelButton = secondaryButton("Cancel");
        JButton saveButton = primaryButton(
                editing ? "Save changes" : "Add employee"
        );

        cancelButton.addActionListener(e -> dialog.dispose());

        saveButton.addActionListener(e -> {

            try {

                String name = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                String position = positionField.getText().trim();

                String firstText = firstSalaryField.getText().trim();
                String secondText = secondSalaryField.getText().trim();

                DepartmentName selectedName =
                        (DepartmentName) deptBox.getSelectedItem();

                if (name.isEmpty() || ageText.isEmpty()
                        || position.isEmpty()
                        || firstText.isEmpty()
                        || secondText.isEmpty()
                        || selectedName == null) {

                    throw new IllegalArgumentException(
                            "Please complete all required fields."
                    );
                }

                int age = Integer.parseInt(ageText);
                double first = Double.parseDouble(firstText);
                double second = Double.parseDouble(secondText);

                if (age <= 0) {
                    throw new IllegalArgumentException(
                            "Age must be a positive integer."
                    );
                }

                if (first < 0 || second < 0) {
                    throw new IllegalArgumentException(
                            "Salary values, rates and hours cannot be negative."
                    );
                }

                Department department = getDepartment(selectedName);

                if (department == null) {
                    throw new IllegalArgumentException(
                            "Please select a valid department."
                    );
                }

                boolean fullTime = typeBox.getSelectedItem().equals("Full-Time");

                if (editing) {

                    boolean updated = manager.replaceEmployeeType(
                            existing.getId(),
                            name,
                            department,
                            age,
                            position,
                            fullTime,
                            first,
                            second
                    );

                    if (!updated) {
                        throw new IllegalArgumentException(
                                "Employee could not be updated."
                        );
                    }

                }
                 else{

                    Employee employee;

                    if (fullTime) {

                        employee = new FullTimeEmployee(
                                name,
                                department,
                                age,
                                position,
                                first,
                                second
                        );

                    } else {

                        employee = new PartTimeEmployee(
                                name,
                                department,
                                age,
                                position,
                                first,
                                second
                        );
                    }

                    if (!manager.addEmployee(employee)) {
                        throw new IllegalArgumentException(
                                "Employee could not be added."
                        );
                    }
                }

                refreshTable();
                refreshDashboard();
                dialog.dispose();

                JOptionPane.showMessageDialog(
                        this,
                        editing
                                ? "Employee updated successfully!"
                                : "Employee added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Age must be an integer and salary values must be numeric.",
                        "Invalid input",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        ex.getMessage(),
                        "Invalid input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        buttons.add(cancelButton);
        buttons.add(saveButton);

        outer.add(buttons, BorderLayout.SOUTH);

        dialog.setContentPane(outer);
        dialog.setVisible(true);
    }

    private void addFormField(JPanel panel, String labelText, JComponent component) {

        JLabel label = new JLabel(labelText);
        styleFormLabel(label);

        panel.add(label);
        panel.add(component);
    }

    private void styleFormLabel(JLabel label) {
        label.setForeground(BLUE);
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
    }

    private Department getDepartment(DepartmentName name) {

        for (Department department : departments) {

            if (department.getDepartmentName() == name) {
                return department;
            }
        }

        return null;
    }

    private void editSelectedEmployee() {

        int row = employeeTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee first."
            );
            return;
        }

        int modelRow = employeeTable.convertRowIndexToModel(row);

        int id = Integer.parseInt(
                employeeModel.getValueAt(modelRow, 0).toString()
        );

        Employee existing = manager.searchById(id);

        if (existing == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Employee was not found."
            );

            return;
        }

        showEmployeeDialog(existing);
    }

    private void deleteSelectedEmployee() {

        int row = employeeTable.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee first."
            );

            return;
        }

        int modelRow = employeeTable.convertRowIndexToModel(row);

        int id = Integer.parseInt(
                employeeModel.getValueAt(modelRow, 0).toString()
        );

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Delete this employee record? This action cannot be undone.",
                "Confirm deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (answer == JOptionPane.YES_OPTION) {

            if (manager.deleteEmployee(id)) {

                refreshTable();
                refreshDashboard();

                JOptionPane.showMessageDialog(
                        this,
                        "Employee deleted successfully."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Employee could not be deleted."
                );
            }
        }
    }

    private void searchEmployees() {

        String value = searchField.getText().trim();

        DepartmentName selectedDepartment =
                (DepartmentName) departmentFilter.getSelectedItem();

        ArrayList<Employee> result = new ArrayList<>();

        String kind = (String) searchType.getSelectedItem();

        if (value.isEmpty()) {

            result = manager.getAllEmployees();

        } else if ("ID".equals(kind)) {

            try {

                Employee employee = manager.searchById(
                        Integer.parseInt(value)
                );

                if (employee != null) {
                    result.add(employee);
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Employee ID must be an integer."
                );

                return;
            }

        } else if ("Name".equals(kind)) {

            result = manager.searchByName(value);

        } else {

            for (Employee employee : manager.getAllEmployees()) {

                boolean matches =
                        String.valueOf(employee.getId()).contains(value)
                                || employee.getFullName()
                                .toLowerCase()
                                .contains(value.toLowerCase());

                if (matches) {
                    result.add(employee);
                }
            }
        }

        if (selectedDepartment != null) {

            result.removeIf(employee ->
                    employee.getDepartment().getDepartmentName()
                            != selectedDepartment
            );
        }

        fillTable(result);

        if (result.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No matching employees were found.",
                    "Search results",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Refreshes the table and employee count
    public void refreshTable() {

        ArrayList<Employee> employees = manager.getAllEmployees();

        employeeCountLabel.setText(
                "Total employees: " + employees.size()
        );

        fillTable(employees);
    }

    private void fillTable(ArrayList<Employee> employees) {

        employeeModel.setRowCount(0);

        for (Employee employee : employees) {

            String type = employee instanceof FullTimeEmployee
                    ? "Full-Time"
                    : "Part-Time";

            employeeModel.addRow(new Object[]{
                    employee.getId(),
                    employee.getFullName(),
                    employee.getAge(),
                    employee.getDepartment().getDepartmentName(),
                    employee.getPosition(),
                    type,
                    String.format(
                            "%,.2f EGP",
                            employee.calculateSalary()
                    )
            });
        }
    }

    private void refreshDashboard() {

        if (dashboardRefresh != null) {
            dashboardRefresh.run();
        }
    }

    private JLabel pageTitle(String titleText, String subtitleText) {

        JLabel label = new JLabel(
                "<html><font color='#1B2A44' size='6'>"
                        + titleText
                        + "</font><br><font color='#3E5C7A' size='3'>"
                        + subtitleText
                        + "</font></html>"
        );

        label.setFont(new Font("SansSerif", Font.PLAIN, 12));

        return label;
    }

    private void styleInput(JTextField field) {

        field.setFont(new Font("SansSerif", Font.PLAIN, 12));
        field.setForeground(NAVY);
        field.setBackground(WHITE);

        field.setBorder(
                new CompoundBorder(
                        new LineBorder(PALE, 1, true),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        field.setPreferredSize(new Dimension(100, 37));
    }

    private JButton primaryButton(String text) {

        JButton button = new JButton(text);

        button.setForeground(WHITE);
        button.setBackground(ROSE);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBorder(new EmptyBorder(11, 17, 11, 17));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private JButton secondaryButton(String text) {

        JButton button = new JButton(text);

        button.setForeground(BLUE);
        button.setBackground(new Color(0xEAF0F5));
        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setBorder(new EmptyBorder(10, 14, 10, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private JButton dangerButton(String text) {

        JButton button = new JButton(text);

        button.setForeground(WHITE);
        button.setBackground(ROSE);
        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setBorder(new EmptyBorder(10, 14, 10, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void styleComboBox(JComboBox<?> comboBox) {

        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        comboBox.setForeground(NAVY);
        comboBox.setBackground(WHITE);

        comboBox.setBorder(
                new CompoundBorder(
                        new LineBorder(PALE, 1, true),
                        new EmptyBorder(3, 8, 3, 8)
                )
        );

        comboBox.setFocusable(false);
    }

    private void refreshDepartmentFilter() {

        updatingDepartmentFilter = true;

        try {

            DepartmentName previous =
                    (DepartmentName) departmentFilter.getSelectedItem();

            DefaultComboBoxModel<DepartmentName> model =
                    new DefaultComboBoxModel<>();

            for (Department department : departments) {
                model.addElement(department.getDepartmentName());
            }

            departmentFilter.setModel(model);

            if (previous != null && model.getIndexOf(previous) >= 0) {
                departmentFilter.setSelectedItem(previous);
            } else {
                departmentFilter.setSelectedIndex(-1);
            }

        } finally {
            updatingDepartmentFilter = false;
        }
    }

    public void setDepartments(ArrayList<Department> departments) {

        this.departments = departments;

        refreshDepartmentFilter();
    }
}