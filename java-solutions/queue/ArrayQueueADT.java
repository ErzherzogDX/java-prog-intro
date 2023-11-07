package queue;

import java.util.*;

/*
Model: a[1]..a[size]
Invariant: size >= 0 && for i=1..n: a[i] != null

Let immutable(n): for i=1..n: a'[i] == a[i]
The contracts are described below.
*/

public class ArrayQueueADT {
    private  Object[] elements = new Object[5];
    private  int size;
    private  int capacity = elements.length;
    private  int head;
    private  int tail;

    public static ArrayQueueADT create(){
        final ArrayQueueADT que = new ArrayQueueADT();
        que.elements = new Object[5];
        return que;
    }

    public static void enqueue(final ArrayQueueADT que, final Object element){
        Objects.requireNonNull(element);
        if (que.size == que.capacity) {
            expand(que);
        }
        que.elements[que.tail] = element;
        que.tail = (que.tail + 1) % que.capacity;
        que.size++;
    }

    private static void expand(final ArrayQueueADT que) {
        Object[] newElements = new Object[que.capacity * 2];
        for (int i = 0; i < que.size; i++) {
            newElements[i] = que.elements[(que.head + i) % que.capacity];
        }
        que.elements = newElements;
        que.head = 0;
        que.tail = que.size;
        que.capacity *= 2;
    }

    public static Object dequeue(final ArrayQueueADT que){
        if (isEmpty(que)) {
            throw new AssertionError("Fail to dequeue: queue is empty!");
        }
        Object result = que.elements[que.head];
        que.elements[que.head] = null;
        que.head = (que.head+1) % que.capacity;
        que.size--;
        return result;
    }

    public static boolean isEmpty(final ArrayQueueADT que){
        return (que.size == 0);
    }

    public static int size(final ArrayQueueADT que) {
        return que.size;
    }

    public static void clear(final ArrayQueueADT que) {
        for (int i = 0; i < que.capacity; i++) {
            que.elements[i] = null;
        }
        que.head = 0;
        que.tail = 0;
        que.size = 0;
    }

    public static Object element(final ArrayQueueADT que) {
        if (isEmpty(que)) {
            throw new AssertionError("Fail to get first element: queue is empty!");
        }
        return que.elements[que.head];
    }

    public static Object peek(final ArrayQueueADT que){
        if(que.tail == 0){
            return que.elements[que.capacity - 1];
        }
        return que.elements[que.tail - 1];
    }
    //Post: Res = que[0]

    //Pre: size > 0
    public static Object remove(final ArrayQueueADT que) {
        Object res = peek(que);
        que.tail--;
        if (que.tail < 0) {
            que.tail = que.capacity - 1;
        } que.size--;
        return res;
    }
    //Post: Res = queue[size - 1] && size' = size - 1 && immutable(size)

    //Pre: true
    public static void push(final ArrayQueueADT que, Object item) {
        if (que.size == que.capacity) {
            expand(que);
        }
        que.head--;
        if (que.head < 0) {
            que.head = que.capacity - 1;
        }
        que.elements[que.head] = item;
        que.size++;
    }
    //Post: size' = size + 1 && que'[0] == item && immutable(size)

    //Pre: index >= 0 && index < size
    public static Object get(final ArrayQueueADT que,int index) {
        if (index >= que.size || index < 0) {
            throw new AssertionError("Fail to set element: index does not match the queue");
        }
        return que.elements[(que.head + index) % que.capacity];
    }
    //Post: Res == que[index]

    //Pre: index >= 0 && index < size && item != null
    public static void set(final ArrayQueueADT que,int index, Object item) {
        if (index >= que.size || index < 0) {
            throw new AssertionError("Fail to set element: index does not match the queue");
        }
        que.elements[(que.head + index) % que.capacity] = item;
    }
    //Post:  que[index] == item


}
