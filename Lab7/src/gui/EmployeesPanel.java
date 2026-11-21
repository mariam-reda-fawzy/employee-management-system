package gui;

import model.Employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class EmployeesPanel extends JPanel {
    private final ArrayList<Employee> employees;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField searchField = new JTextField();

    public EmployeesPanel(ArrayList<Employee> employees) {
        this.employees = employees;
        setOpaque(false);
        setLayout(new BorderLayout(0, 14));

        JPanel top = Theme.card();
        top.setLayout(new BorderLayout(12, 0));

        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Employees");
        title.setFont(Theme.SUBTITLE);
        title.setForeground(Theme.TEXT);
        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(4));
        titleBox.add(Theme.muted("Employees added during this application session."));
        top.add(titleBox, BorderLayout.WEST);

        JPanel search = new JPanel(new BorderLayout(8, 0));
        search.setOpaque(false);
        searchField.setPreferredSize(new Dimension(210, 38));
        Theme.styleTextField(searchField);
        JButton searchButton = new JButton("Search ID");
        JButton clearButton = new JButton("Clear");
        Theme.styleButton(searchButton, Theme.PURPLE);
        Theme.styleButton(clearButton, Theme.NAVY);
        search.add(searchField, BorderLayout.CENTER);
        search.add(searchButton, BorderLayout.EAST);
        JPanel searchArea = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        searchArea.setOpaque(false);
        searchArea.add(search);
        searchArea.add(clearButton);
        top.add(searchArea, BorderLayout.EAST);

        String[] columns = {"ID", "Full name", "Age", "model.Department",
                "Position", "Type", "Monthly salary (EGP)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(34);
        table.setFont(Theme.BODY);
        table.setForeground(Theme.TEXT);
        table.setGridColor(Theme.BORDER);
        table.setSelectionBackground(new Color(250, 229, 240));
        table.setSelectionForeground(Theme.TEXT);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(Theme.NAVY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(Theme.BORDER, 1, true));
        scroll.getViewport().setBackground(Theme.WHITE);

        JPanel tableCard = Theme.card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(scroll, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchById());
        clearButton.addActionListener(e -> {
            searchField.setText("");
            refreshTable();
        });
        searchField.addActionListener(e -> searchById());

        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Employee employee : employees) {
            addEmployeeRow(employee);
        }
    }

    private void addEmployeeRow(Employee employee) {
        String type = employee.getClass().getSimpleName()
                .replace("model.Employee", "");
        tableModel.addRow(new Object[]{
                employee.getId(),
                employee.getFullName(),
                employee.getAge(),
                employee.getDepartment().getDepartmentName(),
                employee.getPosition(),
                type,
                String.format("%.2f", employee.calculateSalary())
        });
    }

    private void searchById() {
        String value = searchField.getText().trim();
        if (value.isEmpty()) {
            refreshTable();
            return;
        }

        try {
            int id = Integer.parseInt(value);
            tableModel.setRowCount(0);
            for (Employee employee : employees) {
                if (employee.getId() == id) {
                    addEmployeeRow(employee);
                }
            }
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "No employee found with ID " + id + ".",
                        "Search result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid integer employee ID.",
                    "Invalid search", JOptionPane.ERROR_MESSAGE);
        }
    }
}
