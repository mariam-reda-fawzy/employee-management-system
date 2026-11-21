package appGUI;

import manager.EmployeeManager;
import model.Department;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EmployeesPanel extends JPanel {
    private EmployeeManager manager;

    public EmployeesPanel(EmployeeManager employeeManager, ArrayList<Department> allDepartments) {
        this.manager = manager;
        //this.allDepartments = allDepartments;
    }
}
