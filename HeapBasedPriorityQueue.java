import java.util.*;


public class HeapBasedPriorityQueue<T extends Comparable<T>>
{
	private ArrayList<T> heapArray;
	private int lastIndex;
	
	public HeapBasedPriorityQueue(int initialSize)
	{
		initialSize = (initialSize * 2) + 2;
		heapArray = new ArrayList<T>(initialSize);
		heapArray.add(null); //initialize first index to null
		lastIndex = 0;
	}
	
	public boolean offer(T data)
	{
		boolean offerSuccessful = true;
		if(data == null)
		{
			offerSuccessful = false;
		}
		else if(lastIndex < heapArray.size() - 1)
		{
			lastIndex++;
			heapArray.set(lastIndex, data);
			heapifyUpward();
		}
		else
		{
			heapArray.add(data);
			lastIndex++;
			heapifyUpward();
		}
		
		return offerSuccessful;
	}
	
	public T poll()
	{
		T removedData = heapArray.get(1);
		heapArray.set(1, heapArray.get(lastIndex));
		heapArray.set(lastIndex, null);
		lastIndex--;
		heapifyDownward();
		
		return removedData;
	}

	public T peek()
	{
		T data = heapArray.get(1);
		return data;
	}
	
	
	
	
	//Bubble up the last inserted node into the proper place
	public void heapifyUpward()
	{
		int index = lastIndex;
		if(index > 1)
		{
			while(hasParent(index) && parentIsGreater(index))
			{
				T temp = heapArray.get(index);
				heapArray.set(index, getParentValue(index));
				heapArray.set(getParentIndex(index), temp);
				index = getParentIndex(index);
			}
		}
	}
	
	//With the root removed and replaced with the last node,
	//bubble down the node until it reached the correct location
	public void heapifyDownward()
	{
		int index = 1;
		boolean heapified = false;
		while(index * 2 < heapArray.size() && heapArray.get(index * 2) != null)
		{
			int leftIndex = index * 2;
			int rightIndex = (leftIndex + 1 < heapArray.size() && heapArray.get(leftIndex + 1) != null) ? leftIndex + 1 : -1;

			int smallestChildIndex;
			if(rightIndex == -1)
			{
				smallestChildIndex = leftIndex;
			}
			else
			{
				smallestChildIndex = (leftChildIsSmaller(leftIndex, rightIndex)) ? leftIndex : rightIndex; 
			}
			
			if(heapArray.get(index).compareTo(heapArray.get(smallestChildIndex)) > 0)
			{
				T temp = heapArray.get(smallestChildIndex);
				heapArray.set(smallestChildIndex, heapArray.get(index));
				heapArray.set(index, temp);
			}
			index = smallestChildIndex;
		}
	}
	
	private boolean hasParent(int index)
	{
		return index > 1;
	}
	
	private T getParentValue(int childIndex)
	{
		return heapArray.get(childIndex/2);
	}
	
	private int getParentIndex(int childIndex)
	{
		return childIndex/2;
	}
	
	private boolean parentIsGreater(int childIndex)
	{
		return getParentValue(childIndex).compareTo(heapArray.get(childIndex)) > 0;
	}
	
	private boolean leftChildIsSmaller(int leftChildIndex, int rightChildIndex)
	{
		return heapArray.get(leftChildIndex).compareTo(heapArray.get(rightChildIndex)) < 0;
	}
	
	public String toString()
	{
		String heap = "(";
		for(int i = 0; i < heapArray.size(); i++)
		{
			heap += heapArray.get(i) + ", ";
		}
		heap = heap.substring(0, heap.length() - 2) + ")";
		return heap;
	}
}