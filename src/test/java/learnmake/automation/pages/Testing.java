package learnmake.automation.pages;

import java.util.Stack;

public class Testing {
    int n1=0,n2=1,n3=0;
    public void checkPalindrom(String actual){
        char[]temp=actual.toCharArray();
        String expected="";
        for(int i=actual.length()-1;i>=0;i--){
            expected=expected+temp[i];
        }
        System.out.println(expected);
    }
public void reverseArray(int[] array,int length){
        int temp=length;
        int[]reverse=new int[temp];
        for(int i=0;i<array.length;i++){
            reverse[temp-1]=array[i];
            temp=temp-1;
        }
        for(int j=0;j<reverse.length;j++){
            System.out.println(reverse[j]+" ");
        }
}

public void fibonacci(int count){


       if(count>0){
           n3=n2+n1;
           n1=n2;
           n2=n3;
           System.out.println(""+n3);
           fibonacci(count-1);
       }

        }

    public static void main(String args[]){
        Stack<Integer>testing=new Stack<Integer>();
        testing.push(12);
        testing.push(13);
        System.out.println(testing.pop());
        Testing test=new Testing();
        int num=12;

        test.fibonacci(num-2);
        test.checkPalindrom("2edrrt");
        int[]ar={2,3,4,523,345,67,77};
        int length=ar.length;
        test.reverseArray(ar,length);

    }
}
