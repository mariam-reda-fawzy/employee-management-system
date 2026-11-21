package gui;

import model.Department;
import model.DepartmentName;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class DepartmentPanel extends JPanel {
    private final Map<DepartmentName, Department> departments;
    private final DefaultTableModel model;

    public DepartmentPanel(Map<DepartmentName, Department> departments) {
        this.departments = departments;
        setOpaque(false);
        setLayout(new BorderLayout(0, 14));

        JPanel heading = Theme.card();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Departments");
        title.setFont(Theme.SUBTITLE);
        title.setForeground(Theme.TEXT);
        heading.add(title);
        heading.add(Box.createVerticalStrut(5));
        heading.add(Theme.muted("Departments currently defined in the project."));

        String[] columns = {"model.Department ID", "model.Department name"};
        model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(38);
        table.setFont(Theme.BODY);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(Theme.NAVY);
        table.getTableHeader().setForeground(Color.WHITE);

        JPanel tableCard = Theme.card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        add(heading, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);
        refreshTable();
    }

    public void refreshTable() {
        model.setRowCount(0);
        for (Department department : departments.values()) {
            model.addRow(new Object[]{
                    department.getDepartmentId(),
                    department.getDepartmentName()
            });
        }
    }
}
