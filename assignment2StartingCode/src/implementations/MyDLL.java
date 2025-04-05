package implementations;

import utilities.ListADT;
import utilities.Iterator;

/**
 * Implementation of ListADT using a doubly linked list.
 * @param <E> the type of elements held in this list
 */
public class MyDLL<E> implements ListADT<E> {
    private MyDLLNode<E> head;
    private MyDLLNode<E> tail;
    private int size;

    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) throw new NullPointerException("Cannot add null element");
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index out of bounds");
        if (index == size) {
            add(toAdd);
            return true;
        }
        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
        if (index == 0) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        } else {
            MyDLLNode<E> current = getNode(index);
            newNode.setNext(current);
            newNode.setPrevious(current.getPrevious());
            current.getPrevious().setNext(newNode);
            current.setPrevious(newNode);
        }
        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot add null element");
        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot add null list");
        boolean changed = false;
        Iterator<? extends E> iter = toAdd.iterator();
        while (iter.hasNext()) {
            add(iter.next());
            changed = true;
        }
        return changed;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        MyDLLNode<E> toRemove = getNode(index);
        E data = toRemove.getData();
        if (size == 1) {
            head = tail = null;
        } else if (index == 0) {
            head = head.getNext();
            head.setPrevious(null);
        } else if (index == size - 1) {
            tail = tail.getPrevious();
            tail.setNext(null);
        } else {
            toRemove.getPrevious().setNext(toRemove.getNext());
            toRemove.getNext().setPrevious(toRemove.getPrevious());
        }
        size--;
        return data;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) throw new NullPointerException("Cannot remove null element");
        int index = indexOf(toRemove);
        if (index == -1) return null;
        return remove(index);
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        return getNode(index).getData();
    }

    /**
     * Retrieves the index of the searched element in the doubly linked list
     * Preconditions: a valid MyDLL object must exist
     * Postconditions: the index of the first matching element to the parameter element is returned
     * 
     * @param element Element to be searched for
     * @return Returns the index of the first matching element
     * @throws NullPointerException If the parameter element is null
     */
    public int indexOf(E element) throws NullPointerException { // Helper method, not overridden
        if (element == null) throw new NullPointerException("Cannot search for null element");
        MyDLLNode<E> current = head;
        for (int i = 0; i < size; i++) {
            if (element.equals(current.getData())) return i;
            current = current.getNext();
        }
        return -1;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return indexOf(toFind) != -1;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new DLLIterator();
    }
/**
 * Returns the node at the specified index
 * Preconditions: a valid MyDLLNode Object must exist
 * Postconditions: the node at the specified index is returned
 * 
 * @param index Index of the node to retrieve
 * @return Returns node at the specified index
 * @throws IndexOutOfBoundsException If the index specified is negative or larger than the size of the DLL
 */
    private MyDLLNode<E> getNode(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        MyDLLNode<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }

    /**
     * Private inner class that creates the iterator for MyDLL
     * Iterates through the list while returning each of the elements in order one at a time
     */
    private class DLLIterator implements Iterator<E> {
        private MyDLLNode<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            E data = current.getData();
            current = current.getNext();
            return data;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        MyDLLNode<E> current = head;
        for (int i = 0; i < size; i++) {
            result[i] = current.getData();
            current = current.getNext();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) throw new NullPointerException("Holder array cannot be null");
        if (holder.length < size) {
            holder = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), size);
        }
        MyDLLNode<E> current = head;
        for (int i = 0; i < size; i++) {
            holder[i] = current.getData();
            current = current.getNext();
        }
        if (size < holder.length) holder[size] = null;
        return holder;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) throw new NullPointerException("Cannot set null element");
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        MyDLLNode<E> node = getNode(index);
        E old = node.getData();
        node.setData(toChange);
        return old;
    }
}