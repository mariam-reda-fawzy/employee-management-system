package gui;

import model.Department;
import model.DepartmentName;
import model.Employee;
import model.FullTimeEmployee;
import model.PartTimeEmployee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;
import java.util.function.Consumer;

public class AddEmployeePanel extends JPanel {
    private final Consumer<Employee> onEmployeeAdded;
    private final Map<DepartmentName, Department> departments;

    private final JTextField nameField = new JTextField();
    private final JTextField ageField = new JTextField();
    private final JTextField positionField = new JTextField();
    private final JComboBox<DepartmentName> departmentBox =
            new JComboBox<>(DepartmentName.values());
    private final JComboBox<String> typeBox =
            new JComboBox<>(new String[]{"Full-Time", "Part-Time"});

    private final JTextField basicSalaryField = new JTextField();
    private final JTextField bonusField = new JTextField();
    private final JTextField hourlyRateField = new JTextField();
    private final JTextField hoursWorkedField = new JTextField();

    private final JPanel salaryFields = new JPanel(new GridBagLayout());
    private final JLabel salaryTitle = new JLabel("Salary information");

    public AddEmployeePanel(Consumer<Employee> onEmployeeAdded,
                            Map<DepartmentName, Department> departments) {
        this.onEmployeeAdded = onEmployeeAdded;
        this.departments = departments;
        setOpaque(false);
        setLayout(new BorderLayout(0, 15));

        JPanel formCard = Theme.card();
        formCard.setLayout(new BorderLayout(0, 18));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Add new employee");
        title.setFont(Theme.SUBTITLE);
        title.setForeground(Theme.TEXT);
        heading.add(title);
        heading.add(Box.createVerticalStrut(4));
        heading.add(Theme.muted("Enter the employee details below."));
        formCard.add(heading, BorderLayout.NORTH);

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);
        addField(fields, "Full name", nameField, 0, 0);
        addField(fields, "Age", ageField, 1, 0);
        addField(fields, "Position", positionField, 0, 1);
        addField(fields, "model.Department", departmentBox, 1, 1);
        addField(fields, "model.Employee type", typeBox, 0, 2);

        salaryFields.setOpaque(false);
        salaryFields.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                "Salary information",
                0, 0, new Font("Segoe UI", Font.BOLD, 12), Theme.PURPLE));
        formCard.add(fields, BorderLayout.CENTER);

        JPanel lower = new JPanel(new BorderLayout(0, 12));
        lower.setOpaque(false);
        lower.add(salaryFields, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);
        JButton clear = new JButton("Clear");
        JButton add = new JButton("Add employee");
        Theme.styleButton(clear, Theme.NAVY);
        Theme.styleButton(add, Theme.PINK);
        buttons.add(clear);
        buttons.add(add);
        lower.add(buttons, BorderLayout.SOUTH);

        formCard.add(lower, BorderLayout.SOUTH);
        add(formCard, BorderLayout.CENTER);

        Theme.styleTextField(nameField);
        Theme.styleTextField(ageField);
        Theme.styleTextField(positionField);
        Theme.styleTextField(basicSalaryField);
        Theme.styleTextField(bonusField);
        Theme.styleTextField(hourlyRateField);
        Theme.styleTextField(hoursWorkedField);

        styleCombo(departmentBox);
        styleCombo(typeBox);

        typeBox.addActionListener(e -> updateSalaryFields());
        clear.addActionListener(e -> clearForm());
        add.addActionListener(e -> submitForm());

        updateSalaryFields();
    }

    private void addField(JPanel panel, String label, JComponent component,
                          int column, int row) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = column;
        c.gridy = row;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(8, 8, 8, 8);

        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        JLabel caption = new JLabel(label);
        caption.setFont(new Font("Segoe UI", Font.BOLD, 12));
        caption.setForeground(Theme.TEXT);
        component.setPreferredSize(new Dimension(200, 38));
        wrapper.add(caption);
        wrapper.add(Box.createVerticalStrut(6));
        wrapper.add(component);
        panel.add(wrapper, c);
    }

    private void styleCombo(JComboBox<?> combo) {
        combo.setFont(Theme.BODY);
        combo.setBackground(Theme.WHITE);
        combo.setForeground(Theme.TEXT);
    }

    private void updateSalaryFields() {
        salaryFields.removeAll();
        boolean fullTime = "Full-Time".equals(typeBox.getSelectedItem());

        if (fullTime) {
            addField(salaryFields, "Basic monthly salary (EGP)",
                    basicSalaryField, 0, 0);
            addField(salaryFields, "Monthly bonus (EGP)",
                    bonusField, 1, 0);
        } else {
            addField(salaryFields, "Hourly payment rate (EGP)",
                    hourlyRateField, 0, 0);
            addField(salaryFields, "Hours worked per month",
                    hoursWorkedField, 1, 0);
        }
        salaryFields.revalidate();
        salaryFields.repaint();
    }

    private void submitForm() {
        try {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String position = positionField.getText().trim();
            DepartmentName selectedName =
                    (DepartmentName) departmentBox.getSelectedItem();
            Department department = departments.get(selectedName);

            if (name.isEmpty() || position.isEmpty()) {
                throw new IllegalArgumentException("Please complete all required fields.");
            }

            Employee employee;
            if ("Full-Time".equals(typeBox.getSelectedItem())) {
                double basic = parseNonNegative(basicSalaryField, "Basic salary");
                double bonus = parseNonNegative(bonusField, "Bonus");
                employee = new FullTimeEmployee(
                        name, department, age, position, basic, bonus);
            } else {
                double rate = parseNonNegative(hourlyRateField, "Hourly rate");
                double hours = parseNonNegative(hoursWorkedField, "Hours worked");
                employee = new PartTimeEmployee(
                        name, department, age, position, rate, hours);
            }

            onEmployeeAdded.accept(employee);
            clearForm();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Age must be a valid integer and salary fields must be numeric.",
                    "Invalid input", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Invalid employee data", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parseNonNegative(JTextField field, String fieldName) {
        double value = Double.parseDouble(field.getText().trim());
        if (!Double.isFinite(value) || value < 0) {
            throw new IllegalArgumentException(
                    fieldName + " must be a non-negative number.");
        }
        return value;
    }

    private void clearForm() {
        nameField.setText("");
        ageField.setText("");
        positionField.setText("");
        basicSalaryField.setText("");
        bonusField.setText("");
        hourlyRateField.setText("");
        hoursWorkedField.setText("");
        departmentBox.setSelectedIndex(0);
        typeBox.setSelectedIndex(0);
        updateSalaryFields();
    }
}
