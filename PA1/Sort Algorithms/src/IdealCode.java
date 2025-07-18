public class IdealCode {
//    class Main {
//        public static void main(String[] args) {
//            RecordManager recordManager = new RecordManager("TrafficFlowDataset.csv");
//            recordManager.readRecordsFromCSV();
//            Record[] records = recordManager.getRecords();
//
//            SortAnalysis sortAnalysis = new SortAnalysis(records);
//            sortAnalysis.runExperiment(10, SortAnalysis.initialArrayState.RANDOM);
//            sortAnalysis.runExperiment(10, SortAnalysis.initialArrayState.ASCENDING);
//            sortAnalysis.runExperiment(10, SortAnalysis.initialArrayState.DESCENDING);
//            sortAnalysis.showAndSaveArrayComparisonCharts();
//        }
//    }


    //import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class RecordManager {
//    public RecordManager(String filePath, int maxRecordCount) {
//        this.filePath = filePath;
//        this.maxRecordCount = maxRecordCount;
//        this.records = new Record[maxRecordCount];
//    }
//    public RecordManager(String filePath) {
//        this(filePath, 250000);
//    }
//    private String filePath;
//    private int maxRecordCount;
//
//    public String getFilePath() {
//        return filePath;
//    }
//
//    public void setFilePath(String filePath) {
//        this.filePath = filePath;
//    }
//
//    public int getMaxRecordCount() {
//        return maxRecordCount;
//    }
//
//    public void setMaxRecordCount(int maxRecordCount) {
//        this.maxRecordCount = maxRecordCount;
//    }
//
//    public Record[] getRecords() {
//        return records;
//    }
//
//    public void setRecords(Record[] records) {
//        this.records = records;
//    }
//
//    private Record[] records;
//
//
//    public void readRecordsFromCSV() {
//        int recordIndex = 0;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line = br.readLine();
//
//            for (int i = 0; i < maxRecordCount; i++) {
//                if ((line = br.readLine()) == null) break;
//                if (recordIndex == maxRecordCount) {
//                    System.out.println("Max record limit reached");
//                    break;
//                }
//                String[] values = line.split(",");
//
//                String id = values[0];
//                LocalDateTime timeStamp = LocalDateTime.parse(values[1], formatter);
//                long duration = Long.parseLong(values[2]);
//
//                records[recordIndex] = new Record(id, timeStamp, duration);
//                recordIndex++;
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}


    //import java.util.*;
