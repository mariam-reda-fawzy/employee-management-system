public class PartTimeEmployee extends Employee{
    private double hourlyPaymentRate;
    private double hoursWorked;

    private void validatePaymentRate(double hourlyPaymentRate){
        if (hourlyPaymentRate < 0){
            throw new IllegalArgumentException("Hourly payment rate can't be a negative number");
        }
    }

    private void validateHoursWorked(double hoursWorked){
        if (hoursWorked < 0){
            throw new IllegalArgumentException("Number of hours worked can't be a negative number");
        }
    }

    public PartTimeEmployee(String fullName, Department department, int age, String position, double hourlyPaymentRate, double hoursWorked) {
        super(fullName, department, age, position);

        validatePaymentRate(hourlyPaymentRate);
        this.hourlyPaymentRate = hourlyPaymentRate;

        validateHoursWorked(hoursWorked);
        this.hoursWorked = hoursWorked;
    }

    // Constructor for loading
    public PartTimeEmployee(int id, String fullName,
                            Department department, int age, String position,
                            double hourlyPaymentRate, double hoursWorked) {

        super(id, fullName, department, age, position);

        this.hourlyPaymentRate = hourlyPaymentRate;
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyPaymentRate() {
        return hourlyPaymentRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHourlyPaymentRate(double hourlyPaymentRate) {
        validatePaymentRate(hourlyPaymentRate);
        this.hourlyPaymentRate = hourlyPaymentRate;
    }

    public void setHoursWorked(double hoursWorked) {
        validateHoursWorked(hoursWorked);
        this.hoursWorked = hoursWorked;
    }
    @Override
    public double calculateSalary(){
        return hourlyPaymentRate*hoursWorked;
    }
}
