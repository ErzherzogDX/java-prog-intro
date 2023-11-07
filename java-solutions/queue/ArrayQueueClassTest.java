package queue;

public class ArrayQueueClassTest {

    public static void main(String[] args) {
        ArrayQueue que1 = new ArrayQueue();
        ArrayQueue que2 = new ArrayQueue();

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

    public static void enqueueCheck(ArrayQueue que, String queNum, int num){
        for(int i = 0; i < num; i++){
            System.out.println("Iteration: " + (i+1));
            Object a1 = (queNum + i);
            que.enqueue(a1);

            if(que.size() == i+1 && (que.peek() == a1)) {
                System.out.println(que.peek() + " - is the " + que.size() + " element of queue " + queNum);
            } else {
                error("enqueue");
            }
        }
    }

    public static void dequeueCheck(ArrayQueue que, int num){
        for(int i = 0; i < num; i++){
            if (que.size() > 0){
                int oldsize = que.size();
                Object a = que.dequeue();

                if(que.size() == oldsize - 1){
                    System.out.println(que.size() + " " + a + ". Is empty now?: " + que.isEmpty());
                }
                else {
                    error("dequeue");
                }
            } else{
                try {
                    que.dequeue();
                    error("dequeue");
                }
                catch (AssertionError er){
                    System.out.println("Queue is empty, dequeue is impossible.");
                }
            }
        }
    }

    public static void clearCheck(ArrayQueue que){
        que.clear();
        if(que.size() == 0){
            System.out.println("Queue successfully cleaned");
        } else {
            error("clear");
        }

    }

    public static void emptyElementCheck(ArrayQueue que){
        que.clear();
        try {
            Object n = que.element();
            error("element");
        } catch (AssertionError e){
            System.out.println("Queue is empty, getting first element is impossible.");
        }
    }
    public static void elementCheck(ArrayQueue que, Object first){
        if((que.element().equals(first) && que.size > 0)) {
            System.out.println(que.element() + " - is the first element que.");
        } else{
            error("element");
        }
    }
    public static void peekCheck(ArrayQueue que, Object last){
        if((que.peek().equals(last) && que.size > 0)) {
            System.out.println(que.peek() + " - is the last element que.");
        } else{
            error("peek");
        }
    }

    public static void pushCheck(ArrayQueue que, String queNum, int from, int to){
        for(int i = from; i <= to; i++){
            System.out.println("Iteration: " + (i+1));
            que.push((queNum + i));
            System.out.println(que.element() + " - is the " + que.size() + " element and the HEAD of queue " + queNum);
        }
    }

    public static void getCheck(ArrayQueue que, String queNum, int from, int to){
        que.expansion();
        for(int i = from; i < to; i++){
            if(i >= 0 && i < que.size()){
                if(que.get(i).equals(que.get(i))){
                    System.out.println("Trying to get " + i + " element, it is " + que.get(i));
                }
                else error("get");
            }
            else{
                try {
                    System.out.println("Trying to get " + i + " element, it is " + que.get(i));
                    error("get");
                } catch (AssertionError e){
                    System.out.println("Fail to get element: incorrect index");
                }
            }
        }
    }

    public static void setCheck(ArrayQueue que, int from, int to){
        for(int i = from; i < to; i++){
            if(i >= 0 && i < que.size()){
                que.set(i, 100-i);
                if(que.get(i).equals(100-i)){
                    System.out.println("Trying to set " + (i) + " element, it is " + que.get(i));
                }
                else error("set");
            }
            else{
                try {
                    que.set(i, 100-i);
                    System.out.println("Trying to set " + (100-i) + " element, it is " + que.get(i));
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
