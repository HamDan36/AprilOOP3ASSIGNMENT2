package implementations;

import utilities.ListADT;
import utilities.Iterator;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Implementation of ListADT using an array as the underlying data structure.
 * @param <E> the type of elements held in this list
 */
public class MyArrayList<E> implements ListADT<E> {
    private E[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private final int MULTIPLIER = 2;

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

//    @Override
//    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
//        if (toAdd == null) throw new NullPointerException("Cannot add null element");
//        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index out of bounds");
//        if (size == array.length) {
//            array = Arrays.copyOf(array, array.length * 2);
//        }
//        for (int i = size; i > index; i--) {
//            array[i] = array[i - 1];
//        }
//        array[index] = toAdd;
//        size++;
//        return true;
//    }
    
 	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException
	{
		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException("Index out of range");
			
		}
		if (toAdd == null)
		{
			throw new NullPointerException("Element cannot be null");
		}
		
		ensureCapacity();
		
		if(index == size)
		{
			array[size++] = toAdd;
		}
		else
		{
			for ( int i = size; i > index; i--)
			{
				array[i] = array[i - 1];
			}
		
			array[index] = toAdd;
			size++;
		}
		
		return true;
	}   

//    @Override
//    public boolean add(E toAdd) throws NullPointerException {
//        if (toAdd == null) throw new NullPointerException("Cannot add null element");
//        if (size == array.length) {
//            array = Arrays.copyOf(array, array.length * 2);
//        }
//        array[size++] = toAdd;
//        return true;
//    }
 	
	@Override
	public boolean add(E toAdd) throws NullPointerException
	{
		if(toAdd == null) throw new NullPointerException();
		ensureCapacity();
		array[size++] = toAdd;
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
	
//	@Override
//	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException
//	{	
//		Iterator< ? extends E > iterator = toAdd.iterator();	
//		
//		if (toAdd == null) throw new NullPointerException("Cannot add null list.");
//		
////		int backupSize = size;
////		E[] backupArray = (E[]) new Object[backupSize];
//		
//		
//		// create backup
//		for(int i = 0 ; i < array.length; i++)
//		{
//			backupArray[i] = array[i]; // copies all the objects in old array into new one
//		}
//		
//		try
//		{
//			while(iterator.hasNext() == true)
//			{
//				add(iterator.next());
//			}
//			size = array.length;
//		}
//		
//		catch (Exception e)
//		{
//			array = backupArray; // restore if failure to add all elements
//			size = backupSize;	
//			return false;
//		}
//		
//		return true;
//	}

    @Override
    public E remove(int index) throws IndexOutOfBoundsException 
    {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        E removed = array[index];
        for (int i = index; i < size - 1; i++) 
        {
            array[i] = array[i + 1];
        }
        
        array[--size] = null;
        return removed;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException 
    {
        if (toRemove == null) throw new NullPointerException("Cannot remove a null element");
        for (int i = 0; i < size; i++) 
        {
            if (toRemove.equals(array[i])) 
            {
                return remove(i);
            }
        }
        return null;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException 
    {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        return array[index];
    }

/**
 * Returns the index of a element
 * 
 * Preconditions: a valid MyArrayList Object must exist
 * Postconditions: the first index of the element being searched is returned
 * 
 * @param element Element you are searching for in the MyArrayList Object
 * @return Returns the index of the first matching element
 * @throws NullPointerException if the element being searched for is null
 */
    public int indexOf(E element) throws NullPointerException 
    { 
        if (element == null) throw new NullPointerException("Cannot search for null element");
        for (int i = 0; i < size; i++) 
        {
            if (element.equals(array[i])) return i;
        }
        return -1;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException 
    {
        return indexOf(toFind) != -1;
    }

    @Override
    public void clear() 
    {
        for (int i = 0; i < size; i++) 
        {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public int size() 
    {
        return size;
    }

    @Override
    public boolean isEmpty() 
    {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() 
    {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<E>
    {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() 
        {
            return currentIndex < size;
        }
       
		@Override
		public E next()
		{
			if(!hasNext())
			{
				throw new NoSuchElementException("You have reached the end of the list.");
			}
			return array[currentIndex++];
		}
	}
    
    
    @Override
    public Object[] toArray() 
    {
        return Arrays.copyOf(array, size);
    }

    @SuppressWarnings("unchecked")
	@Override
    public E[] toArray(E[] toHold) throws NullPointerException
    {
    	if (toHold == null)
    	{
    		throw new NullPointerException("Holder array cannot be null.");
    	}
    	
        if (toHold.length < size) 
        {
            return (E[]) Arrays.copyOf(array, size, toHold.getClass());
        }
    	
    	return toHold;
    }
    
    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException 
    {
        if (toChange == null) throw new NullPointerException("Cannot set null element");
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds");
        E oldElement = array[index];
        array[index] = toChange;
        return oldElement;
    }
    
    /**
     * Checks the size of the array and increases the size if it cannot hold anymore elements
     * 
     * Preconditions: a valid MyArrayList Object must exist
     * Postconditions: if the size of the array is not enough, it is increased by the length of the the array times the MULTIPLIER 
     */
	@SuppressWarnings("unchecked")
	private void ensureCapacity()
	{
		if(array.length == size)
		{
			E[] newArray = (E[]) new Object[array.length * MULTIPLIER];
			for(int i = 0 ; i < array.length; i++)
			{
				newArray[i] = array[i]; // copies all the objects in old array into new one
			}
			array = newArray; //assign old name to new array
		}
	}
}
