package implementations;

import utilities.StackADT;
import utilities.Iterator;
import java.util.EmptyStackException;

/**
 * Implementation of StackADT using MyArrayList as the underlying data structure.
 * @param <E> the type of elements held in this stack
 */
public class MyStack<E> implements StackADT<E> {
    private MyArrayList<E> list;

    /**
     * No argument constructor
     * 
     * Preconditions: none
     * Postconditions: a new MyStack Object is created
    
    /**
     * Constructor that initializes an empty stack.
     * Preconditions: none
     * Postconditions: a new empty stack is created
     */
    public MyStack() {
        list = new MyArrayList<>();
    }
    
    /**
     * Pushes an element onto the top of the stack.
     * Preconditions: toAdd must not be null
     * Postconditions: toAdd is added to the top of the stack
     */
    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot push null element");
        list.add(toAdd);
    }
    
    /**
     * Removes and returns the element at the top of the stack.
     * Preconditions: stack must not be empty
     * Postconditions: the top element is removed and returned
     */
    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return list.remove(list.size() - 1);
    }
    
    /**
     * Returns the top element without removing it.
     * Preconditions: stack must not be empty
     * Postconditions: the top element is returned, stack remains unchanged
     */
    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return list.get(list.size() - 1);
    }
    
    /**
     * Clears the stack of all elements.
     * Preconditions: none
     * Postconditions: stack is empty
     */
    @Override
    public void clear() {
        list.clear();
    }
    
    /**
     * Checks if the stack is empty.
     * Preconditions: none
     * Postconditions: true is returned if the stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns an array of the stack contents from top to bottom.
     * Preconditions: none
     * Postconditions: a new array is returned containing the stack elements
     */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = list.get(size() - 1 - i); // Reverse order: top to bottom
        }
        return result;
    }
    
    /**
     * Returns a provided array filled with the stack contents.
     * Preconditions: holder must not be null
     * Postconditions: the holder is filled with stack elements from top to bottom
     */
    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) throw new NullPointerException("Holder array cannot be null");
        if (holder.length < size()) {
            holder = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), size());
        }
        for (int i = 0; i < size(); i++) {
            holder[i] = list.get(size() - 1 - i); // Reverse order: top to bottom
        }
        if (size() < holder.length) holder[size()] = null;
        return holder;
    }
    
    /**
     * Checks if the stack contains a specified element.
     * Preconditions: toFind must not be null
     * Postconditions: true is returned if element is found, false otherwise
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException("Cannot search for null element");
        return list.contains(toFind);
    }
    
    /**
     * Returns the 1-based position from the top of the stack of the element.
     * Preconditions: none
     * Postconditions: returns distance from top if found, -1 otherwise
     */
    @Override
    public int search(E toFind) {
        if (toFind == null) return -1;
        int index = list.indexOf(toFind);
        return index == -1 ? -1 : list.size() - index;
    }
    
    /**
     * Returns an iterator over the elements in this stack (from top to bottom).
     * Preconditions: none
     * Postconditions: an iterator is returned
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = list.size() - 1; // Start from the top of the stack

            @Override
            public boolean hasNext() {
                return currentIndex >= 0; // Continue iterating as long as there are elements
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                return list.get(currentIndex--); // Return the element and move down the stack
            }
        };
    }
    
    /**
     * Compares this stack with another stack for equality.
     * Preconditions: that must not be null
     * Postconditions: returns true if both stacks contain the same elements in order
     */
    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null || this.size() != that.size()) return false;
        Iterator<E> thisIter = this.iterator();
        Iterator<E> thatIter = that.iterator();
        while (thisIter.hasNext()) {
            if (!thisIter.next().equals(thatIter.next())) return false;
        }
        return true;
    }
    
    /**
     * Returns the number of elements in the stack.
     * Preconditions: none
     * Postconditions: the number of elements is returned
     */
    @Override
    public int size() {
        return list.size();
    }
    
    /**
     * Checks if the stack is full.
     * Preconditions: none
     * Postconditions: always returns false, as the stack is unbounded
     */
    @Override
    public boolean stackOverflow() {
        return false; // No fixed capacity in this implementation
    }
}
