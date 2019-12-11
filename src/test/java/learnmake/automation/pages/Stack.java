package learnmake.automation.pages;

public class Stack {
    int array[]=new int[5];
    int temp=0;
    public void push(int value){
        array[temp]=value;
        temp++;
    }
public void show(){
        for(int i=0;i<array.length;i++){
            System.out.println(array[i]+" ");
        }
}
public void pop(){
        temp--;
        int test=0;
        array[temp]=test;

}
}
