public class Department {
    private int departmentId;
    private DepartmentName departmentName;

    public Department(int departmentId, DepartmentName departmentName)
    {
        setDepartmentId(departmentId);
        setDepartmentName(departmentName);
    }


    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        if (departmentId<=0)
        {
            throw new IllegalArgumentException("Invalid department id.");
        }
        this.departmentId = departmentId;
    }

    public DepartmentName getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(DepartmentName departmentName) {
        if (departmentName == null) {
            throw new IllegalArgumentException("Invalid department name.");
        }
        this.departmentName = departmentName;
    }
}
