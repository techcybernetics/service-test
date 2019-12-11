package learnmake.automation.pages;



public class Employee implements Comparable<Employee>{
    private int empID;
    private String empName;
    private String dept;

    public Employee(int empID, String empName, String dept) {
        this.empID = empID;
        this.empName = empName;
        this.dept = dept;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empID=" + empID +
                ", empName='" + empName + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }

    @Override
    public int compareTo(Employee emp) {
        if(this.empID>emp.empID){
            return 1;
        }
        else {
            return -1;
        }
    }
}
