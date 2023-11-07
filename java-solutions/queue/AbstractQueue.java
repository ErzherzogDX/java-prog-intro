package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue{
    protected int size;

    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return (size == 0);
    }

    public Object dequeue(){
        if (isEmpty()) {
            throw new AssertionError("Fail to dequeue: queue is empty!");
        }
        Object result = element();
        dequeueImpl();
        size--;
        return result;
    }

    public void enqueue(Object element){
        Objects.requireNonNull(element);
        enqueueImpl(element);
        size++;
    }

    public Object element() {
        assert size > 0;
        if (isEmpty()) {
            throw new AssertionError("Fail to get first element: queue is empty!");
        }
        return elementImpl();
    }

    //Pre: n > 0
    public AbstractQueue getNth(int n){
        int count = 1;
        AbstractQueue res  = extraction();
        int siz = size();
        while (count <= siz) {
            Object obj = dequeue();
            if (count % n == 0) {
                res.enqueue(obj);
            }
            count++;
            enqueue(obj);
        }
        return res;
    }
    //Post: size = size' && Res = {que[1 * n], que[2 * n] ... que[size / n]} && immutable(size)

    //Pre: n > 0
    public AbstractQueue removeNth(int n){
        AbstractQueue res  = extraction();

        int count = 1; int siz = size();
        while (count != siz+1) {
            Object a = dequeue();
            if (count % n == 0) {
                res.enqueue(a);
            } else {
                enqueue(a);
            }
            count++;
        }
        return res;
    }
    //Post: size = size' - (size' / n) && Res = {que'[1 * n], que'[2 * n] ... que'[size / n]}
    //&& for all i = 1 to size: que[i] = que'[i + (i-1)/(n-1)]


    //Pre: n > 0
    public void dropNth(int n) {
        int siz = size();

        for (int i = 0; i < siz; i++) {
            Object a = dequeue();
            if ((i + 1) % n != 0) {
                enqueue(a);
            }
        }
    }
    //Post: size = size' - (size' / n) && for all i = 1 to size: que[i] = que'[i + (i-1)/(n-1)]



    public void clear(){
        clearImpl();
    }
    protected abstract Object elementImpl();
    protected abstract void dequeueImpl();
    protected abstract void enqueueImpl(Object obj);

    protected abstract AbstractQueue extraction();

    protected abstract void clearImpl();


}
