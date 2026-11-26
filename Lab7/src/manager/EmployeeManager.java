package manager;

import java.util.ArrayList;
import model.*;
import fileHandling.*;
import java.util.Locale;

public class EmployeeManager {
    private static ArrayList<Employee> employees;

    public EmployeeManager(ArrayList<Department> departments) {
        this.employees = FileHandler.loadEmployees(departments);
    }

    private void saveChanges() {
        FileHandler.saveEmployees(employees);
    }

    public boolean addEmployee(Employee emp){
        if(emp==null)
            return false;

        for(Employee e: employees)
            if(e.getId()==emp.getId())
                return false;

        employees.add(emp);
        saveChanges();
        return true;
    }

    public Employee searchById(int id){
        for(Employee e: employees)
            if(e.getId()==id)
                return e;

        return null;
    }

    public ArrayList<Employee> searchByDepartment(String dep){
        ArrayList<Employee> emps = new ArrayList<>();
        if(dep==null || dep.trim().isEmpty())
            return emps;

        for(Employee e: employees)
            if(e.getDepartment().getDepartmentName().toString().equalsIgnoreCase(dep.trim()))
                emps.add(e);

        return emps;
    }

    public ArrayList<Employee> searchByName(String name){
        ArrayList<Employee> emps = new ArrayList<>();
        if(name==null || name.trim().isEmpty())
            return emps;

        for(Employee e: employees)
            if(e.getFullName().toLowerCase().contains(name.trim().toLowerCase()))
                emps.add(e);

        return emps;
    }

    public boolean updateEmployee(int id, String fullName, Department department, int age, String position){
        Employee emp = searchById(id);

        if(emp==null)
            return false;

        if (fullName == null || fullName.trim().isEmpty()
                || !fullName.matches("[a-zA-Z ]+"))
            throw new IllegalArgumentException("Invalid name.");

        if (department == null)
            throw new IllegalArgumentException("Invalid Department.");

        if (age <= 0)
            throw new IllegalArgumentException("Invalid age.");

        if (position == null || position.trim().isEmpty())
            throw new IllegalArgumentException("Invalid position.");

        emp.setAge(age);
        emp.setDepartment(department);
        emp.setFullName(fullName);
        emp.setPosition(position);

        saveChanges();
        return true;
    }

    public boolean updateFullTimeSalary(int id, double basicMonthlySalary, double bonus){
        Employee emp = searchById(id);

        if(emp==null || !(emp instanceof FullTimeEmployee))
            return false;

        FullTimeEmployee e = (FullTimeEmployee) emp;

        if (basicMonthlySalary < 0 || bonus < 0)
            throw new IllegalArgumentException("Invalid salary information.");

        e.setBasicMonthlySalary(basicMonthlySalary);
        e.setBonus(bonus);

        saveChanges();
        return true;
    }

    public boolean updatePartTimeSalary(int id, double hourlyPaymentRate, double hoursWorked){
        Employee emp = searchById(id);

        if(emp==null || !(emp instanceof PartTimeEmployee))
            return false;

        PartTimeEmployee e = (PartTimeEmployee) emp;

        if (hourlyPaymentRate < 0 || hoursWorked < 0)
            throw new IllegalArgumentException("Invalid payment information.");

        e.setHourlyPaymentRate(hourlyPaymentRate);
        e.setHoursWorked(hoursWorked);

        saveChanges();
        return true;
    }

    public boolean replaceEmployeeType(
            int id,
            String fullName,
            Department department,
            int age,
            String position,
            boolean fullTime,
            double firstSalary,
            double secondSalary
    ) {
        Employee oldEmployee = searchById(id);

        if (oldEmployee == null)
            return false;

        int index = employees.indexOf(oldEmployee);

        Employee newEmployee;

        if (fullTime) {

            newEmployee = new FullTimeEmployee(
                    id,
                    fullName,
                    department,
                    age,
                    position,
                    firstSalary,
                    secondSalary
            );

        } else {

            newEmployee = new PartTimeEmployee(
                    id,
                    fullName,
                    department,
                    age,
                    position,
                    firstSalary,
                    secondSalary
            );
        }

        employees.set(index, newEmployee);

        saveChanges();

        return true;
    }

    public boolean deleteEmployee(int id){
        Employee emp = searchById(id);

        if(emp==null)
            return false;

        employees.remove(emp);
        saveChanges();
        return true;
    }

    public static ArrayList<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
}
