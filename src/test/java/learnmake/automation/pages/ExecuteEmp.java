package learnmake.automation.pages;


import java.util.*;
import java.util.Stack;

public class ExecuteEmp {

    public static void main(String args[]) {
        LinkedList<Integer>linkedList=new LinkedList<>();

        List<Employee> emp=new ArrayList<>();
        emp.add(new Employee(228344,"David","Infra"));
        emp.add(new Employee(334545,"Ron","IT"));
        emp.add(new Employee(56676,"Jeremy","Tech"));
        emp.add(new Employee(12232,"Houton","Support"));
        /*Collections.sort(emp);
        for(Employee em:emp){
            System.out.println(em);
        }*/
        Comparator<Employee>employeeComparator=new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                if(o1.getEmpID()>o2.getEmpID()){
                    return 1;
                }
                else
                    return -1;
            }
        };
        Collections.sort(emp,employeeComparator);
        for(Employee em:emp){
            System.out.println(em);
        }
}





}