package implementations;

/**
 * Node class for doubly linked list implementation.
 * @param <E> the type of data stored in the node
 */
public class MyDLLNode<E> {
    private E data;
    private MyDLLNode<E> next;
    private MyDLLNode<E> previous;

    public MyDLLNode(E data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public MyDLLNode<E> getNext() {
        return next;
    }

    public void setNext(MyDLLNode<E> next) {
        this.next = next;
    }

    public MyDLLNode<E> getPrevious() {
        return previous;
    }

    public void setPrevious(MyDLLNode<E> previous) {
        this.previous = previous;
    }
}