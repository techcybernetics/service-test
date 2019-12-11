package learnmake.automation.pages;

public class RunStack {
    public static void main(String args[]){
        Stack stack=new Stack();
        stack.push(2);
        stack.push(4);
        stack.push(67);
        stack.pop();
        stack.show();
    }
}
