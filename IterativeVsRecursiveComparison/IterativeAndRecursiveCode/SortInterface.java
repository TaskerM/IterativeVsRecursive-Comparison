/*
*Author: Mark Tasker
*Date: 5/10/20
*Class: CMSC451
*
*/
public interface SortInterface {
    
    // Do a recursive sort algorithm
    public void recursiveSort(int[] list) throws UnsortedException;

    // Do an iterative sort algorithm
    public void iterativeSort(int[] list) throws UnsortedException;

    // Return the number of operations that has happened
    public int getCount();

    // Return the time it took to perform sorting
    public long getTime();
}
