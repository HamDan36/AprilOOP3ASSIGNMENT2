package implementations;

import utilities.QueueADT;
import utilities.Iterator;
import exceptions.EmptyQueueException;

/**
 * Implementation of QueueADT using MyDLL as the underlying data structure.
 * @param <E> the type of elements held in this queue
 */
public class MyQueue<E> implements QueueADT<E> {
    private MyDLL<E> list;
    
    /**
     * Constructor that initializes an empty queue.
     * Preconditions: none
     * Postconditions: a new empty queue is created
     */
    public MyQueue() {
        list = new MyDLL<>();
    }

    /**
     * Adds an element to the end of the queue.
     * Preconditions: toAdd must not be null
     * Postconditions: toAdd is added to the end of the queue
     */
    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot enqueue null element");
        list.add(toAdd);
    }
    
    /**
     * Removes and returns the element at the front of the queue.
     * Preconditions: queue must not be empty
     * Postconditions: the first element is removed and returned
     */
    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) throw new EmptyQueueException();
        return list.remove(0);
    }
    
    /**
     * Returns the element at the front of the queue without removing it.
     * Preconditions: queue must not be empty
     * Postconditions: the first element is returned, queue remains unchanged
     */
    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) throw new EmptyQueueException();
        return list.get(0);
    }
    
    /**
     * Removes all elements from the queue.
     * Preconditions: none
     * Postconditions: the queue is empty
     */
    @Override
    public void dequeueAll() {
        list.clear();
    }

    /**
     * Checks if the queue is empty.
     * Preconditions: none
     * Postconditions: returns true if the queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    /**
     * Checks if the queue contains the specified element.
     * Preconditions: toFind must not be null
     * Postconditions: returns true if the element is found, false otherwise
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException("Cannot search for null element");
        return list.contains(toFind);
    }
    
    /**
     * Returns the 1-based position of the element in the queue.
     * Preconditions: none
     * Postconditions: returns index + 1 if found, -1 otherwise
     */
    @Override
    public int search(E toFind) {
        if (toFind == null) return -1;
        int index = list.indexOf(toFind);
        return index == -1 ? -1 : index + 1;
    }
    
    /**
     * Returns an iterator over the elements in this queue.
     * Preconditions: none
     * Postconditions: an iterator is returned for traversing the queue
     */
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
    
    /**
     * Compares this queue to another queue for equality.
     * Preconditions: that must not be null
     * Postconditions: returns true if both queues contain the same elements in order
     */
    @Override
    public boolean equals(QueueADT<E> that) {
        if (that == null || this.size() != that.size()) return false;
        Iterator<E> thisIter = this.iterator();
        Iterator<E> thatIter = that.iterator();
        while (thisIter.hasNext()) {
            if (!thisIter.next().equals(thatIter.next())) return false;
        }
        return true;
    }
    
    /**
     * Returns an array containing all elements in this queue in order.
     * Preconditions: none
     * Postconditions: a new array containing the queue elements is returned
     */
    @Override
    public Object[] toArray() {
        return list.toArray();
    }
    
    /**
     * Returns an array containing all elements in this queue, using the provided array if large enough.
     * Preconditions: holder must not be null
     * Postconditions: the array is filled with queue elements and returned
     */
    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) throw new NullPointerException("Holder array cannot be null");
        return list.toArray(holder);
    }
    
    /**
     * Checks if the queue is full.
     * Preconditions: none
     * Postconditions: always returns false, as the queue is unbounded
     */
    @Override
    public boolean isFull() {
        return false; // No fixed capacity in this implementation
    }

    /**
     * Returns the number of elements in the queue.
     * Preconditions: none
     * Postconditions: the number of elements is returned
     */
    @Override
    public int size() {
        return list.size();
    }
}