//import java.io.IOException;
//import org.knowm.xchart.*;
//import org.knowm.xchart.style.Styler;
//
//public class SortAnalysis {
//    public SortAnalysis(Record[] arrayRandom) {
//        this.arrayRandom = arrayRandom;
//        arrayAscending = Arrays.copyOf(arrayRandom, arrayRandom.length);
//        Arrays.sort(arrayAscending);
//        arrayDescending = Arrays.copyOf(arrayAscending, arrayAscending.length);
//        for (int i = 0; i < arrayDescending.length / 2; i++) {
//            Record temp = arrayDescending[i];
//            arrayDescending[i] = arrayDescending[arrayDescending.length - 1 - i];
//            arrayDescending[arrayDescending.length - 1 - i] = temp;
//        }
//    }
//
//    public enum initialArrayState {RANDOM, ASCENDING, DESCENDING}
//
//    public enum algorithm {COMB_SORT, INSERTION_SORT, RADIX_SORT, SHAKER_SORT, SHELL_SORT}
//    Record[] arrayRandom;
//    Record[] arrayAscending;
//    Record[] arrayDescending;
//
//    private int[] arraySizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
//    private Map<algorithm, Integer> mappingRandom = new HashMap<>();
//    private Map<algorithm, Integer> mappingAscending = new HashMap<>();
//    private Map<algorithm, Integer> mappingDescending = new HashMap<>();
//    private double[][] resultRandom = new double[algorithm.values().length][arraySizes.length];
//    private double[][] resultAscending = new double[algorithm.values().length][arraySizes.length];
//    private double[][] resultDescending = new double[algorithm.values().length][arraySizes.length];
//
//
//    public int[] getArraySizes() {
//        return arraySizes;
//    }
//
//    public void setArraySizes(int[] arraySizes) {
//        this.arraySizes = arraySizes;
//    }
//
//
//    public static double measureSortTime(Record[] array, int iterations, int arraySize, algorithm algo, int radixDigitCount) {
//        long totalTime = 0;
//
//        for (int i = 0; i < iterations; i++) {
//            Record[] copy = Arrays.copyOf(array, arraySize);
//            long startTime = 0, endTime = 0;
//
//            switch (algo) {
//                case COMB_SORT:
//                    startTime = System.nanoTime();
//                    Sort.combSort(copy);
//                    endTime = System.nanoTime();
//                    break;
//
//                case RADIX_SORT:
//                    startTime = System.nanoTime();
//                    Sort.radixSort(copy, radixDigitCount);
//                    endTime = System.nanoTime();
//                    break;
//
//                case SHELL_SORT:
//                    startTime = System.nanoTime();
//                    Sort.shellSort(copy);
//                    endTime = System.nanoTime();
//                    break;
//
//                case SHAKER_SORT:
//                    startTime = System.nanoTime();
//                    Sort.shakerSort(copy);
//                    endTime = System.nanoTime();
//                    break;
//
//                case INSERTION_SORT:
//                    startTime = System.nanoTime();
//                    Sort.insertionSort(copy);
//                    endTime = System.nanoTime();
//                    break;
//            }
//
//            totalTime += (endTime - startTime);
//        }
//
//        return (totalTime / (double) iterations) / 1000000;
//    }
//
//    public static double measureSortTime(Record[] array, int iterations, int arraySize, algorithm algo) {
//        return measureSortTime(array, iterations, arraySize, algo, 9);
//    }
//
//    public void runExperiment(int iterations, initialArrayState state) {
//
//        Map<algorithm, Integer> mapping;
//        double[][] result;
//        Record[] array;
//        String title;
//
//        switch (state) {
//            case RANDOM:
//                mapping = mappingRandom;
//                result = resultRandom;
//                array = arrayRandom;
//                title =  "Random Data";
//                break;
//            case ASCENDING:
//                mapping = mappingAscending;
//                result = resultAscending;
//                array = arrayAscending;
//                title =  "Ascending Data";
//                break;
//            case DESCENDING:
//                mapping = mappingDescending;
//                result = resultDescending;
//                array = arrayDescending;
//                title =  "Descending Data";
//                break;
//            default:
//                return;
//        }
//
//        for (int i = 0; i < arraySizes.length; i++) {
//            int size = arraySizes[i];
//
//            for (int j = 0; j < algorithm.values().length; j++) {
//                algorithm algo = algorithm.values()[j];
//
//                double sortTime = measureSortTime(array, iterations, size, algo);
//                result[j][i] = sortTime;
//                mapping.put(algo, j);
//
//            }
//        }
//
//
//        try {
//            showAndSaveSortComparisonChart(title + " 1", arraySizes, result, mapping, mapping.keySet());
//            Set<algorithm> set = new HashSet<>();
//            set.add(algorithm.RADIX_SORT);
//            set.add(algorithm.COMB_SORT);
//            set.add(algorithm.SHELL_SORT);
//            showAndSaveSortComparisonChart(title + " 2", arraySizes, result, mapping, set);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public static void showAndSaveSortComparisonChart(String title, int[] xAxis, double[][] yAxis, Map<algorithm, Integer> mapping, Collection<algorithm> keySet) throws IOException {
//
//        // Create Chart
//        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
//                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();
//
//        // Convert x axis to double[]
//        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();
//
//        // Customize Chart
//        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
//        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
//
//
//        for (algorithm key : keySet) {
//            chart.addSeries(key.toString(), doubleX, yAxis[mapping.get(key)]);
//        }
//
//        // Save the chart as PNG
//        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);
//
//        // Show the chart
//        new SwingWrapper(chart).displayChart();
//    }
//
//    public void showAndSaveArrayComparisonCharts(){
//        printTable("Random Data", mappingRandom, resultRandom);
//        printTable("Ascending Data", mappingAscending, resultAscending);
//        printTable("Descending Data", mappingDescending, resultDescending);
//
//        for (algorithm algo: algorithm.values()){
//            // Create Chart
//            XYChart chart = new XYChartBuilder().width(800).height(600).title(algo.toString())
//                    .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();
//
//            // Convert x axis to double[]
//            double[] doubleX = Arrays.stream(arraySizes).asDoubleStream().toArray();
//
//            // Customize Chart
//            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
//            chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
//
//            chart.addSeries("Random", doubleX, resultRandom[mappingRandom.get(algo)]);
//            chart.addSeries("Ascending", doubleX, resultAscending[mappingAscending.get(algo)]);
//            chart.addSeries("Descending", doubleX, resultDescending[mappingDescending.get(algo)]);
//
//
//            // Save the chart as PNG
//            try {
//                BitmapEncoder.saveBitmap(chart, algo.toString() + ".png", BitmapEncoder.BitmapFormat.PNG);
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//
//            // Show the chart
//            new SwingWrapper(chart).displayChart();
//        }
//    }
//    private void printTable(String title, Map<algorithm, Integer> mapping, double[][] result) {
//        System.out.println("\n" + title);
//        System.out.print(String.format("%-17s", "Algorithm"));
//
//        for (int size : arraySizes) {
//            System.out.print(String.format("%10d", size));
//        }
//        System.out.println();
//        System.out.println(String.join("", Collections.nCopies(120, "-")));
//
//        for (algorithm algo : mapping.keySet()) {
//            int index = mapping.get(algo);
//            System.out.print(String.format("%-17s", algo));
//
//            for (double value : result[index]) {
//                System.out.print(String.format("%10.4f", value));
//            }
//            System.out.println();
//        }
//    }
//}


    //public class Sort {
