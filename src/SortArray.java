import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SortArray {
	
	/** Task: Sorts equally spaced elements of an array into ascending order.
	 *  The paramater arr is an array of Comparable objects.
	 */
	private static int compare = 0; //create counter to measure comparisons 
	private static int move = 0; //create counter to measure moves
	
	public static void shellSort (Comparable[] arr)   
	{
		if((!(compare==0)) || (!(move==0))){ 
			compare = 0; //resets the counters to zero 
			move = 0; //in case a previous sorting algorithm was called and these are non zero
		}
		
		int last = arr.length-1; 

		// Begin with gap = half length of array; reduce by half each time.
		for (int gap = arr.length/2; gap > 0; gap = gap/2)
		{
			//if (gap % 2 == 0) gap++; // if gap is even, move to next largest odd number
			
			// Apply Insertion Sort to the subarrays defined by the gap distance
			for (int first = 0; first < gap; first++) {
				insertionSort (arr, first, last, gap);
			}
		} // end for
	} // end shellSort
	

	/** Helper function for shellSort method, that sorts a subarray
	 * that goes from the first to last index, in steps specified by gap.
	 */	
	private static void insertionSort(Comparable[] a, int first, int last, int gap)                                             
	{
		int index;     // general index for keeping track of a position in array
		int toSort;  // stores the index of an out-of-place element when sorting.

		// NOTE: Instead of considering a full array of adjacent elements, we are considering 
		// a sub-list of elements from 'first' to 'last, separated by 'gap'. All others are ignored.
		//
		// Work forward through the list, starting at 2nd element, 
		// and sort each element relative to the ones before it.
		
		for (toSort = first+gap; toSort <= last; toSort += gap)
		{
			Comparable toSortElement = a[toSort];
			

			// Go back through the list to see how far back (if at all)
			// this element should be moved.
			// Note: we assume all elements before this are sorted relative to each other.
			boolean moveMade = false;
			index = toSort - gap;
			while ((index >= first) && (toSortElement.compareTo(a[index]) < 0))
			{
				// Shuffle elements over to the right, put firstUnsorted before them 
				a[index+gap] = a[index];
				index = index - gap;
				moveMade = true;
				compare++; //comparison is made, increase counter
				
			} 
			if (moveMade) {
				//System.out.println("Inserting " + toSortElement + " at pos " + (index+1));
				a[index+gap] = toSortElement;
				move++; //element is swapped, increase counter
			}
		} 
	} 

	
	/** Version of insertionSort method that uses the correct settings to sort an entire array
	 *  from start to end in steps of 1.
	 *  
	 *  This version uses the 'helper function' version, but is a direct substitute for the 
	 *  quickSort() method call.
	 */
	public static void insertionSort(Comparable arr[])
	{
		if((!(compare==0)) || (!(move==0))){
			compare = 0; //resets the counters to zero
			move = 0; //in case a previous sorting algorithm was called and these are non zero
		}
		insertionSort(arr, 0, arr.length-1, 1);
	}

	
	/** M Madden: Main method to test shellSort */
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Size of array"); 
	    //creates a frame for us to ask the user to input a value
	    String noStrings = JOptionPane.showInputDialog(frame, "What do you want the size of the array to be?");
	    //makes a input box and question appear
	    int n = Integer.parseInt(noStrings); 
	    //converts the inputed value to an int
		String[] arr = createArray(n);
		//calls createArray method to set up an array

       
		// Going to sort a copy of the array, not the original.
		// Note that it is NOT sufficient to say "String[] copy = arr;" as this 
		// just copies a new reference to the same array in memory. 

		
		// we can also use insertionSort directly
		String copy2[] = arr.clone();
		
		JOptionPane.showMessageDialog(null, "Array length is "+ copy2.length);
		//prints length

		JOptionPane.showMessageDialog(null, "Array before sorting with InsertionSort\n" + array2String(copy2));
		//prints array
		long insertT1 = System.currentTimeMillis();
		//starts a timer
		insertionSort(copy2);
		//calls insertionSort
		long insertT2 = System.currentTimeMillis();
		//ends timer
		JOptionPane.showMessageDialog(null, "After Sorting with InsertionSort:\n " + array2String(copy2));
		//prints sorted array
		JOptionPane.showMessageDialog(null, "The value of the compare incrementer is:\n " + compare);
		JOptionPane.showMessageDialog(null, "The value of the move incrementer is:\n " + move);
		//tells user value of compare and move counters
		JOptionPane.showMessageDialog(null, "The time for insertion sort was\n " + (insertT2 - insertT1) + "mS");
		//prints out time
		
		String copy[] = arr.clone();
		//creates a new copy of the array
		
		JOptionPane.showMessageDialog(null, "Array before sorting with Shell Sort:\n" + array2String(copy));
		//prints array
		long shellT1 = System.currentTimeMillis();
		//starts timer
		shellSort(copy);
		//calls shellSort
		long shellT2 = System.currentTimeMillis();
		//ends timer
		JOptionPane.showMessageDialog(null,  "After Shell Sort" + array2String(copy));
		//prints sorted array
		JOptionPane.showMessageDialog(null, "The value of the compare incrementer is:\n " + compare);
		JOptionPane.showMessageDialog(null, "The value of the move incrementer is:\n " + move);
		//tells user value of compare and move counters
		JOptionPane.showMessageDialog(null, "The time for shell sort was\n " + (shellT2 - shellT1) + "mS");
		//prints time
		
		String copy3[] = arr.clone();
		//creates a new copy of array

		JOptionPane.showMessageDialog(null, "Array before sorting:\n" + array2String(copy3));
		//prints array
		
		// quickSort method's first parameter is just the array; 
		// second is a newly created string comparator object (class defined later in this file)
		long quickT1 = System.currentTimeMillis();
		//starts timer
		quickSort(copy3, new StringComparator());
		//calls quickSort
		long quickT2 = System.currentTimeMillis();
		//ends timer

		JOptionPane.showMessageDialog(null, "Array after sorting:\n" + array2String(copy3));
		//prints sorted array
		JOptionPane.showMessageDialog(null, "The value of the compare incrementer is:\n " + compare);
		JOptionPane.showMessageDialog(null, "The value of the move incrementer is:\n " + move);
		//tells user the value of the compare and move methods
		JOptionPane.showMessageDialog(null, "The time for quick sort was\n " + (quickT2 - quickT1) + "mS");
		//tells user the time taken
		System.exit(0);
	}

	/** M Madden: utility method to return string representation of array of strings */
	

	/** QuickSort method:
	  * Sorts the elements of array arr in nondecreasing order according
	  * to comparator c, using the quick-sort algorithm. Most of the work
	  * is done by the auxiliary recursive method quickSortStep.
	  **/
	public static void quickSort (Object[] arr, Comparator c) {
		if((!(compare==0)) || (!(move==0))){
			compare = 0; //resets the counters to zero
			move = 0; //in case a previous sorting algorithm was called and these are non zero
		}
		if (arr.length < 2) return; // the array is already sorted in this case
	    quickSortStep(arr, c, 0, arr.length-1); // call the recursive sort method
	}
	  
	/** QuickSortStep method: 
	  * Method called by QuickSort(), which sorts the elements of array s between
	  * indices leftBound and rightBound, using a recursive, in-place,
	  * implementation of the quick-sort algorithm.
	 **/
	private static void quickSortStep (Object[] s, Comparator c,
	                              int leftBound, int rightBound ) 
	{
	   if (leftBound >= rightBound) return; // the indices have crossed
	   Object temp;  // temp object used for swapping
	    
	   // Set the pivot to be the last element
	   Object pivotValue = s[rightBound];
	    
	   // Now partition the array 
	   int upIndex = leftBound;     // will scan rightward, 'up' the array
	   int downIndex = rightBound-1; // will scan leftward, 'down' the array
	   while (upIndex <= downIndex) 
	   { 
	       // scan right until larger than the pivot
	       while ( (upIndex <= downIndex) && (c.compare(s[upIndex], pivotValue)<=0) ) {
	    	   upIndex++; 
	    	   compare++; //comparison is made
	       }
	    	   
	       // scan leftward to find an element smaller than the pivot
	       while ( (downIndex >= upIndex) && (c.compare(s[downIndex], pivotValue)>=0)){
	    	   downIndex--;
	    	   compare++; //comparison is made
	       }
	    	   
	       if (upIndex < downIndex) { // both elements were found
	          temp = s[downIndex]; 
		      s[downIndex] = s[upIndex]; // swap these elements
		      move++; //one element is moved
		      s[upIndex] = temp;
		      move++; //second element is moved
	       }
	   } // the loop continues until the indices cross
	    
	   int pivotIndex = upIndex;
	   temp = s[rightBound]; // swap pivot with the element at upIndex
	   s[rightBound] = s[pivotIndex]; 
	   move++; //one element is moved
	   s[pivotIndex] = temp; 
	   move++; //second element is moved
	 
	   // the pivot is now at upIndex, so recursively quicksort each side
	   quickSortStep(s, c, leftBound, pivotIndex-1);
	   quickSortStep(s, c, pivotIndex+1, rightBound);
	}
	
	/** utility method to return string representation of array of strings */
	private static String array2String(String[] a)
	{
		String text="[";
		for (int i=0; i<a.length; i++) {
			text += a[i];
			if (i<a.length-1)
				text += ",";
		}
		text += "]";
		return text;
	}
	
	private static String[] createArray(int n) {
		String refArr[];
		refArr = new String[n];
		for(int i = 0; i < n; i++) {
			refArr[i]= String.valueOf(Math.random());
		}
		return refArr;
	}
}
