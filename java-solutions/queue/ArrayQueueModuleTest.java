package queue;

public class ArrayQueueModuleTest {

    public static void main(String[] args) {
        System.out.println("CHECKING ENQUEUE && SIZE");
        enqueueCheck("s1 ", 7);

        System.out.println("\nCHECKING ELEMENT");
        elementCheck (("s1 " + 0));

        System.out.println("\nCHECKING PEEK");
        peekCheck(("s1 " + 6));

        System.out.println("\nCHECKING DEQUEUE && ISEMPTY");
        dequeueCheck(10);

        System.out.println("\nCHECKING CLEAR && ELEMENT");
        clearCheck();
        emptyElementCheck();

        System.out.println("\nCHECKING PUSH");
        System.out.println("\nEnqueue: ");

        enqueueCheck("s1 ", 9);
        enqueueCheck("s2 ", 9);

        System.out.println("\nPush: ");
        // pushCheck(que1, "s1 ", 9, 15);
        pushCheck( "s2 ", 9, 15);


        System.out.println("\nCHECKING GET && SET");
        getCheck("s2 ", -5, 20);
        setCheck( -5, 20);
    }

    public static void enqueueCheck(String queNum, int num){
        for(int i = 0; i < num; i++){
            System.out.println("Iteration: " + (i+1));
            Object a1 = (queNum + i);
            ArrayQueueModule.enqueue(a1);

            if(ArrayQueueModule.size() == i+1 && (ArrayQueueModule.peek() == a1)) {
                System.out.println(ArrayQueueModule.peek() + " - is the " + ArrayQueueModule.size() + " element of queue " + queNum);
            } else {
                error("enqueue");
            }
        }
    }

    public static void dequeueCheck( int num){
        for(int i = 0; i < num; i++){
            if (ArrayQueueModule.size() > 0){
                int oldsize = ArrayQueueModule.size();
                Object a = ArrayQueueModule.dequeue();

                if(ArrayQueueModule.size() == oldsize - 1){
                    System.out.println(ArrayQueueModule.size() + " " + a + ". Is empty now?: " + ArrayQueueModule.isEmpty());
                }
                else {
                    error("dequeue");
                }
            } else{
                try {
                    ArrayQueueModule.dequeue();
                    error("dequeue");
                }
                catch (AssertionError er){
                    System.out.println("Queue is empty, dequeue is impossible.");
                }
            }
        }
    }

    public static void clearCheck(){
        ArrayQueueModule.clear();
        if(ArrayQueueModule.size() == 0){
            System.out.println("Queue successfully cleaned");
        } else {
            error("clear");
        }

    }

    public static void emptyElementCheck(){
        ArrayQueueModule.clear();
        try {
            Object n = ArrayQueueModule.element();
            error("element");
        } catch (AssertionError e){
            System.out.println("Queue is empty, getting first element is impossible.");
        }
    }
    public static void elementCheck(Object first){
        if((ArrayQueueModule.element().equals(first) && ArrayQueueModule.size() > 0)) {
            System.out.println(ArrayQueueModule.element() + " - is the first element que.");
        } else{
            error("element");
        }
    }
    public static void peekCheck(Object last){
        if((ArrayQueueModule.peek().equals(last) && ArrayQueueModule.size() > 0)) {
            System.out.println(ArrayQueueModule.peek() + " - is the last element que.");
        } else{
            error("peek");
        }
    }

    public static void pushCheck(String queNum, int from, int to){
        for(int i = from; i <= to; i++){
            System.out.println("Iteration: " + (i+1));
            ArrayQueueModule.push((queNum + i));
            System.out.println(ArrayQueueModule.element() + " - is the " + ArrayQueueModule.size() + " element and the HEAD of queue " + queNum);
        }
    }

    public static void getCheck(String queNum, int from, int to){
        for(int i = from; i < to; i++){
            Object a = (queNum + from);
            if(i >= 0 && i < ArrayQueueModule.size()){
                if(ArrayQueueModule.get(i) != null){
                    System.out.println("Trying to get " + i + " element, it is " + ArrayQueueModule.get(i));
                }
                else error("get");
            }
            else{
                try {
                    System.out.println("Trying to get " + i + " element, it is " + ArrayQueueModule.get(i));
                    error("get");
                } catch (AssertionError e){
                    System.out.println("Fail to get element: incorrect index");
                }
            }
        }
    }

    public static void setCheck( int from, int to){
        for(int i = from; i < to; i++){
            if(i >= 0 && i < ArrayQueueModule.size()){
                ArrayQueueModule.set(i, 100-i);
                if(ArrayQueueModule.get(i).equals(100-i)){
                    System.out.println("Trying to set " + (i) + " element, it is " + ArrayQueueModule.get(i));
                }
                else error("set");
            }
            else{
                try {
                    ArrayQueueModule.set(i, 100-i);
                    System.out.println("Trying to set " + (100-i) + " element, it is " + ArrayQueueModule.get(i));
                    error("set");
                } catch (AssertionError e){
                    System.out.println("Fail to set element: incorrect index");
                }
            }
        }
    }
    public static void error(String er){
        System.out.println("Error in operation: " + er);
    }
}
