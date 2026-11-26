# Employee Management System

A Java desktop application for managing employees and departments. The project uses **Java Swing** for its graphical user interface and object-oriented programming (OOP) to represent employee types, calculate salaries, and separate the interface from the management and file-handling logic.

## Features

- Login screen and dashboard.
- Add, view, update, and delete employee records.
- Search employees by ID or name.
- Filter employees by department.
- Manage departments.
- Calculate salaries according to employee type:
  - **Full-time:** basic monthly salary + monthly bonus.
  - **Part-time:** hourly payment rate × hours worked per month.
- Save and load employee and department records using CSV files.

## Project structure

```text
Lab7/
├── src/
│   ├── appGUI/
│   │   ├── LoginFrame.java
│   │   ├── MainFrame.java
│   │   ├── EmployeesPanel.java
│   │   ├── DepartmentPanel.java
│   │   └── main.java
│   ├── fileHandling/
│   │   └── FileHandler.java
│   ├── manager/
│   │   ├── EmployeeManager.java
│   │   └── DepartmentManager.java
│   └── model/
│       ├── Employee.java
│       ├── FullTimeEmployee.java
│       ├── PartTimeEmployee.java
│       ├── Department.java
│       └── DepartmentName.java
├── employees.csv
└── departments.csv
```

### Package responsibilities

- `model`: employee and department classes. `Employee` is an abstract base class; `FullTimeEmployee` and `PartTimeEmployee` provide their own salary calculations.
- `manager`: operations for managing employee and department records.
- `fileHandling`: reads and writes employee and department data.
- `appGUI`: Swing windows and panels for login, dashboard, employee management, and department management.

## Requirements

- Java Development Kit (JDK) installed.
- An IDE such as IntelliJ IDEA, Eclipse, or VS Code with Java support, or a terminal.
- No external libraries are required by the source files in this project.

## Run the application

### Using IntelliJ IDEA

1. Extract the ZIP file.
2. Open the `Lab7` folder as a project in IntelliJ IDEA.
3. Make sure the project SDK is set to an installed JDK.
4. In the Project view, open `src/appGUI/main.java`.
5. Run the `main` method in that file.

**Important:** The application reads and writes `employees.csv` and `departments.csv` using relative file paths. Set the run configuration's **Working directory** to the `Lab7` folder so the CSV files beside the source folder are used.

### Using the terminal

Open a terminal in the `Lab7` folder, then compile and run:

```bash
javac -d out src/model/*.java src/fileHandling/*.java src/manager/*.java src/appGUI/*.java
java -cp out appGUI.main
```

If your environment uses a different shell, adjust the wildcard syntax as needed.

## Login credentials

The login screen currently accepts these demonstration accounts:

| Username | Password |
|---|---|
| `admin` | `admin123` |
| `Heba Kholief` | `10061` |
| `Menna Kholief` | `10040` |
| `mariamreda` | `10032` |
| `Aya Ahmed` | `10493` |
| `Tarek` | `1234` |
| `Kiro` | `1234` |

## Data storage

The application stores data in two CSV files in its working directory:

- `employees.csv` — employee ID, employee type, name, department ID, age, position, and type-specific salary values.
- `departments.csv` — department ID and department name.

The files are loaded when the application starts and updated by the relevant manager/file-handling operations. Keep these files if you want to retain the records between runs. Back them up before manually editing them.

## OOP concepts demonstrated

- **Encapsulation:** fields in the model classes are private and accessed through methods.
- **Inheritance:** full-time and part-time employee classes extend `Employee`.
- **Polymorphism:** each employee subtype implements `calculateSalary()` differently.
- **Abstraction:** `Employee` is abstract and defines the common employee structure and salary contract.

## Group information
Student IDs: 
- `Aya Ahmed ElSayed - 10493`
- `Mariam Reda Fawzy - 10032`
- `Hebatullah Mohamed Kholief - 10061`
- `Mennatullah Mohamed Kholief - 10040`

