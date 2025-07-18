import java.util.*;
import java.io.IOException;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

public class SortAnalysis {
    public SortAnalysis(Long[] arrayRandom) {
        this.arrayRandom = arrayRandom;
    }

    public enum initialArrayState {RANDOM, ASCENDING, DESCENDING}

    public enum algorithm {COMB_SORT, INSERTION_SORT, RADIX_SORT, SHAKER_SORT, SHELL_SORT}
    Long[] arrayRandom;
    private int[] arraySizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
    private Map<algorithm, Integer> mappingRandom = new HashMap<>();
    private Map<algorithm, Integer> mappingAscending = new HashMap<>();
    private Map<algorithm, Integer> mappingDescending = new HashMap<>();
    private double[][] resultRandom = new double[algorithm.values().length][arraySizes.length];
    private double[][] resultAscending = new double[algorithm.values().length][arraySizes.length];
    private double[][] resultDescending = new double[algorithm.values().length][arraySizes.length];


    public int[] getArraySizes() {
        return arraySizes;
    }

    public void setArraySizes(int[] arraySizes) {
        this.arraySizes = arraySizes;
    }


    public static double measureSortTime(Long[] array, int iterations, int arraySize, algorithm algo, int radixDigitCount, initialArrayState state) {
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {
            Long[] copy = Arrays.copyOf(array, arraySize);
            long startTime = 0, endTime = 0;
            switch (state){
                case ASCENDING:
                    Arrays.sort(copy);
                    break;
                case DESCENDING:
                    Arrays.sort(copy, Collections.reverseOrder());
                    break;
            }

            switch (algo) {
                case COMB_SORT:
                    startTime = System.nanoTime();
                    Sort.combSort(copy);
                    endTime = System.nanoTime();
                    break;

                case RADIX_SORT:
                    startTime = System.nanoTime();
                    Sort.radixSort(copy, radixDigitCount);
                    endTime = System.nanoTime();
                    break;

                case SHELL_SORT:
                    startTime = System.nanoTime();
                    Sort.shellSort(copy);
                    endTime = System.nanoTime();
                    break;

                case SHAKER_SORT:
                    startTime = System.nanoTime();
                    Sort.shakerSort(copy);
                    endTime = System.nanoTime();
                    break;

                case INSERTION_SORT:
                    startTime = System.nanoTime();
                    Sort.insertionSort(copy);
                    endTime = System.nanoTime();
                    break;
            }

            totalTime += (endTime - startTime);
        }

        return (totalTime / (double) iterations) / 1000000;
    }

    public static double measureSortTime(Long[] array, int iterations, int arraySize, algorithm algo, initialArrayState state) {
        return measureSortTime(array, iterations, arraySize, algo, 9, state);
    }

    public void runExperiment(int iterations, initialArrayState state) {

        Map<algorithm, Integer> mapping;
        double[][] result;
        Long[] array = arrayRandom;
        String title;

        switch (state) {
            case RANDOM:
                mapping = mappingRandom;
                result = resultRandom;
                title =  "Random Data";
                break;
            case ASCENDING:
                mapping = mappingAscending;
                result = resultAscending;
                title =  "Ascending Data";
                break;
            case DESCENDING:
                mapping = mappingDescending;
                result = resultDescending;
                title =  "Descending Data";
                break;
            default:
                return;
        }

        for (int i = 0; i < arraySizes.length; i++) {
            int size = arraySizes[i];

            for (int j = 0; j < algorithm.values().length; j++) {
                algorithm algo = algorithm.values()[j];

                double sortTime = measureSortTime(array, iterations, size, algo, state);
                result[j][i] = sortTime;
                mapping.put(algo, j);

            }
        }


        try {
            showAndSaveSortComparisonChart(title + " 1", arraySizes, result, mapping, mapping.keySet());
            Set<algorithm> set = new HashSet<>();
            set.add(algorithm.RADIX_SORT);
            set.add(algorithm.COMB_SORT);
            set.add(algorithm.SHELL_SORT);
            showAndSaveSortComparisonChart(title + " 2", arraySizes, result, mapping, set);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void showAndSaveSortComparisonChart(String title, int[] xAxis, double[][] yAxis, Map<algorithm, Integer> mapping, Collection<algorithm> keySet) throws IOException {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);


        for (algorithm key : keySet) {
            chart.addSeries(key.toString(), doubleX, yAxis[mapping.get(key)]);
        }

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }

    public void showAndSaveArrayComparisonCharts(){
        printTable("Random Data", mappingRandom, resultRandom);
        printTable("Ascending Data", mappingAscending, resultAscending);
        printTable("Descending Data", mappingDescending, resultDescending);

        for (algorithm algo: algorithm.values()){
            // Create Chart
            XYChart chart = new XYChartBuilder().width(800).height(600).title(algo.toString())
                    .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

            // Convert x axis to double[]
            double[] doubleX = Arrays.stream(arraySizes).asDoubleStream().toArray();

            // Customize Chart
            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
            chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

            chart.addSeries("Random", doubleX, resultRandom[mappingRandom.get(algo)]);
            chart.addSeries("Ascending", doubleX, resultAscending[mappingAscending.get(algo)]);
            chart.addSeries("Descending", doubleX, resultDescending[mappingDescending.get(algo)]);


            // Save the chart as PNG
            try {
                BitmapEncoder.saveBitmap(chart, algo.toString() + ".png", BitmapEncoder.BitmapFormat.PNG);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            // Show the chart
            new SwingWrapper(chart).displayChart();
        }
    }
    private void printTable(String title, Map<algorithm, Integer> mapping, double[][] result) {
        System.out.println("\n" + title);
        System.out.print(String.format("%-17s", "Algorithm"));

        for (int size : arraySizes) {
            System.out.print(String.format("%10d", size));
        }
        System.out.println();
        System.out.println(String.join("", Collections.nCopies(120, "-")));

        for (algorithm algo : mapping.keySet()) {
            int index = mapping.get(algo);
            System.out.print(String.format("%-17s", algo));

            for (double value : result[index]) {
                System.out.print(String.format("%10.4f", value));
            }
            System.out.println();
        }
    }
}