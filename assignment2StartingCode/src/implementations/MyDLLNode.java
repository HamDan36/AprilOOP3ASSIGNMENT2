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

    /** 
     * Returns the next node in the DLL
     * Preconditions: a valid MyDLL object must exist
     * Postconditions: the next node in the Dll is returned
     * 
     * @return Return the next node in the DLL
     */
    public MyDLLNode<E> getNext() {
        return next;
    }

    /**
     * Sets the node to the next node in the doubly linked list
     * Preconditions: a valid MyDLL object with more that one node must exist
     * Postconditions: the parameter node is set to the next node in the DLL
     * 
     * @param next The node to be set into the next position in the DLL
     */
    public void setNext(MyDLLNode<E> next) {
        this.next = next;
    }

    /**
     * Returns the previous node in the DLL
     * Preconditions: a valid MyDLL object must exist
     * Postconditions: return to the previous node in the DLL
     * 
     * @return Returns the previous node in the DLL
     */
    public MyDLLNode<E> getPrevious() {
        return previous;
    }

    /**
     * Sets the parameter node to the position of previous node relative to the current node in the DLL
     * Preconditions: a valid MyDLL object must exist with at more than one node
     * Postconditions: the parameter node is set into the previous position in the DLL
     * 
     * @param previous The node to be set into the previous position in the DLL object
     */
    public void setPrevious(MyDLLNode<E> previous) {
        this.previous = previous;
    }
}