import java.time.LocalDateTime;

public class Record implements Comparable<Record> {
    private String id;
    private LocalDateTime timeStamp;
    private long duration;

    public Record(String id, LocalDateTime timeStamp, long duration) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public int compareTo(Record that) {
        return Long.compare(this.duration, that.duration);
    }

    @Override
    public String toString() {
        return id + "  |  " + timeStamp + "  |  " + duration;
    }
}
