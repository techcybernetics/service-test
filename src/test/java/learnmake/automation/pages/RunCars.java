package learnmake.automation.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RunCars  {
    public static void main(String args[]){
        List<Cars> car=new ArrayList<>();
        car.add(new Cars("Tesla",35000));
        car.add(new Cars("Mazda",15000));
        car.add(new Cars("Honda",11000));
        car.add(new Cars("Kia",10000));
        car.add(new Cars("Audi",750000));

        Collections.sort(car);
        for(Cars c:car){
            System.out.println(c);
        }

    }



}
