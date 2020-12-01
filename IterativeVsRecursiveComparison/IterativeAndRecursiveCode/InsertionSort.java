/*
*Author: Mark Tasker
*Date: 5/10/20
*Class: CMSC451
*
*/
public class InsertionSort implements SortInterface {

    private int count;
    private long time;

    // Do a recursive insertion sort keeping track of the
    // total number of operations and the time it took to
    // sort the list
    @Override
    public void recursiveSort(int[] list) throws UnsortedException {
        count = 0;
        time = 0;

        // Start sorting recursively
        long startTime = System.nanoTime();
        recursiveSort(list, list.length);
        time = System.nanoTime() - startTime;

        // Verify that we got the list sorted
        if (!validateSortedList(list)) {
            throw new UnsortedException();
        }
    }

    // Helper method to the recursive insertion sort algorithm
    private void recursiveSort(int[] list, int n) {
        if (n <= 1) {
            return;
        }
	
        // Recursively sort from start to n - 1 elements
        recursiveSort(list, n - 1);

        // Put the correct position of the next element
        int last = list[n - 1];
        count += 1; // ASSIGNMENT: list = list[n-1]

        int j = n - 2;
        
        while (j >= 0 && list[j] > last) {
            count += 1; // COMPARISON: list[j] > last

            list[j + 1] = list[j];
            count += 1; // ASSIGNMENT: list[j + 1] = list[j]

            j--;
        }

        count += 1; // COMPARISON list[j] > last

        list[j + 1] = last;
        
		count += 1; // ASSIGNMENT: list[j+1] = last
    }

    // Do an iterative insertion sort keeping track of the
    // total number of operations and the time it took to
    // sort the list
    @Override
    public void iterativeSort(int[] list) throws UnsortedException {
        count = 0;
        time = 0;

        long startTime = System.nanoTime();
        
        for (int i = 1; i < list.length; i++) {
            
            int key = list[i];
            
			count += 1; 	// ASSIGNMENT: key = list[i]
            
            int j = i - 1;
  

            while (j >= 0 && list[j] > key) {
                count += 1; 	// COMPARISON: list[j] > key

                list[j + 1] = list[j];
                
				count += 1; 	// ASSIGNMENT: list[j+1]=list[j]
                
                j--;  
            }
            
            count += 1;  // COMPARISON:	list[j] > key

            list[j + 1] = key;
            
			count += 1;  // ASSIGNMENT: list[j+1] = key
        }

        time = System.nanoTime() - startTime;

        if (!validateSortedList(list)) {
            throw new UnsortedException();
        }
    }

    // Get the counts of operation
    @Override
    public int getCount() {
        return count;
    }

    // Get the time it took to sort
    @Override
    public long getTime() {
        return time;
    }

    // Verify and check that the list is sorted
    private boolean validateSortedList(int[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            for (int j = i; j < list.length; j++) {
                if (list[i] > list[j]) {
                    return false;
                }
            }
        }

        return true;
    }
}