//    private static <T extends Comparable<T>> void swap(T[] array, int i, int j) {
//        T temp = array[i];
//        array[i] = array[j];
//        array[j] = temp;
//    }
//
//    public static <T extends Comparable<T>> void combSort(T[] array) {
//        int gap = array.length;
//        float shrink = 1.3f;
//        boolean sorted = false;
//        while (!sorted) {
//            gap = Math.max(1, (int) Math.floor(gap / shrink));
//            sorted = (gap == 1);
//            for (int i = 0; i < array.length - gap; i++) {
//                if (array[i].compareTo(array[i+gap]) > 0){
//                    swap(array, i, i+gap);
//                    sorted = false;
//                }
//            }
//        }
//    }
//
//    public static <T extends Comparable<T>> void insertionSort(T[] array) {
//        T key;
//        int i;
//
//        for (int j = 1; j < array.length; j++) {
//            key = array[j];
//            i = j - 1;
//
//            while (i >= 0 && array[i].compareTo(key) > 0) {
//                array[i + 1] = array[i];
//                i = i - 1;
//            }
//            array[i + 1] = key;
//        }
//    }
//
//    public static <T extends Comparable<T>> void shakerSort(T[] array){
//        boolean swapped = true;
//        while (swapped){
//            swapped = false;
//            for (int i = 0; i < array.length-1; i++) {
//                if (array[i].compareTo(array[i+1]) > 0) {
//                    swap(array, i, i+1);
//                    swapped = true;
//                }
//            }
//            if (!swapped){
//                break;
//            }
//            swapped = false;
//            for (int i = array.length-2; i >= 0; i--) {
//                if (array[i].compareTo(array[i+1]) > 0){
//                    swap(array, i, i+1);
//                    swapped  = true;
//                }
//            }
//        }
//    }
//
//    public static <T extends Comparable<T>> void shellSort(T[] array){
//        int n = array.length;
//        int gap = n/2;
//        int j;
//        T key;
//
//        while (gap>0){
//
//            for (int i = gap; i < n; i++) {
//                key = array[i];
//                j = i;
//
//                while (j>= gap && array[j-gap].compareTo(key) > 0){
//                    array[j] = array[j-gap];
//                    j -= gap;
//                }
//                array[j] = key;
//            }
//            gap /= 2;
//        }
//    }
//
//    public static Record[] radixSort(Record[] array, int d){
//        for (int pos = 1; pos <= d; pos++) {
//            array = countingSort(array, pos);
//        }
//        return array;
//    }
//    private static Record[] countingSort(Record[] array, int pos) {
//        int size = array.length;
//        long digitNumber = (long) Math.pow(10, pos-1);
//        int[] count = new int[10];
//        Record[] output = new Record[size];
//
//        for (int i = 0; i < size; i++) {
//            int digit = getDigit(array[i], digitNumber);
//            count[digit]++;
//        }
//
//        for (int i = 1; i < 10; i++) {
//            count[i] += count[i - 1];
//        }
//
//        for (int i = size - 1; i >= 0; i--) {
//            int digit = getDigit(array[i], digitNumber);
//            output[count[digit] - 1] = array[i];
//            count[digit]--;
//        }
//        return output;
//    }
//    private static int getDigit(Record record, long digitNumber) {
//        long number = record.getDuration();
//        return (int) ((number / digitNumber) % 10);
//    }
//}
}