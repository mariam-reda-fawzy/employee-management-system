import java.util.ArrayList;

public class DepartmentManager {
    private ArrayList <Department> departments;

    public DepartmentManager()
    {
        this.departments = new ArrayList<>();
    }
    public void addDepartment(Department department) {
        for (Department d : departments) {
            if (d.getDepartmentId() == department.getDepartmentId()) {
                throw new IllegalArgumentException(
                        "Department ID already exists."
                );
            }
            if (d.getDepartmentName() == department.getDepartmentName()) {
                throw new IllegalArgumentException(
                        "Department name already exists."
                );
            }
        }
        departments.add(department);
    }

    public void deleteDepartment(Department department) // i will need 3adad elemployees fe kol department
    {
    }
    
}
