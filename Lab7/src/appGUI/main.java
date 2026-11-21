package appGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import manager.*;
import model.Department;
import model.Employee;
import fileHandling.*;

public class main {
    public static void main(String[] args) {
        DepartmentManager departmentManager = new DepartmentManager();
        EmployeeManager employeeManager =
                new EmployeeManager(departmentManager.getAllDepartments());
        departmentManager.setEmployeeManager(employeeManager);
        new LoginFrame(employeeManager, departmentManager);
    }
}
