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

    public MyQueue() {
        list = new MyDLL<>();
    }

    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot enqueue null element");
        list.add(toAdd);
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) throw new EmptyQueueException();
        return list.remove(0);
    }

    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) throw new EmptyQueueException();
        return list.get(0);
    }

    @Override
    public void dequeueAll() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException("Cannot search for null element");
        return list.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) return -1;
        int index = list.indexOf(toFind);
        return index == -1 ? -1 : index + 1;
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

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

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) throw new NullPointerException("Holder array cannot be null");
        return list.toArray(holder);
    }

    @Override
    public boolean isFull() {
        return false; // No fixed capacity in this implementation
    }

    @Override
    public int size() {
        return list.size();
    }
}