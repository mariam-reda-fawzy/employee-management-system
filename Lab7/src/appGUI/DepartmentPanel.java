
package appGUI;

import manager.DepartmentManager;
import model.Department;
import model.DepartmentName;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DepartmentPanel extends JPanel {
    private final DepartmentManager manager;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField idField = new JTextField();
    private final JTextField searchField = new JTextField();

    // Search dropdown
    private final JComboBox<String> searchTypeBox =
            new JComboBox<>(new String[]{"ID", "Name"});

    private final JComboBox<DepartmentName> nameBox = new JComboBox<>();

    private final JLabel resultsLabel = new JLabel();
    private final JLabel activeLabel = new JLabel("0");
    private final JLabel employeesLabel = new JLabel("0");
    private final JLabel totalLabel = new JLabel("0");

    private final JButton addButton = new JButton("Add Department");
    private final JButton deleteButton = new JButton("Delete Selected");


    private Runnable onDepartmentsChanged;

    public DepartmentPanel(DepartmentManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout(0, 18));
        setOpaque(false);


        JPanel stats = new JPanel(new GridLayout(1, 3, 18, 0));
        stats.setOpaque(false);

        stats.add(statCard("ACTIVE DEPARTMENTS", activeLabel,
                "Departments that have employees", LoginFrame.BLUE));

        stats.add(statCard("TOTAL EMPLOYEES", employeesLabel,
                "Across all departments", LoginFrame.NAVY));

        stats.add(statCard("TOTAL DEPARTMENTS", totalLabel,
                "Registered in the company", LoginFrame.ROSE));


        String[] columns = {
                "Department ID", "Department Name", "Employees"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(LoginFrame.PALE);
        table.setSelectionForeground(LoginFrame.NAVY);
        table.setGridColor(LoginFrame.PALE);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));

        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setBackground(LoginFrame.NAVY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 13));

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(2).setCellRenderer(center);

        JLabel listTitle = new JLabel("All Departments");
        listTitle.setFont(new Font("Serif", Font.BOLD, 18));
        listTitle.setForeground(LoginFrame.NAVY);


        JLabel searchLabel = new JLabel("Search by:");
        searchLabel.setForeground(LoginFrame.BLUE);
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        searchTypeBox.setPreferredSize(new Dimension(110, 36));

        searchField.setPreferredSize(new Dimension(230, 36));
        styleField(searchField);

        resultsLabel.setForeground(new Color(0x8995A4));
        resultsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel searchBar = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 4));

        searchBar.setOpaque(false);
        searchBar.add(searchLabel);
        searchBar.add(searchTypeBox);
        searchBar.add(searchField);
        searchBar.add(resultsLabel);

        JPanel listHeader = new JPanel(new BorderLayout(0, 6));
        listHeader.setOpaque(false);
        listHeader.add(listTitle, BorderLayout.NORTH);
        listHeader.add(searchBar, BorderLayout.CENTER);

        JPanel tableCard = new JPanel(new BorderLayout(0, 8));
        tableCard.setBackground(Color.WHITE);

        tableCard.setBorder(new CompoundBorder(
                new LineBorder(LoginFrame.PALE, 1, true),
                new EmptyBorder(14, 14, 14, 14)));

        tableCard.add(listHeader, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(
                BorderFactory.createLineBorder(LoginFrame.PALE));

        scroll.setPreferredSize(
                new Dimension(0, table.getRowHeight() * 5 + 34));

        tableCard.add(scroll, BorderLayout.CENTER);


        JLabel formTitle = new JLabel("Add or Remove a Department");
        formTitle.setFont(new Font("Serif", Font.BOLD, 18));
        formTitle.setForeground(LoginFrame.NAVY);

        JLabel formHint = new JLabel(
                "Enter the department information below");

        formHint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        formHint.setForeground(new Color(0x8995A4));

        JPanel titleRow = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 12, 0));

        titleRow.setOpaque(false);
        titleRow.add(formTitle);
        titleRow.add(formHint);

        idField.setPreferredSize(new Dimension(120, 38));
        styleField(idField);

        nameBox.setPreferredSize(new Dimension(190, 38));

        JLabel idLabel = new JLabel("Department ID");
        JLabel nameLabel = new JLabel("Department Name");

        idLabel.setForeground(LoginFrame.BLUE);
        nameLabel.setForeground(LoginFrame.BLUE);

        idLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        JPanel fieldsRow = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 12, 6));

        fieldsRow.setOpaque(false);
        fieldsRow.add(idLabel);
        fieldsRow.add(idField);
        fieldsRow.add(nameLabel);
        fieldsRow.add(nameBox);


        styleButton(addButton, LoginFrame.ROSE);
        styleButton(deleteButton, LoginFrame.BLUE);


        JPanel buttonsRow = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 12, 6));

        buttonsRow.setOpaque(false);
        buttonsRow.add(addButton);
        buttonsRow.add(deleteButton);


        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);

        form.setBorder(new CompoundBorder(
                new LineBorder(LoginFrame.PALE, 1, true),
                new EmptyBorder(14, 12, 8, 12)));

        form.add(titleRow);
        form.add(fieldsRow);
        form.add(buttonsRow);


        addButton.addActionListener(e -> addDepartment());
        deleteButton.addActionListener(e -> deleteDepartment());


        searchField.getDocument().addDocumentListener(
                new DocumentListener() {

                    public void insertUpdate(DocumentEvent e) {
                        refreshTable();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        refreshTable();
                    }

                    public void changedUpdate(DocumentEvent e) {
                        refreshTable();
                    }
                });

        searchTypeBox.addActionListener(e -> refreshTable());


        JPanel content = new JPanel(
                new BorderLayout(0, 18));

        content.setOpaque(false);
        content.add(stats, BorderLayout.NORTH);
        content.add(tableCard, BorderLayout.CENTER);
        content.add(form, BorderLayout.SOUTH);

        add(content, BorderLayout.NORTH);

        idField.setText(String.valueOf(nextId()));
        refreshTable();
    }

    public void setOnDepartmentsChanged(Runnable callback) {
        this.onDepartmentsChanged = callback;
    }


    private void addDepartment() {
        String idText = idField.getText().trim();

        if (idText.isEmpty()) {
            showError("Please enter a department ID.");
            return;
        }

        int id;

        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            showError("Department ID must be an integer.");
            return;
        }

        if (id <= 0) {
            showError("Department ID must be a positive number.");
            return;
        }

        DepartmentName name =
                (DepartmentName) nameBox.getSelectedItem();

        if (name == null) {
            showError("All department names are already in use.");
            return;
        }

        if (manager.findDepartmentById(id) != null) {
            showError("This department ID already exists.");
            return;
        }

        if (manager.findDepartmentByName(name) != null) {
            showError("This department name already exists.");
            return;
        }

        try {
            manager.addDepartment(new Department(id, name));
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(
                this, "Department added successfully!");

        searchField.setText("");
        idField.setText(String.valueOf(nextId()));

        refreshTable();
        notifyChanged();
    }

    private void deleteDepartment() {
        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department first.",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(
                tableModel.getValueAt(row, 0).toString());

        Department department = manager.findDepartmentById(id);

        if (department == null) {
            showError("Department not found.");
            refreshTable();
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Delete the " + department.getDepartmentName()
                        + " department?",
                "Confirm deletion",
                JOptionPane.YES_NO_OPTION);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            manager.deleteDepartment(department);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Cannot delete department",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(
                this, "Department deleted successfully!");

        idField.setText(String.valueOf(nextId()));

        refreshTable();
        notifyChanged();
    }

    public void refreshTable() {

        String query = searchField.getText()
                .trim().toLowerCase();

        String searchType =
                (String) searchTypeBox.getSelectedItem();

        ArrayList<Department> all =
                manager.getAllDepartments();

        tableModel.setRowCount(0);

        int shown = 0;
        int active = 0;
        int totalEmployees = 0;

        for (Department department : all) {

            int count = countOf(department);
            totalEmployees += count;

            if (count > 0) {
                active++;
            }

            String name =
                    String.valueOf(department.getDepartmentName());

            String id =
                    String.valueOf(department.getDepartmentId());

            if (!query.isEmpty()) {

                if ("ID".equals(searchType)
                        && !id.contains(query)) {
                    continue;
                }

                if ("Name".equals(searchType)
                        && !name.toLowerCase().contains(query)) {
                    continue;
                }
            }

            tableModel.addRow(new Object[]{
                    department.getDepartmentId(),
                    name,
                    count
            });

            shown++;
        }

        resultsLabel.setText("Results: " + shown);

        activeLabel.setText(String.valueOf(active));
        employeesLabel.setText(String.valueOf(totalEmployees));
        totalLabel.setText(String.valueOf(all.size()));

        refreshNameBox();
    }

    private int countOf(Department department) {
        try {
            return manager.countEmployees(department);
        } catch (IllegalArgumentException ex) {
            return 0;
        }
    }

    private void refreshNameBox() {

        DepartmentName previous =
                (DepartmentName) nameBox.getSelectedItem();

        DefaultComboBoxModel<DepartmentName> model =
                new DefaultComboBoxModel<>();

        for (DepartmentName name : DepartmentName.values()) {

            if (manager.findDepartmentByName(name) == null) {
                model.addElement(name);
            }
        }

        nameBox.setModel(model);

        if (previous != null
                && model.getIndexOf(previous) >= 0) {
            nameBox.setSelectedItem(previous);
        }

        addButton.setEnabled(model.getSize() > 0);
    }


    private int nextId() {

        int max = 0;

        for (Department department : manager.getAllDepartments()) {
            max = Math.max(max, department.getDepartmentId());
        }

        return max + 1;
    }

    private void notifyChanged() {
        if (onDepartmentsChanged != null) {
            onDepartmentsChanged.run();
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid input",
                JOptionPane.ERROR_MESSAGE);
    }


    private JPanel statCard(
            String heading,
            JLabel value,
            String note,
            Color accent) {

        JPanel card = new JPanel();

        card.setBackground(LoginFrame.WHITE);

        card.setBorder(new CompoundBorder(
                new LineBorder(LoginFrame.PALE, 1, true),
                new EmptyBorder(18, 20, 18, 20)));

        card.setLayout(new BoxLayout(
                card, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(heading);
        label.setForeground(LoginFrame.BLUE);
        label.setFont(new Font("SansSerif", Font.BOLD, 10));

        value.setForeground(accent);
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


    private static void styleField(JTextField field) {

        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(LoginFrame.NAVY);
        field.setBackground(LoginFrame.LIGHT);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LoginFrame.PALE, 1),
                new EmptyBorder(6, 10, 6, 10)));
    }


    private static void styleButton(
            JButton button,
            Color background) {

        button.setBackground(background);
        button.setForeground(Color.WHITE);

        button.setFont(new Font("SansSerif", Font.BOLD, 13));

        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setPreferredSize(new Dimension(160, 40));
    }
}
