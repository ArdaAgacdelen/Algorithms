import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RecordManager {
    public RecordManager(String filePath, int maxRecordCount) {
        this.filePath = filePath;
        this.maxRecordCount = maxRecordCount;
        this.records = new Long[maxRecordCount];
    }
    public RecordManager(String filePath) {
        this(filePath, 250000);
    }
    private String filePath;
    private int maxRecordCount;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getMaxRecordCount() {
        return maxRecordCount;
    }

    public void setMaxRecordCount(int maxRecordCount) {
        this.maxRecordCount = maxRecordCount;
    }

    public Long[] getRecords() {
        return records;
    }

    public void setRecords(Long[] records) {
        this.records = records;
    }

    private Long[] records;


    public void readRecordsFromCSV() {
        int recordIndex = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            for (int i = 0; i < maxRecordCount; i++) {
                if ((line = br.readLine()) == null) break;
                if (recordIndex == maxRecordCount) {
                    System.out.println("Max record limit reached");
                    break;
                }
                String[] values = line.split(",");

                String id = values[0];
                LocalDateTime timeStamp = LocalDateTime.parse(values[1], formatter);
                long duration = Long.parseLong(values[2]);

                records[recordIndex] = duration;
                recordIndex++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
