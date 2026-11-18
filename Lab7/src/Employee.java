public abstract class Employee {
    private int id;
    private static int employeeCount = 0;
    private String fullName;
    private String department; // change to enum department when we make it
    private int age;
    private String position;

    public Employee(String fullName, String department, int age, String position) {
        employeeCount++;
        this.id = employeeCount+1000;
        setFullName(fullName);
        setDepartment(department);
        setAge(age);
        setPosition(position);
    }

    public static int getEmployeeCount() {
        return employeeCount;
    }

    public static void setEmployeeCount(int employeeCount) {
        Employee.employeeCount = employeeCount;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty() || !fullName.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Invalid name.");
        }
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) { //fix validating when making it enum
        if (department == null || department.trim().isEmpty() || !department.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Invalid Department.");
        }
        this.department = department;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age<=0) {
            throw new IllegalArgumentException("Invalid age.");
        }
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid position.");
        }
        this.position = position;
    }

    abstract public double calculateSalary();
}
