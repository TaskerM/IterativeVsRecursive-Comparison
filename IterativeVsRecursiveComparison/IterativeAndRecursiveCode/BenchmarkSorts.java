/*
*Author: Mark Tasker
*Date: 5/10/20
*Class: CMSC451
*
*/
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BenchmarkSorts {

    private InsertionSort insertionSort = new InsertionSort();
    private Random random = new Random();

    private int[] dataSizes;

    private Map<Integer, Map<String, Double>> iterativeMeasurements;
    private Map<Integer, Map<String, Double>> recursiveMeasurements;

    // Create a new benchmark sort for the different data sizes
    public BenchmarkSorts(int[] dataSizes) {
        this.dataSizes = dataSizes;

        // Prepare where to store the measurements
        iterativeMeasurements = new HashMap<>();
        recursiveMeasurements = new HashMap<>();

        for (Integer dataSize : dataSizes) {
            iterativeMeasurements.put(dataSize, new HashMap<>());
            recursiveMeasurements.put(dataSize, new HashMap<>());
        }
    }

    // Create a random list of data given a size
    private int[] createRandomList(int dataSize) {
        int[] list = new int[dataSize];

        for (int i = 0; i < list.length; i++) {
            list[i] = random.nextInt(1000) + 1;
        }

        return list;
    }

    // Run a sort for a specific size
    private void runSort(int dataSize, PrintWriter iterativeReport, PrintWriter recursiveReport) throws Exception {
        // For a data size we run it 50 times with different data sets
        // then we get its average, we need to use the same data set
        // for both iterative and recursive sort
        if (iterativeReport != null) {
            iterativeReport.print(dataSize + " ");
        }

        if (recursiveReport != null) {
            recursiveReport.print(dataSize + " ");
        }

        for (int i = 0; i < 50; i++) {
            int[] iterData = createRandomList(dataSize);
            int[] recData = new int[dataSize];
            System.arraycopy(iterData, 0, recData, 0, iterData.length);

            // Measure iterative sort
            insertionSort.iterativeSort(iterData);

            if (iterativeReport != null) {
                iterativeReport.print(insertionSort.getCount() + " " + insertionSort.getTime() + " ");
            }

            // Measure recursive sort
            insertionSort.recursiveSort(recData);

            if (recursiveReport != null) {
                recursiveReport.print(insertionSort.getCount() + " " + insertionSort.getTime() + " ");
            }
        }

        if (iterativeReport != null) {
            iterativeReport.println();
        }

        if (recursiveReport != null) {
            recursiveReport.println();
        }
    }

    // Run the insertion sort on different data sizes
    public void runSorts(boolean isWarmUp) throws Exception {
        // Run sort for each different data sizes
        PrintWriter iterativeReport = null;
        PrintWriter recursiveReport = null;

        if (!isWarmUp) {
            iterativeReport = new PrintWriter(new File("iterative_report.txt"));
            recursiveReport = new PrintWriter(new File("recursive_report.txt"));
        }

        for (Integer dataSize : dataSizes) {
            runSort(dataSize, iterativeReport, recursiveReport);
        }

        if (!isWarmUp) {
            iterativeReport.close();
            recursiveReport.close();

            System.out.println("Iterative benchmark results are written to 'iterative_report.txt'");
            System.out.println("Recursive benchmark results are written to 'recursive_report.txt'");
        }
    }

    // Prepare different data sizes and run the sort iteratively
    // and recursively
    public static void main(String[] args) throws Exception {
        int[] dataSizes = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
        BenchmarkSorts benchmark = new BenchmarkSorts(dataSizes);

        // Do a warmp up
        benchmark.runSorts(true);

        // Now we do the actual sorting and measuring
        benchmark.runSorts(false);
    }
}
