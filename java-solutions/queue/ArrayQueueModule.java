package queue;

import java.util.*;

/*
Model: que[1]..que[size]
Invariant: size >= 0 && for i=1..size: que[i] != null

Let immutable(size): for i=1..n: que'[i] == que[i]
The contracts are described below.
*/

public class ArrayQueueModule {
    private static Object[] elements = new Object[5];
    private static int size;
    private static int capacity = elements.length;
    private static int head;
    private static int tail;


    //Pred: element != null
    public static void enqueue(final Object element){
        Objects.requireNonNull(element);
        if (size == capacity) {
            expand();
        }
        elements[tail] = element;
        tail = (tail + 1) % capacity;
        size++;
    }
    //Post: size' = size+1 && que[tail'] = element && tail' = (tail + 1) % capacity && immutable(size)

    //Pre: capacity > 0
    private static void expand() {
        Object[] newElements = new Object[capacity * 2];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(head + i) % capacity];
        }
        elements = newElements;
        head = 0;
        tail = size;
        capacity *= 2;
    }
    // Post: capacity' == 2*capacity && size' == size && (que'[i] == que[i] for i = 0..n - 1)

    //Pre: size >= 1
    public static Object dequeue(){
        if (isEmpty()) {
            throw new AssertionError("Fail to dequeue: queue is empty!");
        }
        Object result = elements[head];
        elements[head] = null;
        head = (head+1) % capacity;
        size--;
        return result;
    }
    //Post: size' == size - 1 && immutable(size') && Res = que[head]

    //Pre: true
    public static boolean isEmpty(){
        return (size == 0);
    }
    //Post: Res == (size == 0) && immutable(size)

    //Pre: true
    public static int size() {
        return size;
    }
    //Post: size' == size && Res == size - it's current size of the queue  && immutable(size)

    //Pre: true
    public static void clear() {
        for (int i = 0; i < capacity; i++) {
            elements[i] = null;
        }
        head = 0;
        tail = 0;
        size = 0;
    }
    //Post: for all i from 0 to size: que[i] == null

    //Pre: true
    public static Object element() {
        if (isEmpty()) {
            throw new AssertionError("Fail to get first element: queue is empty!");
        }
        return elements[head];
    }
    //Post: (Res == null && size == 0) || (Res == que[head] && size > 0)

    //Pre: size > 0
    public static Object peek(){
        if(tail == 0){
            return elements[capacity - 1];
        }
        return elements[tail - 1];
    }
    //Post: Res = que[0]

    //Pre: size > 0
    public static Object remove() {
        Object res = peek();
        tail--;
        if (tail < 0) {
            tail = capacity - 1;
        } size--;
        return res;
    }
    //Post: Res = queue[size - 1] && size' = size - 1 && immutable(size)

    //Pre: true
    public static void push(Object item) {
        if (size == capacity) {
            expand();
        }
        head--;
        if (head < 0) {
            head = capacity - 1;
        }
        elements[head] = item;
        size++;
    }
    //Post: size' = size + 1 && que'[0] == item && immutable(size)

    //Pre: index >= 0 && index < size
    public static Object get(int index) {
        if (index >= size || index < 0) {
            throw new AssertionError("Fail to get element: index does not match the queue");
        }
        return elements[(head + index) % capacity];
    }
    //Post: Res == que[index]

    //Pre: index >= 0 && index < size && item != null
    public static void set(int index, Object item) {
        if (index >= size || index < 0) {
            throw new AssertionError("Fail to set element: index does not match the queue");
        }
        elements[(head + index) % capacity] = item;
    }
    //Post:  que[index] == item

}