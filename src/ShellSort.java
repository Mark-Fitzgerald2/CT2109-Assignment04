/** Shellort.java: 
 *  
 *  Based on code from Carrano & Savitch, “Data Structures and Abstractions with Java”
 *  With some simplifications for clarity, an extra insertionSort method, 
 *  and test code at the end.
 */

import javax.swing.JOptionPane;

public class ShellSort
{
	/** Task: Sorts equally spaced elements of an array into ascending order.
	 *  The paramater arr is an array of Comparable objects.
	 */
	
	public static void shellSort (Comparable[] arr)   
	{
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
			} 
			if (moveMade) {
				//System.out.println("Inserting " + toSortElement + " at pos " + (index+1));
				a[index+gap] = toSortElement;
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
		insertionSort(arr, 0, arr.length-1, 1);
	}

	
	/** M Madden: Main method to test shellSort */
	public static void main(String[] args)
	{
		//String[] arr = {"aa", "xa", "bq", "aq", "sw2", "ph", "pp"};
		String[] arr = {"03", "09", "01", "11", "05", "02", "08", "04"};
       
		// Going to sort a copy of the array, not the original.
		// Note that it is NOT sufficient to say "String[] copy = arr;" as this 
		// just copies a new reference to the same array in memory. 

		
		// we can also use insertionSort directly
		String copy2[] = arr.clone();
		
		JOptionPane.showMessageDialog(null, "Array length is "+ copy2.length);

		JOptionPane.showMessageDialog(null, "Array before sorting with InsertionSort\n" + array2String(copy2));

		insertionSort(copy2);
		JOptionPane.showMessageDialog(null, "After Sorting with InsertionSort:\n " + array2String(copy2));

		
		String copy[] = arr.clone();
		
		JOptionPane.showMessageDialog(null, "Array before sorting with Shell Sort:\n" + array2String(copy));

		shellSort(copy);
		JOptionPane.showMessageDialog(null,  "After Shell Sort" + array2String(copy));

		
		System.exit(0);
	}

	/** M Madden: utility method to return string representation of array of strings */
	private static String array2String(String[] a)
	{
		String text="[";
		for (int i=0; i<a.length; i++) {
			text += a[i];
			if (i<a.length-1)
				text += " ";
		}
		text += "]";
		return text;
	}

	
}