package queue;

import java.util.*;

/*
Model: que[1]..que[size]
Invariant: size >= 0 && for i=1..n: que[i] != null

Let immutable(n): for i=1..n: que'[i] == que[i]
The contracts are described below.
*/


public class ArrayQueue extends AbstractQueue {
    private  Object[] elements = new Object[5];
    private  int capacity = elements.length;
    private  int head;
    private  int tail;


    //Pred: element != null
    protected void enqueueImpl(Object element){
        if (this.size == this.capacity) {
            expansion();
        }
        this.elements[this.tail] = element;
        this.tail = (this.tail + 1) % this.capacity;
    }
    //Post: size' = size+1 && que[tail'] = element && tail' = (tail + 1) % capacity && immutable(size)

    //Pre: capacity > 0
    protected void expansion() {
        Object[] newElements = new Object[elements.length * 2];
        int partLength = Math.min(elements.length - head, size);

        System.arraycopy(elements, head, newElements, 0, partLength);
        if (head + size > elements.length) System.arraycopy(elements, 0, newElements, partLength, size - partLength);

        this.elements = newElements;
        this.head = 0;
        this.tail = this.size;
        this.capacity *= 2;
    }
    // Post: capacity' == 2*capacity && size' == size && (que'[i] == que[i] for i = 0..n - 1)

    //Pre: size >= 1
    protected void dequeueImpl(){

        assert size > 0;
        this.elements[this.head] = null;
        this.head = (this.head+1) % this.capacity;
    }

    //Post: size' == size - 1 && immutable(size') && Res = que[head]

    //Pre: true
    protected void clearImpl() {
        for (int i = 0; i < this.capacity; i++) {
            this.elements[i] = null;
        }
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }
    //Post: for all i from 0 to size: que[i] == null

    //Pre: true
    @Override
    protected Object elementImpl() {
        return this.elements[this.head];
    }
    //Post: (Res == null && size == 0) || (Res == que[head] && size > 0)

    public Object peek(){
        if(this.tail == 0){
            return this.elements[this.capacity - 1];
        }
        return this.elements[this.tail - 1];
    }
    //Post: Res = que[0]

    //Pre: size > 0
    public  Object remove() {
        Object res = peek();
        this.tail--;
        if (this.tail < 0) {
            this.tail = this.capacity - 1;
        } this.size--;
        return res;
    }
    //Post: Res = que[size - 1] && size' = size - 1 && immutable(size)

    //Pre: true
    public  void push( Object item) {
        if (this.size == this.capacity) {
            expansion();
        }
        this.head--;
        if (this.head < 0) {
            this.head = this.capacity - 1;
        }
        this.elements[this.head] = item;
        this.size++;
    }
    //Post: size' = size + 1 && que'[0] == item && immutable(size)

    //Pre: index >= 0 && index < size
    public Object get(int index) {
        if (index >= size || index < 0) {
            throw new AssertionError("Fail to get element: index does not match the queue");
        }
        return this.elements[(this.head + index) % this.capacity];
    }
    //Post: Res == que[index]

    //Pre: index >= 0 && index < size && item != null
    public void set(int index, Object item) {
        if (index >= size || index < 0) {
            throw new AssertionError("Fail to set element: index does not match the queue");
        }
        this.elements[(this.head + index) % this.capacity] = item;
    }
    //Post:  que[index] == item

    protected ArrayQueue extraction() {
        return new ArrayQueue();
    }
}
