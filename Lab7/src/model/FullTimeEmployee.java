package model;

public class FullTimeEmployee extends Employee {
    private double basicMonthlySalary;
    private double bonus;

    private void validateSalary(double basicMonthlySalary){
        if (basicMonthlySalary < 0){
            throw new IllegalArgumentException("Salary can't be a negative number");
        }
    }
    private void validateBonus(double bonus){
        if (bonus < 0){
            throw new IllegalArgumentException("Bonus can't be a negative number");
        }
    }
    public FullTimeEmployee(String fullName, Department department, int age, String position, double basicMonthlySalary, double bonus) {
        super(fullName, department, age, position);

        validateSalary(basicMonthlySalary);
        this.basicMonthlySalary = basicMonthlySalary;

        validateBonus(bonus);
        this.bonus = bonus;
    }

    // Constructor for loading
    public FullTimeEmployee(int id, String fullName,
                            Department department, int age, String position,
                            double basicMonthlySalary, double bonus) {

        super(id, fullName, department, age, position);
        this.basicMonthlySalary = basicMonthlySalary;
        this.bonus = bonus;
    }


    public double getBasicMonthlySalary() {
        return basicMonthlySalary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBasicMonthlySalary(double basicMonthlySalary) {
        validateSalary(basicMonthlySalary);
        this.basicMonthlySalary = basicMonthlySalary;
    }

    public void setBonus(double bonus) {
        validateBonus(bonus);
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary(){
        return basicMonthlySalary + bonus;
    }

//    @Override
//    public String getEmployeeType() {
//        return "Full-Time";
//    }

}
