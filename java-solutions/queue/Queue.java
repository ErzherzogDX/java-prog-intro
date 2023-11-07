package queue;

public interface Queue {
    //Pre: true
    int size();
    //Post: size' == size && Res == size - it's current size of the queue  && immutable(size)

    //Pre: true
    boolean isEmpty();
    //Post: Res == (size == 0) && immutable(size)


    //Pre: size >= 1
    Object dequeue();
    //Post: size' == size - 1 && immutable(size') && Res = que[head]

    //Pred: element != null
    void enqueue(Object obj);
    //Post: size' = size+1 && que[tail'] = element && tail' = (tail + 1) % capacity && immutable(size)

    //Pre: true

    void clear();
    //Post: for all i from 0 to size: que[i] == null

    //Pre: true
    Object element();
    //Post: (Res == null && size == 0) || (Res == que[head] && size > 0)



}
