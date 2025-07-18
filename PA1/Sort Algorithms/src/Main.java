class Main {
    public static void main(String[] args) {
        RecordManager recordManager = new RecordManager("TrafficFlowDataset.csv");
        recordManager.readRecordsFromCSV();
        Long[] records = recordManager.getRecords();

        SortAnalysis sortAnalysis = new SortAnalysis(records);
        sortAnalysis.runExperiment(10, SortAnalysis.initialArrayState.RANDOM);
        sortAnalysis.runExperiment(10, SortAnalysis.initialArrayState.ASCENDING);
        sortAnalysis.runExperiment(10, SortAnalysis.initialArrayState.DESCENDING);
        sortAnalysis.showAndSaveArrayComparisonCharts();
    }
}