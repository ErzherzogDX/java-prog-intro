package queue;

public class ArrayQueueADTTest {

    public static void main(String[] args) {
        ArrayQueueADT que1 =  ArrayQueueADT.create();
        ArrayQueueADT que2 =  ArrayQueueADT.create();

        System.out.println("CHECKING ENQUEUE && SIZE");
        enqueueCheck(que1, "s1 ", 7);
        enqueueCheck(que2, "s2 ", 8);


        System.out.println("\nCHECKING ELEMENT");
        elementCheck(que1, ("s1 " + 0));
        elementCheck(que2, ("s2 " + 0));

        System.out.println("\nCHECKING PEEK");
        peekCheck(que1, ("s1 " + 6));
        peekCheck(que2, ("s2 " + 7));

        System.out.println("\nCHECKING DEQUEUE && ISEMPTY");
        dequeueCheck(que1, 10);

        System.out.println("\nCHECKING CLEAR && ELEMENT");
        clearCheck(que2);
        emptyElementCheck(que2);

        System.out.println("\nCHECKING PUSH");
        System.out.println("\nEnqueue: ");

        enqueueCheck(que1, "s1 ", 9);
        enqueueCheck(que2, "s2 ", 9);

        System.out.println("\nPush: ");
        // pushCheck(que1, "s1 ", 9, 15);
        pushCheck(que2, "s2 ", 9, 15);


        System.out.println("\nCHECKING GET && SET");
        getCheck(que2, "s2 ", -5, 20);
        setCheck(que2, -5, 20);
    }

    public static void enqueueCheck(ArrayQueueADT que, String queNum, int num){
        for(int i = 0; i < num; i++){
            System.out.println("Iteration: " + (i+1));
            Object a1 = (queNum + i);
            ArrayQueueADT.enqueue(que, a1);

            if(ArrayQueueADT.size(que) == i+1 && (ArrayQueueADT.peek(que) == a1)) {
                System.out.println(ArrayQueueADT.peek(que) + " - is the " + ArrayQueueADT.size(que) + " element of queue " + queNum);
            } else {
                error("enqueue");
            }
        }
    }

    public static void dequeueCheck(ArrayQueueADT que, int num){
        for(int i = 0; i < num; i++){
            if (ArrayQueueADT.size(que) > 0){
                int oldsize = ArrayQueueADT.size(que);
                Object a = ArrayQueueADT.dequeue(que);

                if(ArrayQueueADT.size(que) == oldsize - 1){
                    System.out.println(ArrayQueueADT.size(que) + " " + a + ". Is empty now?: " + ArrayQueueADT.isEmpty(que));
                }
                else {
                    error("dequeue");
                }
            } else{
                try {
                    ArrayQueueADT.dequeue(que);
                    error("dequeue");
                }
                catch (AssertionError er){
                    System.out.println("Queue is empty, dequeue is impossible.");
                }
            }
        }
    }

    public static void clearCheck(ArrayQueueADT que){
        ArrayQueueADT.clear(que);
        if(ArrayQueueADT.size(que) == 0){
            System.out.println("Queue successfully cleaned");
        } else {
            error("clear");
        }

    }

    public static void emptyElementCheck(ArrayQueueADT que){
        ArrayQueueADT.clear(que);
        try {
            Object n = ArrayQueueADT.element(que);
            error("element");
        } catch (AssertionError e){
            System.out.println("Queue is empty, getting first element is impossible.");
        }
    }
    public static void elementCheck(ArrayQueueADT que, Object first){
        if((ArrayQueueADT.element(que).equals(first) && ArrayQueueADT.size(que) > 0)) {
            System.out.println(ArrayQueueADT.element(que) + " - is the first element que.");
        } else{
            error("element");
        }
    }
    public static void peekCheck(ArrayQueueADT que, Object last){
        if((ArrayQueueADT.peek(que).equals(last) && ArrayQueueADT.size(que) > 0)) {
            System.out.println(ArrayQueueADT.peek(que) + " - is the last element que.");
        } else{
            error("peek");
        }
    }

    public static void pushCheck(ArrayQueueADT que, String queNum, int from, int to){
        for(int i = from; i <= to; i++){
            System.out.println("Iteration: " + (i+1));
            ArrayQueueADT.push(que,(queNum + i));
            System.out.println(ArrayQueueADT.element(que) + " - is the " + ArrayQueueADT.size(que) + " element and the HEAD of queue " + queNum);
        }
    }

    public static void getCheck(ArrayQueueADT que, String queNum, int from, int to){
        for(int i = from; i < to; i++){
            if(i >= 0 && i < ArrayQueueADT.size(que)){
                if(ArrayQueueADT.get(que, i) != null){
                    System.out.println("Trying to get " + i + " element, it is " + ArrayQueueADT.get(que, i));
                }
                else error("get");
            }
            else{
                try {
                    System.out.println("Trying to get " + i + " element, it is " + ArrayQueueADT.get(que,i));
                    error("get");
                } catch (AssertionError e){
                    System.out.println("Fail to get element: incorrect index");
                }
            }
        }
    }

    public static void setCheck(ArrayQueueADT que, int from, int to){
        for(int i = from; i < to; i++){
            if(i >= 0 && i < ArrayQueueADT.size(que)){
                ArrayQueueADT.set(que,i, 100-i);
                if(ArrayQueueADT.get(que,i).equals(100-i)){
                    System.out.println("Trying to set " + (i) + " element, it is " + ArrayQueueADT.get(que,i));
                }
                else error("set");
            }
            else{
                try {
                    ArrayQueueADT.set(que,i, 100-i);
                    System.out.println("Trying to set " + (100-i) + " element, it is " + ArrayQueueADT.get(que,i));
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
