import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    private static final String EMPLOYEE_FILE = "employees.csv";
    private static final String DEPARTMENT_FILE = "departments.csv";

    public static void saveEmployees(ArrayList<Employee> employees) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EMPLOYEE_FILE))) {
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
        try (PrintWriter writer = new PrintWriter(new FileWriter(DEPARTMENT_FILE))) {
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

}







