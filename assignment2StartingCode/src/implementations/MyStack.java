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

    public MyStack() {
        list = new MyArrayList<>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot push null element");
        list.add(toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return list.remove(list.size() - 1);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return list.get(list.size() - 1);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = list.get(size() - 1 - i); // Reverse order: top to bottom
        }
        return result;
    }

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

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException("Cannot search for null element");
        return list.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) return -1;
        int index = list.indexOf(toFind);
        return index == -1 ? -1 : list.size() - index;
    }

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

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean stackOverflow() {
        return false; // No fixed capacity in this implementation
    }
}
