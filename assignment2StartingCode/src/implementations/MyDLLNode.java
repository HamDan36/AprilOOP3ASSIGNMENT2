package implementations;

/**
 * Node class for doubly linked list implementation.
 * @param <E> the type of data stored in the node
 */
public class MyDLLNode<E> {
    private E data;
    private MyDLLNode<E> next;
    private MyDLLNode<E> previous;

    /**
     * Constructor for a doubly linked list containing the specified data
     * Preconditions: none
     * Postconditions: a new MyDLL doubly linked list object is created containing the parameter data
     * 
     * @param data Data to be inserted into the new MyDLL object
     */
    public MyDLLNode(E data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }

    /**
     * Returns the data from the node
     * Preconditions: a valid MyDLL object must exist
     * Postconditions: the data from the node is returned
     * 
     * @return Returns the data from the node
     */
    public E getData() {
        return data;
    }

    /**
     * Sets the data into the node
     * Preconditions: a valid MyDLL object must exist
     * Postconditions: the data in the node is set to the parameter data
     * 
     * @param data Data to be placed into the node
     */
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