
void main() {

    DepartmentManager dm = new DepartmentManager();
    EmployeeManager em = new EmployeeManager(dm.getAllDepartments());
    dm.setEmployeeManager(em);

}
