package appGUI;

import manager.DepartmentManager;

import javax.swing.*;

public class DepartmentPanel extends JPanel {
    private DepartmentManager manager;

    public DepartmentPanel(DepartmentManager departmentManager) {
        this.manager = manager;
    }
}
