package fileHandling;

import java.io.*;
import java.util.ArrayList;
import manager.*;
import model.*;

public class FileHandler {
    private static final String EMPLOYEE_FILE = "employees.csv";
    private static final String DEPARTMENT_FILE = "departments.csv";

    public static void saveEmployees(ArrayList<Employee> employees) {
        try (PrintWriter writer = new PrintWriter(EMPLOYEE_FILE)) {
            writer.println("id,type,fullName,departmentId,age,position,basicSalary,bonus,hourlyRate,hoursWorked");
            for (Employee e : employees) {
                if (e instanceof FullTimeEmployee) {
                    FullTimeEmployee f = (FullTimeEmployee) e;
                    writer.println(
                            f.getId() + "," +
                                    "FullTime" + "," +
                                    f.getFullName() + "," +
                                    f.getDepartment().getDepartmentId() + "," +
                                    f.getAge() + "," +
                                    f.getPosition() + "," +
                                    f.getBasicMonthlySalary() + "," +
                                    f.getBonus() + "," +
                                    "0,0"
                    );
                } else if (e instanceof PartTimeEmployee) {
                    PartTimeEmployee p = (PartTimeEmployee) e;
                    writer.println(
                            p.getId() + "," +
                                    "PartTime" + "," +
                                    p.getFullName() + "," +
                                    p.getDepartment().getDepartmentId() + "," +
                                    p.getAge() + "," +
                                    p.getPosition() + "," +
                                    "0,0," +
                                    p.getHourlyPaymentRate() + "," +
                                    p.getHoursWorked()
                    );
                }
            }

        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    public static void saveDepartments(ArrayList<Department> departments) {
        try (PrintWriter writer = new PrintWriter(DEPARTMENT_FILE)) {
            writer.println("departmentId,departmentName");
            for (Department d : departments) {
                writer.println(d.getDepartmentId() + "," + d.getDepartmentName());
            }
        }
        catch (IOException e) {
            System.out.println("Error saving departments: " + e.getMessage());
        }
    }

    public static ArrayList<Department> loadDepartments() {
        ArrayList<Department> departments = new ArrayList<>();

        File file = new File(DEPARTMENT_FILE);
        if (!file.exists()) {
            return departments;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                int id = Integer.parseInt(data[0]);
                DepartmentName name = DepartmentName.valueOf(data[1]);
                departments.add(new Department(id, name));
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading departments: " + e.getMessage());
        }
        return departments;
    }

    public static ArrayList<Employee> loadEmployees(ArrayList<Department> departments) {
        ArrayList<Employee> employees = new ArrayList<>();
        File file = new File(EMPLOYEE_FILE);
        if (!file.exists()) {
            return employees;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);

                int id = Integer.parseInt(data[0]);
                String type = data[1];
                String name = data[2];
                int departmentId = Integer.parseInt(data[3]);
                int age = Integer.parseInt(data[4]);
                String position = data[5];

                Department department = null;

                for (Department d : departments) {
                    if (d.getDepartmentId() == departmentId) {
                        department = d;
                        break;
                    }
                }

                if (department == null) {
                    System.out.println(
                            "Department not found for employee ID: " + id
                    );
                    continue;
                }


                if (type.equals("FullTime")) {
                    double basicSalary = Double.parseDouble(data[6]);
                    double bonus = Double.parseDouble(data[7]);

                    FullTimeEmployee employee = new FullTimeEmployee(id, name, department, age, position, basicSalary, bonus);
                    employees.add(employee);

                } else if (type.equals("PartTime")) {
                    double hourlyRate = Double.parseDouble(data[8]);

                    double hoursWorked = Double.parseDouble(data[9]);

                    PartTimeEmployee employee = new PartTimeEmployee(id, name, department, age, position, hourlyRate, hoursWorked);
                    employees.add(employee);

                } else {
                    System.out.println(
                            "Unknown employee type for ID: " + id
                    );
                }
            }

        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {

            System.out.println("Error loading employees: " + e.getMessage());
        }

        return employees;
    }

}








