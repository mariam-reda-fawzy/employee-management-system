package manager;

import model.*;
import fileHandling.*;
import java.util.ArrayList;
import manager.*;

public class DepartmentManager {
    private static ArrayList <Department> departments;
    private EmployeeManager employeeManager;

    public DepartmentManager()
    {
        this.departments = FileHandler.loadDepartments();
    }

    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public void addDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Invalid department.");
        }
        for (Department d : departments) {
            if (d.getDepartmentId() == department.getDepartmentId()) {
                throw new IllegalArgumentException(
                        "model.Department ID already exists."
                );
            }
            if (d.getDepartmentName() == department.getDepartmentName()) {
                throw new IllegalArgumentException(
                        "model.Department name already exists."
                );
            }
        }
        departments.add(department);
        FileHandler.saveDepartments(departments);
    }

    public int countEmployees(Department department) {
        if (department == null || employeeManager == null) {
            throw new IllegalArgumentException("Invalid department or employee manager.");
        }
        int count = 0;
        for (Employee e : employeeManager.getAllEmployees()) {
            if (e.getDepartment().getDepartmentId()
                    == department.getDepartmentId()) {
                count++;
            }
        }

        return count;
    }

    public void deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            throw new IllegalArgumentException("Department not found.");
        }
        if (countEmployees(department) > 0) {
            throw new IllegalArgumentException("Cannot delete department because it has employees.");
        }
        departments.remove(department);
        FileHandler.saveDepartments(departments);
    }

    public Department findDepartmentById(int id) {
        for (Department d : departments) {
            if (d.getDepartmentId() == id) {
                return d;
            }
        }
        return null;
    }

    public Department findDepartmentByName(DepartmentName name) {
        for (Department d : departments) {
            if (d.getDepartmentName() == name) {
                return d;
            }
        }
        return null;
    }

    public static ArrayList<Department> getAllDepartments() {
        return new ArrayList<>(departments);
    }
    
}
