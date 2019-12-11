package learnmake.automation.pages;



import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.TreeMap;

public class Sorting {

    public void bubbleSort(int[] array){
        int temp=0;

        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j]>array[j+1]){
                    temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
        }
        System.out.println(array.toString());

    }
    public void reverseArray(int[] array){
        int[] reverseArray=new int[array.length];
        int k=array.length;
        for(int i=0;i<array.length-1;i++){
            reverseArray[k-1]=array[i];
            k=k-1;


        }
        for(int j=0;j<reverseArray.length-1;j++){
            System.out.println(reverseArray[j]);
        }
    }

    public boolean checkAnagram(String actual,String expected){
        boolean result=false;
        actual=actual.replace("//s","").toLowerCase();
        expected=expected.replace("//s","").toLowerCase();
        char[] actual1=actual.toCharArray();
        char[] expected2=expected.toCharArray();
        Arrays.sort(actual1);
        Arrays.sort(expected2);
        result=Arrays.equals(actual1,expected2);
        if(result==true){
            System.out.println("The values are same");
        }
        else{
            System.out.println("The values are not matching");
        }
        return result;
    }
    public void recursive(String input){
        System.out.println("Calling recursive");
        if(input!="hello"){
           recursive(input);

        }
    }


    public static void main(String args[]) {

        for(int i=0;i<=50;i++){
            if(i%3==0) {
                System.out.println("Fizz" +"iteration: "+i);
            }
               else if(i%5==0){
                    System.out.println("Buzz"+"iteration:"+i);

                }
               else if(i%5*3==0){
                   System.out.println("FizzBuzz"+"iteration:"+i);
            }


        }
        StringBuffer str=new StringBuffer("test");
System.out.println(str.append("test test"));

        Sorting test=new Sorting();
        test.recursive("hello");

        test.checkAnagram("abcderr","rredabc");
        int arr[] ={3,60,35,2,45,320,5};
        test.reverseArray(arr);
        test.bubbleSort(arr);
        int[] intArray = new int[]{ 1,2,3,4,5,6,7,8,9,10 };
        int sum=0;
        for(int i=0;i<intArray.length-1;i++){
            sum=sum+intArray[i];

        }
        System.out.println(sum);
    }
}