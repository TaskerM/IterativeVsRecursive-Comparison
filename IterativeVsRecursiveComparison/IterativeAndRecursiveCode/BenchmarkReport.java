/*
*Author: Mark Tasker
*Date: 5/10/20
*Class: CMSC451
*
*/
import java.awt.BorderLayout;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class BenchmarkReport {

    // Calculate the average of list of measurements
    private static double computeAverage(long[] measurements) {
        double sum = 0;

        for (int i = 0; i < measurements.length; i++) {
            sum += measurements[i];
        }

        return sum / measurements.length;
    }

    // Calculate the coefficient of variance of a given list of measurements
    private static double computeCoefficientOfVariance(long[] measurements) {
        double mean = computeAverage(measurements);

        if (mean == 0) {
            return 0;
        }

        double sum = 0;

        for (int i = 0; i < measurements.length; i++) {
            sum += Math.pow(measurements[i] - mean, 2);
        }

        double standardDeviation = Math.sqrt(sum / (measurements.length - 1));
        return standardDeviation / mean * 100;
    }

    // Okay, now we do some measuring
    public static void main(String[] args) throws Exception {
        // Initialize the window
        JFrame frame = new JFrame();
        frame.setTitle("Benchmark Report");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a table for displaying calculated results
        Object[] tableColumnNames = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
        DefaultTableModel tableModel = new DefaultTableModel(tableColumnNames, 0);
        JTable table = new JTable(tableModel);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        for (int i = 0; i < tableColumnNames.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        frame.add(BorderLayout.CENTER, new JScrollPane(table));

        // Let the user browse for the report file
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        // Load the file, load the details
        Scanner inFile = new Scanner(fileChooser.getSelectedFile());

        while (inFile.hasNextLine()) {
            // There'll be 50 pairs of count and time each line
            Scanner sc = new Scanner(inFile.nextLine());
            int dataSize = sc.nextInt();

            long[] countMeasurements = new long[50];
            long[] timeMeasurements = new long[50];

            for (int i = 0; i < 50; i++) {
                countMeasurements[i] = sc.nextLong();
                timeMeasurements[i] = sc.nextLong();
            }
            sc.close();
            
            // Calculate the averages and coefficients
            double averageCount = computeAverage(countMeasurements);
            double coefCount = computeCoefficientOfVariance(countMeasurements);
            double averageTime = computeAverage(timeMeasurements);
            double coefTime = computeCoefficientOfVariance(timeMeasurements);

            // Put it on display
            tableModel.addRow(new Object[]{dataSize,
                String.format("%.2f", averageCount),
                String.format("%.2f", coefCount) + "%",
                String.format("%.2f", averageTime),
                String.format("%.2f", coefTime) + "%"});
        }

        inFile.close();
        frame.setVisible(true);
    }

}
