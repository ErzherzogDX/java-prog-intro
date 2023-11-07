package queue;

public class LinkedQueue extends AbstractQueue{
    private Node head;
    private Node tail;

    public LinkedQueue() {
        head = null;
        tail = null;
    }

    private static class Node {
        private final Object data;
        private Node next;

        public Node(Object data) {
            this.data = data;
            this.next = null;
        }
    }

    protected void enqueueImpl(Object element) {
        Node newNode = new Node(element);

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }
    @Override
    protected Object elementImpl() {
        return head.data;
    }

    protected void dequeueImpl() {
        assert size > 0;

        head = head.next;
        if (size - 1 == 0) {
            tail = null;
        }
    }


    protected void clearImpl() {
        head = null;
        tail = null;
        size = 0;
    }


    protected LinkedQueue extraction() {
        return new LinkedQueue();
    }
}
