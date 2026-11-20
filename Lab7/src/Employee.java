public abstract class Employee {
    private int id;
    private static int employeeCount = 0;
    private String fullName;
    private Department department;
    private int age;
    private String position;

    public Employee(String fullName, Department department, int age, String position) {
        employeeCount++;
        this.id = employeeCount+1000;
        setFullName(fullName);
        setDepartment(department);
        setAge(age);
        setPosition(position);
    }

    // Constructor used when loading employees
    public Employee(int id, String fullName, Department department,
                    int age, String position) {
        this.id = id;
        setFullName(fullName);
        setDepartment(department);
        setAge(age);
        setPosition(position);

        if (id - 1000 > employeeCount) {
            employeeCount = id - 1000;
        }
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        if (department == null) {
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
