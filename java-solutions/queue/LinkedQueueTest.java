package queue;

public class LinkedQueueTest {
    public static void main(String[] args) {
        LinkedQueue que1 = new LinkedQueue();
        LinkedQueue que2 = new LinkedQueue();

        System.out.println("CHECKING ENQUEUE && SIZE");
        enqueueCheck(que1, "s1 ", 7);
        enqueueCheck(que2, "s2 ", 8);


        System.out.println("\nCHECKING ELEMENT");
        elementCheck(que1, ("s1 " + 0));
        elementCheck(que2, ("s2 " + 0));

        System.out.println("\nCHECKING DEQUEUE && ISEMPTY");
        dequeueCheck(que1, 10);

        System.out.println("\nCHECKING CLEAR && ELEMENT");
        clearCheck(que2);
        emptyElementCheck(que2);

        System.out.println("\nCHECKING PUSH");
        System.out.println("\nEnqueue: ");

        enqueueCheck(que1, "s1 ", 9);
        enqueueCheck(que2, "s2 ", 9);

    }

    public static void enqueueCheck(LinkedQueue que, String queNum, int num){
        for(int i = 0; i < num; i++){
            System.out.println("Iteration: " + (i+1));
            Object a1 = (queNum + i);
            que.enqueue(a1);

            if(que.size() == i+1) {
                System.out.println(que.size() + " new size of queue " + queNum);
            } else {
                error("enqueue");
            }
        }
    }

    public static void dequeueCheck(LinkedQueue que, int num){
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

    public static void clearCheck(LinkedQueue que){
        que.clear();
        if(que.size() == 0){
            System.out.println("Queue successfully cleaned");
        } else {
            error("clear");
        }

    }

    public static void emptyElementCheck(LinkedQueue que){
        que.clear();
        try {
            Object n = que.element();
            error("element");
        } catch (AssertionError e){
            System.out.println("Queue is empty, getting first element is impossible.");
        }
    }
    public static void elementCheck(LinkedQueue que, Object first){
        if((que.element().equals(first) && que.size > 0)) {
            System.out.println(que.element() + " - is the first element que.");
        } else{
            error("element");
        }
    }


    public static void error(String er){
        System.out.println("Error in operation: " + er);
    }

}
