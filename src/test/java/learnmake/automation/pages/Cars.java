package learnmake.automation.pages;

public class Cars implements Comparable<Cars>{
   private String name;
   private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Cars(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }





    public int compareTo(Cars obj) {
        if(this.getPrice()>obj.getPrice()){
            return 1;
        }
        else {
            return -1;
        }

    }

}
