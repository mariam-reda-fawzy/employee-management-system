import java.util.ArrayList;
public class EmployeeManager {
    private ArrayList<Employee> employees;

    public EmployeeManager(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public boolean addEmployee(Employee emp){
        if(emp==null)
            return false;

        for(Employee e: employees)
            if(e.getId()==emp.getId())
                return false;

        employees.add(emp);
        return true;
    }

    public ArrayList<Employee> viewEmployees(){
        return new ArrayList<>(employees);
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
            if(e.getDepartment().toString().equalsIgnoreCase(dep.trim()))
                emps.add(e);

        return emps;
    }

    public boolean updateEmployee(int id, String fullName, Department department, int age, String position){
        Employee emp = searchById(id);

        if(emp==null)
            return false;

        emp.setAge(age);
        emp.setDepartment(department);
        emp.setFullName(fullName);
        emp.setPosition(position);
        return true;
    }

    public boolean updateFullTimeSalary(int id, double basicMonthlySalary, double bonus){
        Employee emp = searchById(id);

        if(emp==null || !(emp instanceof FullTimeEmployee))
            return false;

        FullTimeEmployee e = (FullTimeEmployee) emp;

        e.setBasicMonthlySalary(basicMonthlySalary);
        e.setBonus(bonus);

        return true;
    }

    public boolean updatePartTimeSalary(int id, double hourlyPaymentRate, double hoursWorked){
        Employee emp = searchById(id);

        if(emp==null || !(emp instanceof PartTimeEmployee))
            return false;

        PartTimeEmployee e = (PartTimeEmployee) emp;

        e.setHourlyPaymentRate(hourlyPaymentRate);
        e.setHoursWorked(hoursWorked);

        return true;
    }

    public boolean deleteEmployee(int id){
        Employee emp = searchById(id);

        if(emp==null)
            return false;

        employees.remove(emp);
        return true;
    }
}
